package com.zhjydy.view.avtivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.zhjydy.R;
import com.zhjydy.view.fragment.MainExpertFragment;
import com.zhjydy.view.fragment.MainHomeFragment;
import com.zhjydy.view.fragment.MainInfoFragment;
import com.zhjydy.view.fragment.MainMineFragment;
import com.zhjydy.view.fragment.MainOrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainTabsActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.main_tabs)
    AdvancedPagerSlidingTabStrip mainTabs;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;


    private static final int VIEW_FIRST = 0;
    private static final int VIEW_SECOND = 1;
    private static final int VIEW_THIRD = 2;
    private static final int VIEW_FOURTH = 3;
    private static final int VIEW_FIVE = 4;

    private static final int VIEW_SIZE = 5;
    private MainHomeFragment mFirstFragment = null;
    private MainExpertFragment mSecondFragment = null;
    private MainInfoFragment mThirdFragment = null;
    private MainOrderFragment mFourthFragment = null;
    private MainMineFragment mFifthFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mainViewpager.setOffscreenPageLimit(VIEW_SIZE);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
        mainTabs.setViewPager(mainViewpager);
        mainTabs.setOnPageChangeListener(this);
        mainViewpager.setCurrentItem(VIEW_FIRST);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class FragmentAdapter extends FragmentStatePagerAdapter implements AdvancedPagerSlidingTabStrip.IconTabProvider {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        if (null == mFirstFragment)
                            mFirstFragment = MainHomeFragment.instance();
                        return mFirstFragment;

                    case VIEW_SECOND:
                        if (null == mSecondFragment)
                            mSecondFragment = MainExpertFragment.instance();
                        return mSecondFragment;

                    case VIEW_THIRD:
                        if (null == mThirdFragment)
                            mThirdFragment = MainInfoFragment.instance();
                        return mThirdFragment;

                    case VIEW_FOURTH:
                        if (null == mFourthFragment)
                            mFourthFragment = MainOrderFragment.instance();
                        return mFourthFragment;
                    case VIEW_FIVE:
                        if (null == mFifthFragment)
                            mFifthFragment = MainMineFragment.instance();
                        return mFifthFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position >= 0 && position < VIEW_SIZE) {
                switch (position) {
                    case VIEW_FIRST:
                        return "首页";
                    case VIEW_SECOND:
                        return "专家";
                    case VIEW_THIRD:
                        return "资讯";
                    case VIEW_FOURTH:
                        return "订单";
                    case VIEW_FIVE:
                        return "个人";

                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public Integer getPageIcon(int index) {
            if (index >= 0 && index < VIEW_SIZE) {
                switch (index) {
                    case VIEW_FIRST:
                        return R.mipmap.titlebar_back;
                    case VIEW_SECOND:
                        return R.mipmap.titlebar_back;
                    case VIEW_THIRD:
                        return R.mipmap.titlebar_back;
                    case VIEW_FOURTH:
                        return R.mipmap.titlebar_back;
                    case VIEW_FIVE:
                        return R.mipmap.titlebar_back;

                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Integer getPageSelectIcon(int index) {
            if (index >= 0 && index < VIEW_SIZE) {
                switch (index) {
                    case VIEW_FIRST:
                        return R.mipmap.titlebar_back;
                    case VIEW_SECOND:
                        return R.mipmap.titlebar_back;
                    case VIEW_THIRD:
                        return R.mipmap.titlebar_back;
                    case VIEW_FOURTH:
                        return R.mipmap.titlebar_back;
                    default:
                        break;
                }
            }
            return 0;
        }

        @Override
        public Rect getPageIconBounds(int position) {
            return null;
        }
    }

}
