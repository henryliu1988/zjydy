package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.cache.SPUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithData;
import com.zhjydy.presenter.contract.InitLoaderContract;
import com.zhjydy.util.Utils;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/17 0017.
 */
public class InitLoaderPresenterImp implements InitLoaderContract.Presenter {

    private InitLoaderContract.View mView;

    public InitLoaderPresenterImp(InitLoaderContract.View view) {
        this.mView = view;
        start();
    }

    @Override
    public void start() {
        tryLogInBackGroud();
    }

    private void tryLogInBackGroud() {

        String phoneNum = Utils.toString(SPUtils.get("login_phoneNum", ""));
        String passoword = Utils.toString(SPUtils.get("login_password", ""));
        boolean autoLogin = Utils.toBoolean(SPUtils.get("login_auto", false));
        if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(passoword) && autoLogin) {
            UserData.getInstance().tryLoginManager(phoneNum, passoword, null);
        } else {
            if (mView != null) {
                mView.gotoMainTabs();
            }
        }
    }

    @Override
    public void finish() {

    }

    @Override
    public void onRefreshWithData(int key, Object data) {
        if (key == RefreshKey.LOGIN_RESULT_BACK) {
            Map<String, Object> dataMap = Utils.parseObjectToMapString(data);
            boolean status = Utils.toBoolean(dataMap.get("status"));
            String msg = Utils.toString(dataMap.get("msg"));
            if (mView != null) {
                mView.gotoMainTabs();
            }
        }
    }
}
