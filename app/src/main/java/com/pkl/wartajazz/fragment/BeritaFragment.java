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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.api.RetrofitRssClient;
import com.pkl.wartajazz.models.Item;
import com.pkl.wartajazz.models.Obj;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaFragment extends Fragment {

    private View view;
    private ProgressBar pbLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_berita, container, false);
        pbLoading = view.findViewById(R.id.pbLoading);


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


                LinearLayout itemContainer = view.findViewById(R.id.itemContainer);
                itemContainer.removeAllViews();

                List<Item> itemResult = obj.getItem();
                for (final Item item : itemResult) {

                    LinearLayout newsView = (LinearLayout) getLayoutInflater().inflate(R.layout.template_item_news, null);
                    LinearLayout newsItem = newsView.findViewById(R.id.newsItem);
                    TextView newsTitle = newsView.findViewById(R.id.newsTitle);
                    ImageView newsThumbnail = newsView.findViewById(R.id.newsThumbnail);


                    newsItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent newWindow = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()) );
                            //parsing link to WebActivity
//                            newWindow.putExtra("url", item.getLink());
                            startActivity(newWindow);
                        }
                    });



                    //MENGGANTI GAMBAR DENGAN PLUG IN PICASSO
                    if(item.getThumbnail().equals("")){

                    }else{
                        Picasso.with(getActivity()).load(item.getThumbnail()).into(newsThumbnail);
                    }
                    newsTitle.setText(item.getTitle());
                    itemContainer.addView(newsView);

                }
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Obj> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
