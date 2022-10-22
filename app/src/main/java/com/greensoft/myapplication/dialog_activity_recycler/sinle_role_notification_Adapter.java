package com.greensoft.myapplication.dialog_activity_recycler;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.greensoft.myapplication.R;

import java.util.Random;

public class sinle_role_notification_Adapter extends RecyclerView.Adapter<sinle_role_notification_Adapter.ViewHolder> {
    private single_role_notification_model[] mylist;

    public sinle_role_notification_Adapter(single_role_notification_model[] mylist) {
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public sinle_role_notification_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.single_row_medication_notification_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull sinle_role_notification_Adapter.ViewHolder holder, int position) {
        final single_role_notification_model singlerolenotificationmodel = mylist[position];
        holder.alarm_grug_name.setText(mylist[position].getName());
        holder.alarm_grug_amount.setText(mylist[position].getDrug_amount()+" pill/s");
        holder.alarm_grug_time.setText(""+mylist[position].getDrug_time());
        holder.alarm_drug_image.setImageResource(mylist[position].getImages());

        //random background color
        Random random=new Random();
        int color= Color.argb(255,random.nextInt(255),random.nextInt(255),random.nextInt(256));
        //holder.imageView.setBackgroundColor(color);
        holder.alarm_drug_image.setPadding(20,20,20,20);

      /*  holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You are Click on" + singlerolenotificationmodel.getName(), Toast.LENGTH_SHORT).show();
//background and text color change
                holder.itemView.setBackgroundColor(color);
                holder.alarm_grug_name.setTextColor(Color.WHITE);
                holder.alarm_grug_amount.setTextColor(Color.WHITE);
                holder.alarm_drug_image.setBackgroundColor(Color.WHITE);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mylist.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView alarm_drug_image;
        public TextView alarm_grug_name;
        public TextView alarm_grug_amount;
        public TextView alarm_grug_time;
        public RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.alarm_drug_image = itemView.findViewById(R.id.active_imageview);
            this.alarm_grug_name = itemView.findViewById(R.id.alarm_drud_name);
            this.alarm_grug_amount =  itemView.findViewById(R.id.alarm_drug_amount);
            this.alarm_grug_time =  itemView.findViewById(R.id.alarm_drug_time);
            this.relativeLayout = itemView.findViewById(R.id.relativelayout);
        }
    }
}