package com.qburst.blaise.movie.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.fragment.SlidePageFragment;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public Set<Integer> fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fav = new HashSet<Integer>();
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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