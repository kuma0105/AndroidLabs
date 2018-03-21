package com.example.rajatkumar.androidlabs;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static java.lang.System.in;

public class WeatherForecast extends Activity {

    ImageView image;
    TextView cTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView wind;
    ProgressBar bar;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        image = (ImageView )findViewById(R.id.weatherImage);
        cTemp = (TextView) findViewById(R.id.currentTemp);
        minTemp = (TextView) findViewById(R.id.minTemp);
        maxTemp = (TextView) findViewById(R.id.maxTemp);
        wind = (TextView) findViewById(R.id.windSpeed);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);

        ForecastQuery weather = new ForecastQuery();
        weather.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");

    }

    public class ForecastQuery extends AsyncTask<String, Integer , String>{
        public String currentTemp;
        public String minimumTemp;
        public String maximumTemp;
        public String windSpeed;
        public Bitmap bitmap;
        public String iconName;
        public String ImageURL;
        HTTPUtils http = new HTTPUtils();
        protected String doInBackground(String ...args){
            URL url = null;
            HttpURLConnection conn = null;
            try {
                for(String myURL: args) {
                    url = new URL(myURL);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 );
                    conn.setConnectTimeout(15000 );
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xml = factory.newPullParser();
                    xml.setInput(conn.getInputStream(),"UTF-8");
                    int type;

                    while( (type = xml.getEventType()) != XmlPullParser.END_DOCUMENT){
                        if (xml.getEventType() == xml.START_TAG){
                            if(xml.getName().equals("temperature")){
                                currentTemp= xml.getAttributeValue(null, "value");
                                publishProgress(25);
                                Log.i("current temprature", currentTemp);
                                minimumTemp = xml.getAttributeValue(null, "min");
                                publishProgress(50);
                                Log.i("Minimum temprature", minimumTemp);
                                maximumTemp = xml.getAttributeValue(null, "max");
                                publishProgress(75);
                                Log.i("Maximum temprature", maximumTemp);
                            }
                            else if (xml.getName().equals("speed")){
                                windSpeed = xml.getAttributeValue(null, "value");
                                Log.i("Wind speed", windSpeed);
                            }
                            else if (xml.getName().equals("weather")){
                                iconName = xml.getAttributeValue(null, "icon");
                                ImageURL=  "http://openweathermap.org/img/w/" + iconName + ".png";
                                publishProgress(100);
                            }
                       }
                        xml.next();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error", e.getMessage());
            }
            if(fileExistance(iconName+".png")==true) {
                FileInputStream fis = null;
                try {
                    fis = openFileInput(iconName + ".png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap = BitmapFactory.decodeStream(fis);
                Log.i("Finding image in files","Looking for "+iconName+".png");
                Log.i("Weather Image", "Opening the image from Local file directory");
            }
            else {
                FileOutputStream outputStream = null;
                try {
                    bitmap = http.getImage(ImageURL);
                    Log.i("Finding image online","Looking for "+iconName+".png");
                    Log.i("Weather Image", "Downloading the image online");
                    outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return "finished";
        }
        public void onProgressUpdate(Integer ...data){
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(data[0]);
        }

        public void onPostExecute(String result){
            bar.setVisibility(View.INVISIBLE);
            cTemp.setText("current Temperature "+currentTemp+"C");
            minTemp.setText("minimum Temperature "+minimumTemp+"C");
            maxTemp.setText("maximum Temperature "+maximumTemp+"C");
            wind.setText("wind Speed "+windSpeed);
            image.setImageBitmap(bitmap);
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }
    }



     public class HTTPUtils {
        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }


    }

}
