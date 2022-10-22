package com.greensoft.myapplication.jsony;

import com.google.gson.annotations.SerializedName;

public class Dosage_saver {

    @SerializedName("id")
    private int id;
    @SerializedName("medicationName")
    private String medicationName;
    @SerializedName("medicationAmount")
    private int medicationAmount;
    @SerializedName("dataTaken")
    private String dateTaken;
    @SerializedName("timeTaken")
    private String timeTaken;
    @SerializedName("hasTaken")
    private boolean hasTaken;
    @SerializedName("Condition")
    private String Condition;
    @SerializedName("refill_reminders")
    private boolean refill_reminders;
    @SerializedName("strength_unit")
    private String strength_unit;
    @SerializedName("strength_amount")
    private int strength_amount;
    @SerializedName("skipping_reason")
    private String skipping_reason;
    private boolean skipped;
    private String breast_feeding;
    private String pregnant;
    private String intake_method;
    private String takewith_meal;






    /*public Dosage_saver(int id, String medicationName, int medicationAmount, String dateTaken, String timeTaken, boolean hasTaken, String condition, boolean refill_reminders, String strength_unit, int strength_amount, String skipping_reason) {
        this.id = id;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.dateTaken = dateTaken;
        this.timeTaken = timeTaken;
        this.hasTaken = hasTaken;
        this.Condition = condition;
        this.refill_reminders = refill_reminders;
        this.strength_unit = strength_unit;
        this.strength_amount = strength_amount;
        this.skipping_reason = skipping_reason;
    }*/

   /* public Dosage_saver(int id, String medicationName, int medicationAmount, String dateTaken, String timeTaken, boolean hasTaken, String condition, boolean refill_reminders, String strength_unit, int strength_amount, String skipping_reason, boolean skipped) {
        this.id = id;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.dateTaken = dateTaken;
        this.timeTaken = timeTaken;
        this.hasTaken = hasTaken;
        Condition = condition;
        this.refill_reminders = refill_reminders;
        this.strength_unit = strength_unit;
        this.strength_amount = strength_amount;
        this.skipping_reason = skipping_reason;
        this.skipped = skipped;
    }*/

    public Dosage_saver(int id, String medicationName, int medicationAmount, String dateTaken, String timeTaken, boolean hasTaken, String condition, boolean refill_reminders, String strength_unit, int strength_amount, String skipping_reason, boolean skipped, String breast_feeding, String pregnant, String intake_method, String takewith_meal) {
        this.id = id;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.dateTaken = dateTaken;
        this.timeTaken = timeTaken;
        this.hasTaken = hasTaken;
        Condition = condition;
        this.refill_reminders = refill_reminders;
        this.strength_unit = strength_unit;
        this.strength_amount = strength_amount;
        this.skipping_reason = skipping_reason;
        this.skipped = skipped;
        this.breast_feeding = breast_feeding;
        this.pregnant = pregnant;
        this.intake_method = intake_method;
        this.takewith_meal = takewith_meal;
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
}
