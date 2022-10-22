package com.greensoft.myapplication.jsony;

import com.google.gson.annotations.SerializedName;

public class medi_dosing {

    @SerializedName("Interval")
    private String Interval;
    @SerializedName("times1")
    private String times1;
    @SerializedName("times2")
    private String times2;
    @SerializedName("times3")
    private String times3;
    @SerializedName("times4")
    private String times4;
    @SerializedName("times5")
    private String times5;
    @SerializedName("times6")
    private String times6;

    public medi_dosing(String interval, String times1, String times2, String times3, String times4, String times5,String times6) {
        Interval = interval;
        this.times1 = times1;
        this.times2 = times2;
        this.times3 = times3;
        this.times4 = times4;
        this.times5 = times5;
        this.times6 = times6;
    }
    public medi_dosing(String interval, String times1, String times2, String times3, String times4,String times5) {
        Interval = interval;
        this.times1 = times1;
        this.times2 = times2;
        this.times3 = times3;
        this.times4 = times4;
        this.times5 = times5;

    }
    public medi_dosing(String interval, String times1, String times2, String times3,String times4) {
        Interval = interval;
        this.times1 = times1;
        this.times2 = times2;
        this.times3 = times3;
        this.times4 = times4;

    }
    public medi_dosing(String interval, String times1, String times2, String times3) {
        Interval = interval;
        this.times1 = times1;
        this.times2 = times2;
        this.times3 = times3;

    }
    public medi_dosing(String interval, String times1, String times2) {
        Interval = interval;
        this.times1 = times1;
        this.times2 = times2;

    }
    public medi_dosing(String interval, String times1) {
        Interval = interval;
        this.times1 = times1;
    }

    public String getInterval() {
        return Interval;
    }

    public String getTimes1() {
        return times1;
    }

    public String getTimes2() {
        return times2;
    }

    public String getTimes3() {
        return times3;
    }

    public String getTimes4() {
        return times4;
    }

    public String getTimes5() {
        return times5;
    }
}
