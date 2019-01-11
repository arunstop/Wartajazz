package com.bimo.wartajazz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bimo.wartajazz.api.RetrofitClient;
import com.bimo.wartajazz.models.LoginResponse;
import com.bimo.wartajazz.storage.SharedPrefManager;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private Button login;
    private EditText editTextUsername, editTextPassword;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        editTextUsername = (EditText) findViewById(R.id.user);
        editTextPassword = (EditText) findViewById(R.id.pass);
        register = (TextView) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty()) {
                    editTextUsername.setError("Username is required");
                    editTextUsername.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password required");
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 4) {
                    editTextPassword.setError("Password should be atleast 4 character long");
                    editTextPassword.requestFocus();
                    return;
                }

                Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(username, password);

                // Set up progress before call
                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(LoginActivity.this);
                progressDoalog.setMessage("Processing....");
                progressDoalog.setIndeterminate(false);
                progressDoalog.setCancelable(false);
                // show it
                progressDoalog.show();

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();

                        if (!loginResponse.isError()) {
                            SharedPrefManager.getInstance(LoginActivity.this)
                                    .saveUser(loginResponse.getUser());

                            progressDoalog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            progressDoalog.dismiss();
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        progressDoalog.dismiss();
                        System.out.println("Failure");
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
