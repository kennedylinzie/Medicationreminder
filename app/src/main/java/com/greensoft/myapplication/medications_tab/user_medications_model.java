package com.greensoft.myapplication.medications_tab;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.R;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.shared_persistence;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class user_medications_model {

    private String med_tab_name;
    private int  med_tab_strength;
    private int  id;
    private String  med_tab_strength_unit;
    private String  med_tab_drug_events;
    private int med_tab_image;


    //a list to store all the products
    private List<user_medications_model> productList;
    private List<Patient> generalInfoObject;




   /* public user_medications_model(String med_tab_name, int med_tab_strength, String med_tab_strength_unit, String med_tab_drug_events, int med_tab_image) {
        this.med_tab_name = med_tab_name;
        this.med_tab_strength = med_tab_strength;
        this.med_tab_strength_unit = med_tab_strength_unit;
        this.med_tab_drug_events = med_tab_drug_events;
        this.med_tab_image = med_tab_image;
    }*/

    public user_medications_model(String med_tab_name, int med_tab_strength, int id, String med_tab_strength_unit, String med_tab_drug_events, int med_tab_image) {
        this.med_tab_name = med_tab_name;
        this.med_tab_strength = med_tab_strength;
        this.id = id;
        this.med_tab_strength_unit = med_tab_strength_unit;
        this.med_tab_drug_events = med_tab_drug_events;
        this.med_tab_image = med_tab_image;
    }

    public user_medications_model() {

    }

    public String getMed_tab_strength_unit() {
        return med_tab_strength_unit;
    }

    public void setMed_tab_strength_unit(String med_tab_strength_unit) {
        this.med_tab_strength_unit = med_tab_strength_unit;
    }

    public String getMed_tab_name() {
        return med_tab_name;
    }

    public void setMed_tab_name(String med_tab_name) {
        this.med_tab_name = med_tab_name;
    }

    public int getMed_tab_strength() {
        return med_tab_strength;
    }

    public void setMed_tab_strength(int med_tab_strength) {
        this.med_tab_strength = med_tab_strength;
    }

    public String getMed_tab_drug_events() {
        return med_tab_drug_events;
    }

    public void setMed_tab_drug_events(String med_tab_drug_events) {
        this.med_tab_drug_events = med_tab_drug_events;
    }

    public int getMed_tab_image() {
        return med_tab_image;
    }

    public void setMed_tab_image(int med_tab_image) {
        this.med_tab_image = med_tab_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<user_medications_model> populate(){
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
        //String k = Context_maker.getInstance().getTheTempTimeDealer();
        if(Context_maker.getInstance().getTheTempTimeDealer().length() > 1)
        {
            //String compare_formatDateTime = comapare_date.format(formatter);
            now_formatDateTime = Context_maker.getInstance().getTheTempTimeDealer();
            Context_maker.getInstance().setTheTempTimeDealer("");
        }

        if(generalInfoObject != null)
        {
            for (Patient dss : generalInfoObject) {
                //System.out.println(dss.getName());
                String tempname="";
                for (int i = 0; i < dss.getmDosage().size(); i++) {
                    if(now_formatDateTime.equals(dss.getmDosage().get(i).getDateTaken()))
                    {

                            if(tempname.equals(dss.getDrugName()))
                            {
                            }else {
                                productList.add(
                                        new user_medications_model(
                                                dss.getDrugName(),
                                                dss.getmDosage().get(i).getStrength_amount(),
                                                dss.getId(),
                                                dss.getmDosage().get(i).getStrength_unit(),
                                                "nothing yet",
                                                R.drawable.pill



                                        ));
                                tempname = dss.getDrugName();
                            }



                    }

                }
            }
        }

        return productList;
    }
}