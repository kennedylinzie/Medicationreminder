package com.greensoft.myapplication;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.shared_persistence;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Medication implements Comparable {
    private int id;
    private String medication_name;
    private int pill_at_time;
    private boolean if_drug_taken;
    private String time_to_take_pill;
    private String date_to_take_pill;
    private int image;
    //a list to store all the products
    private List<Medication> productList;
    private List<Patient> generalInfoObject;

    private String Condition;
    private boolean refill_reminders;
    private String strength_unit;
    private int strength_amount;
    private String skipping_reason;
    private boolean skipped;




  /*  public Medication(int id, String medication_name, int pill_at_time, boolean if_drug_taken, String time_to_take_pill, String date_to_take_pill, int image) {
        this.id = id;
        this.medication_name = medication_name;
        this.pill_at_time = pill_at_time;
        this.if_drug_taken = if_drug_taken;
        this.time_to_take_pill = time_to_take_pill;
        this.date_to_take_pill = date_to_take_pill;
        this.image = image;
    }*/

   /* public Medication(int id, String medication_name, int pill_at_time, boolean if_drug_taken, String time_to_take_pill, String date_to_take_pill, int image, String condition, boolean refill_reminders, String strength_unit, int strength_amount, String skipping_reason) {
        this.id = id;
        this.medication_name = medication_name;
        this.pill_at_time = pill_at_time;
        this.if_drug_taken = if_drug_taken;
        this.time_to_take_pill = time_to_take_pill;
        this.date_to_take_pill = date_to_take_pill;
        this.image = image;
        Condition = condition;
        this.refill_reminders = refill_reminders;
        this.strength_unit = strength_unit;
        this.strength_amount = strength_amount;
        this.skipping_reason = skipping_reason;
    }*/

    public Medication(int id, String medication_name, int pill_at_time, boolean if_drug_taken, String time_to_take_pill, String date_to_take_pill, int image, String condition, boolean refill_reminders, String strength_unit, int strength_amount, String skipping_reason, boolean skipped) {
        this.id = id;
        this.medication_name = medication_name;
        this.pill_at_time = pill_at_time;
        this.if_drug_taken = if_drug_taken;
        this.time_to_take_pill = time_to_take_pill;
        this.date_to_take_pill = date_to_take_pill;
        this.image = image;
        Condition = condition;
        this.refill_reminders = refill_reminders;
        this.strength_unit = strength_unit;
        this.strength_amount = strength_amount;
        this.skipping_reason = skipping_reason;
        this.skipped = skipped;
    }

    public Medication() {

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

    public boolean isIf_drug_taken() {
        return if_drug_taken;
    }

    public String getDate_to_take_pill() {
        return date_to_take_pill;
    }

    public void setDate_to_take_pill(String date_to_take_pill) {
        this.date_to_take_pill = date_to_take_pill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedication_name() {
        return medication_name;
    }

    public void setMedication_name(String medication_name) {
        this.medication_name = medication_name;
    }

    public int getPill_at_time() {
        return pill_at_time;
    }

    public void setPill_at_time(int pill_at_time) {
        this.pill_at_time = pill_at_time;
    }

    public boolean getIf_drug_taken() {
        return if_drug_taken;
    }

    public void setIf_drug_taken(boolean if_drug_taken) {
        this.if_drug_taken = if_drug_taken;
    }

    public String getTime_to_take_pill() {
        return time_to_take_pill;
    }

    public void setTime_to_take_pill(String time_to_take_pill) {
        this.time_to_take_pill = time_to_take_pill;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Medication> populate(){
        //getting the recyclerview from xml
        generalInfoObject =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json(Context_maker.getInstance().getMyContext());
        Gson gson = new Gson();

        Type foundlistType = new TypeToken<ArrayList<Patient>>(){}.getType();
        generalInfoObject = gson.fromJson(output, foundlistType);

        //initializing the productlist
        productList = new ArrayList<>();

        ////////////////////
        LocalDateTime now_date = LocalDateTime.now();
        //LocalDateTime comapare_date = LocalDateTime.of(2022, 5, 20, 3, 35,10);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now_formatDateTime = now_date.format(formatter);
        //String compare_formatDateTime = comapare_date.format(formatter);
         //String k = Context_maker.getInstance().getTheTempTimeDealer();
       if(Context_maker.getInstance().getTheTempTimeDealer().length() > 1)
       {
           now_formatDateTime = Context_maker.getInstance().getTheTempTimeDealer();
           Context_maker.getInstance().setTheTempTimeDealer("");
       }

        if(generalInfoObject != null)
        {
            for (Patient dss : generalInfoObject) {
                //System.out.println(dss.getName());
                for (int i = 0; i < dss.getmDosage().size(); i++) {
                    if(now_formatDateTime.equals(dss.getmDosage().get(i).getDateTaken()))
                    {


                        productList.add(
                                new Medication(
                                        dss.getmDosage().get(i).getId(),
                                        dss.getmDosage().get(i).getMedicationName(),
                                        dss.getmDosage().get(i).getMedicationAmount(),
                                        dss.getmDosage().get(i).isHasTaken(),
                                        dss.getmDosage().get(i).getTimeTaken(),
                                        dss.getmDosage().get(i).getDateTaken(),
                                        R.drawable.icons8_pills_480px,
                                        dss.getmDosage().get(i).getCondition(),
                                        dss.getmDosage().get(i).isRefill_reminders(),
                                        dss.getmDosage().get(i).getStrength_unit(),
                                        dss.getmDosage().get(i).getStrength_amount(),
                                        dss.getmDosage().get(i).getSkipping_reason(),
                                        dss.getmDosage().get(i).isSkipped()
                                ));
                    }

                }
            }
        }
          productList.sort(Comparator.comparing(Medication::getTime_to_take_pill));
      return productList;
    }

    @Override
    public int compareTo(Object o) {
        Medication md = (Medication)o;
        if(md.getIf_drug_taken() == this.if_drug_taken || this.isSkipped() == this.isSkipped())
            return 0;
            return 1;

    }
}