package com.example.bhumihar.mafiv;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText id_txt , password_txt ;
    Button login_btn;

    String url ="http://mafiv.freeoda.com/android_php/login.php";
    private Map<String,String> params;
    UserData userdata;
    SaveUserData setuserdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intilize();
        login_btn.setOnClickListener(this);

    }

    private void intilize() {
        id_txt = (EditText)findViewById(R.id.user_id);
        password_txt = (EditText)findViewById(R.id.password);
        login_btn = (Button)findViewById(R.id.btn_login);
        setuserdata = new SaveUserData(this);
    }


    @Override
    public void onClick(View view) {

        final String id = id_txt.getText().toString().trim();
        final String password = password_txt.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("Response",response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean Success = jsonObject.getBoolean("success");

                            if(Success)
                            {
                                String name = jsonObject.getString("name");
                                String semester = jsonObject.getString("semester");
                                String branch = jsonObject.getString("branch");
                                userdata = new UserData(id ,name ,semester ,branch ,password);
                                setuserdata.Store_User_Data(userdata);
                                setuserdata.LoggedIn(true);
                                Intent newintent = new Intent(MainActivity.this, User_Activity.class);

                                MainActivity.this.startActivity(newintent);

                            } else {
                                Log.e("MainActivity","In Fail Activity");
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login Failed")
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
                Log.e("Return","Params");
                return params;

            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


}
