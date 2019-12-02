package com.pkl.wartajazz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.LoginResponse;
import com.pkl.wartajazz.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    ProgressDialog progressDialog;
    private Button login, googleLogin, btnRegister;
    private EditText editTextUsername, editTextPassword;
    private TextView forgetPass;
    private GoogleSignInClient googleSignInClient;
    private Context context;
    private String token;
    private SharedPrefManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = LoginActivity.this;
        session = SharedPrefManager.getInstance(context);
        forgetPass = findViewById(R.id.forgetPass);
        login = findViewById(R.id.login);
        googleLogin = findViewById(R.id.google_login);
        editTextUsername = findViewById(R.id.user);
        editTextPassword = findViewById(R.id.pass);
        btnRegister = findViewById(R.id.register);

        token = FirebaseInstanceId.getInstance().getToken();
        session.saveDeviceToken(token);
        System.out.println("ATTA-token : " + token);


        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Processing....");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

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

                Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(username, password, token);

                // Set up progress before call
                final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Processing....");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                // show it
                progressDialog.show();
                //bypass login
//                session
//                        .saveUser(new User(1
//                                , "bypass@bypass.com"
//                                , "Tresspasser"
//                                , "0123456789"
//                                , "2019-09-09"
//                                , 1
//                                , "123"));
//
//                progressDoalog.dismiss();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage() + "", Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful() && !loginResponse.isError()) {
                            session.saveUser(loginResponse.getUser());

                            progressDialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        System.out.println("Failure");
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                finish();
            }
        });

        // google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                startActivityForResult(googleSignInClient.getSignInIntent(), 101);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, ForgetPassActivity.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w("Google Login", "signInResult:failed code=" + e.getStatusCode());
                        Toast.makeText(context, "Sign In failed, error code = " + e.getMessage() + "", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    break;
            }
        } else {
            Toast.makeText(context, "Sign In failed", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }

    }


    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Uri photoUrl = googleSignInAccount.getPhotoUrl();
        String provider_id = googleSignInAccount.getId();
        String email = googleSignInAccount.getEmail();
        String name = googleSignInAccount.getDisplayName();
        String thumbnail = photoUrl.toString();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().googleAuth(provider_id, email, name, thumbnail, token);

        // Set up progress before call

        // show it
//        progressDialog.show();
//        Toast.makeText(context, provider_id+"\n"+email+"\n"+name+"\n"+thumbnail+"\n"+token+"", Toast.LENGTH_SHORT).show();

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccessful() && !loginResponse.isError()) {
                    session.saveUser(loginResponse.getUser());
                    //save Token
                    Call<LoginResponse> call1 = RetrofitClient.getInstance().getApi().updateToken(session.getUser().getUsername(), token);

                    call1.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                            Toast.makeText(context, "Notification is ON", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(LoginActivity.this, (loginResponse.getMessage() == null ? "Welcome" : loginResponse.getMessage()) + "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
//                    Toast.makeText(LoginActivity.this, response.message()+"-", Toast.LENGTH_LONG).show();
                    googleSignInClient.signOut();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage() + "", Toast.LENGTH_LONG).show();

            }
        });
    }
}
