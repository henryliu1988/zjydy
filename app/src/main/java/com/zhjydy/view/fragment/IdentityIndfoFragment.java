package com.zhjydy.view.fragment;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.presenterImp.AccountSafePresenterImp;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class IdentityIndfoFragment extends StatedFragment implements AccountSafeContract.View {


    private AccountSafeContract.Presenter mPresenter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_safe;
    }

    @Override
    protected void afterViewCreate() {
        new AccountSafePresenterImp(this);
    }

    @Override
    public void setPresenter(AccountSafeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
