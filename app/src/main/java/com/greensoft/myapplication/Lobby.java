package com.greensoft.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.greensoft.myapplication.jsony.connectionsManager;
import com.greensoft.myapplication.jsony.shared_persistence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Lobby extends AppCompatActivity {
  connectionsManager conman= new connectionsManager();
    Handler handler = new Handler();
    Runnable runnableCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Context_maker.getInstance().setMyContext(getApplicationContext());
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(getApplicationContext());
        //Context_maker.getInstance().check_verified(shad.getEmail(),shad.getFirst_name());


        // Define the code block to be executed
         runnableCode = new Runnable() {
            @Override
            public void run() {

                check_verified();
                handler.postDelayed(this, 2000);
            }
        };

        handler.post(runnableCode);





    }

    public void check_verified() {

        shared_persistence shad = new shared_persistence();
        shad.get_user_data(getApplicationContext());

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_login_ver(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(status.equals("success"))
                    {

                        int Verified = Integer.parseInt(jsonObject.getString("Verified"));

                        if(Verified==0){
                           // Toast.makeText(getApplicationContext(),"unverified",Toast.LENGTH_SHORT).show();
                            Log.d("loom", "unverified");


                        }else if(Verified==1){
                            //Toast.makeText(getApplicationContext(),"verified",Toast.LENGTH_SHORT).show();
                            Log.d("loom", "verified");
                            // Removes pending code execution
                            handler.removeCallbacks(runnableCode);
                            Intent gulag_release = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(gulag_release);
                            finish();


                        }else{
                           // Toast.makeText(getApplicationContext(),"anything else",Toast.LENGTH_SHORT).show();
                            Log.d("loom", "anything else");
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Context_maker.getInstance().setLogin_success(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email",shad.getEmail());
                params.put("First_name",shad.getFirst_name());

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onDestroy() {

        // Removes pending code execution
        handler.removeCallbacks(runnableCode);

        super.onDestroy();
    }

    int back_add=0;
    @Override
    public void onBackPressed() {
        // Example of logic

        if ( back_add < 1 ) {
            // Removes pending code execution
            handler.removeCallbacks(runnableCode);
            //Toast.makeText(this, "The second time will close the window", Toast.LENGTH_SHORT).show();
            Intent openActivity = new Intent(Lobby.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

}