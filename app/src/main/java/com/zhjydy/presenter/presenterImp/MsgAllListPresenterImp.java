package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.contract.MainInfoContract;
import com.zhjydy.presenter.contract.MsgAllListContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MsgAllListPresenterImp implements MsgAllListContract.Presenter {

    private MsgAllListContract.View mView;

    public MsgAllListPresenterImp(MsgAllListContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadMsg();
    }

    private void loadMsg() {
    }

    @Override
    public void finish() {

    }
}
