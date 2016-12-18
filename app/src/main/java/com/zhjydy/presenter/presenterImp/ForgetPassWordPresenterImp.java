package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.ForgetPassWordContract;
import com.zhjydy.view.zhview.zhToast;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/30 0030.
 */
public class ForgetPassWordPresenterImp implements ForgetPassWordContract.Presenter {

    private ForgetPassWordContract.View mView;

    public ForgetPassWordPresenterImp(ForgetPassWordContract.View view) {
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
        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", phoneNum);
        params.put("type", 2);
        return WebCall.getInstance().call(WebKey.func_sendSms, params);
    }

    @Override
    public void resetPassWord(HashMap<String, Object> params) {
        WebCall.getInstance().call(WebKey.func_reset, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "请稍后，正在提交数据") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    String msg = webResponse.getData();
                    mView.resetOk(msg);
                } else {
                    zhToast.showToast(WebUtils.getWebMsg(webResponse));
                }
            }
        });
    }
}

