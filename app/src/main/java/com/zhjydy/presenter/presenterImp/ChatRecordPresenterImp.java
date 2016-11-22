package com.zhjydy.presenter.presenterImp;

import com.zhjydy.presenter.contract.ChatRecordContract;
import com.zhjydy.presenter.contract.MineInfoContract;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ChatRecordPresenterImp implements ChatRecordContract.Presenter {

    private ChatRecordContract.View mView;

    public ChatRecordPresenterImp(ChatRecordContract.View view) {
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


    @Override
    public void setChatMsgs(List<Map<String, Object>> msg) {

    }
}
