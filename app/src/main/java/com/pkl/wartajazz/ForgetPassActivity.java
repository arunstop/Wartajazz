package com.pkl.wartajazz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.LoginResponse;
import com.pkl.wartajazz.models.SignupResponse;
import com.pkl.wartajazz.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassActivity extends Activity {

    private Button forget;
    private EditText editEmail, editPassword, editCPassword;
    private TextView tvLogin;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        context = ForgetPassActivity.this;

        tvLogin = findViewById(R.id.login);
        forget = findViewById(R.id.forget);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editCPassword = findViewById(R.id.confirmPassword);


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String cpassword = editCPassword.getText().toString().trim();

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
                    editPassword.setError("Password is required");
                    editPassword.requestFocus();
                    return;
                }

                if (cpassword.isEmpty()) {
                    editCPassword.setError("Email is required");
                    editCPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editPassword.setError("Password should be atleast 6 character long");
                    editPassword.requestFocus();
                    return;
                }
                if (cpassword.length() < 6) {
                    editCPassword.setError("Password should be atleast 6 character long");
                    editCPassword.requestFocus();
                    return;
                }

                if (!cpassword.equals(password)) {
                    editCPassword.setError("Confirmation password is not valid");
                    editCPassword.requestFocus();
                    return;
                }


                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .resetPass(email, password, cpassword);


                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(ForgetPassActivity.this);
                progressDialog.setMessage("Processing....");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                // show it
                progressDialog.show();

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse res = response.body();
                        Toast.makeText(context, res.getMessage()+"", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent login = new Intent(ForgetPassActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}