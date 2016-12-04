package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.PasswordChangContract;
import com.zhjydy.presenter.contract.PhoneNumChangContract;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PhoneNumChangePresenterImp implements PhoneNumChangContract.Presenter {

    private PhoneNumChangContract.View mView;

    public PhoneNumChangePresenterImp(PhoneNumChangContract.View view) {
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
    public Observable<WebResponse> getConfirmCode(String phone) {
        HashMap<String,Object> params  = new HashMap<>();
        params.put("mobile",phone);
        params.put("type",1);
        return WebCall.getInstance().call(WebKey.func_sendSms,params);

    }
}
