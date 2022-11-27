package com.greensoft.myapplication.jsony;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.Gurdian;
import com.greensoft.myapplication.Lobby;
import com.greensoft.myapplication.notifcation_monolith.The_alarm_man;
import com.greensoft.myapplication.user_register_activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class shared_persistence {

    private final connectionsManager conman = new connectionsManager();
    private int UU_ID;
    private String First_name;
    private String Last_name;
    private int Age;
    private float Weight;
    private float Bloodpressure;
    private String Email;
    private String Phone_number;
    private String Existing_illness;
    private String Location;
    private String Prescription;
    private int Verified;
    private float Latitude;
    private float Longitude;
    private boolean login_success;
    private String password;
    private String membership;

    public void patient_register(Context context) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_register(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(status.equals("success"))
                    {
                        Context_maker.getInstance().setRegister_success(true);

                        // Toast.makeText(context, "successsfull", Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                        Context_maker.getInstance().setRegister_success(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Context_maker.getInstance().setRegister_success(false);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                Context_maker.getInstance().setRegister_success(false);



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                setPrescription("None");

                params.put("First_name",getFirst_name());
                params.put("Last_name",getLast_name());
                params.put("Age",getAge()+"");
                params.put("Weight",getWeight()+"");
                params.put("Bloodpressure",getBloodpressure()+"");
                params.put("Phone_number",getPhone_number());
                params.put("Existing_illness",getExisting_illness());
                params.put("Location",getLocation());
                params.put("Password",getPassword());
                params.put("Latitude",getLatitude()+"");
                params.put("Longitude",getLongitude()+"");
                params.put("Email",getEmail());
                params.put("Membership_number",getMembership());

                return params;
            }
        };
        requestQueue.add(stringRequest);



    }

    public void patient_recover(Context context,int membership) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_recover(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(status.equals("success"))
                    {
                        Context_maker.getInstance().setLogin_success(true);
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
                        String drugs =jsonObject.getString("Drugs");
                        int Verified = Integer.parseInt(jsonObject.getString("Verified"));
                        float Latitude = Float.parseFloat( jsonObject.getString("Latitude"));
                        float Longitude = Float.parseFloat( jsonObject.getString("Longitude"));

                        // Creating a StringBuffer object
                        StringBuffer sb = new StringBuffer(Prescription);

                        // Removing the last character
                        // of a string
                        sb.delete(Prescription.length() - 1, Prescription.length());

                        // Removing the first character
                        // of a string
                        sb.delete(0, 1);

                        // Converting StringBuffer into
                        // string & return modified string
                        Prescription = sb.toString();


                        // Creating a StringBuffer object..........................................
                        StringBuffer sb_D = new StringBuffer(drugs);

                        // Removing the last character
                        // of a string
                        sb_D.delete(drugs.length() - 1, drugs.length());

                        // Removing the first character
                        // of a string
                        sb_D.delete(0, 1);

                        // Converting StringBuffer into
                        // string & return modified string
                        drugs = sb_D.toString();

                        shared_persistence pref_manager = new shared_persistence();
                      //pref_manager.save_user(context,UU_ID,First_name,Last_name,Age,Weight
//                                ,Bloodpressure,Email,Phone_number,Existing_illness,Location,Prescription,
//                                Verified,Latitude,Longitude);
                       pref_manager.save_json(context,Prescription);
                       pref_manager.save_json_user_prep(context,drugs);
                       Toast.makeText(context, "Download successful", Toast.LENGTH_LONG).show();
//
//
//                        Intent intent_diag = new Intent(context, Lobby.class);
//                        intent_diag.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        context.startActivity(intent_diag);



                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                        Context_maker.getInstance().setLogin_success(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Context_maker.getInstance().setLogin_success(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                Context_maker.getInstance().setLogin_success(false);
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("membership_id",membership+"");

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    public void patient_login(Context context,String email,String password,int membership) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_login(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(status.equals("success"))
                    {
                        Context_maker.getInstance().setLogin_success(true);
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
                        String drugs =jsonObject.getString("Drugs");
                        int Verified = Integer.parseInt(jsonObject.getString("Verified"));
                        float Latitude = Float.parseFloat( jsonObject.getString("Latitude"));
                        float Longitude = Float.parseFloat( jsonObject.getString("Longitude"));

                        shared_persistence pref_manager = new shared_persistence();
                        pref_manager.save_user(context,UU_ID,First_name,Last_name,Age,Weight
                        ,Bloodpressure,Email,Phone_number,Existing_illness,Location,Prescription,
                         Verified,Latitude,Longitude);
                        pref_manager.save_json(context,Prescription);
                        pref_manager.save_json_user_prep(context,drugs);



                        Intent intent_diag = new Intent(context, Lobby.class);
                        intent_diag.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent_diag);



                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                        Context_maker.getInstance().setLogin_success(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Context_maker.getInstance().setLogin_success(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                Context_maker.getInstance().setLogin_success(false);
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email",email);
                params.put("Password",password);
                params.put("membership_id",membership+"");

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void patient_backup_prescription(Context context) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_backup_prescription(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                        Context_maker.getInstance().setPrescription_backup_success(true);
                        // Toast.makeText(context, "successsfull", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                        Context_maker.getInstance().setPrescription_backup_success(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Context_maker.getInstance().setPrescription_backup_success(false);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                Context_maker.getInstance().setPrescription_backup_success(false);

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                get_user_data(context);
                Map<String, String> params = new HashMap<>();
                params.put("UU_ID",getUU_ID()+"");
                params.put("Prescription",get_json(context));
                params.put("Drugs",get_json_user_prep(context));

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    ////////////////////////////json user saving starts here
    public  void clear_user(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public  void  save_user(Context context,int UU_ID,String First_name,String Last_name,int Age,float Weight,float Bloodpressure,String Email
            ,String Phone_number,String Existing_illness,String Location,String Prescription,int Verified,float Latitude,float Longitude)
    {

        SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UU_ID",UU_ID);
        editor.putString("First_name",First_name);
        editor.putString("Last_name",Last_name);
        editor.putInt("Age",Age);
        editor.putFloat("Weight",Weight);
        editor.putFloat("Bloodpressure",Bloodpressure);
        editor.putString("Email",Email);
        editor.putString("Phone_number",Phone_number);
        editor.putString("Existing_illness",Existing_illness);
        editor.putString("Location",Location);
        editor.putString("Prescription",Prescription);
        editor.putInt("Verified",Verified);
        editor.putFloat("Latitude",Latitude);
        editor.putFloat("Longitude",Longitude);
        editor.apply();
    }

    public void get_user_data(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);

        setUU_ID(sharedPreferences.getInt("UU_ID",0));
        setFirst_name( sharedPreferences.getString("First_name",""));
        setLast_name(sharedPreferences.getString("Last_name","")); ;
        setAge(sharedPreferences.getInt("Age",0));
        setWeight(sharedPreferences.getFloat("Weight",0));
        setBloodpressure(sharedPreferences.getFloat("Bloodpressure",0));
        setEmail(sharedPreferences.getString("Email",""));
        setPhone_number(sharedPreferences.getString("Phone_number",""));
        setExisting_illness(sharedPreferences.getString("Existing_illness",""));
        setLocation(sharedPreferences.getString("Location",""));
        setPrescription(sharedPreferences.getString("Prescription",""));
        setVerified(sharedPreferences.getInt("Verified",0));
        setLatitude(sharedPreferences.getFloat("Latitude",0));
        setLongitude(sharedPreferences.getFloat("Longitude",0));
    }

////////////////////////////json stuff start here
    //clears json data
    public  void clear_json(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("json_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
//saves json prescription
    public  void  save_json(Context context,String json )
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("json_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usertype",json);
        editor.apply();
    }

    //loads the json for use
    public String get_json(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("json_storage", Context.MODE_PRIVATE);
        return sharedPreferences.getString("usertype","");
    }


    ////////////////////////////json for regeneratable prescription
    //clears json data
    public  void clear_json_user_prep(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pres_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    //saves json prescription
    public  void  save_json_user_prep(Context context,String json )
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pres_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_prep",json);
        editor.apply();
    }

    //loads the json for use
    public String get_json_user_prep(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pres_storage", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_prep",null);
    }



    public void add_guardian(Context context, String g_name_, String g_sirname_, String g_email_, String g_relationship_, String g_phone_number_){
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(context);
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getAdding_guards(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(status.equals("success"))
                    {
                        //Context_maker.getInstance().setLogin_success(true);

                       Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
                       /* int UU_ID = Integer.parseInt(jsonObject.getString("UU_ID"));
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
                        float Latitude = Float.parseFloat( jsonObject.getString("Latitude"));
                        float Longitude = Float.parseFloat( jsonObject.getString("Longitude"));


                        shared_persistence pref_manager = new shared_persistence();
                        pref_manager.save_user(context,UU_ID,First_name,Last_name,Age,Weight
                                ,Bloodpressure,Email,Phone_number,Existing_illness,Location,Prescription,
                                Verified,Latitude,Longitude);
                        pref_manager.save_json(context,Prescription);*/


                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                        Context_maker.getInstance().setLogin_success(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Context_maker.getInstance().setLogin_success(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                Context_maker.getInstance().setLogin_success(false);
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("g_name",g_name_);
                params.put("g_lastname",g_sirname_);
                params.put("g_relation",g_relationship_);
                params.put("g_phone_number",g_phone_number_+"");
                params.put("g_email",g_email_);
                params.put("patient_id",shad.getUU_ID()+"");

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public  void update_user(Context context,String firstname,String lastname,int age,Double weight,Double bp,String email,String phone_number
            ,String existing_illnes,String location,Double longitude,Double latitude,String membership_number)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_update_accounts(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                        //Context_maker.getInstance().setLogin_success(true);
                        Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                get_user_data(context);
                Map<String, String> params = new HashMap<>();
                params.put("UU_ID",getUU_ID()+"");
                params.put("First_name",firstname);
                params.put("Last_name",lastname);
                params.put("Age",age+"");
                params.put("Weight",weight+"");
                params.put("Bloodpressure",bp+"");
                params.put("Email",email);
                params.put("Phone_number",phone_number+"");
                params.put("Existing_illness",existing_illnes);
                params.put("Location",location);
                params.put("Latitude",latitude+"");
                params.put("Longitude",longitude+"");
                params.put("Membership_number",membership_number);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public  void change_password(Context context,String oldpass,String newpass)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPatient_change_password(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                        Context_maker.getInstance().setLogout_success(true);
                        Toast.makeText(context, "Password change successful", Toast.LENGTH_SHORT).show();
                        shared_persistence shad = new shared_persistence();

                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                get_user_data(context);
                Map<String, String> params = new HashMap<>();
                params.put("UU_ID",getUU_ID()+"");
                params.put("old_pass",oldpass);
                params.put("new_pass",newpass);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public int getUU_ID() {
        return UU_ID;
    }

    public void setUU_ID(int UU_ID) {
        this.UU_ID = UU_ID;
    }

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String first_name) {
        First_name = first_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String last_name) {
        Last_name = last_name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public float getWeight() {
        return Weight;
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    public float getBloodpressure() {
        return Bloodpressure;
    }

    public void setBloodpressure(float bloodpressure) {
        Bloodpressure = bloodpressure;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getExisting_illness() {
        return Existing_illness;
    }

    public void setExisting_illness(String existing_illness) {
        Existing_illness = existing_illness;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPrescription() {
        return Prescription;
    }

    public void setPrescription(String prescription) {
        Prescription = prescription;
    }

    public int getVerified() {
        return Verified;
    }

    public void setVerified(int verified) {
        Verified = verified;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public boolean isLogin_success() {
        return login_success;
    }

    public void setLogin_success(boolean login_success) {
        this.login_success = login_success;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    ////////////////////////////saves the emergency number
    //clears emergency number
    public  void clear_emergency_number(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("emergency_number", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    //saves emergency number
    public  void  save_emergency_number(Context context,String number )
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("emergency_number", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("number",number);
        editor.apply();
    }

    //loads emergency number
    public String get_emergency_number(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("emergency_number", Context.MODE_PRIVATE);
        return sharedPreferences.getString("number","");
    }


}