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

public class ViewActivePolls extends AppCompatActivity {

    ListView activePollList;
  //  public static final String QuestionID = "View";
    String selected_id,expiry;
    ArrayAdapter<String> adapter;
    public static final String Extra_Selected_Question="com.first.diksha.appiiitv.ViewActivePolls.selected_question";
    public static final String Extra_Selected_Question_ID="com.first.diksha.appiiitv.ViewActivePolls.selected_question_id";
    public static final String Extra_Selected_Question_Expiry="com.first.diksha.appiiitv.ViewActivePolls.selected_question_expiry";
    List<String> pollList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_active_polls);

        Toolbar toolbar = (Toolbar)findViewById(R.id.active_poll_toolbar);
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
            actionBar.setTitle("Active Poll");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        activePollList=(ListView)findViewById(R.id.viewPollsList);
        pollList=new ArrayList<String>();



        // send to the server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://mafiv.freeoda.com/android_php/polls_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String s) {
                Log.e("rs", s);
                //poll id, question,expiry date

                JSONObject num,ob;
                JSONArray data;

                pollList.clear();
                try {
                    num = new JSONObject(s);
                    String value = num.getString("data");
                    if (!value.equals("-1")) {
                        data = num.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            ob = data.getJSONObject(i);

                            String inactive_id = getIntent().getExtras().getString("inactive_id");
                            if (ob.getString("poll_id").contentEquals(inactive_id))
                            {
                              continue ;

                            }
                            else
                            {
                                pollList.add(ob.getString("question"));
                            }

                        }
                        adapter = new ArrayAdapter<String>(ViewActivePolls.this, R.layout.array_adapter_listview, R.id.yo, pollList);
                        activePollList.setAdapter(adapter);
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



                activePollList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected=parent.getItemAtPosition(position).toString();

                         //get from the object
                        JSONObject num,ob;
                        JSONArray data;
                        Log.e("rs", s);
                        try {
                            num=new JSONObject(s);
                            data=num.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                ob=data.getJSONObject(i);
                                if(ob.getString("question").equals(selected))
                                {
                                    selected_id=ob.getString("poll_id");
                                    expiry=ob.getString("expiry_date");
                                }
                            }
                            adapter=new ArrayAdapter<String>(ViewActivePolls.this,R.layout.array_adapter_listview,R.id.yo,pollList);
                            activePollList.setAdapter(adapter);
                            Intent intent=new Intent(ViewActivePolls.this,FillForm.class);
                            intent.putExtra(Extra_Selected_Question,selected);
                            intent.putExtra(Extra_Selected_Question_ID,selected_id);
                            intent.putExtra(Extra_Selected_Question_Expiry,expiry);
                            startActivity(intent);

                        }
                        catch(Exception e)
                        {
                            Log.e("except",e.toString());
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
                SaveUserData saveUserData = new SaveUserData(ViewActivePolls.this);
                UserData userData = saveUserData.Logged_User_Data();
                String id = userData.id;
                values.put("audience", id);

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
