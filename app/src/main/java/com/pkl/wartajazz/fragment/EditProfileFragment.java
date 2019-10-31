package com.pkl.wartajazz.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pkl.wartajazz.LoginActivity;
import com.pkl.wartajazz.R;
import com.pkl.wartajazz.RegisterActivity;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.SignupResponse;
import com.pkl.wartajazz.models.User;
import com.pkl.wartajazz.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private Button btnEdit;
    private TextView tvUsername, tvEmail;
    private EditText etFullName, etPassword, etConfirmPassword, etPhone, etAddress;
    private View view;
    private Context context;
    private SharedPrefManager session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        context = getActivity();
        session = SharedPrefManager.getInstance(context);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        etFullName = view.findViewById(R.id.etFullName);
        tvUsername = view.findViewById(R.id.tvUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        etPhone = view.findViewById(R.id.etPhone);
        etAddress = view.findViewById(R.id.etAddress);
        btnEdit = view.findViewById(R.id.btnEdit);


        tvUsername.setText(session.getUser().getUsername());
        tvEmail.setText(session.getUser().getEmail());
        etFullName.setText(session.getUser().getFullname());
        etPhone.setText(session.getUser().getPhone_number());
        etAddress.setText(session.getUser().getAddress());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        return view;
    }

    private void editProfile() {

        final String fullName = etFullName.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        String cpassword = etConfirmPassword.getText().toString().trim();
        final String phone = etPhone.getText().toString().trim();
        final String address = etAddress.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError("Name required");
            etFullName.requestFocus();
            return;
        }

        if (!password.isEmpty()) {
            if (password.length() < 6) {
                etPassword.setError("Password should be atleast 6 character long");
                etPassword.requestFocus();
                return;
            }
            if (cpassword.isEmpty()) {
                etConfirmPassword.setError("Confirmation Password required");
                etConfirmPassword.requestFocus();
                return;
            }
            if (cpassword.length() < 6) {
                etConfirmPassword.setError("Confirmation Password should be atleast 6 character long");
                etConfirmPassword.requestFocus();
                return;
            }
            if (!password.equals(cpassword)) {
                etConfirmPassword.setError("Passwords must be the same");
                etConfirmPassword.requestFocus();
                return;
            }
        }


        if (phone.isEmpty()) {
            etPhone.setError("Phone required");
            etPhone.requestFocus();
            return;
        }

        if (phone.length() < 10 || phone.length() > 12) {
            etPhone.setError("Phone should be atleast 10 or 12 digit long");
            etPhone.requestFocus();
            return;
        }

        Call<SignupResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .editUser(session.getUser().getUsername(), fullName, cpassword, phone, address);


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(context);
        progressDoalog.setMessage("Processing....");
        progressDoalog.setIndeterminate(false);
        progressDoalog.setCancelable(false);
        // show it
        progressDoalog.show();

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                SignupResponse signupResponse = response.body();

                if (response.isSuccessful() && isAdded()) {
                    progressDoalog.dismiss();
                    session.saveUser(
                            new User(session.getUser().getUser_id(),
                                    session.getUser().getUsername(),
                                    session.getUser().getEmail(),
                                    (password.equals("") ? session.getUser().getPassword() : ""),
                                    session.getUser().getRole_id(),
                                    session.getUser().getToken(),
                                    fullName,
                                    phone,
                                    address,
                                    session.getUser().getJoin_date(),
                                    session.getUser().getThumbnail(),
                                    session.getUser().getProvider_id()
                            )
                    );
                    Toast.makeText(context, signupResponse.getMsg(), Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                } else {
                    progressDoalog.dismiss();
                    Toast.makeText(context, signupResponse.getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });

    }
}
