package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.MsgData;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithKey;
import com.zhjydy.presenter.contract.MainTabsContract;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MainTabsPrensenter implements MainTabsContract.Presenter,RefreshWithKey{

    private MainTabsContract.View mView;

    public MainTabsPrensenter(MainTabsContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.ORDER_DATA_READ,this);
        RefreshManager.getInstance().addNewListener(RefreshKey.NEW_COMMENT_DATA_READ,this);
        start();
    }

    @Override
    public void start() {
        MsgData.getInstance().loadData();
        loadFavList();
    }

    @Override
    public void finish() {

    }

    public void loadMsgCount() {
        int count = MsgData.getInstance().getUnReadMsgCount();
        if (mView != null) {
            mView.updateMsgCount(count);
        }
    }
    public void loadFavList() {

    }

    @Override
    public void onRefreshWithKey(int key) {
        loadMsgCount();
    }
}
