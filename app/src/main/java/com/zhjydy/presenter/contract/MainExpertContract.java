package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public interface MainExpertContract {

    interface MainExpertView extends BaseView<MainExpertPresenter> {
        void updateDistrict(Map<String, ArrayList> distrctData);

        void updateOffice(ArrayList<NormalPickViewData> officeData);

        void updateBusiness(ArrayList<NormalPickViewData> officeData);

        Map<String, Object> getFilterConditions();

        void updateFavExpertCount(int count);

        void updateCityAndHos(Map<String, ArrayList> data);
    }

    interface MainExpertPresenter extends BasePresenter {
        void reloadExperts();
    }


    interface TabView extends BaseView<TabPresenter> {

        Map<String, Object> getFilterConditions();

    }

    interface TabPresenter extends BasePresenter {
        void reloadExperts();
    }

}
