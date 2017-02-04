package com.example.bhumihar.mafiv;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class View_Remainder extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView listv;
    Button unset_btn;
    ArrayList<String> task;
    Remainder_db get_db;
    ListView listview;
    ArrayList<String> inp_data ;
    ArrayAdapter<String> arrayAdapter;
    String id ,tittle,body,date;
    long id_pos1;

    AlarmManager alarmManager ;
    Intent my_intent;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__remainder);

        Toolbar toolbar = (Toolbar)findViewById(R.id.view_remainder_toolbar);
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
            actionBar.setTitle("Reminder View");
            actionBar.setDisplayShowTitleEnabled(true);

        }
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //unset_btn = (Button)findViewById(R.id.unset_task);
        my_intent = new Intent(View_Remainder.this,Alarm_Receiver.class);

        String un_task = " ";
        un_task = getIntent().getExtras().getString("Ringtone");
        if (un_task.contentEquals("untask"))
        {
            Button myButton = new Button(this);
            myButton.setText("UnSet");

            LinearLayout ll = (LinearLayout)findViewById(R.id.untask_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    my_intent.putExtra("A_extra","yes");
                    pendingIntent = PendingIntent.getBroadcast(View_Remainder.this ,10 ,my_intent ,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    my_intent.putExtra("A_extra","no");
                    sendBroadcast(my_intent);
                }
            });

        }


        listv = (ListView)findViewById(R.id.view_remainder_listview);
        task = new ArrayList<>();
        inp_data = new ArrayList<>();

        get_db = new Remainder_db(View_Remainder.this);
        get_db.open();

            inp_data = get_db.get_id_info();

        get_db.close();

        listview = (ListView)findViewById(R.id.view_remainder_listview);
        arrayAdapter = new ArrayAdapter<String>(View_Remainder.this ,R.layout.remainder_value ,inp_data);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.remainder_menu , menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.menu_view_remainder:
                createdialog();
                break;

            case R.id.menu_update:
                createdialog1();

                break;

            case R.id.menu_delete:
                 createdialog2();
                break;
        }
        return false;
    }

    private void createdialog2() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(View_Remainder.this);
        dialog.setTitle("Update Remainder");
        dialog.setMessage("Enter Remainder Id");
        final EditText id_etxt = new EditText(View_Remainder.this);
        id_etxt.setHint("Enter Id");


        LinearLayout linearLayout = new LinearLayout(View_Remainder.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

        linearLayout.addView(id_etxt);



        dialog.setView(linearLayout);

        dialog.setNegativeButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                id = id_etxt.getText().toString();
                id_pos1 = 0;
                try {
                    id_pos1 = Long.parseLong((id != null) ? id.trim() : "0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id_pos1!=0)
                {
                    get_db.open();
                    get_db.delete_data(id_pos1);
                    get_db.close();
                    startActivity(new Intent(View_Remainder.this ,View_Remainder.class));

                } else {
                    Toast.makeText(View_Remainder.this, "Sorry! Null Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();


    }

    private void createdialog1() {


        AlertDialog.Builder dialog = new AlertDialog.Builder(View_Remainder.this);
        dialog.setTitle("Update Remainder");
        dialog.setMessage("Enter Remainder Id");
        final EditText id_etxt = new EditText(View_Remainder.this);
        id_etxt.setHint("Enter Id");


        LinearLayout linearLayout = new LinearLayout(View_Remainder.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

        linearLayout.addView(id_etxt);



        dialog.setView(linearLayout);

        dialog.setNegativeButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                id = id_etxt.getText().toString();
                id_pos1 = 0;
                try {
                    id_pos1 = Long.parseLong((id != null) ? id.trim() : "0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id_pos1!=0)
                {
                    Intent up_rem_intent = new Intent(View_Remainder.this ,Set_Remainder.class);
                    up_rem_intent.putExtra("Save","update");
                    up_rem_intent.putExtra("Lid",id_pos1);
                    Log.e("Iddd",String.valueOf(id_pos1));
                    get_db.open();
                    tittle = get_db.gettittle(id_pos1);
                    up_rem_intent.putExtra("Tittle",tittle);
                    body = get_db.getbody(id_pos1);
                    up_rem_intent.putExtra("Body",body);
                    date = get_db.gettime(id_pos1);
                    up_rem_intent.putExtra("Date",date);
                    get_db.close();
                    startActivity(up_rem_intent);

                } else {
                    Toast.makeText(View_Remainder.this, "Sorry! Null Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();

    }

    private void createdialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(View_Remainder.this);
        dialog.setTitle("View Remainder");
        dialog.setMessage("Enter Remainder Id");
        final EditText id_etxt = new EditText(View_Remainder.this);
         id_etxt.setHint("Enter Id");


        LinearLayout linearLayout = new LinearLayout(View_Remainder.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

        linearLayout.addView(id_etxt);



        dialog.setView(linearLayout);

        dialog.setNegativeButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                id = id_etxt.getText().toString();
                id_pos1 = 0;
                try {
                    id_pos1 = Long.parseLong((id != null) ? id.trim() : "0");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id_pos1!=0)
                {
                    Intent vw_rem_intent = new Intent(View_Remainder.this ,About_Remainder.class);
                    Bundle db = new Bundle();
                    db.putLong("LId",id_pos1);
                    vw_rem_intent.putExtras(db);
                    Log.e("Iddd",String.valueOf(id_pos1));
                    startActivity(vw_rem_intent);

                } else {
                    Toast.makeText(View_Remainder.this, "Sorry! Null Input", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String str = ((TextView)view).getText().toString();

        char a = str.charAt(0);

        int id =Integer.valueOf(a) - 48;
        long id_v = id;

        if (id >= 1 && i>=1)
        {
            Intent vw_rem_intent = new Intent(View_Remainder.this ,About_Remainder.class);
            Bundle db = new Bundle();
            db.putLong("LId",id_v);
            vw_rem_intent.putExtras(db);
            startActivity(vw_rem_intent);
        }
    }
}
