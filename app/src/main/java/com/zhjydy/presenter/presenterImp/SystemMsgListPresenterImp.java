package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.OrderMsgListContract;
import com.zhjydy.presenter.contract.SystemMsgListContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class SystemMsgListPresenterImp implements SystemMsgListContract.Presenter {

    private SystemMsgListContract.View mView;


    public SystemMsgListPresenterImp(SystemMsgListContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {

        loadSystemList();
    }

    private void loadSystemList() {

    }

    @Override
    public void finish() {
    }


}
