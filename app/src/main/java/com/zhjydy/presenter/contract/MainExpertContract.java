package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.DocTorInfo;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainExpertContract {

    interface MainExpertView extends BaseView<MainExpertPresenter>
    {
        void updateFilters(Map<String,Object> data);
        void updateExperts(List<DocTorInfo> list);
    }

    interface MainExpertPresenter extends BasePresenter
    {
    }
}
