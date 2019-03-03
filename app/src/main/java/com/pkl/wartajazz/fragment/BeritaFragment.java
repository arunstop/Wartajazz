package com.pkl.wartajazz.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pkl.wartajazz.R;
import com.pkl.wartajazz.api.RetrofitClient;
import com.pkl.wartajazz.api.RetrofitRssClient;
import com.pkl.wartajazz.models.Item;
import com.pkl.wartajazz.models.Obj;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_berita, container, false);


        setNewsItem();
        return view;

    }

    public void setNewsItem() {
        Call<Obj> call = RetrofitRssClient
                .getInstance()
                .getApi()
                .getFeed("http://www.wartajazz.com/category/news/feed");

        call.enqueue(new Callback<Obj>() {
            @Override
            public void onResponse(Call<Obj> call, Response<Obj> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                final Obj obj = response.body();
//                Toast.makeText(getActivity(), obj.getStatus(), Toast.LENGTH_SHORT).show();


                LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.itemContainer);
                itemContainer.removeAllViews();

                List<Item> itemResult = obj.getItem();
                for (final Item item : itemResult) {

                    LinearLayout newsView = (LinearLayout) getLayoutInflater().inflate(R.layout.news_item, null);
                    LinearLayout newsItem = newsView.findViewById(R.id.newsItem);
                    TextView newsTitle = newsView.findViewById(R.id.newsTitle);
                    ImageView newsThumbnail = newsView.findViewById(R.id.newsThumbnail);

                    newsItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent newWindow = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                            startActivity(newWindow);
                        }
                    });

                    //MENGGANTI GAMBAR DENGAN PLUG IN PICASSO
                    RequestOptions option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
                    Glide.with(getActivity()).load(item.getThumbnail()).apply(option).into(newsThumbnail);
                    newsTitle.setText(item.getTitle());
                    itemContainer.addView(newsView);

                }
            }

            @Override
            public void onFailure(Call<Obj> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
