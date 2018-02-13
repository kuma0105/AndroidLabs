package com.example.rajatkumar.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";
public static final String file = "userFile";
    private static final String userName = "loginText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "in onCreate(): ");

   Button btn = (Button) findViewById(R.id.button2);

    EditText loginName = (EditText) findViewById(R.id.editText1);


        SharedPreferences sharedPref = getSharedPreferences( file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


loginName.setText(sharedPref.getString(userName, "email@domain.com"));
        btn.setOnClickListener(e-> {

            editor.putString(userName, loginName.getText().toString());
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
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


}
