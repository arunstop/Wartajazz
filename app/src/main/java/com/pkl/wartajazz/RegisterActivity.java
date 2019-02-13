package com.pkl.wartajazz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.SignupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    private Button register;
    private EditText editfirstName, editlastName, editEmail, editUsername, editPassword, editPhone, editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button) findViewById(R.id.register);
        editfirstName = findViewById(R.id.firstName);
        editlastName = findViewById(R.id.lastName);
        editEmail = findViewById(R.id.email);
        editUsername = findViewById(R.id.userName);
        editPassword = findViewById(R.id.password);
        editPhone = findViewById(R.id.phone);
        editAddress = findViewById(R.id.address);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String firstname = editfirstName.getText().toString().trim();
                String lastname = editlastName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String address = editAddress.getText().toString().trim();

                if (username.isEmpty()) {
                    editUsername.setError("Username required");
                    editUsername.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    editEmail.setError("Email is required");
                    editEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmail.setError("Enter a valid email");
                    editEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editPassword.setError("Password required");
                    editPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editPassword.setError("Password should be atleast 6 character long");
                    editPassword.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    editPhone.setError("Phone required");
                    editPhone.requestFocus();
                    return;
                }

                if (phone.length() < 10 || phone.length() > 12) {
                    editPhone.setError("Phone should be atleast 10 or 12 digit long");
                    editPhone.requestFocus();
                    return;
                }

                Call<SignupResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(username, password, email, firstname, lastname, phone, address);


                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(RegisterActivity.this);
                progressDoalog.setMessage("Processing....");
                progressDoalog.setIndeterminate(false);
                progressDoalog.setCancelable(false);
                // show it
                progressDoalog.show();

                call.enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        SignupResponse signupResponse = response.body();

                        if (signupResponse.isError()) {
                            progressDoalog.dismiss();
                            Toast.makeText(RegisterActivity.this, signupResponse.getMsg(), Toast.LENGTH_LONG).show();
                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(RegisterActivity.this, signupResponse.getMsg(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        progressDoalog.dismiss();
                    }
                });
            }
        });
    }
}