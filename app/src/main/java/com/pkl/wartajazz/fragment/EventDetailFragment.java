package com.pkl.wartajazz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.Event;
import com.pkl.wartajazz.models.EventDetail;
import com.pkl.wartajazz.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailFragment extends Fragment {

    String event_id;
    View view;
    SharedPrefManager session;
    Context context;
    int fragmentContainer;
    LinearLayout llEventDetailContainer;
    ProgressBar pbLoading;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        context = getActivity();
        session = SharedPrefManager.getInstance(context);
        fragmentContainer = R.id.fragment_container;

        llEventDetailContainer = view.findViewById(R.id.llEventDetailContainer);
        pbLoading = view.findViewById(R.id.pbLoading);


        showEventDetail();

        return view;
    }

    public void showEventDetail() {
        llEventDetailContainer.removeAllViews();

        for (int i=0;i<3;i++){
            View child = getLayoutInflater().inflate(R.layout.template_item_event_detail, null);

            llEventDetailContainer.addView(child);
        }
        pbLoading.setVisibility(View.GONE);
        Toast.makeText(context, getEvent_id() + "", Toast.LENGTH_SHORT).show();

        Call<EventDetail> call = RetrofitClient.getInstance().getApi().showDetailEvent("123");

        call.enqueue(new Callback<EventDetail>() {
            @Override
            public void onResponse(Call<EventDetail> call, Response<EventDetail> response) {
                Toast.makeText(context, response.message()+"", Toast.LENGTH_SHORT).show();
                EventDetail edData = response.body();
                if (response.isSuccessful() && isAdded()){
                    Toast.makeText(context, edData.getEvent_id()+"", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, response.message()+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventDetail> call, Throwable t) {
                Toast.makeText(context, t.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

