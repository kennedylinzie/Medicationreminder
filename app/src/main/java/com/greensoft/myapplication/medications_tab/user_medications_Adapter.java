package com.greensoft.myapplication.medications_tab;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.greensoft.myapplication.ActionBottomDialogFragment;
import com.greensoft.myapplication.Context_maker;
import com.greensoft.myapplication.R;
import com.greensoft.myapplication.jsony.second_prescription;

import java.util.List;
import java.util.Random;

public class user_medications_Adapter extends RecyclerView.Adapter<user_medications_Adapter.ViewHolder>
        {
    private second_prescription[] myusermed_list;

    public second_prescription[] getMyusermed_list() {
        return myusermed_list;
    }

    public void setMyusermed_list(second_prescription[] myusermed_list) {
        this.myusermed_list = myusermed_list;
    }

    public user_medications_Adapter() {

    }

    public user_medications_Adapter(List<second_prescription> mylist_) {
        this.myusermed_list = mylist_.toArray(new second_prescription[0]);
    }

    @NonNull
    @Override
    public user_medications_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.active_medication_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull user_medications_Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final second_prescription singlerolenotificationmodel = myusermed_list[position];
        //Toast.makeText(Context_maker.getInstance().getMyContext(),myusermed_list[position]+"",Toast.LENGTH_LONG).show();
        holder.med_tab_name.setText(myusermed_list[position].getDrug_name());
        holder.med_tab_strength.setText(myusermed_list[position].getDrug_strength()+" "+myusermed_list[position].getDrug_unit());
        holder.med_tab_image.setImageResource(R.drawable.supplement_bottlex);
        holder.med_tab_event.setText(myusermed_list[position].getIntake_method()+"");

        

        //random background color
        Random random=new Random();
        int color= Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(256));
        //holder.imageView.setBackgroundColor(color);
        holder.med_tab_image.setPadding(20,20,20,20);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //RO4
                // 9Toast.makeText(v.getContext(), "You are Click on  " + myusermed_list[position].getMed_tab_name(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(Context_maker.getInstance().getMyContext(),myusermed_list[position].getMed_tab_name()+"",Toast.LENGTH_LONG).show();

                DialogFragment bottomSheetDialogFragment = new ActionBottomDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("med_name",myusermed_list[position].getDrug_name());
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(((FragmentActivity)v.getContext()).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());



                //background and text color change
               // holder.itemView.setBackgroundColor(color);
               // holder.alarm_grug_name.setTextColor(Color.WHITE);
               // holder.alarm_grug_amount.setTextColor(Color.WHITE);
                //holder.alarm_drug_image.setBackgroundColor(Color.WHITE);
            }
        });
    }



    @Override
    public int getItemCount() {
        return myusermed_list.length;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView med_tab_image;
        public TextView med_tab_name;
        public TextView med_tab_strength;
        public TextView med_tab_event;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.med_tab_image = (ImageView) itemView.findViewById(R.id.active_imageview);
            this.med_tab_name = (TextView) itemView.findViewById(R.id.med_tab_medname);
            this.med_tab_strength = (TextView) itemView.findViewById(R.id.med_tab_strength);
            this.med_tab_event = (TextView) itemView.findViewById(R.id.med_tab_condition);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout);
        }
    }
}