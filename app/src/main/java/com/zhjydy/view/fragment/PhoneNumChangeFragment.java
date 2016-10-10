package com.zhjydy.view.fragment;

import com.zhjydy.presenter.contract.PasswordChangContract;
import com.zhjydy.presenter.contract.PhoneNumChangContract;
import com.zhjydy.presenter.presenterImp.PasswordChangPresenterImp;
import com.zhjydy.presenter.presenterImp.PhoneNumChangePresenterImp;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PhoneNumChangeFragment extends  StatedFragment implements PhoneNumChangContract.View {

    private PhoneNumChangContract.Presenter mPresenter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void afterViewCreate() {
        new PhoneNumChangePresenterImp(this);
    }



    @Override
    public void setPresenter(PhoneNumChangContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
