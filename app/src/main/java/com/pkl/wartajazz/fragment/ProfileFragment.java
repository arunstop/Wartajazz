package com.pkl.wartajazz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.storage.SharedPrefManager;

public class ProfileFragment extends Fragment {
View view;
SharedPrefManager session;
Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);

        context = getActivity();
        session = SharedPrefManager.getInstance(context);
        Toast.makeText(context, session.getUser().getFullname()+"", Toast.LENGTH_SHORT).show();
        return view;
    }
}
