package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainHomeContract {

    interface MainHomeView extends BaseView<MainHomePresenter> {

        void updateBanner(List<Map<String,Object>> images);

        void updateMsg(List<String> news);

        void updateExpert(List<Map<String, Object>> maps);

        void updateInfo(List<Map<String, Object>> infos);

        void updateOfficeList(List<NormalDicItem> items);
    }

    interface MainHomePresenter extends BasePresenter {
    }
}
