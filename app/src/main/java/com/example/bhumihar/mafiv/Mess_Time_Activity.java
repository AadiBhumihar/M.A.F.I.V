package com.example.bhumihar.mafiv;

import android.support.v7.app.ActionBar;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mess_Time_Activity extends AppCompatActivity {


    TextView day_txt_v ,mtxt2 ,mtxt4,mtxt6,mtxt8;
    String Time_Table ,day;

    private Map<String,String> params;



    String url = "http://mafiv.freeoda.com/mess_menu.php";

    String breakfast ;
    String lunch ;
    String snacks ;
    String dinner ;





    private JSONArray users = null;
    public static final String JSON_ARRAY = "result";
    public static final String KEY_BREAKFAST= "breakfast";
    public static final String KEY_LUNCH = "lunch";
    public static final String KEY_SNACKS = "snacks";
    public static final String KEY_DINNER = "dinner";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess__time_);
        intilize();

        Bundle p = getIntent().getExtras();
        day = p.getString("day");
        day_txt_v.setText(day);
        Time_Table = p.getString("time-table");

        Toolbar toolbar = (Toolbar) findViewById(R.id.mess_time_activity_toolbar);
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

    }

    private void intilize() {


        day_txt_v = (TextView) findViewById(R.id.day_text);
        mtxt2 = (TextView)findViewById(R.id.mtext2);
        mtxt4 = (TextView)findViewById(R.id.mtext4);
        mtxt6 = (TextView)findViewById(R.id.mtext6);
        mtxt8 = (TextView)findViewById(R.id.mtext8);

    }


    private void getDatafromserver() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringrequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            breakfast = jsonObject.getString("breakfast");
                            lunch = jsonObject.getString("lunch");
                            snacks = jsonObject.getString("snacks");
                            dinner = jsonObject.getString("dinner");
                            mtxt2.setText(breakfast);
                            mtxt4.setText(lunch);
                            mtxt6.setText(snacks);
                            mtxt8.setText(dinner);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "That didn't work!");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                params = new HashMap<>();
                params.put("day", day);
                Log.e("Return", "Params");
                return params;

            }
        };

        queue.add(stringrequest);
    }




}
