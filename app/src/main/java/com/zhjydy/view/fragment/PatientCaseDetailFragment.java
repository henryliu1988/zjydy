package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.presenter.presenterImp.PatientCaseDetailPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.MyHorizontalScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseDetailFragment extends PageImpBaseFragment implements PatientCaseDetailContract.View {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_operate)
    TextView rightOperate;
    @BindView(R.id.name_title)
    TextView nameTitle;
    @BindView(R.id.name_value)
    TextView nameValue;
    @BindView(R.id.sex_title)
    TextView sexTitle;
    @BindView(R.id.sex_value)
    TextView sexValue;
    @BindView(R.id.tel_title)
    TextView telTitle;
    @BindView(R.id.tel_value)
    TextView telValue;
    @BindView(R.id.birth_title)
    TextView birthTitle;
    @BindView(R.id.birth_value)
    TextView birthValue;
    @BindView(R.id.domain_title)
    TextView domainTitle;
    @BindView(R.id.domain_value)
    TextView domainValue;
    @BindView(R.id.hospital_title)
    TextView hospitalTitle;
    @BindView(R.id.hospital_value)
    TextView hospitalValue;
    @BindView(R.id.depart_title)
    TextView departTitle;
    @BindView(R.id.depart_value)
    TextView departValue;
    @BindView(R.id.doc_title)
    TextView docTitle;
    @BindView(R.id.doc_value)
    TextView docValue;
    @BindView(R.id.sick_title)
    TextView sickTitle;
    @BindView(R.id.sick_value)
    TextView sickValue;
    @BindView(R.id.sick_discript_title)
    TextView sickDiscriptTitle;
    @BindView(R.id.sick_discript_value)
    TextView sickDiscriptValue;
    @BindView(R.id.comment_title)
    TextView commentTitle;
    @BindView(R.id.comment_value)
    TextView commentValue;
    @BindView(R.id.image)
    MyHorizontalScrollView image;
    private PatientCaseDetailContract.Presenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_case_info;
    }

    @Override
    protected void afterViewCreate() {
        new PatientCaseDetailPresenterImp(this);
        centerTv.setText("患者病例");
        rightOperate.setText("修改");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        rightOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                gotoFragment(FragKey.patient_case_edit_fragment);
            }
        });
    }

    @Override
    public void setPresenter(PatientCaseDetailContract.Presenter presenter) {
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
}
