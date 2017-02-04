package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FillForm extends AppCompatActivity {

    Intent prevIntent;
    HashMap<String,String>option_array;
    RadioGroup radioGroup1;
    RadioButton[] radioButtonArray;
    RadioButton selected;
    TextView question,expire_on,created_by;
    Button btnSubmit;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);
        final Intent getPrevIntent=getIntent();

        option_array = new HashMap<>();
        question=(TextView)findViewById(R.id.question1);
        expire_on=(TextView)findViewById(R.id.ExpiresOn);
        created_by=(TextView)findViewById(R.id.CreatedBy);
        btnSubmit=(Button)findViewById(R.id.submit);
        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup);

        radioButtonArray=new RadioButton[]{
                (RadioButton) findViewById(R.id.Choice11),
                (RadioButton) findViewById(R.id.Choice22),
                (RadioButton) findViewById(R.id.Choice33),
                (RadioButton) findViewById(R.id.Choice44),
                (RadioButton) findViewById(R.id.Choice55),
                (RadioButton) findViewById(R.id.Choice66)

        } ;

        for(int i=0;i<6;i++)
        {
            radioButtonArray[i].setVisibility(View.INVISIBLE);
        }

        prevIntent=getIntent();
        question.setText(prevIntent.getStringExtra(ViewActivePolls.Extra_Selected_Question));
        expire_on.setText(prevIntent.getStringExtra(ViewActivePolls.Extra_Selected_Question_Expiry));

// obtain from server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://mafiv.freeoda.com/android_php/poll_view.php";
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
                    for(i=0;i<data.length()-1;i++){
                        ob=data.getJSONObject(i);
                        radioButtonArray[i].setVisibility(View.VISIBLE);
                       radioButtonArray[i].setText(ob.getString("description"));
                        option_array.put(ob.getString("description"),ob.getString("option_number"));

                    }
                    ob=data.getJSONObject(i);
                    created_by.setText(ob.getString("name"));


                }
                catch(Exception e)
                {
                    Log.e("except",e.toString());
                    //name.setText("except");
                }

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

                values.put("poll_id", prevIntent.getStringExtra(ViewActivePolls.Extra_Selected_Question_ID));

                JSONObject json = new JSONObject(values);
                Map<String, String> params = new HashMap<>();

                params.put("data", json.toString());
                String x = params.toString();
                Log.e("ss", x);
                return params;
            }
        };
        queue.add(stringRequest);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // send to the server
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://mafiv.freeoda.com/android_php/poll_respond.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("rs", s);
                        JSONObject num;
                        try {
                            num = new JSONObject(s);
                            if(num.getString("data").equals("1"))
                            {
                                Toast.makeText(FillForm.this,"Form Submitted",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(FillForm.this,ViewActivePolls.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("inactive_id",prevIntent.getStringExtra(ViewActivePolls.Extra_Selected_Question_ID));
                                startActivity(intent);
                                finish();


                            }

                            else
                                Toast.makeText(FillForm.this,"You have already filled this form",Toast.LENGTH_LONG).show();

                        }
                        catch (Exception e)
                        {
                            Log.e("except",e.toString());
                        }



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
                        //send checked item and poll id to server
                        values.put("poll_id",prevIntent.getStringExtra(ViewActivePolls.Extra_Selected_Question_ID));

                        //values.put("option_number", );
                        int selected_radio_button=radioGroup1.getCheckedRadioButtonId();
                        selected=(RadioButton)findViewById(selected_radio_button);


                        String option_values= option_array.get(selected.getText().toString());

                        values.put("option_number",option_values);
                        JSONObject json = new JSONObject(values);
                        Map<String, String> params = new HashMap<>();

                        params.put("data", json.toString());
                        String x = params.toString();
                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });



    }

}
