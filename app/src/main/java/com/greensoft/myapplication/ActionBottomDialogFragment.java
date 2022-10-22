package com.greensoft.myapplication;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.second_prescription;
import com.greensoft.myapplication.jsony.shared_persistence;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    // Initializing all variables..
    private TextView recordTV, stoprecordTV, playTV, stopplayTV, statusTV;

    // creating a variable for medi recorder object class.
    private MediaRecorder mRecorder;

    // creating a variable for mediaplayer class
    private MediaPlayer mPlayer;

    // string variable is created for storing a file name
    private static String mFileName = null;

    // constant for storing audio permission
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;


    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    TextView bot_medication_name,bot_medication_strength,bot_medication_condition,bot_medication_refill,bot_amount_at_once
            ,bot_amount_of_times_a_day, bot_days_to_taken_medication,bot_take_with_meal;
    ImageView delete_btn;
    ImageView back_btn;
    ImageView renew_btn;
    private List<Patient> generalInfoObject;
    private List<second_prescription> generalInfoObject_pre;


    public static ActionBottomDialogFragment newInstance() {
        return new ActionBottomDialogFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.diag_medicine_name).setOnClickListener(this);

        //generalInfoObject =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json(getContext());
        Gson gson = new Gson();
        Type foundlistType = new TypeToken<ArrayList<Patient>>(){}.getType();
        generalInfoObject = gson.fromJson(output, foundlistType);

        // initialize all variables with their layout items.
        statusTV = view.findViewById(R.id.idTVstatus);
        recordTV = view.findViewById(R.id.btnRecord);
        stoprecordTV = view.findViewById(R.id.btnStop);
        playTV = view.findViewById(R.id.btnPlay);
        stopplayTV = view.findViewById(R.id.btnStopPlay);

        recordTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);

        stoprecordTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        stoprecordTV.setEnabled(false);

        playTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        playTV.setEnabled(false);

        stopplayTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        stopplayTV.setEnabled(false);

        bot_medication_name = view.findViewById(R.id.bot_drug_name);
        bot_medication_strength = view.findViewById(R.id.bot_drug_strength);
        bot_medication_condition = view.findViewById(R.id.bot_drug_a_condtion);
        bot_medication_refill  = view.findViewById(R.id.bot_drug_needs_refill);
        bot_amount_at_once  = view.findViewById(R.id.bot_drug_at_a_time);
        bot_amount_of_times_a_day  = view.findViewById(R.id.bot_drug_times_a_day);
        bot_days_to_taken_medication = view.findViewById(R.id.bot_drug_days_to_take);
        bot_take_with_meal = view.findViewById(R.id.bot_take_with_meal);


        delete_btn = view.findViewById(R.id.botom_diag_delete_meds);
        back_btn = view.findViewById(R.id.back_btn);
        renew_btn = view.findViewById(R.id.botom_diag_renew);
        RequestPermissions();
        ////recording click events
        recordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start recording method will
                // start the recording of audio.
                startRecording();
            }
        });
        stoprecordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause Recording method will
                // pause the recording of audio.
                pauseRecording();

            }
        });
        playTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // play audio method will play
                // the audio which we have recorded
                playAudio();
            }
        });
        stopplayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause play method will
                // pause the play of audio
                pausePlaying();
            }
        });

        File file = new File(Environment.getExternalStorageDirectory() + "/RMrecordings");
        if (!file.exists()) {
            boolean res = file.mkdirs();
        }



        YoYo.with(Techniques.Bounce)
                .duration(2000)
                .playOn(view.findViewById(R.id.bottom_tablet));

        Bundle bundle = getArguments();
        String medicine_name = bundle.getString("med_name","");
        render_view(medicine_name);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/RMrecordings/"+medicine_name+".mp4";

        File file_ = new File(mFileName);
        boolean cond1 = file_.exists();
        if(cond1){
            playTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
            playTV.setEnabled(true);

           /// boolean deleted = file_.delete();      //working
        }


        renew_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Use context
                alertDialog.setTitle("Warning!");
                alertDialog.setMessage("This will take you to adding medication page and will delete this drug and prescription to redo it");
                alertDialog.setIcon(R.drawable.ic_baseline_warning_24);


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Proceed",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               // deleted_active_prescription(medicine_name);

                                generalInfoObject_pre =null;
                                shared_persistence shad_pre = new shared_persistence();
                                String output = shad_pre.get_json_user_prep(Context_maker.getInstance().getMyContext());

                                Type foundlistType = new TypeToken<ArrayList<second_prescription>>(){}.getType();
                                generalInfoObject_pre = gson.fromJson(output, foundlistType);

                                for (int j = 0; j < generalInfoObject_pre.size(); j++) {
                                    if(generalInfoObject_pre.get(j).getDrug_name().equals(medicine_name))
                                    {

                                        String et_drugname = generalInfoObject_pre.get(j).getDrug_name();
                                        String et_at_atime = generalInfoObject_pre.get(j).getAmount_at_a_time();
                                        String et_totaldrugs = generalInfoObject_pre.get(j).getTotal_dosage();
                                        String et_condition = generalInfoObject_pre.get(j).getCondtion();
                                        String et_medication_strength = generalInfoObject_pre.get(j).getDrug_strength();
                                        String et_if_breastfeeding = generalInfoObject_pre.get(j).getOk_for_breastfeeding();
                                        String et_if_takewithmeal = generalInfoObject_pre.get(j).getTakeBeforeOrAfterMeal();
                                        String et_if_pregnant = generalInfoObject_pre.get(j).getTake_with_Pregnancy();
                                        String et_intakemethod = generalInfoObject_pre.get(j).getIntake_method();

                                        Context_maker.getInstance().setEt_drugname_rec(et_drugname);
                                        Context_maker.getInstance().setEt_at_atime_rec(et_at_atime);
                                        Context_maker.getInstance().setEt_totaldrugs_rec(et_totaldrugs);
                                        Context_maker.getInstance().setEt_condition_rec(et_condition);
                                        Context_maker.getInstance().setEt_medication_strength_rec(et_medication_strength);
                                        Context_maker.getInstance().setEt_if_breastfeeding_rec(et_if_breastfeeding);
                                        Context_maker.getInstance().setEt_if_takewithmeal_rec(et_if_takewithmeal);
                                        Context_maker.getInstance().setEt_if_pregnant_rec(et_if_pregnant);
                                        Context_maker.getInstance().setEt_intakemethod_rec(et_intakemethod);
                                        Context_maker.getInstance().setReady_for_recovery(true);

                                        deleted_active_prescription(medicine_name);
                                        deleted_just_drug(medicine_name);
                                        Intent  goto_add_prescription = new Intent(getContext(),Add_medication.class);
                                        startActivity(goto_add_prescription);

                                        try {
                                            ActionBottomDialogFragment.this.finalize();
                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                        }
                                        //Toast.makeText(getContext(), "found", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                ///////////////////
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                ///////////

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBottomDialogFragment.this.dismiss();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create(); //Use context
                alertDialog.setTitle("Warning!");
                alertDialog.setMessage(" Deleting : This cannot be undone");
                alertDialog.setIcon(R.drawable.ic_baseline_warning_24);

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Drug only",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < generalInfoObject.size(); i++) {
                                    if(generalInfoObject.get(i).getDrugName().equals(medicine_name))
                                    {
                                        Toast.makeText(getContext(), "Delete the prescription first", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                deleted_just_drug(medicine_name);
                                //deleting the recording
                                mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                                mFileName += "/RMrecordings/"+medicine_name+".mp4";

                                File file_ = new File(mFileName);
                                boolean cond1 = file_.exists();
                                if(cond1){

                                    boolean deleted = file_.delete();      //working
                                }
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Prescription",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleted_active_prescription(medicine_name);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                ///////////

            }
        });

    }



    public void deleted_active_prescription(String medicine_name){

        for (int i = 0; i < generalInfoObject.size(); i++) {
            if(generalInfoObject.get(i).getDrugName().equals(medicine_name))
            {
                generalInfoObject.remove(i);
                Gson gson = new Gson();
                String json = gson.toJson(generalInfoObject);
                shared_persistence shad = new shared_persistence();
                shad.save_json(getContext(),json);

                bot_medication_name.setText("");
                bot_medication_strength.setText("");
                bot_medication_condition.setText("");
                bot_medication_refill.setText("");
                bot_amount_at_once.setText("");
                bot_amount_of_times_a_day.setText("");
                bot_days_to_taken_medication.setText("");
                bot_take_with_meal.setText("");

                render_view(medicine_name);

                try {
                    ((MainActivity) getActivity()).swipe_fragment();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ActionBottomDialogFragment.this.dismiss();




            }

        }

    }

    public void deleted_just_drug(String medicine_name){

        for (int i = 0; i < generalInfoObject_pre.size(); i++) {
            if(generalInfoObject_pre.get(i).getDrug_name().equals(medicine_name))
            {

                Gson gson = new Gson();



                /////////////////////////////////for prescription
                generalInfoObject_pre =null;
                shared_persistence shad_pre = new shared_persistence();
                String output = shad_pre.get_json_user_prep(Context_maker.getInstance().getMyContext());

                Type foundlistType = new TypeToken<ArrayList<second_prescription>>(){}.getType();
                generalInfoObject_pre = gson.fromJson(output, foundlistType);

                for (int j = 0; j < generalInfoObject_pre.size(); j++) {
                    if(generalInfoObject_pre.get(i).getDrug_name().equals(medicine_name))
                    {
                        generalInfoObject_pre.remove(i);

                        if(generalInfoObject_pre.isEmpty()){
                            shad_pre.clear_json_user_prep(getContext());
                        }
                        String json_prep = gson.toJson(generalInfoObject_pre);
                        shared_persistence shad_pred = new shared_persistence();
                        shad_pred.save_json_user_prep(getContext(),json_prep);

                        render_view(medicine_name);

                        try {
                            ((MainActivity) getActivity()).swipe_fragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ActionBottomDialogFragment.this.dismiss();
                        return;
                    }
                }




            }

        }

    }


    @SuppressLint("SetTextI18n")
 public void render_view(String medicine_name)
 {

     generalInfoObject_pre =null;
     Gson gson = new Gson();
     shared_persistence shad_pre = new shared_persistence();
     String output = shad_pre.get_json_user_prep(Context_maker.getInstance().getMyContext());

     Type foundlistType = new TypeToken<ArrayList<second_prescription>>(){}.getType();
     generalInfoObject_pre = gson.fromJson(output, foundlistType);

     for (int j = 0; j < generalInfoObject_pre.size(); j++) {
         if(generalInfoObject_pre.get(j).getDrug_name().equals(medicine_name))
         {

             bot_medication_name.setText(generalInfoObject_pre.get(j).getDrug_name());
             bot_medication_strength.setText(generalInfoObject_pre.get(j).getDrug_strength()+" "+generalInfoObject_pre.get(j).getDrug_unit());
             bot_medication_condition.setText(generalInfoObject_pre.get(j).getCondtion());
             bot_amount_at_once.setText(generalInfoObject_pre.get(j).getAmount_at_a_time());
             bot_amount_of_times_a_day.setText(generalInfoObject_pre.get(j).getTime_a_day());
             bot_days_to_taken_medication.setText("");
             bot_medication_refill.setText(generalInfoObject_pre.get(j).getNeeds_refill());
             bot_take_with_meal.setText(generalInfoObject_pre.get(j).getTakeBeforeOrAfterMeal());
             int at_a_time = Integer.parseInt(generalInfoObject_pre.get(j).getAmount_at_a_time());
             int times_a_day = Integer.parseInt(generalInfoObject_pre.get(j).getTime_a_day());
             int total = Integer.parseInt(generalInfoObject_pre.get(j).getTotal_dosage());
             int limit  = total/ at_a_time;
             limit = (limit/ times_a_day);
             bot_days_to_taken_medication.setText(""+limit);

         }
     }

 }



    private void startRecording() {
        // check permission method is used to check
        // that the user has granted permission
        // to record nd store the audio.
        if (CheckPermissions()) {

            // setbackgroundcolor method will change
            // the background color of text view.
           /* stoprecordTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
            recordTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));
            playTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));
            stopplayTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));*/

            recordTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
            recordTV.setEnabled(false);

            stoprecordTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
            stoprecordTV.setEnabled(true);

            playTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
            playTV.setEnabled(false);

            stopplayTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
            stopplayTV.setEnabled(false);



            File file = new File(Environment.getExternalStorageDirectory() + "/RMrecordings");
            if (!file.exists()) {
                boolean res = file.mkdirs();
            }

            // we are here initializing our filename variable
            // with the path of the recorded audio file.

            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            Bundle bundle = getArguments();
            String medicine_name = bundle.getString("med_name","");
            mFileName += "/RMrecordings/"+medicine_name+".mp4";

            // below method is used to initialize
            // the media recorder class
            mRecorder = new MediaRecorder();

            // below method is used to set the audio
            // source which we are using a mic.
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            // below method is used to set
            // the output format of the audio.
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            // below method is used to set the
            // audio encoder for our recorded audio.
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.setAudioEncodingBitRate(128000);
            mRecorder.setAudioSamplingRate(16000);

            // below method is used to set the
            // output file location for our recorded audio
            mRecorder.setOutputFile(mFileName);
            try {
                // below method will prepare
                // our audio recorder class
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e("TAG", "prepare() failed");
            }
            // start method will start
            // the audio recording.
            mRecorder.start();
            statusTV.setText("Recording Started");
        } else {
            // if audio recording permissions are
            // not granted by user below method will
            // ask for runtime permission for mic and storage.
            RequestPermissions();
        }
    }






    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // this method is called when user will
        // grant the permission for audio recording.

        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean CheckPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        // this method is used to request the
        // permission for audio recording and storage.
        ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);

    }


    public void playAudio() {
        /*stoprecordTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));
        recordTV.setBackgroundColor(getResources().getColor(R.color.purple_200_rec));
        playTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));
        stopplayTV.setBackgroundColor(getResources().getColor(R.color.purple_200_rec));*/

        recordTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        recordTV.setEnabled(true);

        stoprecordTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        stoprecordTV.setEnabled(false);

        playTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        playTV.setEnabled(false);

        stopplayTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        stopplayTV.setEnabled(true);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        Bundle bundle = getArguments();
        String medicine_name = bundle.getString("med_name","");
        mFileName += "/RMrecordings/"+medicine_name+".mp4";

        // for playing our recorded audio
        // we are using media player class.
        mPlayer = new MediaPlayer();
        try {
            // below method is used to set the
            // data source which will be our file name
            mPlayer.setDataSource(mFileName);

            // below method will prepare our media player
            mPlayer.prepare();

            // below method will start our media player.
            mPlayer.start();
            statusTV.setText("Recording Started Playing");
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }

    public void pauseRecording() {
       /* stoprecordTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));
        recordTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
        playTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
        stopplayTV.setBackgroundColor(getResources().getColor(R.color.purple_200));*/



        recordTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        recordTV.setEnabled(true);

        stoprecordTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        stoprecordTV.setEnabled(false);

        playTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        playTV.setEnabled(true);

        stopplayTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        stopplayTV.setEnabled(true);

        // below method will stop
        // the audio recording.
        mRecorder.stop();

        // below method will release
        // the media recorder class.
        mRecorder.release();
        mRecorder = null;
        statusTV.setText("Recording Stopped");
    }

    public void pausePlaying() {
        // this method will release the media player
        // class and pause the playing of our recorded audio.
        mPlayer.release();
        mPlayer = null;
        /*stoprecordTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));
        recordTV.setBackgroundColor(getResources().getColor(R.color.purple_200_rec));
        playTV.setBackgroundColor(getResources().getColor(R.color.purple_200_rec));
        stopplayTV.setBackgroundColor(getResources().getColor(R.color.gray_rec));*/

        recordTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        recordTV.setEnabled(true);

        stoprecordTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        stoprecordTV.setEnabled(false);

        playTV.getBackground().setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
        playTV.setEnabled(true);

        stopplayTV.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        stopplayTV.setEnabled(true);

        statusTV.setText("Recording Play Stopped");
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }
    public interface ItemClickListener {
        void onItemClick(String item);
    }
}