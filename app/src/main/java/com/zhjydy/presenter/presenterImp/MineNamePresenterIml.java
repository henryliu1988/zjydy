package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.AppData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.model.net.WebCall;
import com.zhjydy.model.net.WebKey;
import com.zhjydy.model.net.WebResponse;
import com.zhjydy.model.net.WebUtils;
import com.zhjydy.presenter.RefreshKey;
import com.zhjydy.presenter.RefreshManager;
import com.zhjydy.presenter.contract.MineNameChangContract;
import com.zhjydy.view.zhview.zhToast;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
public class MineNamePresenterIml implements MineNameChangContract.Presenter {

    private MineNameChangContract.View mView;
    public MineNamePresenterIml(MineNameChangContract.View view) {
        mView =view;
        mView.setPresenter(this);
        start();
   }
    @Override
    public void submitChangeConfirm(final String name) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("nickname", name);
        params.put("sex",AppData.getInstance().getToken().getSex());
        params.put("head_img",AppData.getInstance().getToken().getPhotoId());
        params.put("id",AppData.getInstance().getToken().getId());
        WebCall.getInstance().call(WebKey.func_updateMember,params).subscribe(new BaseSubscriber<WebResponse>() {
            @Override
            public void onNext(WebResponse webResponse) {
                boolean status = WebUtils.getWebStatus(webResponse);
                zhToast.showToast(WebUtils.getWebMsg(webResponse));
                String msg = status ? "修改成功":"修改失败";
                if (status) {
                    AppData.getInstance().getToken().setNickname(name);
                    RefreshManager.getInstance().refreshData(RefreshKey.TOKEN_MSG_NICK_NAME);
                }
                mView.submitResult(status,msg);
            }
        });

    }

    @Override
    public void start() {

    }

    @Override
    public void finish() {

    }
}
