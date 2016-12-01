package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.entity.TokenInfo;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.preference.SPUtils;
import com.zhjydy.presenter.contract.LoginContract;
import com.zhjydy.util.MD5;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class LoginPresenterImp implements LoginContract.Presenter {

    private LoginContract.View mView;

    public LoginPresenterImp(LoginContract.View view) {
        this.mView = view;
        start();
        mView.setPresenter(this);

    }

    @Override
    public void start() {
        loadPrefrence();
    }

    @Override
    public void finish() {

    }

    @Override
    public void tryLogin(final String phoneNum, final String password) {
        HashMap<String, Object> map = new HashMap<>();
        String paswordMd5 = MD5.GetMD5Code(password);
        map.put("mobile", phoneNum);
        map.put("password", paswordMd5);
        WebCall.getInstance().call(WebKey.func_login, map).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "正在登录") {
            @Override
            public void onError(Throwable e) {
                closeLoadingProgress();
                super.onError(e);
            }

            @Override
            public void onNext(WebResponse webResponse) {
                String data = webResponse.getData();
                saveLogInfo(phoneNum, password);
                saveTokenInfo(data);
                mView.onLoginSucess();

            }
        });
    }


    private void saveTokenInfo(Object tokenOb) {
        Map<String, Object> token = Utils.parseObjectToMapString(tokenOb);
        if (token != null && token.size() > 0) {
            TokenInfo info = new TokenInfo();
            info.setId(Utils.toString(token.get("id")));
            info.setMobile(Utils.toString(token.get("mobile")));
            info.setNickname(Utils.toString(token.get("nickname")));
            info.setCollectExperts(Utils.toString(token.get("collectexpert")));
            info.setCollectNews(Utils.toString(token.get("collectnews")));
            info.setIdcard(Utils.toString(token.get("idcard")));
            info.setPaypass(Utils.toString(token.get("paypass")));
            info.setStatus(Utils.toString(token.get("status")));
            AppData.getInstance().setToken(info);
            AppData.getInstance().initData();
        }
    }

    private void saveLogInfo(String phoneNum, String password) {
        SPUtils.put("login_phoneNum", phoneNum);
        SPUtils.put("login_password", password);
    }

    private void loadPrefrence() {
        String phoneNum = Utils.toString(SPUtils.get("login_phoneNum", ""));
        String passoword = Utils.toString(SPUtils.get("login_password", ""));
        if (!TextUtils.isEmpty(phoneNum) || !TextUtils.isEmpty(passoword)) {
            mView.initPreferenceInfo(phoneNum, passoword);
        }
    }

}

