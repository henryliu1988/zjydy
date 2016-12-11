package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.ChatRecordContract;
import com.zhjydy.presenter.contract.MineInfoContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ChatRecordPresenterImp implements ChatRecordContract.Presenter {

    private ChatRecordContract.View mView;

    private Map<String, Object> chatInfo;

    public ChatRecordPresenterImp(ChatRecordContract.View view, Map<String, Object> item) {
        this.mView = view;
        this.chatInfo = item;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadCommentRecord();
        updateExpertName();
    }


    private void updateExpertName(){
        String expertId = Utils.toString(chatInfo.get("expertid"));
        String sendId = Utils.toString(chatInfo.get("sendid"));
        String getId = Utils.toString(chatInfo.get("getid"));
        if (TextUtils.isEmpty(expertId)) {
            return;
        }
        String expertName = "";
        if (expertId.equals(sendId)) {
            expertName = Utils.toString(chatInfo.get("sendname"));
        } else if (expertId.equals(getId)) {
            expertName = Utils.toString(chatInfo.get("getname"));
        }
        mView.updateExpertName(expertName);

    }
    private void loadCommentRecord() {
        String mark = Utils.toString(chatInfo.get("mark"));
        if (TextUtils.isEmpty(mark)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("mark", mark);
        WebCall.getInstance().call(WebKey.func_getComment, param).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),true) {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String, Object>> comments = Utils.parseObjectToListMapString(data);
                String expertUrl = Utils.toString(chatInfo.get("path"));
                mView.setChatMsgs(comments,expertUrl);
            }
        });
    }

    @Override
    public void finish() {

    }


}
