package com.marygalejabagat.it350;

import com.marygalejabagat.it350.helper.AppPreference;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.marygalejabagat.it350.helper.AppPreference;
import com.marygalejabagat.it350.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity  {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressBar progressBar;
    private TextView txtActiveUser;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static final String MyPREFERENCES = "Active User" ;
    SharedPreferences sp;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtActiveUser = (TextView) findViewById(R.id.activeUser);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /*session = new Session(this);
        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }*/

        ButterKnife.bind(this);
        Toast.makeText(getApplicationContext(), "Login Activity ",   Toast.LENGTH_LONG).show();
       /* sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);*/
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

       /* if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }*/

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        RequestLogin();
        //    onLoginSuccess();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        final LinearLayout rl = (LinearLayout) findViewById(R.id.login_layout);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setIndeterminate(false);
        pd.setMessage("Login.....");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(true);
        pd.setMax(100);
        pd.show();

        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void RequestLogin(){
        final LinearLayout rl = (LinearLayout) findViewById(R.id.login_layout);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setIndeterminate(true);
        pd.setMessage("Authenticating.....");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(true);
        pd.setMax(100);
        pd.show();

        String url = "https://mgsurvey.herokuapp.com/api/postLogin";
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RESPONSE", response.toString());
                try {

                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("status") == "failed"){
                        pd.setMessage("Error while login in");
                        pd.dismiss();
                        return;
                    }
                    Log.e("OBJ", obj.getString("status"));
                    Log.e("OBJ", obj.getString("result"));
                    String res = obj.getString("result");
                    JSONArray jsonArray = new JSONArray(res);
                    for(int i = 0; i < jsonArray.length(); i++) {

                        JSONObject result = jsonArray.getJSONObject(i);
                         Log.e("LOGIN", result.toString());
                        User user = new User();
                        user.setEmail(result.getString("email_address"));
                        user.setRoleID(result.getInt("role_id"));
                        user.setRoleName(result.getString("role_name"));
                        user.setFirstName(result.getString("first_name"));
                        user.setLastName(result.getString("last_name"));

                        AppPreference.getInstance(getApplicationContext()).setIsLogin(true);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();

                        pd.dismiss();
                        _loginButton.setEnabled(true);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSonErr", e.getMessage());
                    pd.dismiss();
                    _loginButton.setEnabled(true);
                    Log.e("ERROR_ON_LOGIN", " ON LOGINACTIVITY");
                }




            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.e(TAG, "error on post survey");
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> User = new HashMap<String, String>();
                User.put("email", _emailText.getText().toString());
                User.put("password", _passwordText.getText().toString());
                Log.e("REQUEST PARAM", User.toString());
                return User;
            }
        };//fdbd3cd60f63ebe9505bb7e0310a73d2
        MyRequestQueue.add(loginRequest);
    }
}
