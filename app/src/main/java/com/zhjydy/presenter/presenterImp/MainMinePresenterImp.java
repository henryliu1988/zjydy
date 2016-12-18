package com.zhjydy.presenter.presenterImp;

import android.content.Context;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithData;
import com.zhjydy.presenter.contract.MainMineContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.zhToast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainMinePresenterImp implements MainMineContract.MainMinePresenter, RefreshWithData {

    private MainMineContract.MainMineView mView;

    private int identifycall = -2;

    public MainMinePresenterImp(MainMineContract.MainMineView view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_NICK_NAME, this);
        RefreshManager.getInstance().addNewListener(RefreshKey.TOKEN_MSG_PHOTO, this);
        start();
    }

    private Map<String, Object> identifyMsg;

    @Override
    public void start() {
        loadIdentifyInfo();
    }


    @Override
    public void finish() {

    }

    @Override
    public void loadIdentifyInfo() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("id", UserData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_patient, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                String msg = e.getMessage();
                Map<String, Object> map = Utils.parseObjectToMapString(msg);
                int code = Utils.toInteger(map.get("code"));
                if (code == 2) {
                    identifycall = code;
                    identifyMsg = new HashMap<String, Object>();
                }
            }

            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                identifyMsg = Utils.parseObjectToMapString(webResponse.getData());
                int msgInt = Utils.toInteger(identifyMsg.get("msg"));
                identifycall = msgInt;
                String statusMsg = "";
                if (mView != null) {
                    mView.updateIdentiFyStatus(msgInt, statusMsg);
                }

            }
        });
    }

    @Override
    public Map<String, Object> getIdentifyInfo(Context context) {
        if (identifycall < 0) {
            zhToast.showToast("请稍后，正在获取验证信息");
            loadIdentifyInfo();
            return null;
        } else {
            return identifyMsg;

        }
    }

    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.TOKEN_MSG_NICK_NAME) {
            mView.updateTokenInfo();
        } else if (key == RefreshKey.TOKEN_MSG_PHOTO) {
            mView.updateTokenInfo();
        }
    }
}
