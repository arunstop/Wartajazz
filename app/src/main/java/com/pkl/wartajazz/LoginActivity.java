package com.pkl.wartajazz;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.LoginResponse;
import com.pkl.wartajazz.models.User;
import com.pkl.wartajazz.storage.SharedPrefManager;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private Button login, googleLogin;
    private EditText editTextUsername, editTextPassword;
    private TextView register;
    private GoogleSignInClient googleSignInClient;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private TextView locTest;
    private Button locButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        googleLogin = (Button) findViewById(R.id.google_login);
        editTextUsername = (EditText) findViewById(R.id.user);
        editTextPassword = (EditText) findViewById(R.id.pass);
        register = (TextView) findViewById(R.id.register);

        locTest = (TextView) findViewById(R.id.locTest);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locButton = (Button) findViewById(R.id.locButton);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(LoginActivity.this, location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
                locTest.append(location.getLatitude() + "," + location.getLongitude()+"\n");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                getLocation();

            }

            @Override
            public void onProviderEnabled(String provider) {
                getLocation();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, 10);
                return;
            }
        } else {
            getLocation();
        }

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Kappa", Toast.LENGTH_SHORT).show();
                getLocation();
            }
        });

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
                //bypass login
                SharedPrefManager.getInstance(LoginActivity.this)
                        .saveUser(new User(1
                                , "bypass@bypass.com"
                                , "Tresspasser"
                                , "0123456789"
                                , "2019-09-09"
                                , 1
                                , "123"));

                progressDoalog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

//                call.enqueue(new Callback<LoginResponse>() {
//                    @Override
//                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                        LoginResponse loginResponse = response.body();
//
//                        if (!loginResponse.isError()) {
//                            SharedPrefManager.getInstance(LoginActivity.this)
//                                    .saveUser(loginResponse.getUser());
//
//                            progressDoalog.dismiss();
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
//                        } else {
//                            progressDoalog.dismiss();
//                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                        progressDoalog.dismiss();
//                        System.out.println("Failure");
//                    }
//                });
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

        // google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                    getLocation();
                return;
        }
    }

    private void getLocation() {
//        locationManager.requestLocationUpdates("gps", 10000, 5, locationListener);
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
                    }
                    break;
            }
        }
    }


    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Uri photoUrl = googleSignInAccount.getPhotoUrl();
        String provider_id = googleSignInAccount.getId();
        String email = googleSignInAccount.getEmail();
        String name = googleSignInAccount.getDisplayName();
        String thumbnail = photoUrl.toString();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().googleAuth(provider_id, email, name, thumbnail);

        // Set up progress before call
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setMessage("Processing....");
        progressDoalog.setIndeterminate(false);
        progressDoalog.setCancelable(false);
        // show it
        progressDoalog.show();

        //bypass login
        SharedPrefManager.getInstance(LoginActivity.this)
                .saveUser(new User(1
                        , "bypass@bypass.com"
                        , "Tresspasser"
                        , "0123456789"
                        , "2019-09-09"
                        , 1
                        , "123"));

        progressDoalog.dismiss();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                LoginResponse loginResponse = response.body();
//
//                if (!loginResponse.isError()) {
//                    SharedPrefManager.getInstance(LoginActivity.this)
//                            .saveUser(loginResponse.getUser());
//
//                    progressDoalog.dismiss();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else {
//                    progressDoalog.dismiss();
//                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                progressDoalog.dismiss();
//                System.out.println("Failure");
//            }
//        });
    }
}
