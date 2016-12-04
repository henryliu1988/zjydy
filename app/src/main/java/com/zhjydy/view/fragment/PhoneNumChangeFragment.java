package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.PhoneNumChangContract;
import com.zhjydy.presenter.presenterImp.PhoneNumChangePresenterImp;
import com.zhjydy.util.MD5;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.MyCountTimer;
import com.zhjydy.view.zhview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PhoneNumChangeFragment extends PageImpBaseFragment implements PhoneNumChangContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.confirm)
    TextView confirm;
    private PhoneNumChangContract.Presenter mPresenter;

    private View mLoginPsView;
    private View mPhoneView;
    private View mConfirmView;


    private EditText mLoginPsEdit;
    private EditText mNewPhoneEdit;
    private MyCountTimer mCountTimer;

    private EditText mConfirmCodeEdit;
    private int mCurrentStep;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_phonenum_change;
    }

    @Override
    protected void afterViewCreate() {
        new PhoneNumChangePresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleCenterTv.setText("修改手机号码");
        initLoginLayout();
        initNewPhoneLayout();
        initConfirmLayout();
        switchStep(0);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClick();
            }
        });
    }


    private void initLoginLayout() {
        mLoginPsView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_phone_change_login, null);
        mLoginPsEdit = (EditText)mLoginPsView.findViewById(R.id.edit_old_password);
    }

    private void initNewPhoneLayout() {
        mPhoneView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_phone_change_newphone, null);
        mNewPhoneEdit = (EditText)mPhoneView.findViewById(R.id.new_phone_edit);
        TextView mNewPhoneTv = (TextView)mPhoneView.findViewById(R.id.new_phone_tv);
        mCountTimer = new MyCountTimer(mNewPhoneTv,0xff658dff,0xff658dff);
        mNewPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryGetConfirmCode();
            }
        });
    }
    private void tryGetConfirmCode() {
        String phoneNum = mNewPhoneEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            zhToast.showToast("请输入手机号");
            return;
        }
        if(!Utils.isPhone(phoneNum)) {
            zhToast.showToast("请输入正确的手机号");
            return;
        }
        if (mPresenter != null) {
            mPresenter.getConfirmCode(phoneNum).subscribe(new BaseSubscriber<WebResponse>(getContext(),"") {
                @Override
                public void onNext(WebResponse webResponse) {
                   String  mConfirSmsCode = webResponse.getData();
                    zhToast.showToast(mConfirSmsCode);
                    mCountTimer.start();
                }
            });
        }
    }
    private void initConfirmLayout() {
        mConfirmView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_phone_change_confirm, null);
        mConfirmCodeEdit = (EditText)mConfirmView.findViewById(R.id.edit_confirm_code);
    }


    private void switchStep(int step) {
        mCurrentStep = step;
        inputLayout.removeAllViews();
        switch (step) {
            case 0:
                inputLayout.addView(mLoginPsView);
                confirm.setText("下一步");
                break;
            case 1:
                inputLayout.addView(mPhoneView);
                confirm.setText("下一步");
                break;
            case 2:
                inputLayout.addView(mConfirmView);
                confirm.setText("确认");
                break;

        }
    }
    private void confirmClick() {
        switch (mCurrentStep) {
            case 0:
                loginPassConfirm();
                break;
            case 1:
                newPhoneConfirm();
                break;
            case 2:
                allConfirm();
        }
    }

    private void loginPassConfirm() {
        if (mLoginPsEdit == null){
            return;
        }
        String inputPs = mLoginPsEdit.getText().toString();
        if (TextUtils.isEmpty(inputPs)) {
            zhToast.showToast("请输入登录密码");
            return;
        }
        String inputMd5 = MD5.GetMD5Code(inputPs);
        String password = AppData.getInstance().getToken().getPassoword();
        if (inputMd5.equals(password)) {
            switchStep(1);
        } else{
            zhToast.showToast("请输入正确的登录密码");
            return;
        }

    }

    private void newPhoneConfirm() {
        if (mNewPhoneEdit == null) {
            return;
        }
        String newPhone = mNewPhoneEdit.getText().toString();
        if (TextUtils.isEmpty(newPhone)) {
            zhToast.showToast("请输入手机号码并获取验证码");
            return;
        }
        switchStep(2);
    }
    private void allConfirm() {
        if (mConfirmCodeEdit == null) {
            return;
        }
        String confirmCode = mConfirmCodeEdit.getText().toString();
        if (TextUtils.isEmpty(confirmCode)) {
            zhToast.showToast("请输入验证码");
            return;
        }

    }
    @Override
    public void setPresenter(PhoneNumChangContract.Presenter presenter) {
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
