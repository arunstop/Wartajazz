package com.pkl.wartajazz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager;

import com.pkl.wartajazz.R;
import com.pkl.wartajazz.adapter.CustomSwipeAdapter;

public class HomeFragment extends Fragment {

    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    Handler h = new Handler();
    int delay = 15000; //1 second
    Runnable runnable;
    private int[] pagerIndex = {-1};

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this.getActivity());
        viewPager.setAdapter(adapter);
        h.postDelayed(new Runnable() {
                          public void run() {
                              pagerIndex[0]++;
                              if (pagerIndex[0] >= adapter.getCount()) {
                                  pagerIndex[0] = 0;
                              }

                              viewPager.setCurrentItem(pagerIndex[0]);
                              runnable=this;

                              h.postDelayed(runnable, delay);
                          }
                      }
                , delay);
        return view;
    }
//
//    public void onStart() {
//        h.postDelayed(new Runnable() {
//                          public void run() {
//                              pagerIndex[0]++;
//                              if (pagerIndex[0] >= adapter.getCount()) {
//                                  pagerIndex[0] = 0;
//                              }
//
//                              viewPager.setCurrentItem(pagerIndex[0]);
//                              runnable=this;
//
//                              h.postDelayed(runnable, delay);
//                          }
//                      }
//                , delay);
//
//        super.onStart();
//    }
}
