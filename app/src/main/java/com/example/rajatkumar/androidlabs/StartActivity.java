package com.example.rajatkumar.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "in onCreate(): ");
        Button btn2 = (Button) findViewById(R.id.button) ;
        Button buttonChat = (Button) findViewById(R.id.buttonChat);

        buttonChat.setOnClickListener(e ->{
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            Intent chatIntent = new Intent(StartActivity.this, ChatWindow.class);
            startActivity(chatIntent);
        });

        btn2.setOnClickListener(e -> {

        Intent secondIntent = new Intent(StartActivity.this, ListItemsActivity.class);
        startActivityForResult(secondIntent, 50);

        }  );

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
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data)
    {
        if(resultCode == 50)
        {
            resultCode = 50;
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        else if (resultCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
        Toast toast =    Toast.makeText(StartActivity.this, messagePassed, Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
