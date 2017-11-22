package com.example.dre.emergencybutton;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

import java.util.HashMap;
import java.util.List;


public class MainMenu extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 13;
    public static final int PROXIMITY_RADIUS = 10000;
    List<HashMap<String, String>> nearbyPlacesList;
    double latitude;
    double longitude;
    private GoogleMap map;
    UiSettings mapSettings;

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCRVlmzVHBnRykS5EgcfsOPA8Q8UgcKa3o");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //Inisialisasi
        SharedPreferences sharedPref = getSharedPreferences("status", Context.MODE_PRIVATE);
        SharedPreferences sharedPref1 = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String current_user = sharedPref1.getString("namaUser", "");
        TextView haloUser = (TextView) findViewById(R.id.haloUserText);
        haloUser.setText("Halo, " + current_user);


        Button redButton=(Button) findViewById(R.id.redButton);
        Button howToUse=(Button) findViewById(R.id.howToButton);
        Button sendFeedback=(Button) findViewById(R.id.feedbackButton);

        final RadioButton kantorPolisi = (RadioButton) findViewById(R.id.kantorPolisi);
        final RadioButton rumahSakit = (RadioButton) findViewById(R.id.rumahSakit);
        final RadioButton pemadamKebakaran = (RadioButton) findViewById(R.id.pemadamKebakaran);
        final int REQUEST_PHONE_CALL = 1;

        //Ngeluarin notif
        createNotification();

        //Cek show how to use
        String firstTimeHowTo = sharedPref.getString("showHowToUse", "");

        if (firstTimeHowTo.equals("true")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
            View dialog_howto = getLayoutInflater().inflate(R.layout.dialog_howto, null);

            builder.setView(dialog_howto);
            AlertDialog dialog = builder.create();
            dialog.show();

            editor.putString("showHowToUse", "false");
            editor.apply();
        }

        //Kalo dipencet tombol merahnya
        redButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(kantorPolisi.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                    View viewDialog = getLayoutInflater().inflate(R.layout.dialog_kantorpolisi, null);

                    Button callPhone = viewDialog.findViewById(R.id.button_callPhone);

                    String url = getUrl(latitude, longitude,"hospital");
                    Object[] DataTransfer = new Object[3];
                    DataTransfer[0] = url;
                    GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(latitude,longitude);
                    getNearbyPlacesData.execute(DataTransfer);


                    //BUG :: Kalo di set ACTION_CALL crash
                    callPhone.setOnClickListener(new View.OnClickListener(){
                        public void onClick (View view){


                            Intent call = new Intent(Intent.ACTION_CALL);
                            call.setData(Uri.parse("tel:(0542) 11102"));

                            int checkPermission = ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.CALL_PHONE);
                            if(checkPermission != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(
                                        MainMenu.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        REQUEST_PHONE_CALL);
                            }
                            else{
                                startActivity(call);
                            }
                        }
                    });

                    builder.setView(viewDialog);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else if(rumahSakit.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                    View viewDialog = getLayoutInflater().inflate(R.layout.dialog_rumahsakit, null);

                    Button callPhone = viewDialog.findViewById(R.id.button_callPhone);

                    callPhone.setOnClickListener(new View.OnClickListener(){
                        public void onClick (View view){
                            Intent call = new Intent(Intent.ACTION_CALL);
                            call.setData(Uri.parse("tel:(021) 29309999"));

                            int checkPermission = ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.CALL_PHONE);
                            if(checkPermission != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(
                                        MainMenu.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        REQUEST_PHONE_CALL);
                            }
                            else{
                                startActivity(call);
                            }
                        }
                    });

                    builder.setView(viewDialog);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(pemadamKebakaran.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                    View viewDialog = getLayoutInflater().inflate(R.layout.dialog_damkar, null);

                    Button callPhone = viewDialog.findViewById(R.id.button_callPhone);

                    callPhone.setOnClickListener(new View.OnClickListener(){
                        public void onClick (View view){
                            Intent call = new Intent(Intent.ACTION_CALL);
                            call.setData(Uri.parse("tel:(021) 29177377"));

                            int checkPermission = ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.CALL_PHONE);
                            if(checkPermission != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(
                                        MainMenu.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        REQUEST_PHONE_CALL);
                            }
                            else{
                                startActivity(call);
                            }
                        }
                    });

                    builder.setView(viewDialog);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

        //Button cara penggunaan
        howToUse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                View dialog_howto = getLayoutInflater().inflate(R.layout.dialog_howto, null);

                builder.setView(dialog_howto);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        //Button send feedback
        sendFeedback.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_sendfeedback, null);

                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();

                Button submitFeedback = (Button) viewDialog.findViewById(R.id.button_sendFeedback);

                submitFeedback.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainMenu.this,
                                "Thank you for your feedback !",
                                Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    //Notification Stuff
    public void createNotification(){
        Intent intent = new Intent(this, MainMenu.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainMenu.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Emergency Button is Running. Press Here to Call Help")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }
}
