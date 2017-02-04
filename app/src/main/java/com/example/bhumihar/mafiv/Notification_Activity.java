package com.example.bhumihar.mafiv;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notification_Activity extends AppCompatActivity {

    ArrayList<Notification_Data> not_data ;
    private GridView notification_grid;
    Notification_Adapter notificatoinAdapter ;
    private Map<String,String> params;


    String url = "http://mafiv.freeoda.com/not_display.php";

    String[] tittle_v ;
    String[] description_v ;
    String[] time_v ;
    String[] date_v ;
    int[]   check_v;




    private JSONArray users = null;
    public static final String JSON_ARRAY = "result";
    public static final String KEY_Tittle = "Tittle";
    public static final String KEY_Description = "Description";
    public static final String KEY_time = "time";
    public static final String KEY_date = "date";
    public static final String KEY_CHECK = "check_v";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_);


        Toolbar toolbar = (Toolbar)findViewById(R.id.notification_activity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Notification Viewer");
            actionBar.setDisplayShowTitleEnabled(true);

        }


        not_data = new ArrayList<>();
        notification_grid = (GridView)findViewById(R.id.not_grid);
        notificatoinAdapter = new Notification_Adapter(this);

        getDatafromserver();
        notificatoinAdapter.setData(not_data);
        notification_grid.setAdapter(notificatoinAdapter);


    }


    private void getDatafromserver() {
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
                SaveUserData saveUserData = new SaveUserData(Notification_Activity.this);
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

        Log.e("In Time-Activity","ShowJson Function");
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(response);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            tittle_v = new String[users.length()];
            description_v = new String[users.length()];
            date_v = new String[users.length()];
            time_v = new String[users.length()];
             check_v = new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                org.json.JSONObject jo = users.getJSONObject(i);
                tittle_v[i] = jo.getString(KEY_Tittle);
                description_v[i] = jo.getString(KEY_Description);
                date_v[i] = jo.getString(KEY_date);
                time_v[i] = jo.getString(KEY_time);
                check_v[i] = jo.getInt(KEY_CHECK);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Json_Data json_data  = new Json_Data(tittle_v,description_v,date_v ,time_v,check_v);
        for (int i=0;i<json_data.value1.length;i++)
        {
            not_data.add(new Notification_Data(json_data.value1[i],json_data.value2[i],json_data.value3[i],json_data.value4[i],json_data.value5[i]));
        }
             notificatoinAdapter.setData(not_data);
             notification_grid.setAdapter(notificatoinAdapter);



    }


}
