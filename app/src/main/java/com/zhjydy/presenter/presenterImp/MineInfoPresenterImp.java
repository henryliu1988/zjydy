package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.MineInfoContract;
import com.zhjydy.presenter.contract.PatientCaseContract;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MineInfoPresenterImp implements MineInfoContract.Presenter {

    private MineInfoContract.View mView;

    public MineInfoPresenterImp(MineInfoContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }



    @Override
    public void finish() {

    }


}