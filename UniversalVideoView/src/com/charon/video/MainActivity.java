
package com.charon.video;

import com.charon.video.fragment.LiveFragment;
import com.charon.video.fragment.LocalFragment;
import com.charon.video.fragment.OnlineFragment;
import com.charon.video.view.ScrollingTabs;
import com.charon.video.view.ScrollingTabs.TabAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private ScrollingTabs mScrollingTabs;
    private ViewPager mViewPager;
    private TabAdapter mTabAdapter;
    private PagerAdapter mPagerAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initView();
    }

    private void findView() {
        mScrollingTabs = (ScrollingTabs) findViewById(R.id.st_main);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
    }

    private void initView() {
        mFragments = new ArrayList<Fragment>();
        addFragments();
        mTabAdapter = new VideoTabAdapter();
        mPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager());
        mScrollingTabs.setEqualWidth(true);
        mViewPager.setAdapter(mPagerAdapter);
        mScrollingTabs.setViewPager(mViewPager);
        mScrollingTabs.setTabAdapter(mTabAdapter);
    }

    private void addFragments() {
        mFragments.add(OnlineFragment.getInstance());
        mFragments.add(LocalFragment.getInstance());
        mFragments.add(LiveFragment.getInstance());
    }

    private class VideoPagerAdapter extends FragmentPagerAdapter {

        public VideoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments != null) {
                return mFragments.get(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            if (mFragments != null) {
                return mFragments.size();
            }
            return 0;
        }

    }

    private class VideoTabAdapter implements TabAdapter {

        @Override
        public View getView(int position) {
            View view = View.inflate(MainActivity.this, R.layout.item_tab, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_tabs);
            if (position == 0) {
                tv_title.setText("OnLine");
            } else if (position == 1) {
                tv_title.setText("Local");
            } else if (position == 2) {
                tv_title.setText("Live");
            }
            return view;
        }

        @Override
        public View getSeparator() {
            View view = new View(MainActivity.this);
            view.setBackgroundColor(Color.GRAY);
            view.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public void onTabSelected(int position, ViewGroup mContainer) {
            View tab = (View) mContainer.getChildAt(position);
            TextView tv = (TextView) tab.findViewById(R.id.tv_tabs);
            ImageView iv = (ImageView) tab.findViewById(R.id.iv_tabs);
            tv.setTextColor(Color.GREEN);
            iv.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTabUnSelected(int position, ViewGroup mContainer) {
            View tab = (View) mContainer.getChildAt(position);
            TextView tv = (TextView) tab.findViewById(R.id.tv_tabs);
            ImageView iv = (ImageView) tab.findViewById(R.id.iv_tabs);
            tv.setTextColor(Color.BLACK);
            iv.setVisibility(View.INVISIBLE);
        }

    }
}
