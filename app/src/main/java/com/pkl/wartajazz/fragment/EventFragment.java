package com.pkl.wartajazz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.pkl.wartajazz.models.Event;
import com.pkl.wartajazz.models.EventResponse;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {

    private View view;
    private Context context;
    private LinearLayout llEventContainer;
    private ProgressBar pbLoading;
    private int fragmentContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        context = getActivity();
        llEventContainer = view.findViewById(R.id.llEventContainer);
        fragmentContainer = R.id.fragment_container;
        pbLoading = view.findViewById(R.id.pbLoading);

        showEvents();
        return view;
    }

    private void showEvents() {
        llEventContainer.removeAllViews();

        Call<EventResponse> call = RetrofitClient.getInstance().getApi().showEvent();
        call.enqueue(new Callback<EventResponse>() {
            View child;

            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse eventResponse = response.body();
                if (response.isSuccessful() && isAdded()) {
                    for (final Event eventData : eventResponse.getEvent()) {
                        child = getLayoutInflater().inflate(R.layout.template_item_event, null);
                        LinearLayout llEventItem = child.findViewById(R.id.llEventItem);
                        TextView tvEventTitle = child.findViewById(R.id.tvEventTitle);
                        ImageView ivEventPoster = child.findViewById(R.id.ivEventPoster);
                        TextView tvEventLocation = child.findViewById(R.id.tvEventLocation);
                        TextView tvEventPrice = child.findViewById(R.id.tvEventPrice);
                        TextView tvEventDuration = child.findViewById(R.id.tvEventDuration);

                        tvEventTitle.setText(eventData.getEvent_name());
                        if (!ivEventPoster.equals("")) {
                            Picasso.with(context).load(eventData.getPoster()).fit().into(ivEventPoster);
                        }
                        tvEventLocation.setText(eventData.getLocation());
                        tvEventPrice.setText(eventData.getHtm());
                        tvEventDuration.setText(eventData.getDate_start()+"\n"+eventData.getDate_end());

                        llEventItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EventDetailFragment edf = new EventDetailFragment();
                                edf.setEvent_id(eventData.getEvent_id());
                                getFragmentManager()
                                        .beginTransaction()
                                        .addToBackStack("fragmentStack")
                                        .replace(fragmentContainer, edf).commit();
                            }
                        });
                        llEventContainer.addView(child);
                    }

                    pbLoading.setVisibility(View.GONE);
                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
