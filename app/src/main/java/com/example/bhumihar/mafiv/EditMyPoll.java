package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

public class EditMyPoll extends AppCompatActivity {

    CheckBox[] checkBoxArray;
    TextView question,expire_on;
    Button btnDelete,btnViewResults;
    Intent getPrevIntent;
    String poll_id;
    public static final String Extra_Poll_Id="com.first.diksha.appiiitv.EditMyPoll.poll_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_poll);

        Toolbar toolbar = (Toolbar)findViewById(R.id.edit_poll_toolbar);
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
            actionBar.setTitle("Edit Poll");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        getPrevIntent=getIntent();


        question=(TextView)findViewById(R.id.question1);
        expire_on=(TextView)findViewById(R.id.ExpiryDate);

        btnDelete=(Button)findViewById(R.id.delete);
        btnViewResults=(Button)findViewById(R.id.viewResults);

        checkBoxArray=new CheckBox[]{
                (CheckBox)findViewById(R.id.Choice11),
                (CheckBox)findViewById(R.id.Choice22),
                (CheckBox)findViewById(R.id.Choice33),
                (CheckBox)findViewById(R.id.Choice44),
                (CheckBox)findViewById(R.id.Choice55),
                (CheckBox)findViewById(R.id.Choice66)

        } ;

        for(int i=0;i<6;i++)
        {
            checkBoxArray[i].setVisibility(View.INVISIBLE);
        }
        question.setText(getPrevIntent.getStringExtra(ViewMyPoll.Extra_Selected_Item));

        expire_on.setText(getPrevIntent.getStringExtra(ViewMyPoll.Extra_Selected_Expiry));



        // send to the server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://mafiv.freeoda.com/android_php/my_response.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e("rs", s);
                JSONObject num,ob;
                JSONArray data;
                String name1;

                try {
                    int i;
                    num=new JSONObject(s);
                    data=num.getJSONArray("data");
                    for(i=0;i<data.length();i++){
                        ob=data.getJSONObject(i);
                        checkBoxArray[i].setVisibility(View.VISIBLE);
                        checkBoxArray[i].setText(ob.getString("description"));
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
                values.put("poll_id", getPrevIntent.getStringExtra(ViewMyPoll.Extra_Selected_Item_Id));

                JSONObject json = new JSONObject(values);
                Map<String, String> params = new HashMap<>();

                params.put("data", json.toString());
                String x = params.toString();
                Log.e("ss", x);
                return params;
            }
        };
        queue.add(stringRequest);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // send to the server
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://mafiv.freeoda.com/android_php/delete_poll.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("rs", s);
                        JSONObject num,ob;


                        try {
                            num=new JSONObject(s);

                            if(num.getString("data").equals("1"))
                            {
                                Toast.makeText(getApplicationContext(),"Poll deleted successfully",Toast.LENGTH_LONG);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Poll not deleted",Toast.LENGTH_LONG);
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
                        values.put("poll_id", getPrevIntent.getStringExtra(ViewMyPoll.Extra_Selected_Item_Id));

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
        });

        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditMyPoll.this,Poll_Results.class);
                intent.putExtra(Extra_Poll_Id,getPrevIntent.getStringExtra(ViewMyPoll.Extra_Selected_Item_Id));
                startActivity(intent);
            }
        });
    }
}
