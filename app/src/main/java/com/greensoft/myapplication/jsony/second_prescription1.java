package com.greensoft.myapplication.jsony;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.Context_maker;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class second_prescription1 {

    private String Drug_name ;
    private String Amount_at_a_time;
    private String Total_dosage;
    private String Time_a_day;
    private String Condtion;
    private String Drug_strength;
    private String Drug_unit;
    private String Needs_refill;
    private String Intake_method;
    private String Take_with_Pregnancy;
    private String Ok_for_breastfeeding;
    private String TakeBeforeOrAfterMeal;
    private String Patient_ID;


    private List<second_prescription1> productList;
    private List<second_prescription1> generalInfoObject;

    public second_prescription1(String drug_name, String amount_at_a_time, String total_dosage, String time_a_day, String condtion, String drug_strength, String drug_unit, String needs_refill, String intake_method, String take_with_Pregnancy, String ok_for_breastfeeding, String takeBeforeOrAfterMeal, String patient_ID) {
        Drug_name = drug_name;
        Amount_at_a_time = amount_at_a_time;
        Total_dosage = total_dosage;
        Time_a_day = time_a_day;
        Condtion = condtion;
        Drug_strength = drug_strength;
        Drug_unit = drug_unit;
        Needs_refill = needs_refill;
        Intake_method = intake_method;
        Take_with_Pregnancy = take_with_Pregnancy;
        Ok_for_breastfeeding = ok_for_breastfeeding;
        TakeBeforeOrAfterMeal = takeBeforeOrAfterMeal;
        Patient_ID = patient_ID;
    }

    public second_prescription1() {

    }


    public String getDrug_name() {
        return Drug_name;
    }

    public void setDrug_name(String drug_name) {
        Drug_name = drug_name;
    }

    public String getAmount_at_a_time() {
        return Amount_at_a_time;
    }

    public void setAmount_at_a_time(String amount_at_a_time) {
        Amount_at_a_time = amount_at_a_time;
    }

    public String getTotal_dosage() {
        return Total_dosage;
    }

    public void setTotal_dosage(String total_dosage) {
        Total_dosage = total_dosage;
    }

    public String getTime_a_day() {
        return Time_a_day;
    }

    public void setTime_a_day(String time_a_day) {
        Time_a_day = time_a_day;
    }

    public String getCondtion() {
        return Condtion;
    }

    public void setCondtion(String condtion) {
        Condtion = condtion;
    }

    public String getDrug_strength() {
        return Drug_strength;
    }

    public void setDrug_strength(String drug_strength) {
        Drug_strength = drug_strength;
    }

    public String getDrug_unit() {
        return Drug_unit;
    }

    public void setDrug_unit(String drug_unit) {
        Drug_unit = drug_unit;
    }

    public String getNeeds_refill() {
        return Needs_refill;
    }

    public void setNeeds_refill(String needs_refill) {
        Needs_refill = needs_refill;
    }

    public String getIntake_method() {
        return Intake_method;
    }

    public void setIntake_method(String intake_method) {
        Intake_method = intake_method;
    }

    public String getTake_with_Pregnancy() {
        return Take_with_Pregnancy;
    }

    public void setTake_with_Pregnancy(String take_with_Pregnancy) {
        Take_with_Pregnancy = take_with_Pregnancy;
    }

    public String getOk_for_breastfeeding() {
        return Ok_for_breastfeeding;
    }

    public void setOk_for_breastfeeding(String ok_for_breastfeeding) {
        Ok_for_breastfeeding = ok_for_breastfeeding;
    }

    public String getTakeBeforeOrAfterMeal() {
        return TakeBeforeOrAfterMeal;
    }

    public void setTakeBeforeOrAfterMeal(String takeBeforeOrAfterMeal) {
        TakeBeforeOrAfterMeal = takeBeforeOrAfterMeal;
    }

    public String getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(String patient_ID) {
        Patient_ID = patient_ID;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<second_prescription1> populate(){
        //getting the recyclerview from xml
        generalInfoObject =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json_user_prep(Context_maker.getInstance().getMyContext());
        Gson gson = new Gson();

        Type foundlistType = new TypeToken<ArrayList<second_prescription1>>(){}.getType();
        generalInfoObject = gson.fromJson(output, foundlistType);

        //initializing the productlist
        productList = new ArrayList<>();


        if(generalInfoObject != null)
        {

                String tempname="";
                for (int i = 0; i < generalInfoObject.size(); i++) {
                            productList.add(
                                    new second_prescription1(
                                            generalInfoObject.get(i).getDrug_name(),
                                            generalInfoObject.get(i).getAmount_at_a_time(),
                                            generalInfoObject.get(i).getTotal_dosage(),
                                            generalInfoObject.get(i).getTime_a_day(),
                                            generalInfoObject.get(i).getCondtion(),
                                            generalInfoObject.get(i).getDrug_strength(),
                                            generalInfoObject.get(i).getDrug_unit(),
                                            generalInfoObject.get(i).getNeeds_refill(),
                                            generalInfoObject.get(i).getIntake_method(),
                                            generalInfoObject.get(i).getTake_with_Pregnancy(),
                                            generalInfoObject.get(i).getOk_for_breastfeeding(),
                                            generalInfoObject.get(i).getTakeBeforeOrAfterMeal(),
                                            generalInfoObject.get(i).getPatient_ID()

                                    ));
                         //   tempname = ;

                }

        }

        return productList;
    }



}
