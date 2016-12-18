package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.FileUpLoad;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.contract.IdentityInfoNewContract;
import com.zhjydy.util.Utils;
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
public class IdentityInfoNewPresenterImp implements IdentityInfoNewContract.Presenter {

    private IdentityInfoNewContract.View mView;

    public IdentityInfoNewPresenterImp(IdentityInfoNewContract.View view) {
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
    public void submitIdentifymsg(Map<Integer, String> urls) {
        HashMap<String, Object> params = new HashMap();
        Map<String, Object> img0 = new HashMap<>();
        img0.put(ViewKey.FILE_KEY_TYPE, ViewKey.TYPE_FILE_PATH);
        img0.put(ViewKey.FILE_KEY_URL, urls.get(0));
        Map<String, Object> img1 = new HashMap<>();
        img1.put(ViewKey.FILE_KEY_TYPE, ViewKey.TYPE_FILE_PATH);
        img1.put(ViewKey.FILE_KEY_URL, urls.get(1));
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(img0);
        list.add(img1);
        FileUpLoad.uploadFiles(list).flatMap(new Func1<List<Map<String, Object>>, Observable<WebResponse>>() {
            @Override
            public Observable<WebResponse> call(List<Map<String, Object>> maps) {
                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put("id", UserData.getInstance().getToken().getId());
                params.put("idcard", Utils.getListStrsAdd(maps, "id"));
                return WebCall.getInstance().call(WebKey.func_updateHuan, params);
            }
        }).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "正在提交认证信息") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                mView.onSubmitSuccess(status);
                if (status) {
                    RefreshManager.getInstance().refreshData(RefreshKey.IDENTIFY_MSG_UPDATE);
                } else {
                    zhToast.showToast("上传失败");
                }
            }
        });
    }
}
