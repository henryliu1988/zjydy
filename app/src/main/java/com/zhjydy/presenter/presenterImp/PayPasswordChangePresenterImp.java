package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.PayPasswordChangContract;
import com.zhjydy.presenter.contract.PhoneNumChangContract;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PayPasswordChangePresenterImp implements PayPasswordChangContract.Presenter {

    private PayPasswordChangContract.View mView;

    public PayPasswordChangePresenterImp(PayPasswordChangContract.View view) {
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
    public void confirm(String oldPw, final String newPw) {
        String tokenPass = AppData.getInstance().getToken().getPaypass();
        if (!oldPw.equals(tokenPass)) {
            mView.confirmResult(false,"原密码不正确！");
            return;
        }
        HashMap<String,Object> parasm = new HashMap<>();
        parasm.put("addPayPass",newPw);
        WebCall.getInstance().call(WebKey.func_addPayPass,parasm).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                if(WebUtils.getWebStatus(webResponse)) {
                    AppData.getInstance().getToken().setPaypass(newPw);
                    mView.confirmResult(true,"修改支付密码成功");
                } else {
                    mView.confirmResult(false,"修改支付密码失败");
                }
            }
        });
    }
}
