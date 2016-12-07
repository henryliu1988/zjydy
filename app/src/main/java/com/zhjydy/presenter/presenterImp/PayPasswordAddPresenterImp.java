package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.PayPasswordAddContract;
import com.zhjydy.presenter.contract.PayPasswordChangContract;
import com.zhjydy.util.MD5;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PayPasswordAddPresenterImp implements PayPasswordAddContract.Presenter {

    private PayPasswordAddContract.View mView;

    public PayPasswordAddPresenterImp(PayPasswordAddContract.View view) {
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
    public void confirm(final String newPw) {
        HashMap<String,Object> parasm = new HashMap<>();
        String password = MD5.GetMD5Code(newPw);
        parasm.put("paypass",password);
        WebCall.getInstance().call(WebKey.func_addPayPass,parasm).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                if (WebUtils.getWebStatus(webResponse)) {
                    mView.confirmResult(true,"修改支付密码成功");
                    AppData.getInstance().getToken().setPaypass(newPw);
                } else {
                    mView.confirmResult(false,"修改支付密码失败");
                }
            }
        });
    }
}
