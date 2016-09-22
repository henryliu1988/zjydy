package com.zhjydy.view.fragment;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.presenterImp.MainExpertPresenterImp;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainExpertFragment extends StatedFragment  implements MainExpertContract.MainExpertView{

    private MainExpertContract.MainExpertPresenter mPresenter;
    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_doc;
    }

    @Override
    protected void afterViewCreate() {
        new MainExpertPresenterImp(this);
    }

    @Override
    public void setPresenter(MainExpertContract.MainExpertPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
