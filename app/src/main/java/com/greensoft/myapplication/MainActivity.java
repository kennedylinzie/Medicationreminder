package com.greensoft.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.databinding.ActivityMainBinding;
import com.greensoft.myapplication.emergency.emergency_call;
import com.greensoft.myapplication.jsony.second_prescription;
import com.greensoft.myapplication.jsony.shared_persistence;
import com.greensoft.myapplication.notifcation_monolith.My_notification_service;
import com.greensoft.myapplication.notifcation_monolith.The_alarm_man;
import com.greensoft.myapplication.ui.HomeFragment;
import com.greensoft.myapplication.ui.ProfileFragment;
import com.greensoft.myapplication.ui.SettingsFragment;
import com.greensoft.myapplication.wakerandshaker.AlarmTriggerBroadcastReceiver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//this will be accessible when the the below is addes to gradle only
// buildFeatures{
//        viewBinding true
//    }
public class MainActivity extends AppCompatActivity implements ActionBottomDialogFragment.ItemClickListener, View.OnClickListener
, SensorEventListener {

    ActivityMainBinding binding;
    Button btnPopup;
    private static final String TAG = "examplejobservice";
    // Button start,stop;
    AlertDialog.Builder builder;

    ////vibration system
    private SensorManager sensorManager;
    private Sensor myaccelerometerSensor;
    private boolean isSensorAvailable,ifIsnotFirstTime=false;
    private float current_x,current_y,current_z;
    private float last_x,last_y,last_z;
    private float x_diffrence,y_diffrence,z_diffrence;
    private float shakeThreshold = 20f;
    private Vibrator vibrator;
    private boolean isDialog_active;
    AlertDialog alertDialog_TROUBLE;
    private int counter = 0;
    private TextToSpeech textToSpeech;

    public static final String ALARM_TYPE_REPEAT = "ALARM_TYPE_REPEAT";
    public static final String ALARM_TYPE = "ALARM_TYPE";
    public static final String ALARM_DESCRIPTION = "ALARM_DESCRIPTION";

    private AlarmManager alarmManager = null;

    private PendingIntent pendingIntent = null;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
//        Intent in = new Intent(this,Flash_screen.class);
//        startActivity(in);
          shared_persistence shad = new shared_persistence();
//        shad.clear_json(getApplicationContext());
//        shad.clear_user(getApplicationContext());
//        shad.clear_json_user_prep(getApplicationContext());
//        shad.clear_emergency_number(getApplicationContext());


         ///should always be at the top checks user login status
       // shared_persistence shad = new shared_persistence();
        shad.get_user_data(getApplicationContext());
        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        //shad.clear_json_user_prep(getApplicationContext());
        //shad.clear_json(getApplicationContext());

       // shad.get_json(getApplicationContext());
        Context_maker.getInstance().setMyContext(getApplicationContext());

        if(Context_maker.getInstance().isReady_for_call()){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},101);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+"+265"+shad.get_emergency_number(getApplicationContext())));

            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(intent);
            }else{
                Toast.makeText(MainActivity.this,"Permission not granted.",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},101);
            }
            Context_maker.getInstance().setReady_for_call(false);
        }

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            File file = new File(Environment.getExternalStorageDirectory() + "/RMrecordings");
            if (!file.exists()) {
                boolean res = file.mkdirs();
            }

        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

//        YoYo.with(Techniques.BounceInDown)
//                .duration(2000)
//                .playOn(findViewById(R.id.top_appbar));

        YoYo.with(Techniques.BounceInUp)
                .duration(2000)
                .playOn(findViewById(R.id.adid));
        YoYo.with(Techniques.BounceIn)
                .duration(2000)
                .playOn(findViewById(R.id.emergency));
        YoYo.with(Techniques.BounceInLeft)
                .duration(2000)
                .playOn(findViewById(R.id.adid));




        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            myaccelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isSensorAvailable = true;
        }else{
            //x.setText("accelerometer sensor is not available");
            isSensorAvailable = false;
        }
        alertDialog_TROUBLE = new AlertDialog.Builder(MainActivity.this).create(); //Use context
        isDialog_active = false;
        alertDialog_TROUBLE.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                isDialog_active = false;
            }
        });

        //drop the loop wall
        Handler handler_wait = new Handler();
        Runnable runnableCode_wait = new Runnable() {
            @Override
            public void run() {
                Context_maker.getInstance().setBlocked_with_wall(false);
            }
        };
        handler_wait.postDelayed(runnableCode_wait, 1000);
        //drop the loop wall
        Random rn = new Random();
        int kick = rn.nextInt(1000) + 500;
        Handler handler_wait2 = new Handler();
        Runnable runnableCode_wait2 = new Runnable() {
            @Override
            public void run() {
                Context_maker.getInstance().setBlocked_with_wall2(false);
            }
        };
        handler_wait2.postDelayed(runnableCode_wait2, kick);


          if (shad.getFirst_name() != null) {

                    if (shad.getFirst_name().equals("")) {
                        Toast.makeText(getApplicationContext(), shad.getFirst_name(), Toast.LENGTH_SHORT).show();
                        goto_login();
                    }else{
                        //notification service starting center
                        if (!foregroundServicesRunning()) {
                            foregroundServicesRunning();
                            start_repeat_service();
                        }
                    }

                } else {
                    goto_login();
                }

                foregroundServicesRunning();
         shared_persistence shud = new shared_persistence();
         shud.get_user_data(getApplicationContext());
         String em = shud.getEmail();
         String pas = shud.getPassword();

        Context_maker.getInstance().check_verified(shud.getEmail(),shud.getFirst_name());


        //appInitialization();
        //start = findViewById(R.id.a_stat);
        /*  stop = findViewById(R.id.a_stop);*/

        //start.setOnClickListener(this);
        //* stop.setOnClickListener(this);*/

        // showBottomSheet();

        DrawerLayout navigationView_ = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView1 = navigationView_.findViewById(R.id.navigation_view);
        View headerView = navigationView1.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_name);
        navUsername.setText(shad.getFirst_name() +" "+shad.getLast_name());


        btnPopup = findViewById(R.id.adid);



        MaterialToolbar toolbar = findViewById(R.id.top_appbar);
        toolbar.bringToFront();


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
      /*  TextView name = findViewById(R.id.user_name);
        name.setText("ooooooooooooo");*/



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);

                switch (id) {
                    case R.id.nav_share:
                        Toast.makeText(Context_maker.getInstance().getMyContext(), "share was clicked", Toast.LENGTH_SHORT).show();

                        AlertDialog alertDialog_ = new AlertDialog.Builder(MainActivity.this).create(); //Use context
                        alertDialog_.setTitle("Backup");
                        alertDialog_.setMessage("The backup will be able to be viewed by the Company,but your privacy will be respected and wont be shared");
                        alertDialog_.setIcon(R.drawable.backup);

                        alertDialog_.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog_.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        shared_persistence shad = new shared_persistence();
                                        if(!shad.get_json(getApplicationContext()).isEmpty())
                                        {
                                            shad.patient_backup_prescription(getApplicationContext());
                                            Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(MainActivity.this, "No data to backup", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog_.show();
                        break;
                    case R.id.nav_manage_account:
                       // Toast.makeText(MainActivity.this, "profile was clicked", Toast.LENGTH_SHORT).show();
                        Intent goto_account = new Intent(MainActivity.this,user_update.class);
                        startActivity(goto_account);
                        finish();
                        break;
                    case R.id.nav_download_backup:
                        AlertDialog alertDialog_up = new AlertDialog.Builder(MainActivity.this).create(); //Use context
                        alertDialog_up.setTitle("Restore backup");
                        alertDialog_up.setMessage("If you have an existing medication backup,this will restore it.");
                        alertDialog_up.setIcon(R.drawable.ic_baseline_cloud_download_24);

                        alertDialog_up.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog_up.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        shared_persistence shad = new shared_persistence();
                                        shad.get_user_data(getApplicationContext());
                                        shad.patient_recover(getApplicationContext(),shad.getUU_ID());
                                        recreate();

                                        dialog.dismiss();
                                    }
                                });
                        alertDialog_up.show();
                        break;
                    case R.id.nav_logout:
                        //Toast.makeText(MainActivity.this, "login was clicked", Toast.LENGTH_SHORT).show();
                        ///////////////

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Use context
                        alertDialog.setTitle("Logout");
                        alertDialog.setMessage("Be aware you will lose your prescription data, if you didn't backup. CONTINUE?");
                        alertDialog.setIcon(R.drawable.logsout);

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Backup and log out",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        shared_persistence shad = new shared_persistence();
                                        if(!shad.get_json(getApplicationContext()).isEmpty())
                                        {
                                            shad.patient_backup_prescription(getApplicationContext());
                                        }
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                if(Context_maker.getInstance().isPrescription_backup_success())
                                                {
                                                    shad.clear_json(getApplicationContext());
                                                    shad.clear_user(getApplicationContext());
                                                    shad.clear_json_user_prep(getApplicationContext());
                                                    shad.clear_emergency_number(getApplicationContext());
                                                    goto_login();
                                                    dialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                                }else if(!Context_maker.getInstance().isPrescription_backup_success()){
                                                    Toast.makeText(MainActivity.this, "unable to contact server,make sure you have internet", Toast.LENGTH_LONG).show();
                                                }


                                            }
                                        },500);




                                    }
                                });

                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        shared_persistence shad = new shared_persistence();
                                        shad.clear_json(getApplicationContext());
                                        shad.clear_user(getApplicationContext());
                                        shad.clear_json_user_prep(getApplicationContext());
                                        shad.clear_emergency_number(getApplicationContext());
                                        goto_login();
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        ///////////
                        break;


                }

                return true;
            }
        });

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        break;

                    case R.id.medications:
                        replaceFragment(new ProfileFragment());
                        break;

                    case R.id.more:
                        replaceFragment(new SettingsFragment());
                        break;
                }
                return true;
            }
        });



    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.user_name);
//
//        if (item.getTitle().equals("Set to 'In bed'")) {
//            item.setTitle("Set to 'Out of bed'");
//           // inBed = false;
//        } else {
//            item.setTitle("Set to 'In bed'");
//           // inBed = true;
//        }
//        return super.onPrepareOptionsMenu(menu);
//    }

 /*   @Override
    public void onUserInteraction() {
        alertDialog_TROUBLE.dismiss();
         isDialog_active = false;
        Toast.makeText(this, "Touch anywhere happened", Toast.LENGTH_SHORT).show();
        super.onUserInteraction();
    }
*/




    public void showBottomSheet() {
        DialogFragment addPhotoBottomDialogFragment =
                ActionBottomDialogFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ActionBottomDialogFragment.TAG);
    }

    @Override
    public void onItemClick(String item) {
        //tvSelectedItem.setText("Selected action item is " + item);
        Toast.makeText(this, "" + item, Toast.LENGTH_SHORT).show();
    }

    public void goto_login() {
        Intent startEXITactivity = new Intent(this, user_login_activity.class);
        startActivity(startEXITactivity);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        // first parameter is the file for icon and second one is menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // We are using switch case because multiple icons can be kept
        switch (item.getItemId()) {
            case R.id.shareButton:

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                // type of the content to be shared
                sharingIntent.setType("text/plain");

                // Body of the content
                String shareBody = "Your Body Here";

                // subject of the content. you can share anything
                String shareSubject = "Your Subject Here";

                // passing body of the content
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                // passing subject of the content
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flame_layout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        replaceFragment(new HomeFragment());
        super.onBackPressed();
    }


    public void OPEN_ADD_MEDICATION_PAGE(View view) {

        PopupMenu popup = new PopupMenu(MainActivity.this, btnPopup);
        //inflate the popup
        popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());
        //registering popup with onMenuItenmClicjListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //Toast.makeText(MainActivity.this, "" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                if ("Add Prescription".equals(menuItem.getTitle())) {
                    startActivity(new Intent(getApplicationContext(), Add_medication.class));
                    finish();
                    //showBottomSheet();
                }
                if ("Add notes".equals(menuItem.getTitle())) {

                }
                if ("Add Guardian".equals(menuItem.getTitle())) {
                    Intent go_to_add = new Intent(MainActivity.this,Add_gurdian.class);
                    startActivity(go_to_add);
                    finish();


                }


                return true;
            }
        });
        popup.show();
    }
    //restart the service if it not alive

    public boolean foregroundServicesRunning() {
        start_repeat_service();
        ComponentName componentName = new ComponentName(this, My_notification_service.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultcode = scheduler.schedule(info);
        if (resultcode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "JOB SCHEDULED");
            return true;
        } else {

            Log.d(TAG, "JOB SCHEDULing failed");
        }

        return false;
    }

    public void stop_jobs() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "JOB cancelled");
    }


    @Override
    public void onClick(View view) {

       /*if(view == start)
       {
            Intent intent = new Intent(this, The_alarm_man.class);
            startActivity(intent);
       }*/

    }

    public void swipe_fragment()
    {
        replaceFragment(new ProfileFragment());
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        current_x = sensorEvent.values[0];
        current_y = sensorEvent.values[1];
        current_z = sensorEvent.values[2];

        if(ifIsnotFirstTime)
        {
            x_diffrence = Math.abs(last_x - current_x);
            y_diffrence = Math.abs(last_y - current_y);
            z_diffrence = Math.abs(last_z - current_z);

            if((x_diffrence > shakeThreshold && y_diffrence > shakeThreshold)||
                    (x_diffrence > shakeThreshold && z_diffrence > shakeThreshold)||
                    (y_diffrence > shakeThreshold && z_diffrence > shakeThreshold)
            ){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));

                    Intent go_to_call = new Intent(getApplicationContext(),emergency_call.class);
                    startActivity(go_to_call);
                    finish();

                }
            }else{
                //vibrator.vibrate(500);
                //depricated api
            }

        }

        last_x = current_x;
        last_y = current_y;
        last_z = current_z;
        ifIsnotFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(isSensorAvailable){
            sensorManager.registerListener(this,myaccelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isSensorAvailable){
            sensorManager.unregisterListener(this);
        }


    }

    public void TROUBLE_CALL(View view) {
      Intent go_to_call = new Intent(getApplicationContext(),emergency_call.class);
      startActivity(go_to_call);
      finish();
    }

    public void start_repeat_service(){

        Intent intent = new Intent(getApplicationContext(), AlarmTriggerBroadcastReceiver.class);
        intent.putExtra(ALARM_TYPE, ALARM_TYPE_REPEAT);
        intent.putExtra(ALARM_DESCRIPTION, "Repeat alarm start this broadcast.");
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long alarmStartTime = System.currentTimeMillis();
        // This is too short, it will be expanded by android os to 60 seconds by default.
        long alarmExecuteInterval = 10*1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmExecuteInterval, pendingIntent);

       // Toast.makeText(getApplicationContext(), "A repeat alarm has been created. This alarm will send to a broadcast receiver.", Toast.LENGTH_LONG).show();

    }
    public void stop_repeat_service(){

        alarmManager.cancel(pendingIntent);
       // Toast.makeText(MainActivity.this, "Cancel current alarm.", Toast.LENGTH_LONG).show();

    }



}