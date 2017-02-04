package com.example.bhumihar.mafiv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by bhumihar on 14/11/16.
 */
public class RingtonePlayService extends Service {

    MediaPlayer mediaPlayer;
    int RstartId = 0;
    boolean isRunning = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("R_extra");
        Log.e("Rigntone state is:",state);


        assert state != null;
        switch (state)
        {
            case "yes":

                RstartId =1;
                break;
            case "no":

                RstartId =0;
                break;
            default:

                RstartId =0;
                break;
        }


        if (!this.isRunning && RstartId==1)
        {
            mediaPlayer = MediaPlayer.create(RingtonePlayService.this ,R.raw.alarm1);
            mediaPlayer.start();
            isRunning = true;
            RstartId = 0;

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            Intent start_intent = new Intent(this ,View_Remainder.class);
            start_intent.putExtra("Ringtone","untask");
            PendingIntent start_pending_intent = PendingIntent.getActivity(this ,(int)System.currentTimeMillis(),start_intent,0);
            //NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Go", start_pending_intent).build();
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("ALarm is going off")
                    .setContentText("Alarm off")
                    .setContentIntent(start_pending_intent)
                    .setSmallIcon(R.drawable.set_task)
                    .setAutoCancel(true)
                    .build();

            if (this.getApplicationInfo().targetSdkVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (notification.getSmallIcon() == null)
                {
                    throw new IllegalArgumentException("Invalid notification (no valid small icon): "
                            + notification);
                }
            }
            notificationManager.notify(10,notification);


        } else if (this.isRunning && RstartId==0) {

            mediaPlayer.stop();
            mediaPlayer.reset();
            isRunning = false ;
            RstartId = 0;

        } else if (this.isRunning && RstartId==1) {

            isRunning = true;

        } else if (!this.isRunning && RstartId==0) {

            isRunning = false;
        }


        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Log.e("OnDestroyed Called","Destroyed");
        super.onDestroy();

        this.isRunning = false;
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

}

