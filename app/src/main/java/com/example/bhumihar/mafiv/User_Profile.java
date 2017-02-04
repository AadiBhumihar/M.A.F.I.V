package com.example.bhumihar.mafiv;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class User_Profile extends AppCompatActivity implements View.OnClickListener{


    TextView Name_txt , Id_txt , Sem_txt ,Branch_txt;
    EditText pass_txt ,R_pass_txt ;
    Button save_pass;
    String url ="http://mafiv.freeoda.com/update.php";
    private Map<String,String> params;
    String id ,name ,sem ,branch;
    SaveUserData LoggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
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
            actionBar.setTitle("M.F.A.I.V");
            actionBar.setDisplayShowTitleEnabled(true);

        }

        intilize();
        save_pass.setOnClickListener(this);

    }

    private void intilize() {

        Name_txt = (TextView)findViewById(R.id.name_textview);
        Id_txt = (TextView)findViewById(R.id.id_textView);
        Sem_txt = (TextView)findViewById(R.id.sem_textView);
        Branch_txt = (TextView)findViewById(R.id.branch_textView);
        save_pass = (Button) findViewById(R.id.save_p_btn);
        LoggedUser = new SaveUserData(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (authenciate())
            Display_User();

    }

    public boolean authenciate() {

        if(LoggedUser.User_Logged_in())
            return true ;
        else
            return false ;
    }


    private void Display_User() {
        UserData L_User = LoggedUser.Logged_User_Data();
        name = L_User.name;
        id = L_User.id;
        sem = L_User.sem;
        branch = L_User.branch;
        settext();

    }


    private void settext() {
        Name_txt.setText("Name :"+name);
        Id_txt.setText("ID :"+id);
        Sem_txt.setText("Semester :"+sem);
        Branch_txt.setText("Branch :"+branch);

    }


    @Override
    public void onClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(User_Profile.this);
        dialog.setTitle("UserDetail");
        dialog.setMessage("Enter User Detail");
        pass_txt = new EditText(User_Profile.this);
        pass_txt.setHint("Enter Passwprd");
        R_pass_txt = new EditText(User_Profile.this);
        R_pass_txt.setHint("Retype Password");


        LinearLayout linearLayout = new LinearLayout(User_Profile.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

        linearLayout.addView(pass_txt);
        linearLayout.addView(R_pass_txt);


        dialog.setView(linearLayout);

        dialog.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                changepassword();
            }
        });
        dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();

    }

    private void changepassword() {

        final String password = pass_txt.getText().toString().trim();
        final String f_password = R_pass_txt.getText().toString().trim();

        if (password.contentEquals(f_password))
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("Response is:",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean Success = jsonObject.getBoolean("success");

                                if(Success)
                                {
                                    Toast.makeText(User_Profile.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("UserActivity","In Fail Activity");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(User_Profile.this);
                                    builder.setMessage("Password Update Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley","That didn't work!");
                }
            } ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    params = new HashMap<>();
                    params.put("id", id);
                    params.put("password", password);
                    return params;

                }
            };

            // Add the request to the RequestQueue.
            queue.add(stringRequest);



        } else {

            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();

        }

    }


}
