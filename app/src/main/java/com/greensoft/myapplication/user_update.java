package com.greensoft.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.greensoft.myapplication.emergency.emergency_call;
import com.greensoft.myapplication.jsony.connectionsManager;
import com.greensoft.myapplication.jsony.shared_persistence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class user_update extends AppCompatActivity {
    EditText firstname_,lastname_,age_,weight_,bp_,email_,phone_number_
             ,existing_illnes_,location_,longitude_,latitude_,oldpass_
             ,newpass_,newpass_repeat_,membership_num_ ;
    int the_log_count=5;

    private Handler handler = new Handler();
    private  Runnable runnableCode;

    private Handler handler_log_out = new Handler();
    private  Runnable runnableCode_log_out;

    private GpsTracker gpsTracker;
    private Button user_update,password_update;

    private Button search_u_location_after;
    private Button search_u_location_before,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        //for user data update
        firstname_ = findViewById(R.id.et_up_firstname);
        lastname_ = findViewById(R.id.et_up_last_name);
        age_ = findViewById(R.id.et_u_age);
        weight_ = findViewById(R.id.et_up_weight);
        bp_ = findViewById(R.id.et_up_bp);
        email_ = findViewById(R.id.et_up_email);
        phone_number_ = findViewById(R.id.et_up_phone);
        existing_illnes_ = findViewById(R.id.et_up_exillnes);
        location_ = findViewById(R.id.et_up_location);
        longitude_ = findViewById(R.id.et_up_longitude);
        latitude_ = findViewById(R.id.et_up_latitude);
        membership_num_ = findViewById(R.id.et_up_membership);
        //for password update
        oldpass_ = findViewById(R.id.et_u_oldpassword);
        newpass_ = findViewById(R.id.et_u_newpassword);
        newpass_repeat_ = findViewById(R.id.et_u_newpassword_again);
        user_update = findViewById(R.id.pro_u_save);
        password_update = findViewById(R.id.update_password);

        search_u_location_after = findViewById(R.id.search_u_location_after);
        search_u_location_before = findViewById(R.id.search_u_location_before);
        user_update.setEnabled(false);
        search_u_location_after.setVisibility(View.GONE);
        Context_maker.getInstance().setMyContext(getApplicationContext());

        search_u_location_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               start_location_capture();
            }
        });
        search_u_location_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_location_capture();
            }
        });

         pull_data();

    }

    private void pull_data() {
        connectionsManager conman  = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_data_pull(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                   // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(status.equals("ok"))
                    {


                        // Toast.makeText(context, "successsfull", Toast.LENGTH_SHORT).show();
                        int UU_ID = Integer.parseInt(jsonObject.getString("UU_ID"));
                        String First_name =jsonObject.getString("First_name");
                        String Last_name =jsonObject.getString("Last_name");
                        int Age = Integer.parseInt(jsonObject.getString("Age"));
                        float Weight = Float.parseFloat( jsonObject.getString("Weight"));
                        float Bloodpressure = Float.parseFloat(jsonObject.getString("Bloodpressure"));
                        String Email =jsonObject.getString("Email");
                        String Phone_number =jsonObject.getString("Phone_number");
                        String Existing_illness =jsonObject.getString("Existing_illness");
                        String Location =jsonObject.getString("Location");
                        String Prescription =jsonObject.getString("Prescription");
                        int Verified = Integer.parseInt(jsonObject.getString("Verified"));
                        Double Latitude = Double.parseDouble( jsonObject.getString("Latitude"));
                        Double Longitude = Double.parseDouble( jsonObject.getString("Longitude"));
                        String membership =  jsonObject.getString("Membership_number");

                        firstname_.setText(First_name);
                        lastname_.setText(Last_name);
                        age_.setText(Age+"");
                        weight_.setText(Weight+"");
                        bp_.setText(Bloodpressure+"");
                        email_.setText(Email);
                        phone_number_.setText(Phone_number+"");
                        existing_illnes_.setText(Existing_illness);
                        location_.setText(Location);
                        longitude_.setText(Longitude+"");
                        latitude_.setText(Latitude+"");
                        membership_num_.setText(membership);



                    }else {
                        Toast.makeText(getApplicationContext(),message+"",Toast.LENGTH_LONG).show();
                        //Context_maker.getInstance().setLogin_success(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"" + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                shared_persistence shad = new shared_persistence();
                shad.get_user_data(getApplicationContext());
                params.put("UU_ID",shad.getUU_ID()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }


    public void update_user(View view) {

        if(!firstname_.getText().toString().isEmpty() || !lastname_.getText().toString().isEmpty()|| !age_.getText().toString().isEmpty()
          || !weight_.getText().toString().isEmpty()|| !bp_.getText().toString().isEmpty()|| !email_.getText().toString().isEmpty()
           || !phone_number_.getText().toString().isEmpty()|| !existing_illnes_.getText().toString().isEmpty()|| !location_.getText().toString().isEmpty()
                || !longitude_.getText().toString().isEmpty()|| !latitude_.getText().toString().isEmpty() || !membership_num_.getText().toString().isEmpty())
        {
            shared_persistence sha = new shared_persistence();
            sha.update_user(getApplicationContext()
                    ,firstname_.getText().toString()
                    ,lastname_.getText().toString()
                    ,Integer.parseInt(age_.getText().toString())
                    ,Double.parseDouble(weight_.getText().toString())
                    ,Double.parseDouble(bp_.getText().toString())
                    ,email_.getText().toString()
                    ,phone_number_.getText().toString()
                    ,existing_illnes_.getText().toString()
                    ,location_.getText().toString()
                    ,Double.parseDouble(longitude_.getText().toString())
                    ,Double.parseDouble(latitude_.getText().toString())
                    ,membership_num_.getText().toString()
                    );

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    pull_data();
                }
            }, 1000);


        }else {
            Toast.makeText(user_update.this,"All fields should not be empty",Toast.LENGTH_SHORT).show();
            return;
        }


    }

    public void update_pass(View view) {



        if(oldpass_.getText().toString().isEmpty() || newpass_.getText().toString().isEmpty()|| newpass_repeat_.getText().toString().isEmpty())
        {
            Toast.makeText(user_update.this,"All password fields should not be empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(oldpass_.getText().toString().length() < 8 || newpass_.getText().toString().length() < 8 || newpass_repeat_.getText().toString().length() < 8)
        {
            Toast.makeText(user_update.this,"All passwords should longer than 8 characters ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(newpass_.getText().toString().equals(newpass_repeat_.getText().toString().isEmpty())){
            Toast.makeText(user_update.this,"Passwords don't match",Toast.LENGTH_SHORT).show();
            return;
        }
       //Toast.makeText(user_update.this,"GOOD TO GO",Toast.LENGTH_SHORT).show();

        shared_persistence shad = new shared_persistence();
        shad.change_password(getApplicationContext(),oldpass_.getText().toString(),newpass_.getText().toString());


        runnableCode_log_out = new Runnable() {
            @Override
            public void run() {
                    if(Context_maker.getInstance().isLogout_success()== true){

                        Toast.makeText(user_update.this,"GOT FEEDBACK",Toast.LENGTH_SHORT).show();
                        stop_search_for_logout();
                        password_update.setText("Logingout in "+ the_log_count);
                        if(the_log_count == 0){
                            Context_maker.getInstance().setLogout_success(false);
                        Gurdian gud = new Gurdian();
                        shad.clear_json(getApplicationContext());
                        shad.clear_user(getApplicationContext());
                        gud.clear_gurds(getApplicationContext());
                        shared_persistence shad = new shared_persistence();
                        if(!shad.get_json(getApplicationContext()).isEmpty())
                        {
                            shad.patient_backup_prescription(getApplicationContext());
                        }
                            stop_search_for_logout() ;
                        Intent intent = new Intent(getApplicationContext(), user_login_activity.class );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getApplicationContext().startActivity(intent);
                        finish();
                        Toast.makeText(user_update.this,"logged out",Toast.LENGTH_SHORT).show();
                        }
                        the_log_count--;


                       // finish();
                    }else {
                        Toast.makeText(user_update.this,"Waiting for feedback",Toast.LENGTH_SHORT).show();
                    }

                handler_log_out.postDelayed(this, 1000);
            }
        };

        handler_log_out.post(runnableCode_log_out);
    }

    public void stop_search_for_logout(){

        handler_log_out.removeCallbacks(runnableCode_log_out);

    }
    public void shake_update_body(){
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(findViewById(R.id.u_update_body));
    }

    public void start_location_capture(){


        // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object

                gpsTracker = new GpsTracker(user_update.this);
                if(gpsTracker.canGetLocation()){
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    if(latitude > 0.0 || longitude > 0.0){
                        latitude_.setText(String.valueOf(latitude));
                        longitude_.setText(String.valueOf(longitude));
                        search_u_location_after.setVisibility(View.VISIBLE);
                        search_u_location_before.setVisibility(View.GONE);
                        user_update.setEnabled(true);
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

    private void stop_runnable() {

        handler.removeCallbacks(runnableCode);
    }
    public void animate_loc_search_b4(){
        YoYo.with(Techniques.Bounce)
                .duration(1000)
                .playOn(findViewById(R.id.search_u_location_after));
    }
    public void animate_loc_search_afa(){
        YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn(findViewById(R.id.search_u_location_before));
    }


    int back_add=0;
    @Override
    public void onBackPressed() {
        // Example of logic
        if ( back_add < 1 ) {

            //Toast.makeText(this, "The second time will close the window", Toast.LENGTH_SHORT).show();
            Intent openActivity = new Intent(user_update.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }


}