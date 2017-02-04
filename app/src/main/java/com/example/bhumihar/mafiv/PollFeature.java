package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PollFeature extends AppCompatActivity {

    Button btnNewPoll,btnViewPoll,btnViewMyPoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_feature);

        btnNewPoll=(Button)findViewById(R.id.newPoll);
        btnViewPoll=(Button)findViewById(R.id.activePolls);
        btnViewMyPoll=(Button)findViewById(R.id.myPoll);

        btnNewPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PollFeature.this,CreatePoll.class);
                startActivity(intent);
            }
        });

        btnViewPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PollFeature.this,ViewActivePolls.class);
                intent.putExtra("inactive_id","no");
                startActivity(intent);
            }
        });

        btnViewMyPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PollFeature.this,ViewMyPoll.class);
                startActivity(intent);
            }
        });
    }
}
