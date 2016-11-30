package com.zhjydy.presenter.contract;

import com.zhjydy.model.entity.HosipitalPickViewData;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.entity.PickViewData;
import com.zhjydy.presenter.BasePresenter;
import com.zhjydy.presenter.BaseView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public interface PatientCaseEditContract {

    interface View extends BaseView<Presenter>
    {
        void updateSexPick(ArrayList<PickViewData> sexData);
        void updateDistrict(Map<String,ArrayList> distrctData);
        void updateHospitalByAddress(ArrayList<HosipitalPickViewData> hosData);
        void updateOffice(ArrayList<NormalPickViewData> officeData);
    }

    interface Presenter extends BasePresenter
    {
        void updateHospitalList(String addressId);
    }
}
