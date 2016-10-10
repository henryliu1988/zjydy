package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.CommonContract;
import com.zhjydy.presenter.presenterImp.CommonPresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class CommonFragment extends StatedFragment implements CommonContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.clear_title)
    TextView clearTitle;
    @BindView(R.id.clear_flag)
    ImageView clearFlag;
    @BindView(R.id.clear_layout)
    RelativeLayout clearLayout;
    private CommonContract.Presenter mPresenter;

    @Override
    protected void initData() {
        titleCenterTv.setText("通用");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_common;
    }

    @Override
    protected void afterViewCreate() {
        new CommonPresenterImp(this);
    }


    @Override
    public void setPresenter(CommonContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.title_back, R.id.clear_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                mPresenter.finish();
                break;
            case R.id.clear_layout:
                break;
        }
    }
}
