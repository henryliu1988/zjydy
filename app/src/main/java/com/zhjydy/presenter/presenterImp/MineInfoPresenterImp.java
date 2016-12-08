package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.data.PatientData;
import com.zhjydy.model.entity.PickViewData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.FileUpLoad;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.contract.MineInfoContract;
import com.zhjydy.presenter.contract.PatientCaseContract;
import com.zhjydy.util.ViewKey;
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
public class MineInfoPresenterImp implements MineInfoContract.Presenter {

    private MineInfoContract.View mView;

    public MineInfoPresenterImp(MineInfoContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadPersonInfo();
        loadSexPickData();
    }
    private void loadSexPickData() {
        ArrayList<PickViewData> sexData = new ArrayList<>();
        sexData.add(new PickViewData("1","男"));
        sexData.add(new PickViewData("2","女"));
        mView.updateSexPick(sexData);
    }


    private void loadPersonInfo() {
        if (mView !=null){
            mView.updateInfo( AppData.getInstance().getToken());
        }
       ;
    }

    @Override
    public void finish() {

    }


    @Override
    public void updateMemberPhoto(String path) {
        List<Map<String,Object>> files = new ArrayList<>();
        Map<String,Object> file = new HashMap<>();
        file.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
        file.put(ViewKey.FILE_KEY_URL,path);
        files.add(file);
        FileUpLoad.uploadFiles(files).flatMap(new Func1<String, Observable<WebResponse>>() {
            @Override
            public Observable<WebResponse> call(String s) {
                HashMap<String,Object> params = new HashMap<>();
                params.put("nickname",AppData.getInstance().getToken().getNickname());
                params.put("sex",AppData.getInstance().getToken().getSex());
                params.put("head_img",s);
                params.put("id",AppData.getInstance().getToken().getId());
                return WebCall.getInstance().call(WebKey.func_updateMember,params);
            }
        }).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
            }
        });
    }

    @Override
    public void updateMemberSex(int sex) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("nickname",AppData.getInstance().getToken().getNickname());
        params.put("sex",sex);
        params.put("head_img",AppData.getInstance().getToken().getPhotoId());
        params.put("id",AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateMember,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                zhToast.showToast(WebUtils.getWebMsg(webResponse));
            }
        });
    }

    @Override
    public void updateMemberName(String name) {


        HashMap<String,Object> params = new HashMap<>();
        params.put("nickname",name);
        params.put("sex",AppData.getInstance().getToken().getSex());
        params.put("head_img",AppData.getInstance().getToken().getPhotoId());
        params.put("id",AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateMember,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                zhToast.showToast(WebUtils.getWebMsg(webResponse));
            }
        });
    }
}
