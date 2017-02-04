package com.example.bhumihar.mafiv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button btnLogin;
    SharedPreferences sharedpreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    public static final String NAME = "nameKey";
    public static final String USERNAME = "usernameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        btnLogin=(Button)findViewById(R.id.login);

        sharedpreferences=getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname=username.getText().toString();
                final String pass = password.getText().toString();

                //send them to server to check...
                final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://kamalawasthi.pythonanywhere.com/stand/person_login";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("response", s);
                        try {
                            JSONObject responseJson = new JSONObject(s);
                            Log.e("flag", responseJson.getString("flag"));
                            if (responseJson.getString("flag").equals("True"))   //if all details entered are correct
                            {

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                String student_name = responseJson.getString("name");

                                editor.putString(NAME, student_name);
                                editor.putString(USERNAME, uname);
                                editor.apply();


                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else  //if any of username or pass is wrong
                            {
                               // progressDialog.dismiss();
                                //Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //progressDialog.hide();
                        Log.e("err", volleyError.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("username", uname);
                        map.put("password", pass);
                        Log.e("sent", map.toString());
                        return map;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}
