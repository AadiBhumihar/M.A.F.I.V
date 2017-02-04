package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Week_Activity extends AppCompatActivity implements View.OnClickListener{

    TextView m_daytext ,t_daytext ,w_daytext ,th_daytext ,fr_daytext ,sa_daytext ,su_daytext;
    String Collage_Time_Table;
    Class new_class ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_);

        intilize();

        Bundle bundle = getIntent().getExtras();
        Collage_Time_Table = bundle.getString("Time_Table");


        Toolbar toolbar = (Toolbar)findViewById(R.id.lecture_toolbar);
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
            actionBar.setTitle(Collage_Time_Table+" Time Table");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        m_daytext.setOnClickListener(this);
        t_daytext.setOnClickListener(this);
        w_daytext.setOnClickListener(this);
        th_daytext.setOnClickListener(this);
        fr_daytext.setOnClickListener(this);
        sa_daytext.setOnClickListener(this);
        su_daytext.setOnClickListener(this);

    }

    private void intilize() {

        m_daytext = (TextView)findViewById(R.id.mon_t);
        t_daytext = (TextView)findViewById(R.id.tue_t);
        w_daytext = (TextView)findViewById(R.id.wed_t);
        th_daytext = (TextView)findViewById(R.id.thr_t);
        fr_daytext = (TextView)findViewById(R.id.fri_t);
        sa_daytext = (TextView)findViewById(R.id.sat_t);
        su_daytext = (TextView)findViewById(R.id.sun_t);

        Bundle bundle = getIntent().getExtras();
        bundle.getString("Time_Table");
    }

    @Override
    public void onClick(View view) {

        String activity_str ;

          if (Collage_Time_Table.contentEquals("Bus"))
          {
              activity_str = "Bus_Time_Activity";
          }else if(Collage_Time_Table.contentEquals("Lecture"))
          {
              activity_str = "Time_Activity";

          }else if(Collage_Time_Table.contentEquals("Mess"))
          {
              activity_str = "Mess_Time_Activity";
          } else
          {
              activity_str = " ";
          }
        try {
            new_class = Class.forName("com.example.bhumihar.mafiv."+activity_str);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        switch (view.getId())
        {
            case R.id.mon_t:

                Intent day_intent1 = new Intent(Week_Activity.this ,new_class);
                Bundle mBundle1 = new Bundle();

                String day_text_v1 = m_daytext.getText().toString();
                mBundle1.putString("day", day_text_v1);
                mBundle1.putString("time-table",Collage_Time_Table);
                day_intent1.putExtras(mBundle1);
                startActivity(day_intent1);
                break;

            case R.id.tue_t:
                Intent day_intent2 = new Intent(Week_Activity.this ,new_class);
                Bundle mBundle2 = new Bundle();

                String day_text_v2 = t_daytext.getText().toString();
                mBundle2.putString("day", day_text_v2);
                mBundle2.putString("time-table",Collage_Time_Table);
                day_intent2.putExtras(mBundle2);
                startActivity(day_intent2);
                break;

            case R.id.wed_t:
                Intent day_intent3 = new Intent(Week_Activity.this ,new_class);
                Bundle mBundle3 = new Bundle();

                String day_text_v3 = w_daytext.getText().toString();
                mBundle3.putString("day", day_text_v3);
                mBundle3.putString("time-table",Collage_Time_Table);
                day_intent3.putExtras(mBundle3);
                startActivity(day_intent3);

                break;

            case R.id.thr_t:

                Intent day_intent4 = new Intent(Week_Activity.this ,new_class);
                Bundle mBundle4 = new Bundle();

                String day_text_v4 = th_daytext.getText().toString();
                mBundle4.putString("day", day_text_v4);
                mBundle4.putString("time-table",Collage_Time_Table);
                day_intent4.putExtras(mBundle4);
                startActivity(day_intent4);

                break;

            case R.id.fri_t:


                Intent day_intent5 = new Intent(Week_Activity.this ,new_class);
                Bundle mBundle5 = new Bundle();
                String day_text_v5 = fr_daytext.getText().toString();
                mBundle5.putString("day", day_text_v5);
                mBundle5.putString("time-table",Collage_Time_Table);
                day_intent5.putExtras(mBundle5);
                startActivity(day_intent5);


                break;
            case R.id.sat_t:
                if(Collage_Time_Table.contentEquals("Lecture"))
                {
                    Toast.makeText(this, "No Lecture Today", Toast.LENGTH_SHORT).show();

                } else {
                    Intent day_intent6 = new Intent(Week_Activity.this ,new_class);
                    Bundle mBundle6 = new Bundle();
                    String day_text_v6 = sa_daytext.getText().toString();
                    mBundle6.putString("day", day_text_v6);
                    mBundle6.putString("time-table",Collage_Time_Table);
                    day_intent6.putExtras(mBundle6);
                    startActivity(day_intent6);
                }

                break;

            case R.id.sun_t:
                if(Collage_Time_Table.contentEquals("Lecture"))
                {
                    Toast.makeText(this, "No Lecture Today", Toast.LENGTH_SHORT).show();

                } else
                {
                    Intent day_intent7 = new Intent(Week_Activity.this ,new_class);
                    Bundle mBundle7 = new Bundle();
                    String day_text_v7 = su_daytext.getText().toString();
                    mBundle7.putString("day", day_text_v7);
                    mBundle7.putString("time-table",Collage_Time_Table);
                    day_intent7.putExtras(mBundle7);
                    startActivity(day_intent7);
                }


        }

    }

}
