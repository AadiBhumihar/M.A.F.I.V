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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Bus_Time_Activity extends AppCompatActivity {

    ArrayList<Time_Data> arrayList;
    private GridView bus_grid;
    Bus_Time_Adapter bus_adapter;
    TextView day_txt_v;
    private Map<String,String> params;
    String Time_Table ,day;

    String url = "http://mafiv.freeoda.com/b_time_table.php";

    String[] time_v ;
    String[] from_v ;
    String[] to_v ;
    String[] batch_v ;
    int[] no_v ;



    private JSONArray users = null;
    public static final String JSON_ARRAY = "result";
    public static final String KEY_Time = "Time";
    public static final String KEY_From = "From";
    public static final String KEY_To = "To";
    public static final String KEY_No = "No";
    public static final String KEY_Batch = "Batch";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus__time_);

        intilize();

        Bundle p = getIntent().getExtras();
        day = p.getString("day");
        day_txt_v.setText(day);
        Time_Table = p.getString("time-table");

        Toolbar toolbar = (Toolbar) findViewById(R.id.bus_time_activity_toolbar);
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
        bus_grid = (GridView) findViewById(R.id.bus_view_grid);
        day_txt_v = (TextView) findViewById(R.id.day_text);
        bus_adapter = new Bus_Time_Adapter(this);
        arrayList.add(new Time_Data("Time", "From", "TO", "Batch", "No."));
    }

    private void setgrid() {
        Log.e("In Time-Activity","Setgrid Function");
        bus_grid.setStretchMode(GridView.NO_STRETCH);
        bus_grid.setNumColumns(1);

        bus_adapter.setData(arrayList);
        bus_grid.setAdapter(bus_adapter);

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
                params.put("day",day);
                Log.e("Return","Params");
                return params;

            }
        };

        queue.add(stringrequest);



    }

    public void showJSON(String response) {

        Log.e("In Bus_Time-Activity","ShowJson Function");
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(response);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            time_v = new String[users.length()];
            from_v = new String[users.length()];
            to_v = new String[users.length()];
            batch_v = new String[users.length()];
            no_v = new int[users.length()];

            for (int i = 0; i < users.length(); i++) {
                org.json.JSONObject jo = users.getJSONObject(i);
                time_v[i] = jo.getString(KEY_Time);
                from_v[i] = jo.getString(KEY_From);
                to_v[i] = jo.getString(KEY_To);
                batch_v[i] = jo.getString(KEY_Batch);
                no_v[i] = jo.getInt(KEY_No);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Json_Data json_data  = new Json_Data(time_v,from_v,to_v,batch_v,no_v);
        for (int i=0;i<json_data.value1.length;i++)
        {
            arrayList.add(new Time_Data(json_data.value1[i],json_data.value2[i],json_data.value3[i],json_data.value4[i],String.valueOf(json_data.value5[i])));
        }

        bus_adapter.setData(arrayList);
        bus_grid.setAdapter(bus_adapter);



    }


}
