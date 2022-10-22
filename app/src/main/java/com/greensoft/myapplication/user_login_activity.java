package com.greensoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.greensoft.myapplication.jsony.shared_persistence;
import com.greensoft.myapplication.ui.HomeFragment;

import kotlin.contracts.Returns;

public class user_login_activity extends AppCompatActivity {

    EditText et_email,et_password,membership_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        membership_id = findViewById(R.id.et_login_membership_id);
    }

    public void login_now(View view) {
         Toast.makeText(this,"login",Toast.LENGTH_SHORT).show();
        email_validation email = new email_validation();
        boolean validation = email.isValid(et_email.getText().toString());
        if(et_password.getText().toString().isEmpty() || et_email.getText().toString().isEmpty()|| membership_id.getText().toString().isEmpty())
        {
            Toast.makeText(this,"All fields should be filled",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.login_body));
            return;
        }
         if(et_password.length() < 8)
         {
             Toast.makeText(this,"Password should have more than 8 characters",Toast.LENGTH_SHORT).show();
             YoYo.with(Techniques.Shake)
                     .duration(700)
                     .playOn(findViewById(R.id.et_login_password));
             return;
         }
        if(et_email.equals(""))
        {
            Toast.makeText(this,"Email should not be empty",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.et_login_email));
            return;
        }
        if(!validation)
        {
            Toast.makeText(this,"The Email is not valid",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.et_login_email));
            return;
        }

       // Toast.makeText(this,"the email is "+et_email.getText().toString()+" password is "+et_password.getText().toString(),Toast.LENGTH_SHORT).show();
        shared_persistence shad = new shared_persistence();
        shad.patient_login(getApplicationContext(),et_email.getText().toString(),et_password.getText().toString(),Integer.parseInt(membership_id.getText().toString()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               if(Context_maker.getInstance().isLogin_success())
               {
                   Toast.makeText(getApplicationContext(),Context_maker.getInstance().isLogin_success()+"",Toast.LENGTH_LONG).show();
                   Intent go_to_landing_page = new Intent(getApplicationContext(),MainActivity.class);
                   startActivity(go_to_landing_page);
                   finish();
               }else if(!Context_maker.getInstance().isLogin_success()){
                   YoYo.with(Techniques.Shake)
                           .duration(700)
                           .playOn(findViewById(R.id.et_login_email));
                   YoYo.with(Techniques.Shake)
                           .duration(700)
                           .playOn(findViewById(R.id.et_login_password));
               }
            }
        },500);

    }

    public void goto_register(View view) {

        Intent goto_register = new Intent(this,user_register_activity.class);
        startActivity(goto_register);
        finish();

    }
}