package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
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
import org.json.simple.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Time_Activity extends AppCompatActivity {

    ArrayList<Time_Data> arrayList;
    private GridView time_table_grid;
    Time_Table_Adapter lecture_adapter;
    TextView day_txt_v;
    private Map<String,String> params;
    String Time_Table ,day;
    SaveUserData LoggedUser;
    UserData userdata;
    String url = "http://mafiv.freeoda.com/l_time_table.php";

    String[] time_v ;
    String[] course_id_v ;
    String[] course_v ;
    String[] faculty_v ;
    int[] room_no_v ;



    private JSONArray users = null;
    public static final String JSON_ARRAY = "result";
    public static final String KEY_Time = "Time";
    public static final String KEY_Course_id = "Course_id";
    public static final String KEY_Course = "Course";
    public static final String KEY_Faculty = "Faculty";
    public static final String KEY_Room_no = "Room_no";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_);

           intilize();

        Bundle p = getIntent().getExtras();
        day = p.getString("day");
        day_txt_v.setText(day);
        Time_Table = p.getString("time-table");

        Toolbar toolbar = (Toolbar) findViewById(R.id.time_activity_toolbar);
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
            actionBar.setTitle(Time_Table+"Time Table");

            actionBar.setDisplayShowTitleEnabled(true);

        }

        getDatafromserver();
        setgrid();


    }

    private void intilize() {

        arrayList = new ArrayList<>();
        time_table_grid = (GridView) findViewById(R.id.Lec_grid);

        day_txt_v = (TextView) findViewById(R.id.day_text);
        LoggedUser = new SaveUserData(this);
        userdata = LoggedUser.Logged_User_Data();
        lecture_adapter = new Time_Table_Adapter(this);
        arrayList.add(new Time_Data("Time", "Course_Id", "Course_Name", "Faculty", "Room no."));
    }


    private void setgrid() {
        Log.e("In Time-Activity","Setgrid Function");
        time_table_grid.setStretchMode(GridView.NO_STRETCH);
        time_table_grid.setNumColumns(1);

        lecture_adapter.setData(arrayList);
        time_table_grid.setAdapter(lecture_adapter);

    }


    private void getDatafromserver() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                        Log.e("Response",response);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley","That didn't work!");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("semester",userdata.sem);
                params.put("Branch",userdata.branch );
                params.put("day",day);
                Log.e("Return","Params");
                return params;

            }
        };

        queue.add(stringrequest);



    }

    public void showJSON(String response) {

        Log.e("In Time-Activity","ShowJson Function");
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(response);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            time_v = new String[users.length()];
            course_id_v = new String[users.length()];
            course_v = new String[users.length()];
            faculty_v = new String[users.length()];
            room_no_v = new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                org.json.JSONObject jo = users.getJSONObject(i);
                time_v[i] = jo.getString(KEY_Time);
                course_id_v[i] = jo.getString(KEY_Course_id);
                course_v[i] = jo.getString(KEY_Course);
                faculty_v[i] = jo.getString(KEY_Faculty);
                room_no_v[i] = jo.getInt(KEY_Room_no);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Json_Data json_data  = new Json_Data(time_v,course_id_v,course_v,faculty_v,room_no_v);
        for (int i=0;i<json_data.value1.length;i++)
        {
            arrayList.add(new Time_Data(json_data.value1[i],json_data.value2[i],json_data.value3[i],json_data.value4[i],String.valueOf(json_data.value5[i])));
        }

        lecture_adapter.setData(arrayList);
        time_table_grid.setAdapter(lecture_adapter);



    }


}
