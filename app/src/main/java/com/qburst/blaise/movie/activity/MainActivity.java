package com.qburst.blaise.movie.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.fragment.SlidePageFragment;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences pref;
    private int currentTab;
    private ViewPager viewPager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPagerAdapter v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = this.getSharedPreferences("Fav",MODE_PRIVATE);

        viewPager = findViewById(R.id.viewpager);
        v = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(v);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int c = currentTab;
                viewPager.setAdapter(v);
                viewPager.setCurrentItem(c);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            super.getPageTitle(position);
            String title = null;
            switch (position) {
                case 0: title = "TOP RATED";break;
                case 1: title = "POPULAR";break;
                case 2: title = "FAVOURITE";break;
            }
                return title;
        }

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return SlidePageFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}