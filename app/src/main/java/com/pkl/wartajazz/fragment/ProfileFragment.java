package com.pkl.wartajazz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    View view;
    SharedPrefManager session;
    Context context;
    TextView tvName;
    ImageView llProfileThumbnail;
    int fragmentContainer;
    Button btnEditProfile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = getActivity();
        session = SharedPrefManager.getInstance(context);

        fragmentContainer = R.id.fragment_container;
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

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

        return view;
    }
}
