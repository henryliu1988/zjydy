package com.zhjydy.presenter.presenterImp;

import android.content.Context;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.presenter.contract.MainMineContract;
import com.zhjydy.presenter.contract.MainOrderContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class MainMinePresenterImp implements MainMineContract.MainMinePresenter {

    private MainMineContract.MainMineView mView;

    private int identifycall = -1;
    public MainMinePresenterImp(MainMineContract.MainMineView view) {
        this.mView = view;
        view.setPresenter(this);
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
    public void  loadIdentifyInfo() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("id", AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_patient, params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onError(Throwable e) {
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
                identifycall = 0;
                identifyMsg = Utils.parseObjectToMapString(webResponse.getData());
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
}
