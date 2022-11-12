package com.greensoft.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.greensoft.myapplication.jsony.Dosage_saver_for_alarm_activity;
import com.greensoft.myapplication.jsony.connectionsManager;
import com.greensoft.myapplication.jsony.shared_persistence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Context_maker {

    Context myContext;
    private String theTempTimeDealer ="";
    LinkedList<Dosage_saver_for_alarm_activity> cars = new LinkedList<Dosage_saver_for_alarm_activity>();
    public boolean login_success;
    public boolean register_success;
    public boolean prescription_backup_success;
    public boolean connection = false;
    public boolean logout_success = false;
    public boolean ready_for_call;
    public boolean ready_for_recovery;
    public int trap_counter=0;

    private String et_drugname_rec;
    private String et_at_atime_rec;
    private String et_totaldrugs_rec;
    private String et_condition_rec;
    private String et_medication_strength_rec;
    private String et_if_breastfeeding_rec;
    private String et_if_takewithmeal_rec;
    private String et_if_pregnant_rec;
    private String et_intakemethod_rec;
    private boolean blocked_with_wall = false;
    private boolean blocked_with_wall2 = false;
    private boolean inlobyy =false;
    connectionsManager conman = new connectionsManager();


    public boolean isInlobyy() {
        return inlobyy;
    }

    public void setInlobyy(boolean inlobyy) {
        this.inlobyy = inlobyy;
    }

    public int getTrap_counter() {
        return trap_counter;
    }

    public void setTrap_counter(int trap_counter) {
        this.trap_counter = trap_counter;
    }

    public boolean isBlocked_with_wall() {
        return blocked_with_wall;
    }

    public void setBlocked_with_wall(boolean blocked_with_wall) {
        this.blocked_with_wall = blocked_with_wall;
    }

    public boolean isBlocked_with_wall2() {
        return blocked_with_wall2;
    }

    public void setBlocked_with_wall2(boolean blocked_with_wall2) {
        this.blocked_with_wall2 = blocked_with_wall2;
    }

    public String getEt_drugname_rec() {
        return et_drugname_rec;
    }

    public void setEt_drugname_rec(String et_drugname_rec) {
        this.et_drugname_rec = et_drugname_rec;
    }

    public String getEt_at_atime_rec() {
        return et_at_atime_rec;
    }

    public void setEt_at_atime_rec(String et_at_atime_rec) {
        this.et_at_atime_rec = et_at_atime_rec;
    }

    public String getEt_totaldrugs_rec() {
        return et_totaldrugs_rec;
    }

    public void setEt_totaldrugs_rec(String et_totaldrugs_rec) {
        this.et_totaldrugs_rec = et_totaldrugs_rec;
    }

    public String getEt_condition_rec() {
        return et_condition_rec;
    }

    public void setEt_condition_rec(String et_condition_rec) {
        this.et_condition_rec = et_condition_rec;
    }

    public String getEt_medication_strength_rec() {
        return et_medication_strength_rec;
    }

    public void setEt_medication_strength_rec(String et_medication_strength_rec) {
        this.et_medication_strength_rec = et_medication_strength_rec;
    }

    public String getEt_if_breastfeeding_rec() {
        return et_if_breastfeeding_rec;
    }

    public void setEt_if_breastfeeding_rec(String et_if_breastfeeding_rec) {
        this.et_if_breastfeeding_rec = et_if_breastfeeding_rec;
    }

    public String getEt_if_takewithmeal_rec() {
        return et_if_takewithmeal_rec;
    }

    public void setEt_if_takewithmeal_rec(String et_if_takewithmeal_rec) {
        this.et_if_takewithmeal_rec = et_if_takewithmeal_rec;
    }

    public String getEt_if_pregnant_rec() {
        return et_if_pregnant_rec;
    }

    public void setEt_if_pregnant_rec(String et_if_pregnant_rec) {
        this.et_if_pregnant_rec = et_if_pregnant_rec;
    }

    public String getEt_intakemethod_rec() {
        return et_intakemethod_rec;
    }

    public void setEt_intakemethod_rec(String et_intakemethod_rec) {
        this.et_intakemethod_rec = et_intakemethod_rec;
    }

    public boolean isReady_for_recovery() {
        return ready_for_recovery;
    }

    public void setReady_for_recovery(boolean ready_for_recovery) {
        this.ready_for_recovery = ready_for_recovery;
    }

    public boolean isReady_for_call() {
        return ready_for_call;
    }

    public void setReady_for_call(boolean ready_for_call) {
        this.ready_for_call = ready_for_call;
    }





    public boolean isLogout_success() {
        return logout_success;
    }

    public void setLogout_success(boolean logout_success) {
        this.logout_success = logout_success;
    }

    public boolean isConnection() {
        return connection;
    }

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    private String[] people;

    public String[] getPeople() {
        return people;
    }

    public void setPeople(String[] people) {
        this.people = people;
    }

    public boolean isRegister_success() {
        return register_success;
    }

    public void setRegister_success(boolean register_success) {
        this.register_success = register_success;
    }

    public boolean isLogin_success() {
        return login_success;
    }

    public void setLogin_success(boolean login_success) {
        this.login_success = login_success;
    }

    private static final Context_maker ourInstance = new Context_maker();
    public static Context_maker getInstance() {
        return ourInstance;
    }
    private Context_maker() {



    }

    public Context getMyContext() {


        return myContext;
    }

    public void setMyContext(Context myContext) {
        this.myContext = myContext;
    }

    public String getTheTempTimeDealer() {
        return theTempTimeDealer;
    }

    public void setTheTempTimeDealer(String theTempTimeDealer) {
        this.theTempTimeDealer = theTempTimeDealer;
    }

    public boolean isPrescription_backup_success() {
        return prescription_backup_success;
    }

    public void setPrescription_backup_success(boolean prescription_backup_success) {
        this.prescription_backup_success = prescription_backup_success;
    }

    public LinkedList<Dosage_saver_for_alarm_activity> getCars() {


        return cars;
    }

    public void setCars(LinkedList<Dosage_saver_for_alarm_activity> cars) {
        this.cars = cars;
    }

    public void set_alarm_data(int id, String medicationName, int medicationAmount, String dateTaken, String timeTaken, boolean hasTaken, boolean isSkipped)
    {

            cars.add(new Dosage_saver_for_alarm_activity(id, medicationName, medicationAmount, dateTaken, timeTaken, hasTaken,isSkipped));


    }

    public void check_connection()
    {

        connectionsManager conman = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(getMyContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getCheck_connections(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("success"))
                    {
                        Toast.makeText(getMyContext(), message,Toast.LENGTH_LONG).show();
                        setConnection(true);
                    }else {
                        Toast.makeText(getMyContext(), message,Toast.LENGTH_LONG).show();
                        setConnection(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setConnection(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                setConnection(false);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                /*params.put("UU_ID",getUU_ID()+"");*/
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void check_verified(String email, String First_name) {

        RequestQueue requestQueue = Volley.newRequestQueue(getMyContext());

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
                        int Verified = Integer.parseInt(jsonObject.getString("Verified"));
                        float Latitude = Float.parseFloat( jsonObject.getString("Latitude"));
                        float Longitude = Float.parseFloat( jsonObject.getString("Longitude"));

                        if(Verified==0){
                            Toast.makeText(getMyContext(),"unverified",Toast.LENGTH_SHORT).show();
                            Intent intent_diag = new Intent(getMyContext(), Lobby.class);
                            intent_diag.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getMyContext().startActivity(intent_diag);

                        }else if(Verified==1){
                            Toast.makeText(getMyContext(),"verified",Toast.LENGTH_SHORT).show();


                        }else{
                            Toast.makeText(getMyContext(),"anything else",Toast.LENGTH_SHORT).show();
                        }




                    }else {
                        Toast.makeText(getMyContext(),message+"",Toast.LENGTH_LONG).show();
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

                Toast.makeText(getMyContext(),"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                Context_maker.getInstance().setLogin_success(false);
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Email",email);
                params.put("First_name",First_name);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

}