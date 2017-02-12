package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.entity.TokenInfo;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithKey;
import com.zhjydy.presenter.contract.IdentityInfoContract;
import com.zhjydy.util.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class IdentityInfoPresenterImp implements IdentityInfoContract.Presenter, RefreshWithKey {

    private IdentityInfoContract.View mView;

    public IdentityInfoPresenterImp(IdentityInfoContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.IDENTIFY_MSG_UPDATE, this);
        start();
    }

    public void loadIdentifyInfo() {

        final HashMap<String, Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_patient, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    Map<String, Object> identifyMsg = Utils.parseObjectToMapString(webResponse.getData());
                    int msgInt = Utils.toInteger(identifyMsg.get("msg"));
                    List<String> path = Utils.parseObjectToListString(identifyMsg.get("path"));
                    if (path == null || path.size() < 1) {
                        TokenInfo info = UserData.getInstance().getToken();
                        String idCards = info.getIdcard();
                        if (!TextUtils.isEmpty(idCards)) {
                            List<String> cardList = Arrays.asList(idCards.split(","));
                            path = cardList;
                        }
                    }
                    if (mView != null) {
                        mView.updateIdentifyInfo(msgInt, path);
                    }
                }
            }
        });
    }

    @Override
    public void start() {
        loadIdentifyInfo();
    }


    @Override
    public void finish() {

    }


    @Override
    public void uploadImageFiles(List<String> urls) {

    }

    @Override
    public void onRefreshWithKey(int key) {
        if (key == RefreshKey.IDENTIFY_MSG_UPDATE) {
            loadIdentifyInfo();
        }
    }
}
