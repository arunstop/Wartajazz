package com.pkl.wartajazz.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.pkl.wartajazz.LoginActivity;
import com.pkl.wartajazz.R;
import com.pkl.wartajazz.RegisterActivity;
import com.pkl.wartajazz.SplashActivity;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.LoginResponse;
import com.pkl.wartajazz.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    View view;
    SharedPrefManager session;
    Context context;
    TextView tvName;
    ImageView llProfileThumbnail;
    int fragmentContainer;
    Button btnEditProfile;
    String token;
    Switch sNotification;

    Handler handler;
    Runnable runnable;
    Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = getActivity();
        session = SharedPrefManager.getInstance(context);

        fragmentContainer = R.id.fragment_container;
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        sNotification = view.findViewById(R.id.sNotification);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new EditProfileFragment())
                        .addToBackStack("fragmentStack")
                        .commit();
            }
        });


        llProfileThumbnail = view.findViewById(R.id.llProfileThumbnail);
        Picasso.with(context).load(session.getUser().getThumbnail()).fit().into(llProfileThumbnail);
        tvName = view.findViewById(R.id.tvName);
        tvName.setText(session.getUser().getFullname() + "");


        //turn off notification
        token = FirebaseInstanceId.getInstance().getToken();
        System.out.println("ATTA-token : " + token);

        if (session.getDeviceToken() == null) {
            sNotification.setChecked(false);
        } else {
            sNotification.setChecked(true);
        }

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processing....");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        // show it

        sNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                progressDialog.show();

                if (isChecked) {
                    Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateToken(session.getUser().getUsername(), token);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            session.saveDeviceToken(token);
                            Toast.makeText(context, "Notification is ON", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateToken(session.getUser().getUsername(), null);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            session.saveDeviceToken(null);
                            Toast.makeText(context, "Notification is OFF", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                //hiding progressdialog
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        timer.cancel();
                    }
                };

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(runnable);
                    }
                }, 0, 2000);
            }
        });

        return view;
    }
}
