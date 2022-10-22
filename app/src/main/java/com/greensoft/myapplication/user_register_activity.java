package com.greensoft.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.greensoft.myapplication.jsony.shared_persistence;

public class user_register_activity extends AppCompatActivity  {

    private EditText First_name;
    private EditText Last_name;
    private EditText Age;
    private EditText Weight;
    private EditText Bloodpressure;
    private EditText Email;
    private EditText Phone_number;
    private EditText Existing_illness;
    private EditText Location;
    private EditText Latitude;
    private EditText Longitude;
    private EditText password;
    private EditText password_again;
    private Button btn_save;
    private Button search_location_before, search_location_after;
    private LinearLayout man_of_view;
    private GpsTracker gpsTracker;
    // Create the Handler object (on the main thread by default)
    private  Handler handler = new Handler();
    private  Runnable runnableCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        man_of_view = findViewById(R.id.man_of_view);
        First_name = findViewById(R.id.et_reg_patient_name);
        Last_name = findViewById(R.id.et_reg_lastname);
        Age = findViewById(R.id.et_reg_age);
        Weight = findViewById(R.id.et_reg_weight);
        Bloodpressure = findViewById(R.id.et_reg_BP);
        Email = findViewById(R.id.et_reg_email);
        Phone_number = findViewById(R.id.et_reg_phonenumber);
        Existing_illness = findViewById(R.id.et_reg_existing_condition);
        Location = findViewById(R.id.et_reg_location);
        Latitude = findViewById(R.id.et_reg_latitude);
        Longitude = findViewById(R.id.et_reg_longitude);
        password = findViewById(R.id.et_reg_password);
        password_again = findViewById(R.id.et_reg_password_repeat);
        btn_save = findViewById(R.id.pro_save);
        search_location_before = findViewById(R.id.search_btn_before);
        search_location_after = findViewById(R.id.search_btn_after);
        search_location_after.setVisibility(View.GONE);
        btn_save.setEnabled(false);
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        search_location_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  search_location_after.setVisibility(View.VISIBLE);
                //search_location_before.setVisibility(View.GONE);
                start_location_capture();
                animate_loc_search_b4();
            }
        });
        search_location_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_location_capture();
                animate_loc_search_afa();

            }
        });


        if (Existing_illness.getText().toString().isEmpty()) {
            Existing_illness.setText("None");
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_validation email = new email_validation();
                boolean validation = email.isValid(Email.getText().toString());
                if (First_name.getText().toString().isEmpty() || Last_name.getText().toString().isEmpty()
                        || Age.getText().toString().isEmpty() || Weight.getText().toString().isEmpty()
                        || Bloodpressure.getText().toString().isEmpty() || Email.getText().toString().isEmpty()
                        || Phone_number.getText().toString().isEmpty() || Existing_illness.getText().toString().isEmpty()
                        || Location.getText().toString().isEmpty() || Latitude.getText().toString().isEmpty()
                        || Longitude.getText().toString().isEmpty() || password.getText().toString().isEmpty()
                        || password_again.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All field must be filled", Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.man_of_view));
                    return;
                }
                if (password.equals(password_again)) {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.et_reg_password));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.et_reg_password_repeat));
                    return;
                }
                if (password.length() < 8 || password_again.length() < 8) {
                    Toast.makeText(getApplicationContext(), "8 or more characters for passwords", Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.et_reg_password));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.et_reg_password_repeat));
                    return;
                }
                if (!validation) {
                    Toast.makeText(getApplicationContext(), "The Email is not valid", Toast.LENGTH_SHORT).show();
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.et_reg_email));
                    return;
                }

                shared_persistence shad = new shared_persistence();
                shad.setFirst_name(First_name.getText().toString());
                shad.setLast_name(Last_name.getText().toString());
                shad.setAge(Integer.parseInt(Age.getText().toString()));
                shad.setWeight(Float.parseFloat(Weight.getText().toString()));
                shad.setBloodpressure(Float.parseFloat(Bloodpressure.getText().toString()));
                shad.setEmail(Email.getText().toString());
                shad.setPhone_number(Phone_number.getText().toString());
                shad.setExisting_illness(Existing_illness.getText().toString());
                shad.setLocation(Location.getText().toString());
                shad.setLatitude(Float.parseFloat(Latitude.getText().toString()));
                shad.setLongitude(Float.parseFloat(Longitude.getText().toString()));
                shad.setPassword(password.getText().toString());
                shad.patient_register(getApplicationContext());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (Context_maker.getInstance().isRegister_success()) {
                            Toast.makeText(getApplicationContext(), Context_maker.getInstance().isRegister_success() + "", Toast.LENGTH_LONG).show();
                            Intent go_to_logim_page = new Intent(getApplicationContext(), user_login_activity.class);
                            startActivity(go_to_logim_page);
                            finish();
                        }


                    }
                }, 500);


            }
        });

    }

    public void start_location_capture(){


        // Define the code block to be executed
         runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object

                gpsTracker = new GpsTracker(user_register_activity.this);
                if(gpsTracker.canGetLocation()){
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    if(latitude > 0.0 || longitude > 0.0){
                        Latitude.setText(String.valueOf(latitude));
                        Longitude.setText(String.valueOf(longitude));
                        search_location_after.setVisibility(View.VISIBLE);
                        search_location_before.setVisibility(View.GONE);
                        btn_save.setEnabled(true);
                        stop_runnable();

                    }else {
                        animate_loc_search_afa();
                        animate_loc_search_b4();
                    }
                }else{
                    gpsTracker.showSettingsAlert();
                   stop_runnable();
                }

                handler.postDelayed(this, 3000);
            }
        };
         // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);



    }
public void stop_runnable(){
    handler.removeCallbacks(runnableCode);
}


    public void Register_user(View view) {

        Toast.makeText(this, "register", Toast.LENGTH_SHORT).show();

    }
    public void animate_loc_search_b4(){
        YoYo.with(Techniques.Bounce)
                .duration(1000)
                .playOn(findViewById(R.id.search_btn_before));
    }
    public void animate_loc_search_afa(){
        YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(findViewById(R.id.search_btn_after));
    }

    public void goto_login(View view) {
        Intent goto_login = new Intent(this, user_login_activity.class);
        startActivity(goto_login);
        finish();
    }





}