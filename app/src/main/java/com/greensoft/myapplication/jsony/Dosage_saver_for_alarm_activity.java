package com.greensoft.myapplication.jsony;

import com.google.gson.annotations.SerializedName;

public class Dosage_saver_for_alarm_activity {

    @SerializedName("id")
    private int id;
    @SerializedName("medicationName")
    private String medicationName;

    @SerializedName("medicationAmount")
    private int medicationAmount;

    @SerializedName("dataTaken")
    private String dateTaken;

    @SerializedName("times3")
    private String timeTaken;

    @SerializedName("times4")
    private boolean hasTaken;

    private boolean isSkipped;



    public Dosage_saver_for_alarm_activity(int id, String medicationName, int medicationAmount, String dateTaken, String timeTaken, boolean hasTaken, boolean isSkipped) {
        this.id = id;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.dateTaken = dateTaken;
        this.timeTaken = timeTaken;
        this.hasTaken = hasTaken;
        this.isSkipped = isSkipped;
    }

    public boolean isSkipped() {
        return isSkipped;
    }

    public void setSkipped(boolean skipped) {
        isSkipped = skipped;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicationAmount() {
        return medicationAmount;
    }

    public void setMedicationAmount(int medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public boolean isHasTaken() {
        return hasTaken;
    }

    public void setHasTaken(boolean hasTaken) {
        this.hasTaken = hasTaken;
    }
}
