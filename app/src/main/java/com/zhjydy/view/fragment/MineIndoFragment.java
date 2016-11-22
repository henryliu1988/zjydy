package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.MineInfoContract;
import com.zhjydy.presenter.presenterImp.MineInfoPresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class MineIndoFragment extends PageImpBaseFragment implements MineInfoContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.item_more_flag)
    ImageView itemMoreFlag;
    @BindView(R.id.user_photo)
    ImageView userPhoto;
    @BindView(R.id.user_layout)
    RelativeLayout userLayout;
    @BindView(R.id.user_name_title)
    TextView userNameTitle;
    @BindView(R.id.user_name_value)
    TextView userNameValue;
    @BindView(R.id.user_sex_title)
    TextView userSexTitle;
    @BindView(R.id.user_sex_value)
    TextView userSexValue;
    @BindView(R.id.logout)
    TextView logout;
    private MineInfoContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_info;
    }

    @Override
    protected void afterViewCreate() {
        new MineInfoPresenterImp(this);
        titleCenterTv.setText("个人信息");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    public void setPresenter(MineInfoContract.Presenter presenter) {
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
}
