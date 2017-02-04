package com.example.bhumihar.mafiv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

public class About_Remainder extends AppCompatActivity {

    TextView tittle_v ,body_v ,time_v ;
    long id_pos ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__remainder);

        Bundle bd = getIntent().getExtras();
        id_pos = bd.getLong("LId");
        Log.e("LID..",String.valueOf(id_pos));


        intilize();
        try {
            setext();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setext() throws IOException{

        Remainder_db remainder_db = new Remainder_db(this);
        remainder_db.open();

        String tittle = remainder_db.gettittle(id_pos);
        String body =   remainder_db.getbody(id_pos);
        String timee = remainder_db.gettime(id_pos);

        remainder_db.close();

        tittle_v.setText(tittle);
        body_v.setText(body);
        time_v.setText(timee);

    }

    private void intilize() {

        tittle_v = (TextView)findViewById(R.id.tittle_txt);
        body_v = (TextView)findViewById(R.id.body_txt);
        time_v = (TextView)findViewById(R.id.time_txt);
    }

}
