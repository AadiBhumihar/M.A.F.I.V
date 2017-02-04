package com.example.bhumihar.mafiv;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePoll extends AppCompatActivity {

    String str_num_choices;
    EditText question;
    EditText[] editTextArray;
    Button btnCreatePoll;
    Spinner choices;
    CheckBox year1,year2,year3,year4,CS,IT;
    int num_choices;
    Button change_date;
    final int Date_Dialog_ID=0;
    int cDay,cMonth,cYear; // this is the instances of the current date
    Calendar cDate;
    int sDay,sMonth,sYear;
    private Button date1;
    int num_audience;
    String[] audience;
    int[] year_array,branch_array;


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Date_Dialog_ID:
                return new DatePickerDialog(this, onDateSet, cYear, cMonth,
                        cDay);
        }
        return null;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        date1=(Button)findViewById(R.id.date_picker);


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Date_Dialog_ID);
            }
        });


        //getting current date
        cDate=Calendar.getInstance();
        cDay=cDate.get(Calendar.DAY_OF_MONTH);
        cMonth=cDate.get(Calendar.MONTH);
        cYear=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
        sDay=cDay;
        sMonth=cMonth;
        sYear=cYear;
        updateDateDisplay(sYear,sMonth,sDay);





        question=(EditText)findViewById(R.id.question1);
        editTextArray=new EditText[]{
                (EditText)findViewById(R.id.choice1),
                (EditText)findViewById(R.id.choice2),
                (EditText)findViewById(R.id.choice3),
                (EditText)findViewById(R.id.choice4),
                (EditText)findViewById(R.id.choice5),
                (EditText)findViewById(R.id.choice6)
        };

        for(int i=0;i<6;i++)
        {
            editTextArray[i].setVisibility(View.INVISIBLE);
        }

        choices=(Spinner)findViewById(R.id.spinner2);

        year1=(CheckBox)findViewById(R.id.year1);
        year2=(CheckBox)findViewById(R.id.year2);
        year3=(CheckBox)findViewById(R.id.year3);
        year4=(CheckBox)findViewById(R.id.year4);



        CS=(CheckBox)findViewById(R.id.cs);
        IT=(CheckBox)findViewById(R.id.it);

        btnCreatePoll=(Button)findViewById(R.id.create_button);

        //TODO: Add Date picker

        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5);
        list1.add(6);


        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, list1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choices.setAdapter(dataAdapter);




        choices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_num_choices=parent.getSelectedItem().toString();

                num_choices=Integer.parseInt(str_num_choices);

                for(int i=0;i<6;i++)
                {
                    editTextArray[i].setVisibility(View.INVISIBLE);
                }

                for(int i=1;i<=num_choices;i++)
                {
                    editTextArray[i-1].setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //object

        btnCreatePoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to the server
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://mafiv.freeoda.com/android_php/poll_add.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("rs", s);
                        JSONObject num;
                        try {
                            num = new JSONObject(s);
                            if(num.getString("data").equals("1"))
                            {
                                Toast.makeText(CreatePoll.this,"Poll created successfully",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(CreatePoll.this,PollFeature.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();


                            }

                            else
                                Toast.makeText(CreatePoll.this,"Poll not created",Toast.LENGTH_LONG).show();

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



                        JSONObject json = new JSONObject();
                        Map<String, String> params = new HashMap<>();


                       year_array=new int[4];
                        for(int i=0;i<4;i++)
                        {
                            year_array[i]=-1;
                        }
                       branch_array=new int[2];
                        for(int i=0;i<2;i++)
                        {
                           branch_array[i]=-1;
                        }
                        int count_year=0,count_branch=0;

                        if(year1.isChecked())
                        {
                            count_year++;
                            //Sem1="sem1";
                            year_array[0]=1;

                        }

                        if(year2.isChecked())
                        {
                            count_year++;
                            //Sem3="sem3";
                            year_array[1]=2;
                        }

                        if(year3.isChecked())
                        {
                            count_year++;
                            //Sem5="sem5";
                            year_array[2]=3;

                        }

                        if(year4.isChecked())
                        {
                            count_year++;
                            //Sem7="sem7";
                            year_array[3]=4;
                        }

                        if(CS.isChecked())
                        {
                            count_branch++;
                            //Cs="CS";
                            branch_array[0]=1;

                        }

                        if(IT.isChecked())
                        {
                            count_branch++;
                           branch_array[1]=0;

                        }
                        JSONArray desc=new JSONArray();
                        JSONArray audi=new JSONArray();

                        int total_count=count_branch*count_year;
                        int[] audience_array=new int[total_count];
                        int k=0;
                        for(int i=0;i<4;i++)
                        {
                            for(int j=0;j<2;j++)
                            {
                                if(year_array[i]!=-1 && branch_array[j]!=-1)
                                {
                                    int x;
                                    x=2*year_array[i]-branch_array[j];
                                    audience_array[k]=x;
                                    audi.put(String.valueOf(audience_array[k]));
                                    k++;
                                }

                            }
                        }





                        try{

                            num_audience=2;
                            audience=new String[num_audience];

                            json.put("question",question.getText().toString());
                            json.put("expiry_date",date1.getText().toString());
                            json.put("number_of_options",String.valueOf(num_choices));
                            SaveUserData saveUserData = new SaveUserData(CreatePoll.this);
                            UserData userData = saveUserData.Logged_User_Data();
                            String id = userData.id;
                            json.put("student_id", id);
                            String[] choice_array=new String[num_choices];

                            for(int i=0;i<num_choices;i++)
                            {
                                choice_array[i]=editTextArray[i].getText().toString();
                                desc.put(choice_array[i]);
                                Log.e("des",choice_array[i]);
                            }

                            json.put("description",desc);

                            json.put("number_of_audience",String.valueOf(total_count));

                            json.put("audience",audi);
                        }
                        catch(Exception e){

                        }

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
    private void updateDateDisplay(int year,int month,int date) {

        date1.setText(year+"-"+(month+1)+"-"+date);

    }

    private DatePickerDialog.OnDateSetListener onDateSet=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            sYear=year;
            sMonth=month;
            sDay=dayOfMonth;
            updateDateDisplay(sYear,sMonth,sDay);
        }

    };
}
