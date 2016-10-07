package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yyydjk.library.DropDownMenu;
import com.zhjydy.R;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.presenterImp.MainExpertPresenterImp;
import com.zhjydy.view.adapter.ExperListAdapter;
import com.zhjydy.view.adapter.SimPleTextAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SearchExpertFragment extends StatedFragment implements MainExpertContract.MainExpertView {

    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;
    @BindView(R.id.m_content_layout)
    RelativeLayout mContentLayout;
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    protected MainExpertContract.MainExpertPresenter mPresenter;
    protected String headers[] = {"全部地区", "科室", "职称"};
    protected String depart[] = {"内科", "外科", "五官科"};
    protected String proTitle[] = {"内科", "外科", "五官科"};
    protected List<View> dropViews = new ArrayList<>();
    protected ExperListAdapter mExpertListAdapter;
    @BindView(R.id.title_back)
    ImageView titleBack;

    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_doc;
    }

    @Override
    protected void afterViewCreate() {
        new MainExpertPresenterImp(this);
        titleSearchText.setText("搜索专家");
    }


    @Override
    public void setPresenter(MainExpertContract.MainExpertPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateFilters(Map<String, Object> data) {
        dropViews.clear();
        List<District> districts = (List<District>) data.get(headers[0]);
        List<NormalDicItem> depart = (List<NormalDicItem>) data.get(headers[1]);
        List<NormalDicItem> profe = (List<NormalDicItem>) data.get(headers[2]);


        ListView listView0 = new ListView(getContext());
        SimPleTextAdapter adapter0 = new SimPleTextAdapter(getContext(), depart);
        listView0.setAdapter(adapter0);
        dropViews.add(listView0);


        ListView listView1 = new ListView(getContext());
        SimPleTextAdapter adapter1 = new SimPleTextAdapter(getContext(), profe);
        listView1.setAdapter(adapter1);
        dropViews.add(listView1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dropDownMenu.closeMenu();
            }
        });
        ListView listView2 = new ListView(getContext());
        SimPleTextAdapter adapter2 = new SimPleTextAdapter(getContext(), profe);
        listView2.setAdapter(adapter2);
        dropViews.add(listView2);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), dropViews, new TextView(getContext()));

    }

    @Override
    public void updateExperts(List<DocTorInfo> list) {
        ExperListAdapter adapter = new ExperListAdapter(getContext(), list);
        mList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @OnClick({R.id.title_back, R.id.title_search_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                mPresenter.finish();
                FragmentUtils.back(getActivity());
                break;
            case R.id.title_search_img:
                break;
        }
    }
}
