package com.zhjydy.presenter.presenterImp;

import android.content.Context;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithData;
import com.zhjydy.presenter.contract.MainMineContract;

import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainMinePresenterImp implements MainMineContract.MainMinePresenter, RefreshWithData {

    private MainMineContract.MainMineView mView;


    public MainMinePresenterImp(MainMineContract.MainMineView view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_NICK_NAME, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_PHOTO, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.IDENTIFY_MSG_UPDATE,this);
        start();
    }


    @Override
    public void start() {
        loadIdentifyInfo();
    }


    @Override
    public void finish() {

    }

    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.TOKEN_MSG_NICK_NAME) {
            mView.updateTokenInfo();
        } else if (key == RefreshKey.TOKEN_MSG_PHOTO) {
            mView.updateTokenInfo();
        } else if (key == RefreshKey.IDENTIFY_MSG_UPDATE) {
            loadIdentifyInfo();
        }
    }

    @Override
    public void loadIdentifyInfo() {
        UserData.getInstance().getIdentifyState().subscribe(new BaseSubscriber<Integer>() {
            @Override
            public void onNext(Integer integer) {
                mView.updateIdentiFyStatus(integer, "");
            }
        });
    }

    @Override
    public Observable<Map<String, Object>> getIdentifyInfo(Context context) {
        return UserData.getInstance().getIdentifyMsg();
    }
}
