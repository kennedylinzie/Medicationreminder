package com.greensoft.myapplication.jsony;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class connectionsManager {


    private final String locallink = "http://10.0.2.2"; //for use in emurator enviroment
    private final String wifilink = "http://192.168.137.1"; //connectify or wifi defualt ip addree


    private final String patient_login =                           locallink +"/MR/v2/Api.php?apicall=login";
    private final String patient_register =                        locallink +"/MR/v2/Api.php?apicall=signup";
    private final String patient_backup_prescription =             locallink +"/MR/v2/Api.php?apicall=Update_prescription";
    private final String patient_delete_account =                  locallink +"/MR/v1/delete_account.php";
    private final String patient_update_accounts =                 locallink +"/MR/v2/Api.php?apicall=Update_user";
    private final String patient_change_password =                 locallink +"/MR/v2/Api.php?apicall=Update_password";
    private final String patient_data_pull =                       locallink +"/MR/v2/Api.php?apicall=pullUserData";
    private final String adding_guards =            locallink +"/MR/v2/Api.php?apicall=add_gurdian";
    private final String pull_guards =              locallink +"/MR/v2/Api.php?apicall=pullguardData";
    private final String delete_guards =            locallink +"/MR/v2/Api.php?apicall=Delete_guard";
    private final String check_connections =        locallink +"/MR/v2/Api.php?apicall=ping";
    private final String add_patient_prescription = locallink +"/MR/v2/Api.php?apicall=add_prescription";
    private final String patient_login_ver =        locallink +"/MR/v2/Api.php?apicall=login_verified";

    public String getPatient_login_ver() {
        return patient_login_ver;
    }

    public String getAdd_patient_prescription() {
        return add_patient_prescription;
    }

    public String getPatient_change_password() {
        return patient_change_password;
    }

    public String getPatient_data_pull() {
        return patient_data_pull;
    }

    public String getCheck_connections() {
        return check_connections;
    }

    public String getPatient_login() {
        return patient_login;
    }

    public String getPatient_register() {
        return patient_register;
    }

    public String getPatient_backup_prescription() {
        return patient_backup_prescription;
    }

    public String getAdding_guards() {
        return adding_guards;
    }

    public String getPatient_delete_account() {
        return patient_delete_account;
    }

    public String getPatient_update_accounts() {
        return patient_update_accounts;
    }

    public String getPull_guards() {
        return pull_guards;
    }

    public String getDelete_guards() {
        return delete_guards;
    }
}
