package com.example.bhumihar.mafiv;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.TRANSLATION_Y;

public class Time_Table extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Float..";
    FloatingActionButton floatingActionButton;
    TextView mess_view ,bus_view ,lecture_view ;

    private ImageButton fab;

    private boolean expanded = false;

    private View fabAction1;
    private View fabAction2;
    private View fabAction3;

    private float offset1;
    private float offset2;
    private float offset3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time__table);

        Toolbar toolbar = (Toolbar) findViewById(R.id.time_table_activity_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left1));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Time Table");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        intilize();
        final ViewGroup fabContainer = (ViewGroup) findViewById(R.id.fab_contain);
        fab = (ImageButton) findViewById(R.id.fab);
        fabAction1 = findViewById(R.id.fab_action_1);
        fabAction2 = findViewById(R.id.fab_action_2);
        fabAction3 = findViewById(R.id.fab_action_3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                if (expanded) {
                    expandFab();
                } else {
                    collapseFab();
                }
            }
        });
        fabContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fabContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                offset1 = fab.getY() - fabAction1.getY();
                fabAction1.setTranslationY(offset1);
                offset2 = fab.getY() - fabAction2.getY();
                fabAction2.setTranslationY(offset2);
                offset3 = fab.getY() - fabAction3.getY();
                fabAction3.setTranslationY(offset3);
                return true;
            }
        });


        mess_view.setOnClickListener(this);
        bus_view.setOnClickListener(this);
        lecture_view.setOnClickListener(this);
        fabAction3.setOnClickListener(this);
        fabAction2.setOnClickListener(this);
        fabAction1.setOnClickListener(this);



    }

    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createCollapseAnimator(fabAction1, offset1),
                createCollapseAnimator(fabAction2, offset2),
                createCollapseAnimator(fabAction3, offset3));
        animatorSet.start();
        animateFab();
    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createExpandAnimator(fabAction1, offset1),
                createExpandAnimator(fabAction2, offset2),
                createExpandAnimator(fabAction3, offset3));
        animatorSet.start();
        animateFab();
    }

    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }


    private void intilize() {

        mess_view = (TextView)findViewById(R.id.mess_t);
        bus_view = (TextView)findViewById(R.id.bus_t);
        lecture_view = (TextView)findViewById(R.id.lecture_t);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bus_t:
                Intent day_intent1 = new Intent(Time_Table.this, Week_Activity.class);
                String b_time_table = "Bus";
                Bundle mBundle1 = new Bundle();
                mBundle1.putString("Time_Table", b_time_table);
                day_intent1.putExtras(mBundle1);
                startActivity(day_intent1);

                break;

            case R.id.mess_t:
                Intent day_intent2 = new Intent(Time_Table.this, Week_Activity.class);
                String m_time_table = "Mess";
                Bundle mBundle2 = new Bundle();
                mBundle2.putString("Time_Table", m_time_table);
                day_intent2.putExtras(mBundle2);
                startActivity(day_intent2);

                break;

            case R.id.lecture_t:
                Intent day_intent3 = new Intent(Time_Table.this, Week_Activity.class);
                String l_time_table = "Lecture";
                Bundle mBundle3 = new Bundle();
                mBundle3.putString("Time_Table", l_time_table);
                day_intent3.putExtras(mBundle3);
                startActivity(day_intent3);

                break;
            case R.id.fab_action_3:
                startActivity(new Intent(Time_Table.this ,Notification_Activity.class));
                break;

            case R.id.fab_action_2:
                Intent vw_intent = new Intent(Time_Table.this,View_Remainder.class);
                vw_intent.putExtra("Ringtone","okk");
                startActivity(vw_intent);
                break;

            case R.id.fab_action_1:
                Intent set_rem_intent = new Intent(Time_Table.this,Set_Remainder.class);
                set_rem_intent.putExtra("Save","save");
                startActivity(set_rem_intent);
                break;

        }
    }

}
