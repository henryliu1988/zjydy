package com.zhjydy.view.zhview;

/**
 * Created by Administrator on 2016/10/1 0001.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.zhjydy.R;

import java.util.ArrayList;
import java.util.List;


public class ItemTabViewBase extends LinearLayout {
    protected Context context;
    protected SlidingTabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected PagerAdapter mAdapter;
    protected List<PagerItem> mPagerItems = new ArrayList<>();

    public ItemTabViewBase(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ItemTabViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();

    }

    public void initView() {
        LayoutInflater.from(context).inflate(R.layout.item_tab_layout, this);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerItems.clear();

    }

    public void setPagerItems(List<PagerItem> pagerItems) {
        this.mPagerItems = pagerItems;
        refreshView();
    }

    private void refreshView() {
        mAdapter = new TabPagerAdapter(getContext(), mPagerItems);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

    public View getTabView(String title) {
        for (int i = 0; i < mPagerItems.size(); i++) {
            PagerItem item = mPagerItems.get(i);
            if (item != null && !TextUtils.isEmpty(title) && title.equals(item.getTitle())) {
                return item.getView();
            }
        }
        return null;
    }

    public static class PagerItem {
        String title;
        View view;

        public PagerItem(String title, View view) {
            this.title = title;
            this.view = view;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }
    }

    static class TabPagerAdapter extends android.support.v4.view.PagerAdapter {
        private List<PagerItem> items = new ArrayList();
        private Context context;

        private TabPagerAdapter(Context context, List<PagerItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = items.get(position).getView();
            container.addView(v);
            return v;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).getTitle();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}

