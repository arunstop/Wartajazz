package com.pkl.wartajazz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.models.EventDetail;
import com.pkl.wartajazz.models.EventDetailResponse;
import com.pkl.wartajazz.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

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
    TextView tvEventTitle, tvEventLocation, tvEventPrice, tvEventStart, tvEventEnd;
    ImageView ivEventPoster;

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
        tvEventTitle = view.findViewById(R.id.tvEventTitle);
        ivEventPoster = view.findViewById(R.id.ivEventPoster);
        tvEventLocation = view.findViewById(R.id.tvEventLocation);
        tvEventPrice = view.findViewById(R.id.tvEventPrice);
        tvEventStart = view.findViewById(R.id.tvEventStart);
        tvEventEnd = view.findViewById(R.id.tvEventEnd);

        showEventDetail();

        return view;
    }

    public void showEventDetail() {
        llEventDetailContainer.removeAllViews();

        Call<EventDetailResponse> call = RetrofitClient.getInstance().getApi().showDetailEvent(getEvent_id());

        call.enqueue(new Callback<EventDetailResponse>() {
            @Override
            public void onResponse(Call<EventDetailResponse> call, Response<EventDetailResponse> response) {
                EventDetailResponse edfData = response.body();
                View child;
                if (response.isSuccessful() && isAdded()) {
                    tvEventTitle.setText(edfData.getEvent().getEvent_name());
                    tvEventLocation.setText(edfData.getEvent().getLocation());
                    tvEventPrice.setText(edfData.getEvent().getHtm());
                    tvEventStart.setText(edfData.getEvent().getDate_start());
                    tvEventEnd.setText(edfData.getEvent().getDate_end());

                    if (!edfData.getEvent().getPoster().equals("")) {
                        Picasso.with(context).load(edfData.getEvent().getPoster()).fit().into(ivEventPoster);
                    }
                    for (EventDetail edData : edfData.getEvent().getDetails()) {
                        child = getLayoutInflater().inflate(R.layout.template_item_event_detail, null);
                        TextView tvEventDetailArtist = child.findViewById(R.id.tvEventDetailArtist);
                        TextView tvEventDetailShowtime = child.findViewById(R.id.tvEventDetailShowtime);
                        TextView tvEventDetailCountry = child.findViewById(R.id.tvEventDetailCountry);

                        tvEventDetailArtist.setText(edData.getArtist_name());
                        tvEventDetailShowtime.setText(edData.getShow_time());
                        tvEventDetailCountry.setText(edData.getCountry_of_origin());
                        pbLoading.setVisibility(View.GONE);

                        llEventDetailContainer.addView(child);
                    }
                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventDetailResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
//                showEventDetail();
            }
        });

    }
}

