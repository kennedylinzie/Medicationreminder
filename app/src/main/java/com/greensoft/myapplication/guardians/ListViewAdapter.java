package com.greensoft.myapplication.guardians;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greensoft.myapplication.R;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    private String[] item;
    int[] image;
    LayoutInflater inflater;

    public ListViewAdapter() {

    }

    public String[] getItem() {
        return item;
    }

    public void setItem(String[] item) {
        this.item = item;
    }

    public ListViewAdapter(Context context, int[] image, String[] item)
    {
      this.context = context;
      this.item  = item;
      this.image = image;
    }

    @Override
    public int getCount() {
        return item.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView gu_name,gu_phone;
        ImageView imgflag;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listviewlayout,null,false);
        gu_name = itemView.findViewById(R.id.g_list_name);
        gu_phone = itemView.findViewById(R.id.g_list_phone);
        imgflag = itemView.findViewById(R.id.g_list_imageview);

        gu_name.setText(item[i]);
        gu_phone.setText(item[i]);
        imgflag.setImageResource(image[i]);



        return itemView;
    }
}
