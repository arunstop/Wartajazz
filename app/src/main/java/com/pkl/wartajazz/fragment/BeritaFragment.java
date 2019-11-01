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

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.api.RetrofitRssClient;
import com.pkl.wartajazz.models.Item;
import com.pkl.wartajazz.models.Obj;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaFragment extends Fragment {

    private View view;
    private ProgressBar pbLoading;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_berita, container, false);

        context = getActivity();
        pbLoading = view.findViewById(R.id.pbLoading);


        showNews();
        return view;

    }

    public void showNews() {
        Call<Obj> call = RetrofitRssClient
                .getInstance()
                .getApi()
                .getFeed("http://www.wartajazz.com/category/news/feed");

        call.enqueue(new Callback<Obj>() {
            @Override
            public void onResponse(Call<Obj> call, Response<Obj> response) {
                if (response.isSuccessful() && isAdded()) {

                    final Obj obj = response.body();

                    LinearLayout itemContainer = view.findViewById(R.id.itemContainer);
                    itemContainer.removeAllViews();

                    List<Item> itemResult = obj.getItem();
                    for (final Item item : itemResult) {

                        LinearLayout newsView = (LinearLayout) getLayoutInflater().inflate(R.layout.template_item_news, null);
                        LinearLayout newsItem = newsView.findViewById(R.id.llNewsItem);
                        TextView newsTitle = newsView.findViewById(R.id.tvNewsTitle);
                        TextView newsDate = newsView.findViewById(R.id.tvNewsDate);
                        ImageView newsThumbnail = newsView.findViewById(R.id.tvNewsThumbnail);


                        newsItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent newWindow = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                                //parsing link to WebActivity
//                            newWindow.putExtra("url", item.getLink());
                                startActivity(newWindow);
                            }
                        });


                        //MENGGANTI GAMBAR DENGAN PLUG IN PICASSO
                        if (item.getThumbnail().equals("")) {

                        } else {
                            Picasso.with(getActivity()).load(item.getThumbnail()).fit().centerCrop().into(newsThumbnail);
                        }
                        newsTitle.setText(item.getTitle());
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyy", Locale.ENGLISH);
                            Date pubDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(item.getPubDate());
                            newsDate.setText(sdf.format(pubDate) + "");

                        } catch (ParseException e) {
                            Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                        itemContainer.addView(newsView);

                    }
                    pbLoading.setVisibility(View.GONE);
                } else {
//                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Obj> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                showNews();
            }
        });

    }
}
