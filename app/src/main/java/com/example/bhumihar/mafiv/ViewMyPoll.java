package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

public class ViewMyPoll extends AppCompatActivity {

   ListView myPollsList;
    ArrayAdapter<String> adapter;
    String selected_item,selected_item_id,expiry,selected_check_poll;
    List<String> pollList;
    public static final String Extra_Selected_Item="com.first.diksha.appiiitv.ViewMyPoll.selected_item";
    public static final String Extra_Selected_Item_Id="com.first.diksha.appiiitv.ViewMyPoll.selected_item_id";
    public static final String Extra_Selected_Expiry="com.first.diksha.appiiitv.ViewMyPoll.expiry";
    public static final String Extra_Selected_CheckPoll="com.first.diksha.appiiitv.ViewMyPoll.selected_check_poll";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_poll);

        Toolbar toolbar = (Toolbar)findViewById(R.id.view_my_toolbar);
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
            actionBar.setTitle("View My Poll");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        myPollsList=(ListView)findViewById(R.id.myPollsList);
        pollList=new ArrayList<String>();


        // send to the server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://mafiv.freeoda.com/android_php/my_polls.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {
                Log.e("rs", s);
                //for loop
                //pollList.add("");  for whatever comes

                JSONObject num,ob;
                JSONArray data;
                String name1;
                pollList.clear();
                try {
                    num = new JSONObject(s);
                    String value = num.getString("data");
                    if (!value.equals("-1")) {
                        data = num.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            ob = data.getJSONObject(i);
                            pollList.add(ob.getString("question"));
                        }
                        adapter=new ArrayAdapter<String>(ViewMyPoll.this,R.layout.array_adapter_listview,R.id.yo,pollList);
                        myPollsList.setAdapter(adapter);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No active Polls",Toast.LENGTH_LONG).show();
                    }

                }
                catch(Exception e)
                {
                    Log.e("except", e.toString());
                    //name.setText("except");
                }


                myPollsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selected_item=parent.getItemAtPosition(position).toString();
                        JSONObject num,ob;
                        JSONArray data;
                        try {
                            num = new JSONObject(s);
                           String value=num.getString("data");

                            Log.e("abc",value);
                            if (!value.equals("-1")) {
                                data = num.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    ob = data.getJSONObject(i);
                                    if(ob.getString("question").equals(selected_item))
                                    {
                                        selected_item_id=ob.getString("poll_id");
                                        Log.e("id",selected_item_id);
                                        expiry=ob.getString("expiry_date");
                                        selected_check_poll=ob.getString("check_poll");
                                    }

                                }


                                adapter=new ArrayAdapter<String>(ViewMyPoll.this,R.layout.array_adapter_listview,R.id.yo,pollList);
                                myPollsList.setAdapter(adapter);
                                Intent intent=new Intent(ViewMyPoll.this,EditMyPoll.class);
                                intent.putExtra(Extra_Selected_Item,selected_item);
                                intent.putExtra(Extra_Selected_Item_Id,selected_item_id);
                                intent.putExtra(Extra_Selected_Expiry,expiry);
                                intent.putExtra(Extra_Selected_CheckPoll,selected_check_poll);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"No my Polls available",Toast.LENGTH_LONG).show();
                            }

                        }
                        catch(Exception e)
                        {
                            Log.e("except", e.toString());
                            //name.setText("except");
                        }


                    }
                });




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
                SaveUserData saveUserData = new SaveUserData(ViewMyPoll.this);
                UserData userData = saveUserData.Logged_User_Data();
                String id = userData.id;
                values.put("student_id", id);

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
