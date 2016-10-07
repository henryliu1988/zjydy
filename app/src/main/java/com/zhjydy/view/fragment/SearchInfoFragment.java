package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhjydy.R;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.presenterImp.MainInfoPresenterImp;
import com.zhjydy.view.adapter.MainInfoListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class SearchInfoFragment extends StatedFragment implements MainInfoContract.MainInfoView {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    public static SearchInfoFragment instance() {
        SearchInfoFragment frag = new SearchInfoFragment();
        return frag;
    }

    private MainInfoContract.MainInfoPresenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_info;

    }

    @Override
    protected void afterViewCreate() {
        new MainInfoPresenterImp(this);
    }

    @Override
    public void setPresenter(MainInfoContract.MainInfoPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void updateInfoList(List<Infomation> infos) {
        MainInfoListAdapter adapter = new MainInfoListAdapter(getContext(), infos);
        mList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.title_back, R.id.title_search_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                mPresenter.finish();
                break;
            case R.id.title_search_ly:
                break;
        }
    }
}
