package com.example.bhumihar.mafiv;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_Activity extends AppCompatActivity implements View.OnClickListener{

    SaveUserData LoggedUser;
    TextView library_b ,time_table_b ,poll_b ;

    int[] id_v ;
    String[] description_v ;
    String[] time_v ;
    String[] date_v ;
    int[]   check_v;



    private JSONArray users = null;
    public static final String JSON_ARRAY = "result";
    public static final String KEY_Id= "not_id";
    public static final String KEY_CHECK = "check_v";



    String url = "http://mafiv.freeoda.com/not_display.php";
    HashMap<String,String>params ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_);

        Toolbar toolbar = (Toolbar)findViewById(R.id.user_activity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)
        {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("M.A.F.I.V");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        intilize();
        shownotification();
      time_table_b.setOnClickListener(this);
        library_b.setOnClickListener(this);
        poll_b.setOnClickListener(this);

    }



    private void intilize() {

        LoggedUser = new SaveUserData(this);
        library_b = (TextView)findViewById(R.id.library_btn);
        time_table_b = (TextView)findViewById(R.id.time_table_btn);
        poll_b = (TextView)findViewById(R.id.poll_btn);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.mafiv_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_view_profile :

                startActivity(new Intent(User_Activity.this ,User_Profile.class));
                break;

            case R.id.menu_logout:
                LoggedUser.LoggedIn(false);
                LoggedUser.Clear_Data();
                startActivity(new Intent(User_Activity.this ,MainActivity.class));
                break;

            case R.id.menu_about:
                startActivity(new Intent(User_Activity.this ,About_us.class));
                break;

        }
        return false;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.library_btn:

                Intent intent=new Intent(User_Activity.this,Library_activity.class);
                startActivity(intent);
                break;

            case R.id.time_table_btn:
                 Intent time_table_intent = new Intent(User_Activity.this ,Time_Table.class);
                startActivity(time_table_intent);
                break;

            case R.id.poll_btn:
                Intent intent1 =new Intent(User_Activity.this,PollFeature.class);
                startActivity(intent1);
                break;

        }
    }

    private void shownotification() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                        Log.e("Notification Response",response);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley","That didn't work!");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SaveUserData saveUserData = new SaveUserData(User_Activity.this);
                UserData userData = saveUserData.Logged_User_Data();
                params = new HashMap<>();
                String sem = userData.sem;
                String branch = userData.branch;
                params.put("sem", sem);
                params.put("branch",branch);
                return params ;

            }
        };

        queue.add(stringrequest);



    }

    public void showJSON(String response) {
        
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(response);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            id_v = new int[users.length()];
             check_v = new int[users.length()];
            Notification_db n_db = new Notification_db(this);
            n_db.open();
            int[] get_id = n_db.get_id();

            int not =0;
            for (int i = 0; i < users.length(); i++) {

                org.json.JSONObject jo = users.getJSONObject(i);
               id_v[i] = jo.getInt(KEY_Id);

                if (getArrayIndex(get_id,id_v[i]) >=0){
                    Log.e("exist",String.valueOf(getArrayIndex(get_id,id_v[i])));
                }else
                {
                    not = 1;
                    long val = n_db.insert_data(id_v[i]);
                    Log.e("Longid",String.valueOf(val));
                }
                if(not==1)
                {
                    createnotification();
                }

            }

         n_db.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void createnotification() {

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent start_intent = new Intent(this ,Notification_Activity.class);
        start_intent.putExtra("Ringtone","untask");
        PendingIntent start_pending_intent = PendingIntent.getActivity(this ,(int)System.currentTimeMillis(),start_intent,0);
        //NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Go", start_pending_intent).build();
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Notification")
                .setContentText("New Notification Available")
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

    }

    public int getArrayIndex(int[] arr,int value) {

        int k=-1;
        for(int i=0;i<arr.length;i++){

            if(arr[i]==value){
                k=i;
                break;
            }
        }
        return k;
    }

}
