package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.MsgData;
import com.zhjydy.presenter.contract.MainTabsContract;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MainTabsPrensenter implements MainTabsContract.Presenter,MsgData.onDataCountChangeListener{

    private MainTabsContract.View mView;

    public MainTabsPrensenter(MainTabsContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        MsgData.getInstance().addOnCountChangeListener(this);
        MsgData.getInstance().updateOrderMsgData();
        loadFavList();
    }

    @Override
    public void finish() {

    }

    public void loadMsgCount() {

    }


    public void loadFavList() {

    }
    @Override
    public void onChange(int count) {
        if (mView != null) {
            mView.updateMsgCount(count);
        }
    }
}
