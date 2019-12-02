package com.pkl.wartajazz.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pkl.wartajazz.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class VideoFragment extends Fragment {

    private View view;
    private Context context;
    private LinearLayout llVideoContainer;
    private ProgressBar pbLoading;
//    private String urlAPI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=UUz0T5_-9CByB9GNW-LZG_8g&key=AIzaSyD7ubYW6XJgv3rqZOmb1jsRfMBySZ1DB2o";
    private String urlAPI = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyD7ubYW6XJgv3rqZOmb1jsRfMBySZ1DB2o&channelId=UCz0T5_-9CByB9GNW-LZG_8g&part=snippet&type=video&order=date&maxResults=20";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video, container, false);
        context = getActivity();
        llVideoContainer = view.findViewById(R.id.llVideoContainer);
        pbLoading = view.findViewById(R.id.pbLoading);


        if (isAdded()) {
            showVideos();
        }
        return view;
    }

    public void showVideos() {
        llVideoContainer.removeAllViews();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LinearLayout llVideoItem;
                ImageView ivVideoThumbnail;
                TextView tvVideoTitle;
                View child;
                String videoTitle, videoThumbnail, videoLink;

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
//                    Toast.makeText(context, jsonArray+"", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        child = getLayoutInflater().inflate(R.layout.template_item_video, null);
                        llVideoItem = child.findViewById(R.id.llVideoItem);
                        ivVideoThumbnail = child.findViewById(R.id.ivVideoThumbnail);
                        tvVideoTitle = child.findViewById(R.id.tvVideoTitle);
                        //mengambil value dari API


                        videoTitle = jsonArray.getJSONObject(i)
                                .getJSONObject("snippet")
                                .getString("title");
                        videoThumbnail = jsonArray.getJSONObject(i)
                                .getJSONObject("snippet")
                                .getJSONObject("thumbnails")
                                .getJSONObject("default")
                                .getString("url");
                        //channels playlist videos gunakan url_api yang pertama
//                        videoLink = "http://www.youtube.com/watch?v="
//                                + jsonArray.getJSONObject(i)
//                                .getJSONObject("snippet")
//                                .getJSONObject("resourceId")
//                                .getString("videoId");

                        //channels search videos gunakan url_api yang kedua
                        videoLink = "http://www.youtube.com/watch?v="
                                + jsonArray.getJSONObject(i)
                                .getJSONObject("id")
                                .getString("videoId");


                        //temp variables
                        final String finalVideoLink = videoLink;


                        //click untuk membuka link video
                        llVideoItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent newWindow = new Intent(Intent.ACTION_VIEW, Uri.parse(finalVideoLink));
                                //parsing link to WebActivity
//                            newWindow.putExtra("url", item.getLink());
                                startActivity(newWindow);
                            }
                        });

                        tvVideoTitle.setText(videoTitle);
                        if (!videoThumbnail.equals("")) {
                            Picasso.with(getActivity()).load(videoThumbnail).fit().centerCrop().into(ivVideoThumbnail);
                        }

//                        menambah view pada llVideoContainer(menambah list video)
                        llVideoContainer.addView(child);
                    }
//                    Toast.makeText(context, jsonArray + "", Toast.LENGTH_SHORT).show();

                    //menghilangkan laoding bar
                    pbLoading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pbLoading.setVisibility(View.GONE);
            }
        });
        int socketTimeout = 6000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
