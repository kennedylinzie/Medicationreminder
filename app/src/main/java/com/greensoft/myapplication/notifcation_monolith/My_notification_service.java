package com.greensoft.myapplication.notifcation_monolith;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.jsony.Dosage_saver_for_alarm_activity;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.shared_persistence;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 */
public class My_notification_service extends JobService {

    private static final String TAG = "examplejobservice";
    private boolean jobCancelled = false;


    @SuppressLint("SpecifyJobSchedulerIdRange")
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.d(TAG,"JOB STARTED");
        doBackGroundwork(jobParameters);

        return true;
    }

    private void doBackGroundwork(JobParameters jobParameters) {

        new Thread(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                while (!jobCancelled) {
                    Log.d(TAG,"Run");
                    if(jobCancelled){
                        return;
                    }
                    try {

                        increment();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG,"Job finished");
                jobFinished(jobParameters,false);
            }
        }).start();

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"Job cancelled before completion");
        jobCancelled = true;
        return false;
    }


    boolean trywall=false;
    boolean trywall_before=false;
    public boolean isTrywall_before() {return trywall_before;}
    public void setTrywall_before(boolean trywall_before) {this.trywall_before = trywall_before;}
    public boolean isTrywall() {return trywall;}
    public void setTrywall(boolean trywall) {this.trywall = trywall;}
    boolean gateman = false;
    boolean gateman_before = false;
    Handler handler = new Handler();
    Handler handler_skip = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void increment() {
        // Toast.makeText(this, "nome", Toast.LENGTH_SHORT).show();

        List<Patient> generalInfoObject;
        generalInfoObject = null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json(getApplicationContext());
        if(output.equals("None")){
          return;
        }
        Gson gson = new Gson();
        Type foundlistType = new TypeToken<ArrayList<Patient>>() {
        }.getType();

        generalInfoObject = gson.fromJson(output, foundlistType);
        LinkedList<Dosage_saver_for_alarm_activity> cars = new LinkedList<Dosage_saver_for_alarm_activity>();



        if (isTrywall() == false) {

            if (generalInfoObject != null) {
                for (Patient dss : generalInfoObject) {
                    //System.out.println(dss.getName());
                    for (int i = 0; i < dss.getmDosage().size(); i++) {

                        ////////////////////////////



                        // String time = "23:53:00";
                        String date = dss.getmDosage().get(i).getDateTaken();
                        String time = dss.getmDosage().get(i).getTimeTaken();
                        //String date = "30-06-2022";
                        int year = Integer.parseInt(date.substring(0, 4));
                        int month = Integer.parseInt(date.substring(5, 7));
                        int date_ = Integer.parseInt(date.substring(8, 10));
                        //String date = "30-06-2022";
                        int hour = Integer.parseInt(time.substring(0, 2));
                        int minute = Integer.parseInt(time.substring(3, 5));
                        int seconds = Integer.parseInt(time.substring(6, 8));


                        LocalDateTime date1 = LocalDateTime.now();
                        LocalDateTime date2 = LocalDateTime.of(year, month, date_, hour, minute, seconds,00);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        String formatDateTime1 = date1.format(formatter);
                        String formatDateTime2 = date2.format(formatter);

                        String now_time = formatDateTime1.substring(0, 16);
                        String later_time = formatDateTime2.substring(0, 16);
                        if (now_time.equals(later_time) && !(Context_maker.getInstance().isBlocked_with_wall())) {
                            setTrywall(true);
                            if(dss.getmDosage().get(i).isHasTaken() == false && dss.getmDosage().get(i).isSkipped() == false){
                                gateman = true;

                                for (int snitch = 0; snitch < cars.size(); snitch++) {
                                    if(!cars.get(snitch).getMedicationName().equals(dss.getmDosage().get(i).getMedicationName())
                                    && !cars.get(snitch).getTimeTaken().equals(dss.getmDosage().get(i).getTimeTaken())){

                                        cars.add(new Dosage_saver_for_alarm_activity(dss.getmDosage().get(i).getId()
                                                , dss.getmDosage().get(i).getMedicationName()
                                                , dss.getmDosage().get(i).getMedicationAmount()
                                                , dss.getmDosage().get(i).getDateTaken()
                                                , dss.getmDosage().get(i).getTimeTaken()
                                                , dss.getmDosage().get(i).isHasTaken()
                                                ,dss.getmDosage().get(i).isSkipped()));

                                    }
                                }


                            }

                            Runnable runnableCode = new Runnable() {
                                @Override
                                public void run() {
                                    if(gateman){
                                        Context_maker.getInstance().setBlocked_with_wall(true);
                                        Context_maker.getInstance().setCars(cars);
                                        Intent intent_diag = new Intent(getApplicationContext(), The_alarm_man.class);
                                        intent_diag.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent_diag);
                                        gateman = false;
                                    }
                                    setTrywall(false);
                                }
                            };
                            // Run the above code block on the main thread after 2 seconds
                            handler.postDelayed(runnableCode, 1000);

                        } else {
                            Log.d("love", date1 + " " + date2);
                        }
                        ////////befor and after checker
                    if (date2.isBefore(date1) && !(Context_maker.getInstance().isBlocked_with_wall2())
                                           && !(Context_maker.getInstance().isBlocked_with_wall())) {
                        setTrywall_before(true);
                        if(dss.getmDosage().get(i).isHasTaken() == false && dss.getmDosage().get(i).isSkipped() == false){

                            String skipped_dates = date2.format(formatter);
                            String time_ = formatDateTime1.substring(0, 10);
                            String mo = skipped_dates.substring(0, 10);
                            if(time_.equals(mo) && !(Context_maker.getInstance().isBlocked_with_wall2()))
                            {
                                gateman_before  = true;
                                // Log.d("love_", mo);
                                for (int snitch = 0; snitch < cars.size(); snitch++) {
                                    if(cars.get(snitch).getMedicationName().equals(dss.getmDosage().get(i).getMedicationName())
                                            && cars.get(snitch).getTimeTaken().equals(dss.getmDosage().get(i).getTimeTaken())){
                                        return;
                                    }}

                                cars.add(new Dosage_saver_for_alarm_activity(dss.getmDosage().get(i).getId()
                                        , dss.getmDosage().get(i).getMedicationName()
                                        , dss.getmDosage().get(i).getMedicationAmount()
                                        , dss.getmDosage().get(i).getDateTaken()
                                        , dss.getmDosage().get(i).getTimeTaken()
                                        , dss.getmDosage().get(i).isHasTaken()
                                        ,dss.getmDosage().get(i).isSkipped()));

                                Runnable runnableCode_skip = new Runnable() {
                                    @Override
                                    public void run() {
                                        if(gateman_before){
                                            Context_maker.getInstance().setBlocked_with_wall2(true);
                                            Context_maker.getInstance().setCars(cars);
                                            if(Context_maker.getInstance().getTrap_counter() == 0) {
                                                Context_maker.getInstance().setTrap_counter(Context_maker.getInstance().getTrap_counter()+1);
                                                Intent intent_diag = new Intent(getApplicationContext(), The_alarm_man.class);
                                                intent_diag.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent_diag);
                                            }
                                            gateman_before  = false;
                                        }
                                        setTrywall_before(false);
                                    }
                                };
                                // Run the above code block on the main thread after 2 seconds
                                handler_skip.postDelayed(runnableCode_skip, 10000);
                            }

                        }


                      }


                    }

                }


            }
        }
    }

}
