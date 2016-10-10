package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.contract.MainOrderContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ExpertDetailPresenterImp implements ExpertDetailContract.Presenter {

    private ExpertDetailContract.View mView;

    private String expertId;
    public ExpertDetailPresenterImp(ExpertDetailContract.View view,String id) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
       if (TextUtils.isEmpty(expertId)) {
           return;
       }
        loadExpertInfo(expertId);
        loadComments(expertId);
    }


    private void loadExpertInfo(String id) {

    }
    private void loadComments(String id) {

    }
    @Override
    public void finish() {

    }

    @Override
    public void markExpert() {

    }

    @Override
    public void subscribeExpert() {

    }

    @Override
    public void makeNewComment(String commentId) {

    }
}
