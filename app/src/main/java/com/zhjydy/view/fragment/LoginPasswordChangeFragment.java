package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.LoginPasswordChangeContract;
import com.zhjydy.presenter.presenterImp.LoginPasswordChangePresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class LoginPasswordChangeFragment extends PageImpBaseFragment implements LoginPasswordChangeContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.edit_old_password)
    EditText editOldPassword;
    @BindView(R.id.edit_new_password)
    EditText editNewPassword;
    private LoginPasswordChangeContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loginpassword_change;
    }

    @Override
    protected void afterViewCreate() {
        new LoginPasswordChangePresenterImp();
        titleCenterTv.setText("修改登录密码");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    public void setPresenter(LoginPasswordChangeContract.Presenter presenter) {
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
