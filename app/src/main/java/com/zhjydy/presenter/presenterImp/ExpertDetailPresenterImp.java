package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class ExpertDetailPresenterImp implements ExpertDetailContract.Presenter {

    private ExpertDetailContract.View mView;

    private String expertId;

    public ExpertDetailPresenterImp(ExpertDetailContract.View view, String id) {
        this.mView = view;
        view.setPresenter(this);
        this.expertId = id;
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
        HashMap<String, Object> params = new HashMap<>();
        params.put("zid", id);
        WebCall.getInstance().call(WebKey.func_getExpert, params).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> map = Utils.parseObjectToMapString(data);
                return map;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                mView.updateExpertInfos(map);
            }
        });
    }

    private void loadComments(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("expertid", id);
        WebCall.getInstance().call(WebKey.func_getCommentList, params).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(webResponse.getData());
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> maps) {
                mView.updateComments(maps);
            }
        });
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

    @Override
    public void saveExpert(String id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("expertid", id);
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_collectExpert, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
            }
        });
    }
}
