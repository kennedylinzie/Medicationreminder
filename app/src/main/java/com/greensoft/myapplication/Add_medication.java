package com.greensoft.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.jsony.Patient;
import com.greensoft.myapplication.jsony.shared_persistence;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Add_medication extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private  Spinner presription_spinner,strength_spinner;
    private  String[] prescription_intake = {"Daily","2 Times a day","3 Times a day","4 Times a day","5 Times a day"};
    private  String[] pres_script_strength = {"g","iu","mcg","m"};
    private RadioGroup radioGroup;
    private RadioButton radiobtn_yes,radiobtn_no;

    int prescription_number = 0;
    private String strenth_value = "g";
    private Button prosave;
    private EditText et_drugname,et_at_atime,et_totaldrugs,et_condition,et_medication_strength,et_if_breastfeeding,
            et_if_takewithmeal,et_if_pregnant,et_intakemethod;
    private TextView downCounter;
    int downer =5;
    boolean needsRefill = false;
    private String strength_unit = "g";
    ////camera variables
    private Bitmap bitmap;
    private static final int REQUEST_CAMERA_CODE = 100;
    private List<Patient> generalInfoObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        et_if_breastfeeding = findViewById(R.id.et_if_breastfeeding);
        et_if_takewithmeal = findViewById(R.id.et_if_meal_or_without);
        et_if_pregnant = findViewById(R.id.et_if_pregnant);
        et_intakemethod = findViewById(R.id.et_intake_method);

        et_drugname = findViewById(R.id.et_drug_name);
        et_at_atime = findViewById(R.id.et_at_a_time);
        et_totaldrugs = findViewById(R.id.et_total);
        prosave = findViewById(R.id.pro_save);
        downCounter = findViewById(R.id.add_meds_counter);
        et_condition = findViewById(R.id.et_condition);
        et_medication_strength = findViewById(R.id.et_strength_amount);
        radioGroup = findViewById(R.id.rGroup_drug_strength);
        radiobtn_yes = findViewById(R.id.rButton_yes);
        radiobtn_no = findViewById(R.id.rButton_no);
        radioGroup.check(R.id.rButton_no);
        shared_persistence shad = new shared_persistence();
        shad.get_user_data(getApplicationContext());

        if(Context_maker.getInstance().isReady_for_recovery()){

            String et_drugname_rec =   Context_maker.getInstance().getEt_drugname_rec();
            String et_at_atime_rec = Context_maker.getInstance().getEt_at_atime_rec();
            String et_totaldrugs_rec =  Context_maker.getInstance().getEt_totaldrugs_rec();
            String et_condition_rec =  Context_maker.getInstance().getEt_condition_rec();
            String et_medication_strength_rec =  Context_maker.getInstance().getEt_medication_strength_rec();
            String et_if_breastfeeding_rec = Context_maker.getInstance().getEt_if_breastfeeding_rec();
            String et_if_takewithmeal_rec = Context_maker.getInstance().getEt_if_takewithmeal_rec();
            String et_if_pregnant_rec = Context_maker.getInstance().getEt_if_pregnant_rec();
            String et_intakemethod_rec=  Context_maker.getInstance().getEt_intakemethod_rec();

            Context_maker.getInstance().setReady_for_recovery(false);

            et_drugname.setText(et_drugname_rec);
            et_at_atime.setText(et_at_atime_rec);
            et_totaldrugs.setText(et_totaldrugs_rec);
            et_condition.setText(et_condition_rec);
            et_medication_strength.setText(et_medication_strength_rec);
            et_if_breastfeeding.setText(et_if_breastfeeding_rec);
            et_if_takewithmeal.setText(et_if_takewithmeal_rec);
            et_if_pregnant.setText(et_if_pregnant_rec);
            et_intakemethod.setText(et_intakemethod_rec);

        }




     /* if (shad.getFirst_name() != null) {

            if (shad.getFirst_name().equals("")) {
                Toast.makeText(getApplicationContext(), shad.getFirst_name(), Toast.LENGTH_SHORT).show();
                goto_login();
            }

        } else {
            goto_login();
        }*/

        if(ContextCompat.checkSelfPermission(Add_medication.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Add_medication.this,new String[]{
                    Manifest.permission.CAMERA
            },REQUEST_CAMERA_CODE);
        }
         Context_maker.getInstance().setMyContext(getApplicationContext());


        presription_spinner = findViewById(R.id.pres_spinner);
        strength_spinner = findViewById(R.id.strength_spinner);

        presription_spinner.setOnItemSelectedListener(this);
        strength_spinner.setOnItemSelectedListener(this);

        ArrayAdapter arraySpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item,prescription_intake);
        arraySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presription_spinner.setAdapter(arraySpinner);

        ArrayAdapter strengthSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item,pres_script_strength);
        strengthSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        strength_spinner.setAdapter(strengthSpinner);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rButton_yes:
                        needsRefill = true;
                        Toast.makeText(Add_medication.this, "YES "+needsRefill, Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.rButton_no:
                        needsRefill = false;
                        Toast.makeText(Add_medication.this, "NO "+needsRefill, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });



    }

    private void goto_login() {
            Intent startEXITactivity = new Intent(this, user_login_activity.class);
            startActivity(startEXITactivity);
            finish();
    }


    @SuppressLint("ResourceType")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    // Toast.makeText(this,adapterView.getId(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,adapterView.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

        switch (adapterView.getSelectedItem().toString()){
            case "Daily":
                prescription_number = 1;
               // Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "2 Times a day":
                prescription_number = 2;
               // Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "3 Times a day":
                prescription_number = 3;
              //  Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "4 Times a day":
                prescription_number = 4;
              //  Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "5 Times a day":
                prescription_number = 5;
               // Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
        }

        switch (adapterView.getSelectedItem().toString()){
            case "g":
                strenth_value = "g";
                // Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "iu":
                strenth_value = "iu";
                // Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "mcg":
                strenth_value = "mcg";
                //  Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            case "m":
                strenth_value = "m";
                //  Toast.makeText(this, " "+prescription_number, Toast.LENGTH_SHORT).show();
                break;
            default:
                strenth_value="g";
                break;

        }


    }


        @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save_meds(View view) {
        if(       et_drugname.getText().toString().isEmpty()
                ||et_at_atime.getText().toString().isEmpty()
                ||et_totaldrugs.getText().toString().isEmpty()
                ||et_condition.getText().toString().isEmpty()
                ||et_intakemethod.getText().toString().isEmpty()
                ||et_medication_strength.getText().toString().isEmpty()){
            Toast.makeText(this, "All fields should be filled", Toast.LENGTH_SHORT).show();
            shake();
            return;
        }

        String breast_feeding = "not provided";
        String pregnant = "not provided";
        String intake_method = "not provided";
        String takewith_meal = "not provided";

        String drg_name = et_drugname.getText().toString();
        int drg_atatime = Integer.parseInt(et_at_atime.getText().toString());
        int drg_total = Integer.parseInt(et_totaldrugs.getText().toString());
        String drg_condition = et_condition.getText().toString();
        int drg_strength = Integer.parseInt(et_medication_strength.getText().toString());
        if(!et_if_breastfeeding.getText().toString().isEmpty()){
            breast_feeding = et_if_breastfeeding.getText().toString();
        }
        if(!et_if_takewithmeal.getText().toString().isEmpty()){
            pregnant = et_if_pregnant.getText().toString();
        }
        if(!et_if_pregnant.getText().toString().isEmpty()){
            intake_method = et_intakemethod.getText().toString();
        }
        if(!et_intakemethod.getText().toString().isEmpty()){
            takewith_meal = et_if_takewithmeal.getText().toString();
        }

        if(drg_name.length() < 2){
            Toast.makeText(this, "Fill the drug name or make sure its more than 2 letters", Toast.LENGTH_SHORT).show();
            shake();
            return;
        }
        if (drg_atatime < 1 ||  drg_total < 1)
        {
            Toast.makeText(this, "Amount at a time and total drugs cannot be zero", Toast.LENGTH_SHORT).show();
            shake();
            return;
        }

        int limit  = drg_total/drg_atatime;
        limit = (limit / prescription_number);
        int remainder = drg_total % drg_atatime;
        if(remainder != 0)
        {
            Toast.makeText(this, "Please check your prescription again", Toast.LENGTH_LONG).show();
            shake();
            return;
        }

        generalInfoObject =null;
        shared_persistence shad = new shared_persistence();
        String output = shad.get_json(getApplicationContext());
        Gson gson = new Gson();
        if(output.equals("None")){
            output = "[]";
        }
        Type foundlistType = new TypeToken<ArrayList<Patient>>(){}.getType();
        generalInfoObject = gson.fromJson(output, foundlistType);


        if(output.equals("[]"))
        {
            //Toast.makeText(this,"it matches ",Toast.LENGTH_LONG).show();
            generalInfoObject =null;

        }
if(generalInfoObject != null)
{
    for (Patient dss : generalInfoObject) {
        //System.out.println(dss.getName());

        for (int i = 0; i < dss.getmDosage().size(); i++) {

            if(!dss.getDrugName().equals(drg_name))
            {
                Main_save_meds save_medication = new Main_save_meds();
                save_medication.setDrug_Name(drg_name);
                save_medication.setDrug_Amount_at_a_time(drg_atatime);
                save_medication.setDrug_TotalDosage(drg_total);
                save_medication.setDrug_times_a_day(prescription_number);
                save_medication.setCondition(drg_condition);
                save_medication.setStrength_amount(drg_strength);
                save_medication.setStrength_unit(strength_unit);
                save_medication.setRefill_reminders(needsRefill);
                save_medication.setBreast_feeding(breast_feeding);
                save_medication.setPregnant(pregnant);
                save_medication.setIntake_method(intake_method);
                save_medication.setTakewith_meal(takewith_meal);
                save_medication.setContext(getApplicationContext());
                save_medication.setSkipping_reason("");

                save_medication.setContext(getApplicationContext());
                save_medication.add_meds();

               // downCounter.setText("The window will close in : " + downer);
//                YoYo.with(Techniques.Bounce)
//                        .duration(2000)
//                        .playOn(findViewById(R.id.add_meds_counter));

                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    //    downCounter.setText("The window will close in: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        // downCounter.setText("done!");
                        Intent openActivit = new Intent(Add_medication.this, MainActivity.class);
                        startActivity(openActivit);
                        finish();
                    }
                }.start();
                return;
            }else {
                shake();
                Toast.makeText(this, "SAME DRUGS NOT ALLOWED", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }
}else {
    Main_save_meds save_medication = new Main_save_meds();
    save_medication.setDrug_Name(drg_name.toLowerCase());
    save_medication.setDrug_Amount_at_a_time(drg_atatime);
    save_medication.setDrug_TotalDosage(drg_total);
    save_medication.setDrug_times_a_day(prescription_number);
    save_medication.setCondition(drg_condition);
    save_medication.setStrength_amount(drg_strength);
    save_medication.setStrength_unit(strength_unit);
    save_medication.setRefill_reminders(needsRefill);
    save_medication.setSkipping_reason("");
    save_medication.setBreast_feeding(breast_feeding);
    save_medication.setPregnant(pregnant);
    save_medication.setIntake_method(intake_method);
    save_medication.setTakewith_meal(takewith_meal);
    save_medication.setContext(getApplicationContext());
    save_medication.add_meds();

//    YoYo.with(Techniques.Bounce)
//            .duration(2000)
//            .playOn(findViewById(R.id.pro_save));

    new CountDownTimer(5000, 1000) {

        public void onTick(long millisUntilFinished) {
            prosave.setText("closes in: " + millisUntilFinished / 1000);
        }

        public void onFinish() {
            // downCounter.setText("done!");
            Intent openActivit = new Intent(Add_medication.this, MainActivity.class);
            startActivity(openActivit);
            finish();
        }
    }.start();
    return;
}
       // Toast.makeText(this, ""+prescription_number+" "+drg_name+"a time"+drg_atatime+" total drugs "+drg_total, Toast.LENGTH_LONG).show();
    }

    public void shake()
    {
//                 YoYo.with(Techniques.Shake)
//                .duration(300)
//                .playOn(findViewById(R.id.pro_save));
    }

    public void close_activity(View view) {
        this.finish();
    }
    int back_add=0;
    @Override
    public void onBackPressed() {
        // Example of logic
        if ( back_add < 1 ) {

            //Toast.makeText(this, "The second time will close the window", Toast.LENGTH_SHORT).show();
            Intent openActivity = new Intent(Add_medication.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    public void camera_work_text_extraction(View view) {

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON_TOUCH).start(Add_medication.this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK)
            {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resultUri);

                    getTextFromImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getTextFromImage(Bitmap bitmap)
    {
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if(!recognizer.isOperational())
        {
            Toast.makeText(Add_medication.this,"ERROR OCCURRED",Toast.LENGTH_SHORT).show();
        }else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBulder = new StringBuilder();
            for (int i = 0; i < textBlockSparseArray.size(); i++) {
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBulder.append(textBlock.getValue());
                stringBulder.append("\n");
            }
            et_drugname.setText(stringBulder.toString());
            //capture.setText("Retake");
           // copy.setVisibility(View.VISIBLE);
        }
    }

    public void search_medication_on_google(View view) {

        if(et_drugname.getText().toString().length()<1)
        {
            Toast.makeText(Add_medication.this,"Make sure name is filled......",Toast.LENGTH_SHORT).show();
//            YoYo.with(Techniques.Shake)
//                    .duration(300)
//                    .playOn(findViewById(R.id.et_drug_name));
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, et_drugname.getText().toString());
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    public void scan_medication_on_google(View view) {

        Intent goto_scan = new Intent(this,ScannedBarcodeActivity.class);
        startActivity(goto_scan);

    }
}