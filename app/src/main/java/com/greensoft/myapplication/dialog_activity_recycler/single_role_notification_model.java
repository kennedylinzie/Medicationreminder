package com.greensoft.myapplication.dialog_activity_recycler;

public class single_role_notification_model {

    String name;
    int  drug_amount;
    String  drug_time;
    int images;
//create contractor



    public single_role_notification_model(String name, int drug_amount, int images, String drug_time) {
        this.name = name;
        this.drug_amount = drug_amount;
        this.images = images;
        this.drug_time = drug_time;
    }

    public single_role_notification_model() {

    }
    public void set_data(String name, int drug_amount, int images, String drug_time){
        this.name = name;
        this.drug_amount = drug_amount;
        this.images = images;
        this.drug_time = drug_time;
    }
    //getter and setter


    public String getDrug_time() {
        return drug_time;
    }

    public void setDrug_time(String drug_time) {
        this.drug_time = drug_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public int getDrug_amount() {
        return drug_amount;
    }

    public void setDrug_amount(int drug_amount) {
        this.drug_amount = drug_amount;
    }
}