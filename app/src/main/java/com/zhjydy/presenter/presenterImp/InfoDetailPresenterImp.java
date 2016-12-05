package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.ExpertDetailContract;
import com.zhjydy.presenter.contract.InfoDetailContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.zhToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

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
        loadInfoContent();
        loadFavStatus();
    }
    private void loadFavStatus() {
        List<String> collecs = AppData.getInstance().getToken().getCollectNewsList();
        mView.updateFavStatus(collecs.contains(infoId));
    }
    private void loadInfoContent() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", infoId);
        WebCall.getInstance().call(WebKey.func_getNewsById, params).map(new Func1<WebResponse, Map<String, Object>>() {
            @Override
            public Map<String, Object> call(WebResponse webResponse) {
                String data = webResponse.getData();
                Map<String, Object> map =  Utils.parseObjectToMapString(data);
                String collect = AppData.getInstance().getToken().getCollectNews();
                map.put("collect", collect);
                return map;
            }
        }).subscribe(new BaseSubscriber<Map<String, Object>>() {
            @Override
            public void onNext(Map<String, Object> map) {
                mView.update(map);
            }
        });
    }

    @Override
    public void finish() {
    }

    @Override
    public void saveInfo() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("newsid", infoId);
        params.put("userid", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_collectNews, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    List<String> collect = AppData.getInstance().getToken().getCollectNewsList();
                    if (!collect.contains(infoId)) {
                        collect.add(infoId);
                    }
                    AppData.getInstance().getToken().setCollectNewAsList(collect);
                    loadFavStatus();
                } else {
                    zhToast.showToast("收藏失败");
                }
            }
        });
    }

    @Override
    public void cancelSaveInfo() {

    }

    @Override
    public void shareInfo(String url) {

    }
}
