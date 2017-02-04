package com.example.bhumihar.mafiv;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Set_Remainder extends AppCompatActivity implements View.OnClickListener{

    private static final java.lang.String DATE_FORMAT = "yyyy-MM-dd";
    private static final java.lang.String TIME_FORMAT = "kk:mm" ;
    private static final java.lang.String DATE_Time_FORMAT = "yyyy-MM-dd kk:mm:ss";

    Button DatePicker ,TimePicker  ,savebtn ;
    EditText tittle_txt , body_txt ;
    TextView DateView;
    LinearLayout remainder_update;

    AlarmManager alarmManager;
    Intent my_intent;
    PendingIntent pendingIntent;
    Context acontext ;


    private static final int DATE_PICKER_DIALOG = 0;
    private static final int TIME_PICKER_DIALOG = 1;

    Calendar mcalander;
    String mode ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__remainder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.set_rem_toolbar);
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
            actionBar.setTitle("Set Reminder");
            actionBar.setDisplayShowTitleEnabled(true);

        }


        intilize();
        setupdate();

        DatePicker.setOnClickListener(this);
        TimePicker.setOnClickListener(this);

        savebtn.setOnClickListener(this);

    }

    private void setupdate() {
        mode = getIntent().getExtras().getString("Save");
        if (mode.contentEquals("update"))
        {
            String tittle = getIntent().getExtras().getString("Tittle");
            tittle_txt.setText(tittle);
            String body = getIntent().getExtras().getString("Body");
            body_txt.setText(body);
            String da_ti_v = getIntent().getExtras().getString("Date");
            String date = " " ;
            String time = " ";
            try {
                date = da_ti_v.substring(0,9);
                time = da_ti_v.substring(11,da_ti_v.length()-1);
            }catch (StringIndexOutOfBoundsException S)
            {
                S.printStackTrace();
            }

            DatePicker.setText(date);
            TimePicker.setText(time);

            savebtn.setText("Update");
        } else if(mode.contentEquals("save"))
        {
            savebtn.setText("Save");
        }
    }


    private void intilize() {
        DatePicker = (Button)findViewById(R.id.date);
        TimePicker = (Button)findViewById(R.id.time);
        savebtn = (Button)findViewById(R.id.save);

        tittle_txt = (EditText)findViewById(R.id.tittle);
        body_txt = (EditText)findViewById(R.id.body);

        DateView = (TextView)findViewById(R.id.date_text);
        mcalander = Calendar.getInstance();
        remainder_update = (LinearLayout)findViewById(R.id.update_layout);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        this.acontext = this;
        my_intent = new Intent(acontext,Alarm_Receiver.class);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.date:
                //InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                //inputMethodManager.hideSoftInputFromWindow()
                showDialog(DATE_PICKER_DIALOG);
                break;

            case R.id.time:
                showDialog(TIME_PICKER_DIALOG);
                break;

            case R.id.save:
                if (mode.contentEquals("update"))
                {
                    long id_v = getIntent().getExtras().getLong("Lid");
                    updatetask(id_v);
                } else if(mode.contentEquals("save"))
                {
                    savetask();
                }


                break;


        }

    }

    private void updatetask(final long id_v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Task");
        builder.setMessage("Are you Sure to Update");
        builder.setCancelable(false);
        builder.setNegativeButton("yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean DidItWork = false;
                try{
                    String Title = tittle_txt.getText().toString();
                    String Body = body_txt.getText().toString();
                    SimpleDateFormat date_time = new SimpleDateFormat(DATE_Time_FORMAT);
                    String Task_Date_Time = date_time.format(mcalander.getTime());
                    Remainder_db db_insert = new Remainder_db(Set_Remainder.this);
                    db_insert.open();
                     int id = db_insert.update_data(Title ,Body ,Task_Date_Time,id_v);
                    db_insert.close();
                    if(id!=0){
                        DidItWork = true;
                    } else
                    {
                        DidItWork = false;
                    }


                } catch (Exception e)
                {
                    DidItWork = false;
                }
                finally {
                    if(DidItWork)
                    {
                        Dialog display = new Dialog(Set_Remainder.this);
                        display.setTitle("Task Reminder");
                        TextView tv = new TextView(Set_Remainder.this);
                        tv.setText("Succesfully Saved");
                        display.setContentView(tv);
                        display.show();
                        setAlarm();
                        Intent intent = new Intent(Set_Remainder.this ,Time_Table.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
        builder.setPositiveButton("No" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Set_Remainder.this, "Task not Saved", Toast.
                        LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
        builder.create().show();


    }

    private void setAlarm() {

        my_intent.putExtra("A_extra","yes");

        pendingIntent = PendingIntent.getBroadcast(this.acontext ,10 ,my_intent ,PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT < 23){
            if(Build.VERSION.SDK_INT >= 19){
                alarmManager.setExact(AlarmManager.RTC_WAKEUP ,mcalander.getTimeInMillis(),pendingIntent);
            }
            else{
                alarmManager.set(AlarmManager.RTC_WAKEUP ,mcalander.getTimeInMillis(),pendingIntent);
            }
        }
        else{
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP ,mcalander.getTimeInMillis(),pendingIntent);

        }
    }

    private void savetask() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Task");
        builder.setMessage("Are you Sure to Save");
        builder.setCancelable(false);
        builder.setNegativeButton("yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean DidItWork = false;
                try{
                    String Title = tittle_txt.getText().toString();
                    String Body = body_txt.getText().toString();
                    SimpleDateFormat date_time = new SimpleDateFormat(DATE_Time_FORMAT);
                    String Task_Date_Time = date_time.format(mcalander.getTime());
                    Remainder_db db_insert = new Remainder_db(Set_Remainder.this);
                    db_insert.open();
                    long id = db_insert.insert_data(Title ,Body ,Task_Date_Time);
                    db_insert.close();
                    DidItWork = true;

                } catch (Exception e)
                {
                    DidItWork = false;
                }
                finally {
                    if(DidItWork)
                    {
                        Dialog display = new Dialog(Set_Remainder.this);
                        display.setTitle("Task Remainder");
                        TextView tv = new TextView(Set_Remainder.this);
                        tv.setText("Succesfully Saved");
                        display.setContentView(tv);
                        display.show();
                        setAlarm();
                        Intent intent = new Intent(Set_Remainder.this ,Time_Table.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();


                    }
                }

            }
        });
        builder.setPositiveButton("No" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Set_Remainder.this, "Task not Saved", Toast.
                        LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
        builder.create().show();


    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case DATE_PICKER_DIALOG:
                return show_date_picker();


            case TIME_PICKER_DIALOG :
                return show_time_picker();

        }
        return super.onCreateDialog(id);
    }

    private TimePickerDialog show_time_picker() {

        TimePickerDialog timepicker = new TimePickerDialog(Set_Remainder.this ,new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) {
                mcalander.set(Calendar.HOUR_OF_DAY,i);
                mcalander.set(Calendar.MINUTE,i1);
                updateTimetext();
            }
        } ,mcalander.get(Calendar.HOUR_OF_DAY) ,mcalander.get(Calendar.MINUTE) ,true);

        return timepicker ;
    }

    private void updateTimetext() {
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        String timeForButton = timeFormat.format(mcalander.getTime());
        TimePicker.setText(timeForButton);

    }


    private DatePickerDialog show_date_picker() {

        DatePickerDialog date_picker = new DatePickerDialog(Set_Remainder.this ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                mcalander.set(Calendar.YEAR, i);
                mcalander.set(Calendar.MONTH, i1);
                mcalander.set(Calendar.DAY_OF_MONTH, i2);
                updateDatetext();
            }
        } , mcalander.get(Calendar.YEAR) ,mcalander.get(Calendar.MONTH) ,mcalander.get(Calendar.DAY_OF_MONTH));

        return date_picker;

    }

    private void updateDatetext() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(mcalander.getTime());
        DatePicker.setText(dateForButton);
    }


}
