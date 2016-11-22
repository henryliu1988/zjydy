package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.IdentityInfoContract;
import com.zhjydy.presenter.presenterImp.AccountSafePresenterImp;
import com.zhjydy.presenter.presenterImp.IdentityInfoPresenterImp;
import com.zhjydy.view.zhview.IdentifyInfoStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class IdentityInfoFragment extends PageImpBaseFragment implements IdentityInfoContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.step_upload)
    TextView stepUpload;
    @BindView(R.id.step_wait_verify)
    TextView stepWaitVerify;
    @BindView(R.id.step_wait)
    TextView stepWait;
    @BindView(R.id.step_verify)
    TextView stepVerify;
    @BindView(R.id.edit_info)
    TextView editInfo;
    private IdentityInfoContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identity_info;
    }

    @Override
    protected void afterViewCreate() {
        new IdentityInfoPresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleCenterTv.setText("认证信息");

    }

    @Override
    public void setPresenter(IdentityInfoContract.Presenter presenter) {
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

    @OnClick(R.id.edit_info)
    public void onClick() {

    }
}
