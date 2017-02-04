package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Poll_Results extends AppCompatActivity {

    Intent prevIntent;
    TextView[] textViewArray,textViewArray2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll__results);

        Toolbar toolbar = (Toolbar)findViewById(R.id.poll_result_toolbar);
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
            actionBar.setTitle("Poll Results");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        textViewArray=new TextView[]{
                (TextView) findViewById(R.id.choice1),
                (TextView) findViewById(R.id.choice2),
                (TextView) findViewById(R.id.choice3),
                (TextView) findViewById(R.id.choice4),
                (TextView) findViewById(R.id.choice5),
                (TextView) findViewById(R.id.choice6)

        } ;

        textViewArray2=new TextView[]{
                (TextView) findViewById(R.id.res1),
                (TextView) findViewById(R.id.res2),
                (TextView) findViewById(R.id.res3),
                (TextView) findViewById(R.id.res4),
                (TextView) findViewById(R.id.res5),
                (TextView) findViewById(R.id.res6)

        } ;

        for(int i=0;i<6;i++)
        {
            textViewArray[i].setVisibility(View.INVISIBLE);
            textViewArray2[i].setVisibility(View.INVISIBLE);
        }

        prevIntent=getIntent();
        // send to the server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://mafiv.freeoda.com/android_php/my_response.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("rs", s);
                JSONObject num,ob;
                JSONArray data;


                try {
                    int i;
                    num=new JSONObject(s);
                    data=num.getJSONArray("data");
                    for(i=0;i<data.length();i++){
                        ob=data.getJSONObject(i);
                        textViewArray[i].setVisibility(View.VISIBLE);
                        textViewArray2[i].setVisibility(View.VISIBLE);
                        textViewArray[i].setText(ob.getString("description"));
                        textViewArray2[i].setText(ob.getString("number_of_resp"));
                    }
                }
                catch(Exception e)
                {
                    Log.e("except",e.toString());
                    //name.setText("except");
                }

                //get all details and set them to edittext, checkboxees etc

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("err", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> values = new HashMap<>();
                values.put("poll_id", prevIntent.getStringExtra(EditMyPoll.Extra_Poll_Id));

                JSONObject json = new JSONObject(values);
                Map<String, String> params = new HashMap<>();

                params.put("data", json.toString());
                String x = params.toString();
                Log.e("ss", x);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
