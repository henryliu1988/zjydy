package com.zhjydy.presenter.presenterImp;

import android.text.TextUtils;

import com.zhjydy.model.data.UserData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.RefreshWithKey;
import com.zhjydy.presenter.contract.FavExpertContract;
import com.zhjydy.util.Utils;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class FaveExpertPresenterImp implements FavExpertContract.Presenter,RefreshWithKey {

    private FavExpertContract.View mView;

    public FaveExpertPresenterImp(FavExpertContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        RefreshManager.getInstance().addNewListener(RefreshKey.KEY_FAV_EXPERT,this);
        start();
    }

    @Override
    public void start() {
        loadExperts();
    }

    private void loadExperts() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("collect", UserData.getInstance().getToken().getCollectExperts());
        WebCall.getInstance().call(WebKey.func_getCollectExpert, params).map(new Func1<WebResponse, List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> call(WebResponse webResponse) {
                String data = webResponse.getData();
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(data);
                return list;
            }
        }).subscribe(new BaseSubscriber<List<Map<String, Object>>>() {
            @Override
            public void onNext(List<Map<String, Object>> list) {
                mView.updateExperts(list);
            }
        });
    }

    @Override
    public void finish() {
    }

    @Override
    public void cancelFavExpert(final String id) {
        HashMap<String, Object> params = new HashMap();
        List<String> collect = new ArrayList<>();
        collect.addAll(UserData.getInstance().getToken().getCollectExpertList());
        if (collect.contains(id)) {
            collect.remove(id);
        }
        params.put("userid", UserData.getInstance().getToken().getId());
        params.put("collectexpert", Utils.strListToString(collect));
        WebCall.getInstance().call(WebKey.func_cancelCollectExpert, params).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "") {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                if (status) {
                    ArrayList<String> collect = new ArrayList<String>();
                    collect.addAll(UserData.getInstance().getToken().getCollectExpertList());
                    if (collect.contains(id)) {
                        collect.remove(id);
                    }
                    UserData.getInstance().getToken().setCollectExpertAsList(collect);
                    loadExperts();
                } else {
                    zhToast.showToast("取消收藏失败");
                }
            }
        });
    }

    @Override
    public void searchFavExpert(String condition) {
        if (TextUtils.isEmpty(condition)) {
            loadExperts();
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("expert", condition);
        param.put("collect", UserData.getInstance().getToken().getCollectExperts());
        WebCall.getInstance().call(WebKey.func_searchCollectExpert, param).subscribe(new BaseSubscriber<WebResponse>(mView.getContext(), "") {
            @Override
            public void onNext(WebResponse webResponse) {
                List<Map<String, Object>> list = Utils.parseObjectToListMapString(webResponse.getData());
                mView.updateExperts(list);
            }
        });
    }

    @Override
    public void onRefreshWithKey(int key) {
        loadExperts();
    }
}
