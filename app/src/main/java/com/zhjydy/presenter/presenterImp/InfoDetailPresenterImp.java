package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.contract.InfoDetailContract;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class InfoDetailPresenterImp implements InfoDetailContract.Presenter {

    private InfoDetailContract.View mView;

    private String infoId;
    public InfoDetailPresenterImp(InfoDetailContract.View view, String id) {
        this.mView = view;
        this.infoId = id;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
       if (TextUtils.isEmpty(infoId)) {
           return;
       }
        loadInfoContent(infoId);
    }

    private void loadInfoContent(String id) {

    }
    @Override
    public void finish() {
    }

    @Override
    public void markInfo(String id) {

    }

    @Override
    public void shareInfo(String url) {

    }
}
