package com.zhjydy.presenter.presenterImp;

import com.zhjydy.model.data.DicData;
import com.zhjydy.model.entity.HosipitalPickViewData;
import com.zhjydy.model.entity.HospitalDicItem;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.entity.PickViewData;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.contract.PatientCaseEditContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class PatientCaseEditPresenterImp implements PatientCaseEditContract.Presenter {

    private PatientCaseEditContract.View mView;

    private List<HosipitalPickViewData> mHospitalList = new ArrayList<>();

    public PatientCaseEditPresenterImp(PatientCaseEditContract.View view) {
        this.mView = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        loadPatientCase();
        loadSexPickData();
        loadDistricPickData();
        loadOfficeData();
    }

    private void loadSexPickData() {
        ArrayList<PickViewData> sexData = new ArrayList<>();
        sexData.add(new PickViewData("1", "男"));
        sexData.add(new PickViewData("2", "女"));
        mView.updateSexPick(sexData);
    }

    private void loadOfficeData() {
        ArrayList<NormalPickViewData> officeData = new ArrayList<>();
        List<NormalDicItem> items = DicData.getInstance().getOffice();
        for (NormalDicItem item : items) {
            officeData.add(new NormalPickViewData(item));
        }
        mView.updateOffice(officeData);
    }

    private void loadDistricPickData() {
        DicData.getInstance().getAllDistrictForPicker().subscribe(new BaseSubscriber<Map<String, ArrayList>>() {
            @Override
            public void onNext(Map<String, ArrayList> map) {
                mView.updateDistrict(map);
            }
        });
    }

    private void loadPatientCase() {
    }

    @Override
    public void finish() {

    }


    @Override
    public void updateHospitalList(String addressId) {
        // List<HospitalDicItem> list = DicData.getInstance().getHospitalByAddress(addressId);
        List<HospitalDicItem> list = DicData.getInstance().getHospitals();
        ArrayList<HosipitalPickViewData> listPick = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (HospitalDicItem item : list) {
                listPick.add(new HosipitalPickViewData(item));
            }
        }
        mView.updateHospitalByAddress(listPick);
    }

}
