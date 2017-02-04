package com.example.bhumihar.mafiv;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by bhumihar on 14/11/16.
 */
public class Alarm_Receiver  extends WakefulBroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String a_extra_s = intent.getExtras().getString("A_extra");
        Intent service_intent = new Intent(context ,RingtonePlayService.class);
        service_intent.putExtra("R_extra",a_extra_s);
        context.startService(service_intent);

    }



}
