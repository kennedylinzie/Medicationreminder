package com.greensoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greensoft.myapplication.guardians.ListViewAdapter;
import com.greensoft.myapplication.jsony.shared_persistence;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Add_gurdian extends AppCompatActivity {

    EditText g_name,g_lastname,g_relation,g_phonenumber,g_email;
    String[] item;
    int[] image;
    ListView listView;
    ListViewAdapter adapter;
    private Handler handler = new Handler();
    private  Runnable runnableCode;
    Handler handler_stage;
    Runnable runnableCode_stage;
    Handler handler_delete;
    Runnable runnableCode_delete;

    Handler handler_con_wait;
    Runnable runnableCode_con_wait;
    Gurdian gu =new Gurdian();
    TextView offline_man;
    Button add;
    //String json;

    int count = 0;
    int count1 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gurdian);
        item =null;
        g_name = findViewById(R.id.et_g_name);
        g_lastname = findViewById(R.id.et_g_sirname);
        g_relation = findViewById(R.id.et_g_relationship);
        g_phonenumber = findViewById(R.id.et_g_phonenumber);
        g_email = findViewById(R.id.et_g_email);
        offline_man = findViewById(R.id.add_offline);
        add = findViewById(R.id.pro_save);

        Context_maker.getInstance().setMyContext(getApplicationContext());
        Context_maker.getInstance().check_connection();
         sentinal();



    }

    public void sentinal(){


       //// Toast.makeText(Add_gurdian.this,"the connection is off",Toast.LENGTH_SHORT).show();
        offline_man.setVisibility(View.VISIBLE);
        add.setEnabled(false);
        g_name.setEnabled(false);
        g_lastname.setEnabled(false);
        g_relation.setEnabled(false);
        g_phonenumber.setEnabled(false);
        g_email.setEnabled(false);
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
            }
            load_data(new_pips);

        }else{
            //Toast.makeText(this,"Not save prescriptions",Toast.LENGTH_SHORT).show();
        }

        handler_con_wait = new Handler();
        // Define the code block to be executed
        runnableCode_con_wait = new Runnable() {
            @Override
            public void run() {

                Context_maker.getInstance().check_connection();
                if(!Context_maker.getInstance().isConnection()){
                    //stop_con_wait();
                    offline_man.setVisibility(View.VISIBLE);
                    add.setEnabled(false);
                    g_name.setEnabled(false);
                    g_lastname.setEnabled(false);
                    g_relation.setEnabled(false);
                    g_phonenumber.setEnabled(false);
                    g_email.setEnabled(false);
                    //Toast.makeText(Add_gurdian.this,"waiting",Toast.LENGTH_SHORT).show();
                }else if(Context_maker.getInstance().isConnection()) {
                    //Toast.makeText(Add_gurdian.this,"the connection is ok",Toast.LENGTH_SHORT).show();
                    offline_man.setVisibility(View.GONE);
                    add.setEnabled(true);
                    g_name.setEnabled(true);
                    g_lastname.setEnabled(true);
                    g_relation.setEnabled(true);
                    g_phonenumber.setEnabled(true);
                    g_email.setEnabled(true);
                    start_dance();
                    stop_con_wait();
                    handler_con_wait.removeCallbacks(runnableCode_con_wait);
                }

                handler_con_wait.postDelayed(this, 2000);
            }
        };

        handler_con_wait.post(runnableCode_con_wait);


    }

    private void stop_con_wait() {
        handler_con_wait.removeCallbacks(runnableCode_con_wait);
        handler_con_wait.removeCallbacks(runnableCode_con_wait);
    }

    public void start_dance(){

        gu.pull_gurd(getApplicationContext());
        // Create the Handler object (on the main thread by default)
        handler = new Handler();
        // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {

                if(Context_maker.getInstance().getPeople() == null ){
                   // Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                }else if(Context_maker.getInstance().getPeople() != null) {
                   // Context_maker.getInstance().setPeople(null);
                    String[] man = Context_maker.getInstance().getPeople();
                    load_data(man);
                    stop_dance();
                    //Context_maker.getInstance().setPeople(null);
                   // Toast.makeText(getApplicationContext(),"we have data data",Toast.LENGTH_SHORT).show();
                }

                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnableCode);

    }

    public void stop_dance(){
        handler.removeCallbacks(runnableCode);
    }


    public void load_data(String[] man){

         //gu.pull_gurd(getApplicationContext());
        item =  man;
        //item = new String[]{"item 1","item 2","item 3","item 4","item 5"};
        // item = gu.pull_gurd(getApplicationContext(),"");
        image = new int[]{R.drawable.ic_baseline_person_24,R.drawable.ic_baseline_person_24,R.drawable.ic_baseline_person_24};
        listView = findViewById(R.id.g_list);

        //save to json
       /* Gson  gson = new Gson();
        json = gson.toJson(item);*/

        adapter = new ListViewAdapter(getApplicationContext(),image,item);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),item[i]+".............",Toast.LENGTH_SHORT).show();
                String name = item[i];
                AlertDialog alertDialog = new AlertDialog.Builder(Add_gurdian.this).create(); //Use context
                alertDialog.setTitle("Hello");
                alertDialog.setMessage("Do you wish to delete?");
                alertDialog.setIcon(R.drawable.ic_baseline_warning_24);


                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                               // String[] arr = new String[]{"1","2","3","4","5"};
                                String[] arr_new = new String[item.length-1];
                                String j=item[i];
                                for(int run=0, k=0;run<item.length;run++){
                                    if(item[run]!=j){
                                        arr_new[k]=item[run];
                                        k++;
                                    }
                                }
                                     gu.delete(getApplicationContext(),item[i]);
                                     Context_maker.getInstance().setPeople(null);
                                     load_data(arr_new);
                                     listView.invalidateViews();



                            }
                        });
                alertDialog.show();


                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),item[i],Toast.LENGTH_SHORT).show();

                 handler_stage = new Handler();
                 runnableCode_stage = new Runnable() {
                    @Override
                    public void run() {
                        if(count > 5){
                           clear_stage();
                           count =0;
                            return;
                        }
                        count++;
                        try {
                            gu.pull_gurd_filter(getApplicationContext(),item[i]);
                            g_name.setText(gu.getName());
                            g_lastname.setText(gu.getSirname());
                            g_relation.setText(gu.getRelationship());
                            g_phonenumber.setText(gu.getPhone_number());
                            g_email.setText(gu.getEmail());
                        } catch (Exception e) {
                            start_dance();
                        }
                        handler.postDelayed(this, 500);
                    }
                };
                handler_stage.post(runnableCode_stage);
            }
        });

    }
    public void clear_stage(){

        handler_stage.removeCallbacks(runnableCode_stage);
        gu.setEmail("");
        gu.setName("");
        gu.setSirname("");
        gu.setRelationship("");
        gu.setPhone_number("");
        gu.setId(0);
    }
    public void stop_delete(){
        handler_delete.removeCallbacks(runnableCode_delete);
    }

    public void Add_gurdians(View view) {
            sentinal();
        String  name = g_name.getText().toString();
        String  lastname = g_lastname.getText().toString();
        String  relation = g_relation.getText().toString();
        String  phonenumber = g_phonenumber.getText().toString();
        String  email = g_email.getText().toString();

        if(name.isEmpty() || lastname.isEmpty()|| relation.isEmpty()|| phonenumber.isEmpty()|| email.isEmpty()){
            Toast.makeText(this,"All fields should be filled",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.g_login_body));
            return;
        }

        gu.setName(name);
        gu.setSirname(lastname);
        gu.setRelationship(relation);
        gu.setPhone_number(phonenumber);
        gu.setEmail(email);
        shared_persistence shad = new shared_persistence();
        shad.add_guardian(getApplicationContext(), gu.getName(), gu.getSirname(), gu.getEmail(), gu.getRelationship(), gu.getPhone_number());
        Context_maker.getInstance().setPeople(null);
        start_dance();
        start_dance();
        start_dance();






    }
    int back_add=0;
    @Override
    public void onBackPressed() {
        // Example of logic
        if ( back_add < 1 ) {
            stop_con_wait();
            //Toast.makeText(this, "The second time will close the window", Toast.LENGTH_SHORT).show();
            Intent openActivity = new Intent(Add_gurdian.this, MainActivity.class);
            startActivity(openActivity);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    public void update_user(View view) {

        String  name = g_name.getText().toString();
        String  lastname = g_lastname.getText().toString();
        String  relation = g_relation.getText().toString();
        String  phonenumber = g_phonenumber.getText().toString();
        String  email = g_email.getText().toString();

        if(name.isEmpty() || lastname.isEmpty()|| relation.isEmpty()|| phonenumber.isEmpty()|| email.isEmpty()){
            Toast.makeText(this,"All fields should be filled",Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(findViewById(R.id.g_login_body));
            return;
        }




    }

   /* public void sniff(){
        connectionsManager conman = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getPull_guards(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
               //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                Handler handler = new Handler();

                 Runnable runnableCode = new Runnable() {
                    @Override
                    public void run() {
                        // Do something here on the main thread
                        if(response.equals("")){

                        }else {
                            start_dance();
                        }
                    }
                };

                handler.postDelayed(runnableCode, 2000);

                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }*/
}