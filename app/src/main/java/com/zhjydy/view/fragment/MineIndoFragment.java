package com.zhjydy.view.fragment;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.MineInfoContract;
import com.zhjydy.presenter.contract.PatientCaseEditContract;
import com.zhjydy.presenter.presenterImp.MineInfoPresenterImp;
import com.zhjydy.presenter.presenterImp.PatientCaseEditPresenterImp;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class MineIndoFragment extends StatedFragment implements MineInfoContract.View {


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
    }

    @Override
    public void setPresenter(MineInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }
}
