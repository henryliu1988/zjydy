package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface FavExpertContract {

    interface View extends BaseView<Presenter>
    {
        void updateFilters(Map<String, Object> data);
        void updateExperts(List<Map<String, Object>> list);
    }

    interface Presenter extends BasePresenter
    {
       void cancelFavExpert(String id);
    }
}
