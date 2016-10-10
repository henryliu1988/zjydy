package com.zhjydy.view.fragment;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.presenter.contract.MainOrderContract;
import com.zhjydy.presenter.presenterImp.MainOrderPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.OrderListAdapter;
import com.zhjydy.view.avtivity.PagerImpActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainOrderFragment extends StatedFragment implements MainOrderContract.MainOrderView {
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_l_img)
    ImageView rightLImg;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    private MainOrderContract.MainOrderPresenter mPresenter;


    private String[] mTitles = {"全部订单", "进行中", "已完成", "退款"};

    private ArrayList<CustomTabEntity> tabs = new ArrayList<>();
    private OrderListAdapter mAdapter;
    private List<Map<String,Object>> mOrderList = new ArrayList<>();
    public static MainOrderFragment instance() {
        MainOrderFragment frag = new MainOrderFragment();
        return frag;
    }

    @Override
    protected void initData() {
        for (int i = 0 ; i < mTitles.length; i ++) {
            tabs.add(new TabEntity(mTitles[i]));
        }
        centerTv.setText("订单");
        tabLayout.setTabData(tabs);
        tabLayout.setCurrentTab(0);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                updateAdapter();
            }

            @Override
            public void onTabReselect(int position) {
                updateAdapter();
            }
        });
        mAdapter = new OrderListAdapter(getActivity(),mOrderList);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> item = (Map<String,Object>)adapterView.getAdapter().getItem(i);
                if (item != null && item.size() > 0) {
                    String id = Utils.toString(item.get("id"));
                    id = "fdsk";
                    ActivityUtils.transToFragPagerActivity(getActivity(),PagerImpActivity.class,FragKey.detail_order_fragment,id,false);
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_order;
    }

    @Override
    protected void afterViewCreate() {

        new MainOrderPresenterImp(this);
    }



    @Override
    public void update(List<Map<String, Object>> orders) {
        mOrderList = orders;
        updateAdapter();
    }

    public void updateAdapter() {
        int tab = tabLayout.getCurrentTab();
        List<Map<String,Object>> list = new ArrayList<>();
        switch (tab) {
            case 0:
                list.addAll(mOrderList);
                break;
            case 1:
                list.addAll(mOrderList);
                break;
            case 2:
                list.addAll(mOrderList);
                break;
            case 3:
                list.addAll(mOrderList);
                break;
            default:
                break;
        }
        mAdapter.setData(list);
    }

    @Override
    public void setPresenter(MainOrderContract.MainOrderPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    class OrderListView extends ListView {

        public OrderListView(Context context) {
            super(context);
        }


    }

    public class TabEntity implements CustomTabEntity {
        public String title;

        public TabEntity(String title) {
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return R.mipmap.ic_page_indicator;
        }

        @Override
        public int getTabUnselectedIcon() {
            return R.mipmap.ic_page_indicator;
        }
    }

}
