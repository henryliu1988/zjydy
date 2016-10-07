package com.zhjydy.view.fragment;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.MainMineContract;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainMineFragment extends StatedFragment  implements MainMineContract.MainMineView{

    private MainMineContract.MainMinePresenter mPresenter;
    public static MainMineFragment instance() {
        MainMineFragment frag = new MainMineFragment();
        return frag;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_mine;

    }

    @Override
    protected void afterViewCreate() {

    }

    @Override
    public void setPresenter(MainMineContract.MainMinePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {
    }
}
