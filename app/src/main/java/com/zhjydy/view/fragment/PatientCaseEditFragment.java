package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.zhjydy.R;
import com.zhjydy.model.data.DicData;
import com.zhjydy.model.data.UserData;
import com.zhjydy.model.entity.DistricPickViewData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.HosipitalPickViewData;
import com.zhjydy.model.entity.HospitalDicItem;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.entity.PickViewData;
import com.zhjydy.presenter.contract.PatientCaseEditContract;
import com.zhjydy.presenter.presenterImp.PatientCaseEditPresenterImp;
import com.zhjydy.util.DateUtil;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.MapTextView;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseEditFragment extends PageImpBaseFragment implements PatientCaseEditContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.name_title)
    TextView nameTitle;
    @BindView(R.id.name_value)
    EditText nameValue;
    @BindView(R.id.sex_title)
    TextView sexTitle;
    @BindView(R.id.sex_value)
    MapTextView sexValue;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    EditText telValue;
    @BindView(R.id.birth_title)
    TextView birthTitle;
    @BindView(R.id.birth_value)
    TextView birthValue;
    @BindView(R.id.domain_title)
    TextView domainTitle;
    @BindView(R.id.domain_value)
    MapTextView domainValue;
    @BindView(R.id.hospital_title)
    TextView hospitalTitle;
    @BindView(R.id.hospital_value)
    EditText hospitalValue;
    @BindView(R.id.depart_title)
    TextView departTitle;
    @BindView(R.id.depart_value)
    MapTextView departValue;
    @BindView(R.id.doc_title)
    TextView docTitle;
    @BindView(R.id.doc_value)
    EditText docValue;
    @BindView(R.id.sick_title)
    TextView sickTitle;
    @BindView(R.id.sick_value)
    EditText sickValue;
    @BindView(R.id.sick_discript_title)
    TextView sickDiscriptTitle;
    @BindView(R.id.sick_discript_value)
    EditText sickDiscriptValue;
    @BindView(R.id.comment_title)
    TextView commentTitle;
    @BindView(R.id.comment_value)
    EditText commentValue;
    @BindView(R.id.next_station)
    TextView nextStation;
    private PatientCaseEditContract.Presenter mPresenter;
    private int editType = 0;
    private Map<String, Object> mEditList;


    private TimePickerView mDayPicker;
    private OptionsPickerView<PickViewData> mSexPicker;
    private OptionsPickerView mDistricePicker;
    private OptionsPickerView mHospitalPicker;
    private OptionsPickerView mOfficePicker;


    private ArrayList<DistricPickViewData> mProPickViewData = new ArrayList<>();

    private ArrayList<PickViewData> mSexPickViewData = new ArrayList<>();
    private ArrayList<ArrayList<DistricPickViewData>> mCityPickViewData = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<DistricPickViewData>>> mQuPickViewData = new ArrayList<>();

    private ArrayList<HosipitalPickViewData> mHospitalPickViewData = new ArrayList<>();
    private ArrayList<NormalPickViewData> mDepartPickViewData = new ArrayList<>();

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_new_edit;
    }

    @Override
    protected void afterViewCreate() {
        Bundle bundle = getArguments();
        if (bundle == null || TextUtils.isEmpty(bundle.getString(IntentKey.FRAG_INFO))) {
            titleCenterTv.setText("添加患者");
            editType = 0;
        } else {
            String info = bundle.getString(IntentKey.FRAG_INFO);
            titleCenterTv.setText("修改患者信息");
            mEditList = Utils.parseObjectToMapString(info);
            editType = 1;
        }
        mSexPicker = new OptionsPickerView<PickViewData>(getContext());
        mDistricePicker = new OptionsPickerView<DistricPickViewData>(getContext());
        mHospitalPicker = new OptionsPickerView<HosipitalPickViewData>(getContext());
        mOfficePicker = new OptionsPickerView<NormalDicItem>(getContext());
        initDatePickView();
        new PatientCaseEditPresenterImp(this);

        initView();

    }

    private void initView() {
        if (mEditList == null || mEditList.size() < 1) {
            return;
        }
        String realName = Utils.toString(mEditList.get("realname"));
        String sexName = DicData.getInstance().getSexById(Utils.toString(mEditList.get("sex"))).getName();
        String sexId = DicData.getInstance().getSexById(Utils.toString(mEditList.get("sex"))).getId();
        String phoneNum = Utils.toString(mEditList.get("mobile"));
        String doctor = Utils.toString(mEditList.get("doctor"));
        String comment = Utils.toString(mEditList.get("comment"));
        String descript = Utils.toString(mEditList.get("condition"));
        String name = Utils.toString(mEditList.get("name"));
        long ageLong = Utils.toLong(mEditList.get("age"));
        String birth = "";
        if (ageLong > 0) {
            birth = DateUtil.dateToString(DateUtil.getDateBySeconds(ageLong), DateUtil.LONG_DATE_FORMAT);
        }
        String distrcit = "";
        String hospital = "";
        String depart = "";
        String disCode = Utils.toString(mEditList.get("address"));
        String hosCode = Utils.toString(mEditList.get("hospital"));
        String depCode = Utils.toString(mEditList.get("office"));

        if (!TextUtils.isEmpty(disCode)) {
            List<District> list = DicData.getInstance().getDistrictById(disCode);
            if (list.size() > 0) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    distrcit += list.get(i).getName() + " ";
                }
            }
            mPresenter.updateHospitalList(disCode);
        }
        /*
        if (!TextUtils.isEmpty(hosCode)) {
            hospital = DicData.getInstance().getHospitalById(hosCode).getHospital();
        }
        */
        if (!TextUtils.isEmpty(depCode)) {
            depart = DicData.getInstance().getOfficeById(depCode).getName();
        }

        nameValue.setText(realName);
        sexValue.setMap(sexId, sexName);
        telValue.setText(phoneNum);
        birthValue.setText(birth);
        domainValue.setMap(disCode, distrcit);
      //  hospitalValue.setMap(hosCode, hospital);
        hospitalValue.setText(hosCode);
        departValue.setMap(depCode, depart);
        docValue.setText(doctor);

        sickValue.setText(name);
        sickDiscriptValue.setText(descript);
        commentValue.setText(comment);

    }

    private void initDatePickView() {
        mDayPicker = new TimePickerView(getContext(), TimePickerView.Type.YEAR_MONTH_DAY);
        mDayPicker.setTime(new Date());
        mDayPicker.setCyclic(false);
        mDayPicker.setCancelable(true);
        mDayPicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                String dateStr = DateUtil.dateToString(date, DateUtil.LONG_DATE_FORMAT);
                birthValue.setText(dateStr);
            }
        });
    }

    private void initSexPickView() {
        mSexPicker.setCyclic(false);
        mSexPicker.setSelectOptions(0);
        mSexPicker.setCancelable(true);
        mSexPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String sexName = mSexPickViewData.get(options1).getName();
                String sexId = mSexPickViewData.get(options1).getId();

                sexValue.setMap(sexId, sexName);
            }
        });
    }

    private void initDistricePicker() {
        mDistricePicker.setCyclic(false);
        mDistricePicker.setCancelable(true);
        mDistricePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                District pro = null;
                District city = null;
                District qu = null;
                if (mProPickViewData.size() > options1) {
                    pro = mProPickViewData.get(options1).getDistrict();
                }
                if (mCityPickViewData.size() > options1 && mCityPickViewData.get(options1).size() > option2) {
                    city = mCityPickViewData.get(options1).get(option2).getDistrict();
                }
                if (mQuPickViewData.size() > options1 && mQuPickViewData.get(options1).size() > option2 && mQuPickViewData.get(options1).get(option2).size() > options3) {
                    qu = mQuPickViewData.get(options1).get(option2).get(options3).getDistrict();
                }
                if (pro == null) {
                    return;
                } else if (city == null) {
                    domainValue.setMap(pro.getId(), pro.getName());
                } else if (qu == null) {
                    domainValue.setMap(city.getId(), pro.getName() + " " + city.getName());
                } else {
                    domainValue.setMap(qu.getId(), pro.getName() + " " + city.getName() + " " + qu.getName());
                }
                mHospitalPickViewData.clear();
                mPresenter.updateHospitalList(domainValue.getTextId());
            }
        });
    }

    @Override
    public void updateSexPick(ArrayList<PickViewData> sexData) {
        mSexPickViewData = sexData;
        mSexPicker.setPicker(sexData);
        initSexPickView();
    }

    @Override
    public void updateDistrict(Map<String, ArrayList> distrctData) {
        mProPickViewData = (ArrayList<DistricPickViewData>) distrctData.get("pro");
        mCityPickViewData = (ArrayList<ArrayList<DistricPickViewData>>) distrctData.get("city");
        mQuPickViewData = (ArrayList<ArrayList<ArrayList<DistricPickViewData>>>) distrctData.get("qu");
        mDistricePicker.setPicker(mProPickViewData, mCityPickViewData, mQuPickViewData, true);
        initDistricePicker();
    }

    @Override
    public void updateOffice(ArrayList<NormalPickViewData> officeData) {
        mDepartPickViewData = officeData;
        mOfficePicker.setPicker(mDepartPickViewData);
        mOfficePicker.setCyclic(false);
        mOfficePicker.setSelectOptions(0);
        mOfficePicker.setCancelable(true);
        mOfficePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String officeName = mDepartPickViewData.get(options1).getmItem().getName();
                String officeId = mDepartPickViewData.get(options1).getmItem().getId();
                departValue.setMap(officeId, officeName);
            }
        });

    }

    @Override
    public void updateHospitalByAddress(final ArrayList<HosipitalPickViewData> hosData) {
        if (hosData == null || hosData.size() < 1) {
            return;
        }
        mHospitalPickViewData = hosData;
        mHospitalPicker.setPicker(hosData);
        mHospitalPicker.setCyclic(false);
        mHospitalPicker.setCancelable(true);
        mHospitalPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                HospitalDicItem hos = hosData.get(options1).getHospitalDicItem();
               // hospitalValue.setMap(hos.getId(), hos.getHospital());
            }
        });
    }

    @Override
    public void setPresenter(PatientCaseEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void checkMsg() {
        String reacName = nameValue.getText().toString();
        String sex = sexValue.getTextId();
        String phone = telValue.getText().toString();
        String date = birthValue.getText().toString();
        String districtId = domainValue.getTextId();
        String hosId = hospitalValue.getText().toString();
        String officeId = departValue.getTextId();
        String docName = docValue.getText().toString();
        String patientName = sickValue.getText().toString();
        String discript = sickDiscriptValue.getText().toString();
        String comment = commentValue.getText().toString();
        String name = sickValue.getText().toString();

        if (TextUtils.isEmpty(reacName)) {
            zhToast.showToast("患者姓名不能为空");
            return;

        }
        if (TextUtils.isEmpty(sex)) {
            zhToast.showToast("请选择患者性别");
            return;

        }
        if (TextUtils.isEmpty(reacName)) {
            zhToast.showToast("患者姓名不能为空");
            return;

        }
        if (TextUtils.isEmpty(phone)) {
            zhToast.showToast("联系方式不能为空");
            return;

        }
        if (TextUtils.isEmpty(districtId)) {
            zhToast.showToast("请选择患者所在地区");
            return;

        }
        if (TextUtils.isEmpty(hosId)) {
            zhToast.showToast("请选择患者所在医院");
            return;

        }
        /*
        if (TextUtils.isEmpty(officeId)) {
            zhToast.showToast("请选择患者所在科室");
        }
        */


        Map<String, Object> params = new HashMap<>();
        if (mEditList != null && mEditList.size() > 0) {
            params.putAll(mEditList);
        }
        params.put("realname", reacName);
        params.put("sex", sex);
        params.put("mobile", phone);
        params.put("age", DateUtil.getDiffOfBaseTime(date, DateUtil.LONG_DATE_FORMAT));
        params.put("address", districtId);
        params.put("hospital", hosId);
        params.put("address", districtId);
        params.put("office", officeId);
        params.put("doctor", docName);
        params.put("name", patientName);
        params.put("comment", comment);
        params.put("condition", discript);
        params.put("memberid", UserData.getInstance().getToken().getId());
      /*
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(phone)) {
            return;
        }
*/
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.FRAG_INFO, JSONObject.toJSONString(params));
        bundle.putInt("type", editType);

        gotoFragment(FragKey.patient_case_edit_attach_fragment, bundle);
    }

    @OnClick({R.id.title_back, R.id.sex_value, R.id.birth_value, R.id.domain_value, R.id.hospital_value, R.id.depart_value, R.id.next_station})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.sex_value:
                if (mSexPickViewData.size() > 0) {
                    mSexPicker.show();
                }
                break;
            case R.id.birth_value:
                mDayPicker.show();
                break;
            case R.id.domain_value:
                if (mProPickViewData.size() > 0) {
                    mDistricePicker.show();
                }
                break;
            case R.id.hospital_value:
                if (mHospitalPickViewData.size() > 0) {
                    mHospitalPicker.show();
                }
                break;
            case R.id.depart_value:
                if (mDepartPickViewData.size() > 0) {
                    mOfficePicker.show();
                }
                break;
            case R.id.next_station:
                checkMsg();
                break;
        }
    }


}
