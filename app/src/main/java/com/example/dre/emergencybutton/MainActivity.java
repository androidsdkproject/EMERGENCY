package com.example.dre.emergencybutton;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1100;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPref = getSharedPreferences("status", Context.MODE_PRIVATE);
                String status= sharedPref.getString("firstTime", "");

                if(status.equals("false")){
                    Intent startNewActivity = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(startNewActivity);
                    finish();
                }
                else{
                    Intent startNewActivity = new Intent(MainActivity.this, Signup.class);
                    startActivity(startNewActivity);
                    finish();
            }

            }
        }, SPLASH_TIME_OUT);
    }
}