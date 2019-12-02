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

import com.google.firebase.iid.FirebaseInstanceId;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.SignupResponse;
import com.pkl.wartajazz.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    private Button register;
    private EditText editfirstName, editlastName, editEmail, editUsername, editPassword, editPhone, editAddress;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvLogin = findViewById(R.id.login);
        register = findViewById(R.id.register);
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

                final String token = FirebaseInstanceId.getInstance().getToken();
                SharedPrefManager.getInstance(RegisterActivity.this).saveDeviceToken(token);
                System.out.println("ATTA-token : "+token);


                if (firstname.isEmpty()) {
                    editfirstName.setError("Your Name required");
                    editfirstName.requestFocus();
                    return;
                }

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

                String fullname = firstname + " " + lastname;
                Call<SignupResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createUser(username, password, email, fullname, phone, address,token);


                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Processing....");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                // show it
                progressDialog.show();

                call.enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        SignupResponse signupResponse = response.body();
                        Toast.makeText(RegisterActivity.this, signupResponse.getMsg()+"", Toast.LENGTH_LONG).show();

                        if (response.isSuccessful() && !signupResponse.isError()){
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else {
//                            Toast.makeText(RegisterActivity.this, response.message()+"", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.getMessage()+"", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}