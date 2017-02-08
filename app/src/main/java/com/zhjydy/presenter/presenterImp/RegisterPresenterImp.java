package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.RegisterContract;

import java.util.HashMap;

import rx.Observable;
import rx.functions.Func1;

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
    public Observable<WebResponse> getConfirmCode(final String phoneNum) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", phoneNum);
        params.put("type",1);
        return WebCall.getInstance().call(WebKey.func_checkMobile,params).flatMap(new Func1<WebResponse, Observable<WebResponse>>() {
            @Override
            public Observable<WebResponse> call(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    WebResponse response = new WebResponse(1,"该手机号码已经注册,请直接登录","");
                    return Observable.just(response);
                } else {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("mobile", phoneNum);
                    params.put("type", 1);
                    return WebCall.getInstance().call(WebKey.func_sendSms, params);
                }
            }
        });
    }

    @Override
    public void register(HashMap<String, Object> params) {
        params.put("nickname", "");
        params.put("type", "1");
        WebCall.getInstance().call(WebKey.func_register, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                String msg = webResponse.getData();
                mView.registerOK(msg);
            }
        });
    }
}

