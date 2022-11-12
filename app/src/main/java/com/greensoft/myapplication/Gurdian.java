package com.greensoft.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.google.gson.Gson;
import com.greensoft.myapplication.jsony.connectionsManager;
import com.greensoft.myapplication.jsony.shared_persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Gurdian {



    public Gurdian(String name, String sirname, String email, String relationship, String phone_number, int id) {
        this.name = name;
        this.sirname = sirname;
        this.email = email;
        this.relationship = relationship;
        this.phone_number = phone_number;
        this.id = id;
    }
    public Gurdian() {

    }

    public void delete(Context context,String name){
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(context);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        connectionsManager conman = new connectionsManager();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getDelete_guards(), new Response.Listener<String>() {
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

                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();



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

                Map<String, String> params = new HashMap<>();
                params.put("g_name",name);
                params.put("patient_id",shad.getUU_ID()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void pull_gurd(Context context){
        List<Gurdian> people_backup = new LinkedList<Gurdian>();
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(context);
        connectionsManager conman = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPull_guards(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
               // Toast.makeText(context,response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();
                   try {
                        //converting the string to json array object
                       JSONArray array;
                       String[] people;

                       array = new JSONArray(response);
                       people = new String[array.length()];


                       //traversing through all the object

                           for (int i = 0; i < array.length(); i++) {

                               //getting product object from json array
                               JSONObject product = array.getJSONObject(i);

                               //adding the product to product list

                               String g_uuid = product.getString("g_uuid");
                               String g_name = product.getString("g_name");
                               String g_lastname = product.getString("g_lastname");
                               String g_relation = product.getString("g_relation");
                               String g_phone_number = product.getString("g_phone_number");
                               String g_email = product.getString("g_email");

                               people[i] = g_name;
                               Gurdian gu = new Gurdian(g_name,g_lastname,g_email,g_relation,g_phone_number,Integer.parseInt(g_uuid));
                               people_backup.add(gu);


                       }

                       Context_maker.getInstance().setPeople(people);
                           String json;
                           Gson gson = new Gson();
                           json = gson.toJson(people_backup);
                           save_guards(json);

                    } catch (JSONException e) {
                      //  throw new NullPointerException();
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
                Map<String, String> params = new HashMap<>();
                params.put("patient_id",shad.getUU_ID()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    public void pull_gurd_filter(Context context,String name__){
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(context);
        connectionsManager conman = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPull_guards(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                //Toast.makeText(context,response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();

                try {
                    //converting the string to json array object
                    JSONArray array;


                    array = new JSONArray(response);

                     //traversing through all the object
                     for (int i = 0; i < array.length(); i++) {

                         //getting product object from json array
                         JSONObject product = array.getJSONObject(i);

                         //adding the product to product list
                         if(product.getString("g_name").equals(name__)){
                             String g_uuid = product.getString("g_uuid");
                             String g_name = product.getString("g_name");
                             String g_lastname = product.getString("g_lastname");
                             String g_relation = product.getString("g_relation");
                             String g_phone_number = product.getString("g_phone_number");
                             String g_email = product.getString("g_email");
                             setId(Integer.parseInt(g_uuid));
                             setName(g_name);
                             setSirname(g_lastname);
                             setRelationship(g_relation);
                             setPhone_number(g_phone_number);
                             setEmail(g_email);
                         }
                     }




                } catch (JSONException e) {
                   // throw new NullPointerException();
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
                Map<String, String> params = new HashMap<>();
                params.put("patient_id",shad.getUU_ID()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSirname() {
        return sirname;
    }

    public void setSirname(String sirname) {
        this.sirname = sirname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private String sirname;
    private String email;
    private String relationship;
    private String phone_number;
    private int id;


    ////////////////////////////saves json data as serialised json
    public  void clear_gurds(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("json_gurds", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    //saves json prescription
    public  void  save_guards(String json )
    {

        SharedPreferences sharedPreferences = Context_maker.getInstance().getMyContext().getSharedPreferences("json_gurds", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("guard_data",json);
        editor.apply();
    }

    //loads the json for use
    public String get_guards(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("json_gurds", Context.MODE_PRIVATE);
        return sharedPreferences.getString("guard_data","");
    }


}
