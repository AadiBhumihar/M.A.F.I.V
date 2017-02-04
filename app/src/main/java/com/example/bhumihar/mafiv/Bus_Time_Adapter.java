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
 * Created by bhumihar on 11/11/16.
 */

public class Bus_Time_Adapter extends BaseAdapter{

    Context mcontext;
    ArrayList<Time_Data> time_value;
    TextView text1_v ,text2_v ,text3_v ,text4_v ,text5_v;

    public Bus_Time_Adapter(Context mcontext) {
        this.mcontext = mcontext;
        time_value = new ArrayList<>();
        Log.e("in Time_Adapter","kkkk");
    }


    @Override
    public int getCount() {

        Log.e("Adapter count",String.valueOf(time_value.size()));
        return time_value.size();
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
            viw = LayoutInflater.from(mcontext).inflate(R.layout.time_value,null,false);
        }


        text1_v = (TextView)viw.findViewById(R.id.text1);
        text2_v  = (TextView)viw.findViewById(R.id.text2);
        text3_v = (TextView)viw.findViewById(R.id.text3);
        text4_v = (TextView)viw.findViewById(R.id.text4);
        text5_v = (TextView)viw.findViewById(R.id.text5);



        Time_Data timeData = time_value.get(i);

        text1_v.setText(timeData.time_v1);
        text2_v.setText(timeData.time_v2);
        text3_v.setText(timeData.time_v3);
        text4_v.setText(timeData.time_v4);
        text5_v.setText(timeData.time_v5);



        viw.setBackgroundColor(Color.parseColor("#fbdcbb"));

        return viw;

    }

    public void setData(ArrayList<Time_Data> data) {
        time_value = data;
        Log.e("In Time Adapter","In Set Data");
        notifyDataSetChanged();
    }

}
