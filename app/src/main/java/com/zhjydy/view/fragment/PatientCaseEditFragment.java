package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.PatientCaseEditContract;
import com.zhjydy.presenter.presenterImp.PatientCaseEditPresenterImp;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;

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
    @BindView(R.id.name_flag)
    ImageView nameFlag;
    @BindView(R.id.sex_title)
    TextView sexTitle;
    @BindView(R.id.sex_value)
    TextView sexValue;
    @BindView(R.id.sex_flag)
    ImageView sexFlag;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    EditText telValue;
    @BindView(R.id.tel_flag)
    ImageView telFlag;
    @BindView(R.id.birth_title)
    TextView birthTitle;
    @BindView(R.id.birth_value)
    TextView birthValue;
    @BindView(R.id.birth_flag)
    ImageView birthFlag;
    @BindView(R.id.domain_title)
    TextView domainTitle;
    @BindView(R.id.domain_value)
    TextView domainValue;
    @BindView(R.id.domain_flag)
    ImageView domainFlag;
    @BindView(R.id.hospital_title)
    TextView hospitalTitle;
    @BindView(R.id.hospital_value)
    TextView hospitalValue;
    @BindView(R.id.hospital_flag)
    ImageView hospitalFlag;
    @BindView(R.id.depart_title)
    TextView departTitle;
    @BindView(R.id.depart_value)
    TextView departValue;
    @BindView(R.id.depart_flag)
    ImageView departFlag;
    @BindView(R.id.doc_title)
    TextView docTitle;
    @BindView(R.id.doc_value)
    EditText docValue;
    @BindView(R.id.doc_flag)
    ImageView docFlag;
    @BindView(R.id.sick_title)
    TextView sickTitle;
    @BindView(R.id.sick_value)
    EditText sickValue;
    @BindView(R.id.sick_flag)
    ImageView sickFlag;
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

    private Map<String,Object> mEditList;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_new_edit;
    }

    @Override
    protected void afterViewCreate() {
        String info =  getArguments().getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(info)) {
            titleCenterTv.setText("添加患者");
        }
        else{
            titleCenterTv.setText("修改患者信息");
            mEditList= Utils.parseObjectToMapString(info);
        }
        new PatientCaseEditPresenterImp(this);

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
        String name = nameValue.getText().toString();
        String sex = sexValue.getText().toString();
        String phone = telValue.getText().toString();
      /*
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(phone)) {
            return;
        }
*/
        Bundle bundle = new Bundle();
        gotoFragment(FragKey.patient_case_edit_attach_fragment,bundle);
    }
    @OnClick({R.id.title_back, R.id.sex_value, R.id.sex_flag, R.id.birth_value, R.id.birth_flag, R.id.domain_value, R.id.domain_flag, R.id.hospital_value, R.id.hospital_flag, R.id.depart_value, R.id.depart_flag, R.id.next_station})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.sex_value:
            case R.id.sex_flag:
                break;
            case R.id.birth_value:
            case R.id.birth_flag:
                break;
            case R.id.domain_value:
            case R.id.domain_flag:
                break;
            case R.id.hospital_value:
            case R.id.hospital_flag:
                break;
            case R.id.depart_value:
            case R.id.depart_flag:
                break;
            case R.id.next_station:
                checkMsg();
                break;
        }
    }
}
