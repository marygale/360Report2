package com.marygalejabagat.it350;

import android.app.ProgressDialog;
import android.content.Intent;
/*import android.app.ProgressDialog;*/
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public static ProgressDialog pd;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.last_name) EditText _lnameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    /*@BindView(R.id.input_mobile) EditText _mobileText;*/
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        /*Toast.makeText(getApplicationContext(), "Signup Activity ",   Toast.LENGTH_LONG).show();*/
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }else{
            onPostRegister();
            _signupButton.setEnabled(false);
        }


    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String lname = _lnameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        /*String mobile = _mobileText.getText().toString();*/
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (lname.isEmpty() || lname.length() < 3) {
            _lnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _lnameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        /*if (mobile.isEmpty() || mobile.length()!=11) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }*/

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    public void onPostRegister(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://mgsurvey.herokuapp.com/api/postRegister";
        showProgressBar("Processing...");
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("REGISTER", response.toString());
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();
                onSignupSuccess();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                pd.dismiss();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("first_name", _nameText.getText().toString());//Add the data you'd like to send to the server.
                MyData.put("last_name", _lnameText.getText().toString());
                MyData.put("address", _addressText.getText().toString());
                MyData.put("email_address", _emailText.getText().toString());
                MyData.put("password", _passwordText.getText().toString());

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void showProgressBar(String message){
        pd = new ProgressDialog(this);
        pd.setIndeterminate(false);
        pd.setMessage(message);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        pd.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(true);
        pd.setMax(100);
        pd.show();
    }

}