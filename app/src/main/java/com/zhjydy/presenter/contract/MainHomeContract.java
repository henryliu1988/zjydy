package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.model.entity.Infomation;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainHomeContract {

    interface MainHomeView extends BaseView<MainHomePresenter>
    {

        void updateBanner(List<String> images);
        void updateMsg(Map<String,Object> msg);
        void updateMsg(List<DocTorInfo> experts);
        void updateInfo(List<Infomation> infos);
    }

    interface MainHomePresenter extends BasePresenter
    {
    }
}
