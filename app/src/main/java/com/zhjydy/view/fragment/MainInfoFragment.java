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

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainInfoFragment extends StatedFragment implements MainInfoContract.MainInfoView {
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.right_img)
    ImageView rightImg;
    @BindView(R.id.right_l_img)
    ImageView rightLImg;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;

    public static MainInfoFragment instance() {
        MainInfoFragment frag = new MainInfoFragment();
        return frag;
    }

    private MainInfoContract.MainInfoPresenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_info;

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
        MainInfoListAdapter adapter = new MainInfoListAdapter(getContext(),infos);
        mList.setAdapter(adapter);
    }
}
