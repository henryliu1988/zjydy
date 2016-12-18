package com.zhjydy.presenter.contract;

import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface FavExpertContract {

    interface View extends BaseView<Presenter> {
        void updateExperts(List<Map<String, Object>> list);
    }

    interface Presenter extends BasePresenter {
        void cancelFavExpert(String id);

        void searchFavExpert(String condition);
    }
}
