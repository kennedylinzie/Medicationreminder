package com.greensoft.myapplication.notifcation_monolith;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.Context_maker;

import com.greensoft.myapplication.Gurdian;
import com.greensoft.myapplication.MainActivity;
import com.greensoft.myapplication.Main_save_meds;
import com.greensoft.myapplication.R;
import com.greensoft.myapplication.dialog_activity_recycler.sinle_role_notification_Adapter;
import com.greensoft.myapplication.dialog_activity_recycler.single_role_notification_model;
import com.greensoft.myapplication.emergency.emergency_call;
import com.greensoft.myapplication.jsony.Dosage_saver_for_alarm_activity;
import com.greensoft.myapplication.jsony.shared_persistence;
import com.greensoft.myapplication.user_register_activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class The_alarm_man extends AppCompatActivity implements View.OnClickListener {
    private Button diag_take,diag_skip;
    private LinkedList<Dosage_saver_for_alarm_activity> tasks = new LinkedList<Dosage_saver_for_alarm_activity>();
    private TextView temp;
    private Handler handler = new Handler();
    private Runnable runnableCode;
    private TextToSpeech textToSpeech;
    private String first_string = "Hello, the medication you will take is ";
    private String second_string="";
    private Handler handler_speech = new Handler();
    private Runnable runnableCode_speech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_alarm_man);
        Context_maker.getInstance().setMyContext(getApplicationContext());
        diag_take = findViewById(R.id.diag_drug_take);
        diag_skip = findViewById(R.id.diag_drug_skip);
       // temp = findViewById(R.id.the_temp_view);
        diag_take.setOnClickListener(this);
        diag_skip.setOnClickListener(this);
        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        permission();
        sendmessage();
        YoYo.with(Techniques.BounceInUp)
                .duration(2000)
                .playOn(findViewById(R.id.the_alarm_holder));

        getWindow().setTitle("");
        //getActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

       // Intent start_tone = new Intent(getApplicationContext(), My_sound_service.class);
        //startService(start_tone);
       // String input = editTextinput.getText().toString();

        // create an object textToSpeech and adding features into it
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i == TextToSpeech.SUCCESS){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

       speech();
       /// LinkedList<Dosage_saver_for_alarm_activity> cars = new LinkedList<Dosage_saver_for_alarm_activity>();
       // single_role_notification_model[] myData = null;


                tasks = Context_maker.getInstance().getCars();

                single_role_notification_model[] obj = new single_role_notification_model[tasks.size()];
                String input="";
                for (int i = 0; i < tasks.size(); i++) {
                    //   temp.setText(tasks.get(i).);
                    // obj[i] = new single_role_notification_model(tasks.get(i).getMedicationName(),tasks.get(i).getMedicationAmount(), R.drawable.pill);
                    obj[i] = new single_role_notification_model();

                    obj[i].set_data(tasks.get(i).getMedicationName(),tasks.get(i).getMedicationAmount(), R.drawable.pill,tasks.get(i).getTimeTaken());


                    input = input+""+ tasks.get(i).getMedicationName() + '\n' ;
                    second_string = second_string + " , " + tasks.get(i).getMedicationName()+" at "+tasks.get(i).getTimeTaken();
                }

                Intent serviceIntent = new Intent(getApplicationContext(),My_sound_service_v1.class);
                serviceIntent.putExtra("inputExtra",input);
                startService(serviceIntent);


                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                sinle_role_notification_Adapter sinlerolenotificationAdapter = new sinle_role_notification_Adapter(obj);
                recyclerView.setAdapter(sinlerolenotificationAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));




        // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .playOn(findViewById(R.id.al_man_alarm));
                YoYo.with(Techniques.Bounce)
                        .duration(500)
                        .playOn(findViewById(R.id.the_alarm_holder));
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .playOn(findViewById(R.id.sun));

               // vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.EFFECT_HEAVY_CLICK));

                handler.postDelayed(this, 1400);

            }
        };
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);


    }

    @Override
    public void onClick(View view) {

       // Intent intent = new Intent(this, My_notification_service.class);
      //  Intent start_tone = new Intent(getApplicationContext(), My_sound_service.class);
        Intent serviceIntent = new Intent(this,My_sound_service_v1.class);
        textToSpeech.stop();
        textToSpeech.shutdown();

        if(view == diag_take)
        {
            handler_speech.removeCallbacks(runnableCode_speech);



            Toast.makeText(this, ".......", Toast.LENGTH_SHORT).show();

             tasks = Context_maker.getInstance().getCars();
             Main_save_meds mn = new Main_save_meds();

            for (int i = 0; i < tasks.size(); i++) {
                //   temp.setText(tasks.get(i).);
                String d = tasks.get(i).getDateTaken()+" "+tasks.get(i).getTimeTaken();
                mn.change_taken_status(tasks.get(i).getMedicationName(),tasks.get(i).getId(),d);

            }

            stopService(serviceIntent);

            Intent openActivity = new Intent(The_alarm_man.this, MainActivity.class);
            startActivity(openActivity);
            finish();

        }else if(view == diag_skip)
        {
            handler_speech.removeCallbacks(runnableCode_speech);
            Toast.makeText(this, ".......", Toast.LENGTH_SHORT).show();
            tasks = Context_maker.getInstance().getCars();
            Main_save_meds mn = new Main_save_meds();

            for (int i = 0; i < tasks.size(); i++) {
                //   temp.setText(tasks.get(i).);
                String d = tasks.get(i).getDateTaken()+" "+tasks.get(i).getTimeTaken();
                mn.change_skipped_status(tasks.get(i).getMedicationName(),tasks.get(i).getId(),d);
            }
            stopService(serviceIntent);
            Intent openActivity = new Intent(The_alarm_man.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }

    }





    public void speech() {

        // Define the code block to be executed
        runnableCode_speech = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                textToSpeech.speak(first_string +""+second_string,TextToSpeech.QUEUE_FLUSH,null,null);
                handler_speech.postDelayed(this, 10000);
               handler_speech.removeCallbacks(runnableCode_speech);
                textToSpeech.stop();
                textToSpeech.shutdown();
            }
        };
        // Start the initial runnable task by posting through the handler
        handler_speech.post(runnableCode_speech);

    }

    public void permission() {

        if(ContextCompat.checkSelfPermission(The_alarm_man.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(The_alarm_man.this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED)
        {
            //when permission is granted
            //create method
           // send_sms();
        }else {
            //when permission is granted
            //request permission
            ActivityCompat.requestPermissions(The_alarm_man.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},100);
        }
    }


    public void sendmessage() {

        if(ContextCompat.checkSelfPermission(The_alarm_man.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(The_alarm_man.this, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED)
        {
            //when permission is granted
            //create method
            send_sms();

        }else {
            //when permission is granted
            //request permission
            ActivityCompat.requestPermissions(The_alarm_man.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},100);

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
                String sphone = people_backup.get(i).getPhone_number()+"";
                if(!sphone.equals(""))
                {
                    //initialise sms manager
                    SmsManager smsManager = SmsManager.getDefault();
                    //send text
                    smsManager.sendTextMessage(sphone,null,shad.getFirst_name() +" has to take the following medication"+ second_string,null,null);

                    Toast.makeText(getApplicationContext(),"sms send successfuly",Toast.LENGTH_LONG).show();

                    // Toast.makeText(getApplicationContext(),"limit",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"please fill in the records",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {

        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }
}