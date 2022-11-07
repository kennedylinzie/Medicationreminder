package com.greensoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Flash_screen extends AppCompatActivity {
    TextView hmm;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        Intent in = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
        finish();



        hmm = findViewById(R.id.textView13);
        img = findViewById(R.id.tab1);
        YoYo.with(Techniques.Tada)
                .duration(400)
                .repeat(10)
                .playOn(findViewById(R.id.tab1));

        YoYo.with(Techniques.BounceInDown)
                .duration(1000)
                .playOn(findViewById(R.id.splash));
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(findViewById(R.id.justname));

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
               // hmm.setText("seconds remaining: " + millisUntilFinished / 1000);
                if((millisUntilFinished / 1000)<4){
                    YoYo.with(Techniques.FadeOut)
                            .duration(400)
                            .playOn(findViewById(R.id.tab1));
                }
                if((millisUntilFinished / 1000)<2){
                        img.setVisibility(View.INVISIBLE);
                }
                // logic to set the EditText could go here
            }

            public void onFinish() {
               // hmm.setText("done!");
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                finish();
            }

        }.start();

    }
}