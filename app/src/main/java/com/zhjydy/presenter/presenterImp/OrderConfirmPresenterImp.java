package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.apptalkingdata.push.service.Msg;
import com.zhjydy.model.data.AppData;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.data.MsgData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.contract.OrderConfirmContract;
import com.zhjydy.presenter.contract.PayPasswordAddContract;
import com.zhjydy.util.MD5;
import com.zhjydy.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class OrderConfirmPresenterImp implements OrderConfirmContract.Presenter {

    private OrderConfirmContract.View mView;

    private String mConfirmInfo;
    private Map<String,Object> mPatientInfo;
    private Map<String,Object> mExpertInfo;
    public OrderConfirmPresenterImp(OrderConfirmContract.View view,String info) {
        this.mView = view;
        this.mConfirmInfo = info;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        initDatas();
    }

    private void initDatas() {
        if (TextUtils.isEmpty(mConfirmInfo)) {
            return;
        }
        Map<String,Object> info = Utils.parseObjectToMapString(mConfirmInfo);
        mPatientInfo = Utils.parseObjectToMapString(info.get("patient"));
        mExpertInfo = Utils.parseObjectToMapString(info.get("expert"));
        if (mPatientInfo == null || mPatientInfo.size()<1 || mExpertInfo == null || mExpertInfo.size() < 1) {
            return;
        }
        mView.updateExpert(mExpertInfo);
        mView.updatePatient(mPatientInfo);
    }

    @Override
    public void finish() {

    }


    @Override
    public void confirmSubmit() {
        String patientId = Utils.toString(mPatientInfo.get("id"));
        String experturl = Utils.toString(mExpertInfo.get("path"));
        String expertName = Utils.toString(mExpertInfo.get("realname"));
        String expertid = Utils.toString(mExpertInfo.get("id"));
        String hos = Utils.toString(mPatientInfo.get("hospital"));
        String name = Utils.toString(mPatientInfo.get("realname"));
        String memberId = AppData.getInstance().getToken().getId();
        HashMap<String, Object> params = new HashMap<>();
        params.put("patientid",patientId);
        params.put("experturl",experturl);
        params.put("expertname",expertName);
        params.put("expertid",expertid);
        params.put("patienthospital",hos);
        params.put("patientname",name);
        params.put("memberid",memberId);
        WebCall.getInstance().call(WebKey.func_makeOrder,params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(),"提交预约信息") {
            @Override
            public void onNext(WebResponse webResponse) {
                if (WebUtils.getWebStatus(webResponse)) {
                    mView.subsribExpertResult(true,"成功预约专家");
                    MsgData.getInstance().loadOrderMsgData();
                    RefreshManager.getInstance().refreshData(RefreshKey.ORDET_LIST_CHANGE);
                } else {
                    mView.subsribExpertResult(false,"预约失败  " +WebUtils.getWebMsg(webResponse));
                }
            }
        });

    }
}
