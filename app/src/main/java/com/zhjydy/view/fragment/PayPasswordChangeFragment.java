package com.zhjydy.view.fragment;

import com.zhjydy.presenter.contract.PayPasswordChangContract;
import com.zhjydy.presenter.contract.PhoneNumChangContract;
import com.zhjydy.presenter.presenterImp.PayPasswordChangePresenterImp;
import com.zhjydy.presenter.presenterImp.PhoneNumChangePresenterImp;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class PayPasswordChangeFragment extends  StatedFragment implements PayPasswordChangContract.View {

    private PayPasswordChangContract.Presenter mPresenter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void afterViewCreate() {
        new PayPasswordChangePresenterImp(this);
    }



    @Override
    public void setPresenter(PayPasswordChangContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
