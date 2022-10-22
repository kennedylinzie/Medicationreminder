package com.greensoft.myapplication.jsony;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Patient {
    @SerializedName("id")
    private int id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("drugName")
    private String drugName;
    @SerializedName("age")
    private int age;
    @SerializedName("mail")
    private String mail;
    @SerializedName("family")
    private List<Dosage_saver> mDosage;



    public Patient(int id, String firstName, String drugName, int age, String mail, List<Dosage_saver> mFamily) {
        this.id = id;
        this.firstName = firstName;
        this.drugName = drugName;
        this.age = age;
        this.mail = mail;
        this.mDosage = mFamily;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Dosage_saver> getmDosage() {
        return mDosage;
    }

    public void setmDosage(List<Dosage_saver> mDosage) {
        this.mDosage = mDosage;
    }
}
