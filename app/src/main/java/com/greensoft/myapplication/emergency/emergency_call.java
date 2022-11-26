package com.greensoft.myapplication.emergency;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.Add_medication;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.Gurdian;
import com.greensoft.myapplication.MainActivity;
import com.greensoft.myapplication.R;
import com.greensoft.myapplication.jsony.shared_persistence;
import com.greensoft.myapplication.notifcation_monolith.The_alarm_man;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class emergency_call extends AppCompatActivity {

    private TextView counter_view;
    private int counter = 15;
    private ImageView clock;
    private boolean timer_cut=false;
    private Vibrator vibrator;
    Handler handler_timer;
    Runnable runnableCode_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call);
        counter_view = findViewById(R.id.em_counter);
        clock = findViewById(R.id.em_alarm);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        getWindow().setTitle("");
        //getActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        YoYo.with(Techniques.BounceInUp)
                .duration(2000)
                .playOn(findViewById(R.id.em_alarm_holder));
        permission();
        /////////////////////
        handler_timer = new Handler();
        // Define the code block to be executed
        runnableCode_timer = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {

                counter_view.setText(String.valueOf(counter));

                if(timer_cut == false){
                    MediaPlayer mMediaPlayer;
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.beep);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setLooping(false);
                    mMediaPlayer.start();

                    YoYo.with(Techniques.Flash)
                            .duration(700)
                            .playOn(findViewById(R.id.em_counter));
                    YoYo.with(Techniques.Wobble)
                            .duration(700)
                            .playOn(findViewById(R.id.em_alarm));
                    vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                }
                if(counter == 0 && timer_cut == false){
                    sos();
                    handler_timer.removeCallbacks(runnableCode_timer);
                    timer_cut = true;
                    handler_timer.removeCallbacks(runnableCode_timer);
                    stop_runnable();

                    Intent openActivity = new Intent(emergency_call.this, MainActivity.class);
                    startActivity(openActivity);
                   finish();
                }
                counter--;
                handler_timer.postDelayed(this, 1000);
            }
        };

        handler_timer.post(runnableCode_timer);
        ////////////////////


        int colorFrom = getResources().getColor(R.color.white);
        int colorTo = getResources().getColor(R.color.red);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                counter_view.setTextColor((int) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();

    }

    public void yes(View view) {
            sos();
            timer_cut = true;
            stop_runnable();

            Intent openActivity = new Intent(emergency_call.this, MainActivity.class);
            startActivity(openActivity);
            finish();
    }

    public void no(View view) {
        timer_cut = true;
        stop_runnable();
        Intent openActivity = new Intent(emergency_call.this, MainActivity.class);
        startActivity(openActivity);
        finish();
    }

    public void sos(){
        stop_runnable();
        sendmessage();
        Context_maker.getInstance().setReady_for_call(true);

    }

    public void stop_runnable(){
        handler_timer.removeCallbacks(runnableCode_timer);
        finish();
    }

    public void sendmessage() {

        if(ContextCompat.checkSelfPermission(emergency_call.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(emergency_call.this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(emergency_call.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
        {
            //when permission is granted
            //create method
            send_sms();

        }else {
            //when permission is granted
            //request permission
            ActivityCompat.requestPermissions(emergency_call.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},100);
            ActivityCompat.requestPermissions(emergency_call.this,new String[]{Manifest.permission.CALL_PHONE},101);
        }


    }

    public void permission() {
        if(ContextCompat.checkSelfPermission(emergency_call.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(emergency_call.this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(emergency_call.this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
        {
            //when permission is granted
            //create method
           // send_sms();
        }else {
            //when permission is granted
            //request permission
            ActivityCompat.requestPermissions(emergency_call.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},100);
            ActivityCompat.requestPermissions(emergency_call.this,new String[]{Manifest.permission.CALL_PHONE},101);
        }
    }


    public void send_sms()
    {
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(getApplicationContext());

        List<Gurdian> people_backup = new LinkedList<Gurdian>();
        Gurdian g = new Gurdian();
        Context_maker.getInstance().setConnection(false);
        String json =  g.get_guards(getApplicationContext());
        if(!json.equals("")){
            Gson gson = new Gson();
            Type foundlistType = new TypeToken<ArrayList<Gurdian>>(){}.getType();
            people_backup = gson.fromJson(json, foundlistType);
            String[] new_pips = new String[people_backup.size()];

            for (int i = 0; i < people_backup.size(); i++) {
                new_pips[i] =  people_backup.get(i).getName();
                String sphone = people_backup.get(i).getPhone_number().trim();

                if(!sphone.equals(""))
                {

                    //initialise sms manager
                    SmsManager smsManager = SmsManager.getDefault();
                    //send text
                    smsManager.sendTextMessage(sphone,null,"Hey "+ new_pips[i]+" "+shad.getFirst_name() +" has an emergency",null,null);

                    Toast.makeText(getApplicationContext(),"Sms sent successfuly",Toast.LENGTH_LONG).show();

                    // Toast.makeText(getApplicationContext(),"limit",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"please fill in the records",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    int back_add=0;
    @Override
    public void onBackPressed() {
        // Example of logic
        if ( back_add < 1 ) {
            timer_cut = true;
            //Toast.makeText(this, "The second time will close the window", Toast.LENGTH_SHORT).show();
            Intent openActivity = new Intent(emergency_call.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}