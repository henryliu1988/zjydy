package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.entity.TokenInfo;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.preference.SPUtils;
import com.zhjydy.presenter.contract.LoginContract;
import com.zhjydy.presenter.contract.RegisterContract;
import com.zhjydy.util.MD5;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class RegisterPresenterImp implements RegisterContract.Presenter {

    private RegisterContract.View mView;

    public RegisterPresenterImp(RegisterContract.View view) {
        this.mView = view;
        start();
        mView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }


    @Override
    public Observable<WebResponse> getConfirmCode(String phoneNum) {
        HashMap<String,Object> params  = new HashMap<>();
        params.put("mobile",phoneNum);
        params.put("type",1);
        return WebCall.getInstance().call(WebKey.func_sendSms,params);
    }

    @Override
    public void register(HashMap<String, Object> params) {
        params.put("nickname","");
        params.put("type", "1");
        WebCall.getInstance().call(WebKey.func_register,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String msg = webResponse.getData();
                mView.registerOK(msg);
            }
        });
    }
}

