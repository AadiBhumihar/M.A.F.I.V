package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import static android.R.attr.value;

public class Library_activity extends AppCompatActivity {

    Spinner spinner;
    String selected_item;
    EditText name;
    Button submit;

    private ListView book_list;
    private bookListAdapter bAdapter;
    private List<book> mBookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.library_toolbar);
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
            actionBar.setTitle("Search Library");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        spinner = (Spinner) findViewById(R.id.spinner);
        name = (EditText) findViewById(R.id.name);
        submit = (Button) findViewById(R.id.btnsubmit);
        book_list = (ListView) findViewById(R.id.listView);

        mBookList=new ArrayList<>();

        List<String> list = new ArrayList<String>();
        list.add("title");
        list.add("author");
        list.add("type");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selected_item=parent.getItemAtPosition(position).toString();

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(1);
           }
       });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to the server
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://mafiv.freeoda.com/android_php/library_search.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("rs", s);



                        JSONObject num,ob;
                        JSONArray data;
                        String name1;
                        mBookList.clear();
                        try {
                            num=new JSONObject(s);
                            data=num.getJSONArray("data");
                            String value = num.getString("data");

                            if (!value.equals("-1"))
                                {
                                    for(int i=0;i<data.length();i++){
                                        ob=data.getJSONObject(i);
                                        String avail,issue_id,returnD;
                                        if(ob.getString("avail").equals("1"))
                                        {
                                            avail="Available";
                                            issue_id="";
                                            returnD="";
                                        }
                                        else
                                        {
                                            avail="Not Available";
                                            issue_id="Issued To:"+ob.getString("issue_id");
                                            returnD="Return Date:"+ob.getString("expected_return_date");
                                        }
                                        mBookList.add(new book(i,ob.getString("title"),ob.getString("author"),"Type:"+ob.getString("type"),avail,issue_id,returnD));
                                    }
                                    bAdapter=new bookListAdapter(Library_activity.this,mBookList);
                                    book_list.setAdapter(bAdapter);

                                }else{
                                    Toast.makeText(Library_activity.this, "No Books Found", Toast.LENGTH_SHORT).show();
                                }


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
                        values.put("name", name.getText().toString());
                        values.put("search", selected_item);

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
    }

}

