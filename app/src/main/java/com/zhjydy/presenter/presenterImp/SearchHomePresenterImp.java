package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.SearchExpertContract;
import com.zhjydy.presenter.contract.SearchHomeContract;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class SearchHomePresenterImp implements SearchHomeContract.Presenter {
    private SearchHomeContract.View mView;

    public SearchHomePresenterImp(SearchHomeContract.View view) {
        mView = view;
        mView.setPresenter(this);
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }
}
