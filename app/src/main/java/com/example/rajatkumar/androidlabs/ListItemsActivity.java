package com.example.rajatkumar.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "in onCreate(): ");
        ImageButton btn3 = (ImageButton) findViewById(R.id.imageButton);
        Switch switch2 = (Switch)findViewById(R.id.switch1);
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkb);

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CharSequence text =getResources().getString(R.string.switchOn);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                    toast.show();
                }
                else if (!isChecked){
                    CharSequence text =  getResources().getString(R.string.switchOff);
//      Log.i("demo", "pressed cancel button");
                                    checkBox.setChecked(false);
                                    int duration = Toast.LENGTH_LONG;
                                    Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                                    toast.show();
                                }
                            }});

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                            builder.setMessage(R.string.dialog_message)
                                    .setTitle(R.string.dialog_title)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent resultIntent = new Intent();
                                            resultIntent.putExtra("Response", "Here is my response");
                                            setResult(Activity.RESULT_OK, resultIntent);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            checkBox.setChecked(false);
                                }
                            })
                            .show();
                }}});


        btn3.setOnClickListener(e -> {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 60);
                }

            });
        }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "in onResume(): ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME,  "in onStart(): ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "in onPause(): ");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "in onStop(): ");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "in onDestroy(): ");

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 60 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton btn3 = (ImageButton) findViewById(R.id.imageButton);
            btn3.setImageBitmap(imageBitmap);
        }
    }


}
