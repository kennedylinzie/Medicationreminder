package com.greensoft.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.jsony.Dosage_saver;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.connectionsManager;
import com.greensoft.myapplication.jsony.medi_dosing;
import com.greensoft.myapplication.jsony.second_prescription;
import com.greensoft.myapplication.jsony.shared_persistence;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_save_meds  {


    private List<medi_dosing> prescription_holder = new ArrayList<>();
    private List<Patient> generalInfoObject;
    private String json;
    private int drug_TotalDosage;
    private int drug_Amount_at_a_time;
    private String drug_Name;
    private Context context;
    private int drug_times_a_day;
    private String Condition;
    private boolean refill_reminders;
    private String strength_unit;
    private int strength_amount;
    private String skipping_reason;
    private boolean skipped;
    private String breast_feeding;
    private String pregnant;
    private String intake_method;
    private String takewith_meal;






    @RequiresApi(api = Build.VERSION_CODES.O)
    public void add_meds(){
        recover_Json();
        prescription_holder.add(new medi_dosing("Daily",  "09"));
        prescription_holder.add(new medi_dosing("2 times a day",  "09","21"));
        prescription_holder.add(new medi_dosing("3 times a day",  "09","14","21"));
        prescription_holder.add(new medi_dosing("4 times a day",  "09","13","17","21"));
        prescription_holder.add(new medi_dosing("5 times a day",  "05","09","13","17","21"));
        //THE LAST ONE IS NON FUNCTIONAL
        prescription_holder.add(new medi_dosing("6 times a day",  "05","09","13","17","21","00"));

      //5  Object[] array = prescription_holder.toArray();

        int day = getDrug_times_a_day();
        switch (day) {
            case 1:

                add_prescription(
                        Integer.parseInt(prescription_holder.get(0).getTimes1())
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,1
                );
                break;
            case 2:
                add_prescription(
                        Integer.parseInt(prescription_holder.get(1).getTimes1())
                        ,Integer.parseInt(prescription_holder.get(1).getTimes2())
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,2
                );
                break;
            case 3:
                add_prescription(
                        Integer.parseInt(prescription_holder.get(2).getTimes1())
                        ,Integer.parseInt(prescription_holder.get(2).getTimes2())
                        ,Integer.parseInt(prescription_holder.get(2).getTimes3())
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,3
                );
                break;
            case 4:
                add_prescription(
                        Integer.parseInt(prescription_holder.get(3).getTimes1())
                        ,Integer.parseInt(prescription_holder.get(3).getTimes2())
                        ,Integer.parseInt(prescription_holder.get(3).getTimes3())
                        ,Integer.parseInt(prescription_holder.get(3).getTimes4())
                        ,Integer.parseInt(String.valueOf(0))
                        ,Integer.parseInt(String.valueOf(0))
                        ,4
                );
                break;
            case 5:

                add_prescription(
                        Integer.parseInt(prescription_holder.get(4).getTimes1())
                        ,Integer.parseInt(prescription_holder.get(4).getTimes2())
                        ,Integer.parseInt(prescription_holder.get(4).getTimes3())
                        ,Integer.parseInt(prescription_holder.get(4).getTimes4())
                        ,Integer.parseInt(prescription_holder.get(4).getTimes5())
                        ,Integer.parseInt(String.valueOf(0))
                        ,5
                );
                break;

        }
        /////////////////////////above is a prescription area

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void add_prescription(int time1, int time2, int time3, int time4, int time5, int time6,int times_a_day_)
    {
        Gson gson = new Gson();
        ArrayList<Integer> times = new ArrayList<Integer>();

        if(time1 != 0){
            times.add(time1);
        }
        if(time2 != 0){
            times.add(time2);
        }
        if(time3 != 0){
            times.add(time3);
        }
        if(time4 != 0){
            times.add(time4);
        }
        if(time5 != 0){
            times.add(time5);
        }
        if(time6 != 0){
            times.add(time6);
        }


        int limit  = getDrug_TotalDosage()/ getDrug_Amount_at_a_time();
        limit = (limit/ getDrug_times_a_day());
        // String dt = "2020-05-30";  // Start date

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dt = dtf.format(now);
        List<Dosage_saver> prescription = new ArrayList<>();
        List<Patient> myPatientdetails = new ArrayList<>();
        int patientid =0;
        if(generalInfoObject!= null) {
            myPatientdetails = generalInfoObject;
            if(generalInfoObject.size()>0)
            {
                patientid = generalInfoObject.size()+1;
            }
          //  Toast.makeText(getContext(), "HANDOVER", Toast.LENGTH_SHORT).show();
        }
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(getContext());
        if(shad.getFirst_name() == null){

                Toast.makeText(getContext(), "You need to create an account first" , Toast.LENGTH_SHORT).show();
                Intent take_me_to_register = new Intent(Context_maker.getInstance().getMyContext(), MainActivity.class);
                getContext().startActivity(take_me_to_register);
            return;
        }

        Patient patientdetails = new Patient(patientid,shad.getFirst_name(), getDrug_Name(), shad.getAge(),shad.getEmail(), prescription);


      /*  prescription.add(new Dosage_saver(meds_aday, getDrug_Name(), getDrug_Amount_at_a_time(),dt,Hour +":"+min+":"+sec,false,
                getCondition(),isRefill_reminders(),getStrength_unit(),getStrength_amount(),getSkipping_reason(),isSkipped()
                ,getBreast_feeding(),getPregnant(),getIntake_method(),getTakewith_meal()));*/

        shared_persistence shad1 = new shared_persistence();
        shad1.get_user_data(Context_maker.getInstance().getMyContext());
        add_json_user_prep(getDrug_Name(),getDrug_Amount_at_a_time()+"",getDrug_TotalDosage()+"",getDrug_times_a_day()+"",getCondition(),getStrength_amount()+"",getStrength_unit(),
        isRefill_reminders()+"",getIntake_method(),getPregnant(),getBreast_feeding(),getTakewith_meal(),shad1.getUU_ID()+"");


        for (int dates = 0; dates <= limit; dates++) {

            int hour = 23;
            String min  = "00";
            String sec  = "00";

            if(dates >= 1)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(dt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, 1);  // number of days to add
                dt = sdf.format(c.getTime());  // dt is now the new date
            }




            for (int meds_aday = 0; meds_aday < times.size(); ++meds_aday) {

                DecimalFormat df = new DecimalFormat("00");
                String Hour = df.format(times.get(meds_aday));   // 0009
                //  String a = df.format(99);  // 0099
                // String b = df.format(999); // 0999



                // Log.d("showy", name + " amount taken "+ amount_at_a_time +""+" time "+dt+" "+times.get(meds_aday)+":"+min+":"+sec);
                prescription.add(new Dosage_saver(meds_aday, getDrug_Name(), getDrug_Amount_at_a_time(),dt,Hour +":"+min+":"+sec,false,
                        getCondition(),isRefill_reminders(),getStrength_unit(),getStrength_amount(),getSkipping_reason(),isSkipped()
                ,getBreast_feeding(),getPregnant(),getIntake_method(),getTakewith_meal()));

            }

            //////////////send to jsom

            if((dates+1) > limit) {

                // Employee employee1 = new Employee("Manzy", 20, "emanuel@gmail.com", prescription);
                myPatientdetails.add(patientdetails);
                //myEmployees.add(employee1);
                json = gson.toJson(myPatientdetails);
                //String output = json;
                // tv.setText(json);

                get_back_json(json);
                break;
            }
            //end of send to json code



        }

    }
    public void get_back_json(String json)
    {
        //the_number=0;
        shared_persistence shad = new shared_persistence();
        shad.save_json(getContext(),json);
        //refresh();
        Toast.makeText(getContext(),"Save succeeful",Toast.LENGTH_SHORT).show();
    }

    public void recover_Json() {
        generalInfoObject =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json(getContext());
        if(output.equals("None")){
            output="";
        }
        Gson gson = new Gson();
        Type foundlistType = new TypeToken<ArrayList<Patient>>(){}.getType();
        generalInfoObject = gson.fromJson(output, foundlistType);

       // Toast.makeText(getContext(),"pull succeessful",Toast.LENGTH_SHORT).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void change_taken_status(String name,int id_,String date)
    {
        setContext(Context_maker.getInstance().getMyContext());
        recover_Json();

        ////////////////////
        LocalDateTime now_date = LocalDateTime.now();
        LocalDateTime comapare_date = LocalDateTime.of(2022, 6, 17, 14, 0,0);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now_formatDateTime = now_date.format(formatter);
        String compare_formatDateTime = comapare_date.format(formatter);
        // Toast.makeText(getApplicationContext(),compare_formatDateTime,Toast.LENGTH_SHORT).show();

        Gson gson = new Gson();
        for (Patient dss : generalInfoObject) {
            //System.out.println(dss.getName());

            for (int i = 0; i < dss.getmDosage().size(); i++) {

                if(date.equals(dss.getmDosage().get(i).getDateTaken()+" "+dss.getmDosage().get(i).getTimeTaken()) && dss.getmDosage().get(i).getMedicationName().equals(name) && dss.getmDosage().get(i).getId() == id_)
                {

                //  if(dss.getmDosage().get(i).getId() == id_){
                      Toast.makeText(getContext(), "got through", Toast.LENGTH_SHORT).show();
                      if(!dss.getmDosage().get(i).isHasTaken())
                      {
                          dss.getmDosage().get(i).setHasTaken(true);
                          dss.getmDosage().get(i).setSkipped(false);
                          json = gson.toJson(generalInfoObject);
                          get_back_json(json);
                          Toast.makeText(getContext(), "changed", Toast.LENGTH_SHORT).show();
                      }else {
                          Toast.makeText(getContext(), "It is already taken", Toast.LENGTH_SHORT).show();
                      }
                 // }

                    /*Toast.makeText(getContext(),dss.getmFamily().get(i).getMedicationName()+" "+dss.getmFamily().get(i).getDateTaken()+" "+dss.getmFamily().get(i).getTimeTaken()
                           +" "+dss.getmFamily().get(i).isHasTaken(),Toast.LENGTH_SHORT).show();*/
                }
            }
        }
        // refresh();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void change_skipped_status(String name,int id_,String date)
    {
        setContext(Context_maker.getInstance().getMyContext());
        recover_Json();

        ////////////////////
        LocalDateTime now_date = LocalDateTime.now();
        //LocalDateTime comapare_date = LocalDateTime.of(2022, 6, 17, 14, 0,0);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now_formatDateTime = now_date.format(formatter);
       // String compare_formatDateTime = comapare_date.format(formatter);
        // Toast.makeText(getApplicationContext(),compare_formatDateTime,Toast.LENGTH_SHORT).show();

        Gson gson = new Gson();
        for (Patient dss : generalInfoObject) {
            //System.out.println(dss.getName());

            for (int i = 0; i < dss.getmDosage().size(); i++) {
                if(date.equals(dss.getmDosage().get(i).getDateTaken()+" "+dss.getmDosage().get(i).getTimeTaken()) && dss.getmDosage().get(i).getMedicationName().equals(name) && dss.getmDosage().get(i).getId() == id_)
                {
                   // if(dss.getmDosage().get(i).getId() == id_){
                       // Toast.makeText(getContext(), "got through", Toast.LENGTH_SHORT).show();
                        if(!dss.getmDosage().get(i).isHasTaken())
                        {
                            dss.getmDosage().get(i).setHasTaken(false);
                            dss.getmDosage().get(i).setSkipped(true);

                            json = gson.toJson(generalInfoObject);
                            get_back_json(json);
                           // Toast.makeText(getContext(), "changed", Toast.LENGTH_SHORT).show();
                        }
                  //  }
                    /*Toast.makeText(getContext(),dss.getmFamily().get(i).getMedicationName()+" "+dss.getmFamily().get(i).getDateTaken()+" "+dss.getmFamily().get(i).getTimeTaken()
                            +" "+dss.getmFamily().get(i).isHasTaken(),Toast.LENGTH_SHORT).show();*/
                }

            }

        }
        // refresh();

    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public boolean isRefill_reminders() {
        return refill_reminders;
    }

    public void setRefill_reminders(boolean refill_reminders) {
        this.refill_reminders = refill_reminders;
    }

    public String getStrength_unit() {
        return strength_unit;
    }

    public void setStrength_unit(String strength_unit) {
        this.strength_unit = strength_unit;
    }

    public int getStrength_amount() {
        return strength_amount;
    }

    public void setStrength_amount(int strength_amount) {
        this.strength_amount = strength_amount;
    }

    public String getSkipping_reason() {
        return skipping_reason;
    }

    public void setSkipping_reason(String skipping_reason) {
        this.skipping_reason = skipping_reason;
    }

    public int getDrug_times_a_day() {
        return drug_times_a_day;
    }

    public void setDrug_times_a_day(int drug_times_a_day) {
        this.drug_times_a_day = drug_times_a_day;
    }

    public int getDrug_TotalDosage() {
        return drug_TotalDosage;
    }

    public void setDrug_TotalDosage(int drug_TotalDosage) {
        this.drug_TotalDosage = drug_TotalDosage;
    }

    public int getDrug_Amount_at_a_time() {
        return drug_Amount_at_a_time;
    }

    public void setDrug_Amount_at_a_time(int drug_Amount_at_a_time) {
        this.drug_Amount_at_a_time = drug_Amount_at_a_time;
    }

    public String getDrug_Name() {
        return drug_Name;
    }

    public void setDrug_Name(String drug_Name) {
        this.drug_Name = drug_Name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public String getBreast_feeding() {
        return breast_feeding;
    }

    public void setBreast_feeding(String breast_feeding) {
        this.breast_feeding = breast_feeding;
    }

    public String getPregnant() {
        return pregnant;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public String getIntake_method() {
        return intake_method;
    }

    public void setIntake_method(String intake_method) {
        this.intake_method = intake_method;
    }

    public String getTakewith_meal() {
        return takewith_meal;
    }

    public void setTakewith_meal(String takewith_meal) {
        this.takewith_meal = takewith_meal;
    }


    public void save_prescription_online(String Drug_name,int Amount_at_a_time,int Total_dosage,int Time_a_day,String Condtion,int Drug_strength
            ,String Drug_unit,String Needs_refill,String Intake_method,String Take_with_Pregnancy,String Ok_for_breastfeeding,String TakeBeforeOrAfterMeal)
    {
        connectionsManager conman = new connectionsManager();

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getAdd_patient_prescription(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");

                    if(message.equals("Successful"))
                    {
                        Toast.makeText(context, "Backup successful", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "information not loaded to database", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, error -> Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show()){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                shared_persistence shad = new shared_persistence();
                shad.get_user_data(Context_maker.getInstance().getMyContext());

                params.put("Drug_name",Drug_name);
                params.put("Amount_at_a_time",Amount_at_a_time+"");
                params.put("Total_dosage",Total_dosage+"");
                params.put("Time_a_day",Time_a_day+"");
                params.put("Condtion",Condtion+"");
                params.put("Drug_strength",Drug_strength+"");
                params.put("Drug_unit",Drug_unit);
                params.put("Needs_refill",Needs_refill);
                params.put("Intake_method",Intake_method);
                params.put("Ok_for_breastfeeding",Ok_for_breastfeeding);
                params.put("Take_with_Pregnancy",Take_with_Pregnancy);
                params.put("TakeBeforeOrAfterMeal",TakeBeforeOrAfterMeal);
                params.put("Patient_ID",shad.getUU_ID()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);



    }

        public void add_json_user_prep(String drug_name, String amount_at_a_time, String total_dosage, String times_a_day, String condtion, String drug_strength, String drug_unit, String needs_refill, String intake_method, String take_with_Pregnancy, String ok_for_breastfeeding, String takeBeforeOrAfterMeal, String patient_ID){
            List<second_prescription> user_medication = new ArrayList<>();
            shared_persistence shad = new shared_persistence();
            Gson gson = new Gson();
            String output = shad.get_json_user_prep(getContext());

            Type foundlistType = new TypeToken<ArrayList<second_prescription>>(){}.getType();
            user_medication = gson.fromJson(output, foundlistType);

            if(user_medication == null) {
                user_medication = new ArrayList<>(0);
            }

            String onlinejson_ = "";
            user_medication.add(new second_prescription(drug_name,amount_at_a_time,total_dosage,times_a_day,condtion,drug_strength,drug_unit,needs_refill,intake_method,take_with_Pregnancy,ok_for_breastfeeding,takeBeforeOrAfterMeal,patient_ID,R.drawable.pill));
            onlinejson_ = gson.toJson(user_medication);
            shad.save_json_user_prep(getContext(),onlinejson_);

        }




}
