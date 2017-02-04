package com.example.bhumihar.mafiv;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhumihar on 15/11/16.
 */
public class Notification_Adapter extends BaseAdapter {

    Context mcontext;
    ArrayList<Notification_Data> not_value;
    TextView text1_v ,text2_v ,text3_v ,text4_v ,text_v5 ,text_v6 ,text_v7 ,text_v8;


    public Notification_Adapter(Context context) {
        mcontext = context;
    }

    @Override
    public int getCount() {
        return not_value.size();
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
        Log.e("in Adapter","Get View");
        View viw = view;
        if (viw==null)
        {
            viw = LayoutInflater.from(mcontext).inflate(R.layout.notification_value,null,false);
        }


        text1_v = (TextView)viw.findViewById(R.id.ntext1);
        text2_v  = (TextView)viw.findViewById(R.id.ntext2);
        text3_v = (TextView)viw.findViewById(R.id.ntext3);
        text4_v = (TextView)viw.findViewById(R.id.ntext4);
        text_v5 = (TextView)viw.findViewById(R.id.ntext5);
        text_v6 = (TextView)viw.findViewById(R.id.ntext6);
        text_v7 = (TextView)viw.findViewById(R.id.ntext7);
        text_v8 = (TextView)viw.findViewById(R.id.ntext8);


        Notification_Data notData = not_value.get(i);

        text1_v.setText(notData.not_tit);
        text2_v.setText(notData.not_tit_v);
        text3_v.setText(notData.not_body);
        text4_v.setText(notData.not_body_v);
        text_v5.setText(notData.not_date);
        text_v6.setText(notData.not_date_v);
        text_v7.setText(notData.not_time);
        text_v8.setText(notData.not_time_v);




        viw.setBackgroundColor(Color.parseColor("#fbdcbb"));

        return viw;

    }

    public void setData(ArrayList<Notification_Data> data) {
        not_value = data;
        Log.e("In Time Adapter","In Set Data");
        notifyDataSetChanged();
    }

}
