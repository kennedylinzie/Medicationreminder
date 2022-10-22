package com.greensoft.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.greensoft.myapplication.appUtil.myDiffUtilCallBacks;
import com.greensoft.myapplication.ui.HomeFragment;

import java.text.MessageFormat;
import java.util.List;



public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private final Context mCtx;

    //we are storing all the products in a list
    private final List<Medication> productList;
    AlertDialog.Builder builder;

    //getting the context and product list with constructor
    public MedicationAdapter(Context mCtx, List<Medication> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }



    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_medication, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull List<Object> payloads) {

        if(payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else
        {
            Bundle bundle = (Bundle) payloads.get(0);
            for (String key: bundle.keySet()) {
               if(key.equals("taken") && key.equals("skipped"))
               {
                   holder.if_drug_taken_.setText(bundle.getString("taken"));
                   boolean tak = Boolean.parseBoolean(bundle.getString("taken")) ;
                   boolean skip = Boolean.parseBoolean(bundle.getString("skipped")) ;

                   if(skip){
                       holder.taken_skip.setVisibility(View.VISIBLE);
                   }else if(!skip) {
                       holder.taken_skip.setVisibility(View.GONE);
                   }
                   if(tak){
                       holder.taken_checker.setVisibility(View.VISIBLE);
                   }else if(!tak) {
                       holder.taken_checker.setVisibility(View.GONE);
                   }

               }
            }

        }

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // getting the product of the specified position
        Medication product = productList.get(position);

        //binding the data with the viewholder views
        holder.medication_name_.setText(product.getMedication_name());
        holder.pill_at_time_.setText(String.format("%d Pill(s)  "+product.getStrength_amount()+" ( "+product.getStrength_unit()+" ) ", product.getPill_at_time()));
        holder.if_drug_taken_.setText(MessageFormat.format("Taken( {0})", product.getIf_drug_taken()));
        holder.time_to_take_pill_.setText(MessageFormat.format("To be taken at {0}", product.getTime_to_take_pill()));
        holder.prescription_card.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.reycler_anim_three));
        if(productList.get(position).getIf_drug_taken()){
            holder.taken_checker.setVisibility(View.VISIBLE);
        }else {
            holder.taken_checker.setVisibility(View.GONE);
        }
        if(productList.get(position).isSkipped()){
            holder.taken_skip.setVisibility(View.VISIBLE);
        }else {
            holder.taken_skip.setVisibility(View.GONE);
        }


        holder.image_.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mCtx.getApplicationContext(), "...........", Toast.LENGTH_SHORT).show();

            }
        });
       /* holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onLongClick(View view) {

                Main_save_meds mn = new Main_save_meds();
                String d = product.getDate_to_take_pill()+" "+product.getTime_to_take_pill();
                 mn.change_taken_status(product.getMedication_name(),product.getId(),d);
                //Toast.makeText(view.getContext(),d,Toast.LENGTH_LONG).show();
                updateMedication(new Medication().populate());
                ///////////////
                AlertDialog alertDialog = new AlertDialog.Builder(mCtx).create(); //Use context
                alertDialog.setTitle("Hellow there");
                alertDialog.setMessage("It is time to take your medication");
                alertDialog.setIcon(R.drawable.pill);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
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
                                Main_save_meds mn = new Main_save_meds();
                                String d = product.getDate_to_take_pill()+" "+product.getTime_to_take_pill();
                                mn.change_taken_status(product.getMedication_name(),product.getId(),d);
                                //Toast.makeText(view.getContext(),d,Toast.LENGTH_LONG).show();
                                updateMedication(new Medication().populate());
                                notifyItemChanged(position);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                ///////////
                return false;
            }
        });*/

    }




    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {


        TextView medication_name_, pill_at_time_, if_drug_taken_, time_to_take_pill_;
        ImageView image_,taken_checker,taken_skip;
        CardView prescription_card;

        public ProductViewHolder(View itemView) {
            super(itemView);

            medication_name_ = itemView.findViewById(R.id.medication_name);
            pill_at_time_ = itemView.findViewById(R.id.pilll_at_atime);
            if_drug_taken_ = itemView.findViewById(R.id.drug_taken_status);
            time_to_take_pill_ = itemView.findViewById(R.id.time_to_take_pills);
            image_ = itemView.findViewById(R.id.main_img);
            taken_checker = itemView.findViewById(R.id.taken_check);
            taken_skip = itemView.findViewById(R.id.taken_skipped);
            prescription_card = itemView.findViewById(R.id.prescription_cardview);
        }
    }

        public void updateMedication(List<Medication> newMedication){

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new myDiffUtilCallBacks(productList,newMedication));
            diffResult.dispatchUpdatesTo(this);
            productList.clear();
            productList.addAll(newMedication);

        }

}