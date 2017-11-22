package com.example.dre.emergencybutton;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;


public class Signup extends AppCompatActivity {

    private TextView tanggalLahir;
    private DatePickerDialog bornDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Kotak anu
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
    }

    //Tanggal lahir stuff
    private void findViewsById(){
        tanggalLahir = (TextView) findViewById(R.id.tanggalLahir);

    }

    private void setDateTimeField(){
        Calendar newCalendar = Calendar.getInstance();
        bornDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tanggalLahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }



    //OnClick Handler dibawah

    public void showBornDate(View view){
        bornDatePickerDialog.show();
    }


    //Submit Form stuff
    public void submitForm(View view) {

        EditText nama = (EditText) findViewById(R.id.nama);
        String nama_text = nama.getText().toString();

        TextView tanggalLahir = (TextView)findViewById(R.id.tanggalLahir);
        String tanggalLahir_text = tanggalLahir.getText().toString();

        RadioButton laki = (RadioButton) findViewById(R.id.laki);
        RadioButton perempuan = (RadioButton) findViewById(R.id.perempuan);

        Spinner golDar = (Spinner) findViewById(R.id.spinner_golDar);
        String golDar_text = golDar.getSelectedItem().toString();

        EditText email = (EditText) findViewById(R.id.email);
        String email_text = email.getText().toString();

        EditText noHP = (EditText) findViewById(R.id.noHP);
        String noHP_text = noHP.getText().toString();

        EditText password = (EditText) findViewById(R.id.password);
        String password_text = password.getText().toString();

        EditText noHPLain = (EditText) findViewById(R.id.noHPLain);
        String noHPLain_text = noHPLain.getText().toString();

        EditText noKTP = (EditText) findViewById(R.id.noKTP);
        String noKTP_text = noKTP.getText().toString();



        if(nama_text.isEmpty() || email_text.isEmpty() || noHP_text.isEmpty() || password_text.isEmpty() || noHPLain_text.isEmpty() || noKTP_text.isEmpty() || tanggalLahir_text.isEmpty()){
                AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please Fill All the Fields");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else{
            //Kirim status first time
            SharedPreferences sharedPref = getSharedPreferences("status", Context.MODE_PRIVATE);
            SharedPreferences sharedPref1 = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            SharedPreferences.Editor editor1 = sharedPref1.edit();

            editor.putString("firstTime", "false");
            editor.putString("showHowToUse", "true");
            editor1.putString("namaUser", nama_text);
            editor.apply();
            editor1.apply();

            Intent startNewActivity = new Intent(this, MainMenu.class);
            startActivity(startNewActivity);
            this.finish();
        }
    }

}