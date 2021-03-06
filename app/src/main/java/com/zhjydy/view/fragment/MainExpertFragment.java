package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shizhefei.mvc.MVCHelper;
import com.zhjydy.R;
import com.zhjydy.model.entity.DistricPickViewData;
import com.zhjydy.model.entity.District;
import com.zhjydy.model.entity.HosipitalPickViewData;
import com.zhjydy.model.entity.HospitalDicItem;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.model.pageload.PageLoadDataSource;
import com.zhjydy.presenter.contract.MainExpertContract;
import com.zhjydy.presenter.presenterImp.MainExpertPresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.adapter.MainExpertListAdapter;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.ImageTipsView;
import com.zhjydy.view.zhview.MapTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainExpertFragment extends StatedFragment implements MainExpertContract.MainExpertView {

    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.title_search_img)
    ImageView titleSearchImg;
    @BindView(R.id.title_search_text)
    TextView titleSearchText;
    @BindView(R.id.title_search_ly)
    LinearLayout titleSearchLy;
    @BindView(R.id.filter_dis_tv)
    MapTextView filterDisTv;
    @BindView(R.id.filter_office_tv)
    MapTextView filterOfficeTv;
    @BindView(R.id.filter_business_tv)
    MapTextView filterBusinessTv;
    @BindView(R.id.m_list)
    PullToRefreshListView mList;


    // private OptionsPickerView mDistricePicker;
    private OptionsPickerView mBusinessPicker;
    private OptionsPickerView mOfficePicker;
    private OptionsPickerView mCityAndHosPicker;

    // private ArrayList<DistricPickViewData> mProPickViewData = new ArrayList<>();
    // private ArrayList<ArrayList<DistricPickViewData>> mCityPickViewData = new ArrayList<>();
    // private ArrayList<ArrayList<ArrayList<DistricPickViewData>>> mQuPickViewData = new ArrayList<>();
    private ArrayList<NormalPickViewData> mDepartPickViewData = new ArrayList<>();
    private ArrayList<NormalPickViewData> mBusinessPickViewData = new ArrayList<>();


    private ArrayList<DistricPickViewData> mCityPickViewData1 = new ArrayList<>();
    private ArrayList<ArrayList<HosipitalPickViewData>> mHospiatalPickViewData = new ArrayList<>();

    protected MainExpertContract.MainExpertPresenter mPresenter;
    protected MainExpertListAdapter mExpertListAdapter;

    private MVCHelper<List<Map<String, Object>>> mvcPiHelper;
    private PageLoadDataSource mDataSource;

    public static MainExpertFragment instance() {
        MainExpertFragment frag = new MainExpertFragment();
        return frag;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_doc;
    }

    @Override
    protected void afterViewCreate() {
        initExpertList();
        titleSearchText.setText("搜索专家");
        rightLImg.setImageResource(R.mipmap.shoucang);
        rightImg.setImageResource(R.mipmap.title_msg);
        //   mDistricePicker = new OptionsPickerView<DistricPickViewData>(getContext());
        mBusinessPicker = new OptionsPickerView<NormalDicItem>(getContext());
        mOfficePicker = new OptionsPickerView<NormalDicItem>(getContext());
        mCityAndHosPicker = new OptionsPickerView(getContext());
        new MainExpertPresenterImp(this, mList, mExpertListAdapter);
    }


    private void initExpertList() {
        mExpertListAdapter = new MainExpertListAdapter(getContext(), new ArrayList<Map<String, Object>>());
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> info = (Map<String, Object>) adapterView.getAdapter().getItem(i);
                if (info != null && !TextUtils.isEmpty(Utils.toString(info.get("memberid")))) {
                    ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.detail_expert_fragment, Utils.toString(info.get("memberid")), false);
                }
            }
        });

    }

    public void refreshViewWidthCondition(Map<String, NormalDicItem> condition) {
        if (condition == null || condition.size() < 1) {
            return;
        }
        filterDisTv.setMap("", "全部地区");
        filterBusinessTv.setMap("", "职称");
        filterOfficeTv.setMap("", "科室");

        if (condition.containsKey("address")) {
            filterDisTv.setMap(condition.get("address").getId(), condition.get("address").getName());
        }
        if (condition.containsKey("office")) {
            filterOfficeTv.setMap(condition.get("office").getId(), condition.get("office").getName());
        }
        if (condition.containsKey("business")) {
            filterBusinessTv.setMap(condition.get("business").getId(), condition.get("business").getName());
        }
        mPresenter.reloadExperts();
    }

    @Override
    public void setPresenter(MainExpertContract.MainExpertPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void refreshView() {

    }

    public void updateUnReadMsgCount(int count) {
        String text = "";
        if (count != 0) {
            text = count + "";
        }
        rightImg.setTipText(text);

    }

    @Override
    public void updateFavExpertCount(int count) {
        rightLImg.setTipText(count + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void updateCityAndHos(Map<String, ArrayList> data) {
        mCityPickViewData1 = (ArrayList<DistricPickViewData>) data.get("city");
        mHospiatalPickViewData = (ArrayList<ArrayList<HosipitalPickViewData>>) data.get("hospital");
        mCityAndHosPicker.setPicker(mCityPickViewData1, mHospiatalPickViewData, true);
        initCityAndHospicker();
    }

    private void initCityAndHospicker() {
        mCityAndHosPicker.setCyclic(false);
        mCityAndHosPicker.setCancelable(true);
        mCityAndHosPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                District city = mCityPickViewData1.get(options1).getDistrict();
                HospitalDicItem hos = mHospiatalPickViewData.get(options1).get(option2).getHospitalDicItem();
                filterDisTv.clear();
                if (hos != null && !TextUtils.isEmpty(hos.getId())) {
                    filterDisTv.setMap(hos.getId(), hos.getHospital());
                    filterDisTv.setMoreMap(city.getId(),city.getName());
                } else if (city != null && !TextUtils.isEmpty(city.getId())) {
                    filterDisTv.setMoreMap(city.getId(), city.getName());
                } else {
                    filterDisTv.setMoreMap("", "全部地区");
                }
                mPresenter.reloadExperts();
            }
        });

    }

    @Override
    public void updateDistrict(Map<String, ArrayList> distrctData) {
        /*
        mProPickViewData = (ArrayList<DistricPickViewData>) distrctData.get("pro");
        mCityPickViewData = (ArrayList<ArrayList<DistricPickViewData>>) distrctData.get("city");
        mQuPickViewData = (ArrayList<ArrayList<ArrayList<DistricPickViewData>>>) distrctData.get("qu");
        mDistricePicker.setPicker(mProPickViewData, mCityPickViewData, mQuPickViewData, true);
        initDistricePicker();*/
    }

    private void initDistricePicker() {
        /*
        mDistricePicker.setCancelable(true);
        mDistricePicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                District pro = mProPickViewData.get(options1).getDistrict();
                District city = mCityPickViewData.get(options1).get(option2).getDistrict();
                District qu = mQuPickViewData.get(options1).get(option2).get(options3).getDistrict();
                if (qu != null && !TextUtils.isEmpty(qu.getId())) {
                    filterDisTv.setMap(qu.getId(), qu.getName());
                } else if (city != null && !TextUtils.isEmpty(city.getId())) {
                    filterDisTv.setMap(city.getId(), city.getName());
                } else if (pro != null) {
                    filterDisTv.setMap(pro.getId(), pro.getName());
                }
                mPresenter.reloadExperts();
            }
        });
        */
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
                if (TextUtils.isEmpty(officeId)) {
                    filterOfficeTv.setMap("", "科室");
                } else {
                    filterOfficeTv.setMap(officeId, officeName);
                }
                mPresenter.reloadExperts();
            }
        });

    }

    @Override
    public void updateBusiness(ArrayList<NormalPickViewData> officeData) {
        mBusinessPickViewData = officeData;
        mBusinessPicker.setPicker(mBusinessPickViewData);
        mBusinessPicker.setCyclic(false);
        mBusinessPicker.setSelectOptions(0);
        mBusinessPicker.setCancelable(true);
        mBusinessPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String busName = mBusinessPickViewData.get(options1).getmItem().getName();
                String busId = mBusinessPickViewData.get(options1).getmItem().getId();
                if (TextUtils.isEmpty(busId)) {
                    filterBusinessTv.setMap("", "职称");
                } else {
                    filterBusinessTv.setMap(busId, busName);
                }
                mPresenter.reloadExperts();
            }
        });

    }

    @Override
    public Map<String, Object> getFilterConditions() {
        String hosId = filterDisTv.getTextId();
        String cityId= filterDisTv.getMoreTextId();
        String officeId = filterOfficeTv.getTextId();
        String businessId = filterBusinessTv.getTextId();
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(hosId)) {
            params.put("hospital", hosId);
        }
        if (!TextUtils.isEmpty(cityId)) {
            params.put("address", cityId);
        }
        if (!TextUtils.isEmpty(officeId)) {
            params.put("office", officeId);
        }
        if (!TextUtils.isEmpty(businessId)) {
            params.put("business", businessId);
        }
        return params;
    }


    @OnClick({R.id.title_search_ly, R.id.right_img, R.id.right_l_img, R.id.filter_dis_tv, R.id.filter_business_tv, R.id.filter_office_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_search_ly:
                Bundle bundle = new Bundle();
                bundle.putInt("key", FragKey.search_expert_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundle, false);
                break;
            case R.id.right_l_img:
                Bundle bundleFave = new Bundle();
                bundleFave.putInt("key", FragKey.fave_expert_list_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleFave, false);
                break;
            case R.id.right_img:
                Bundle bundleMsg = new Bundle();
                bundleMsg.putInt("key", FragKey.msg_all_fragment);
                ActivityUtils.transActivity(getActivity(), PagerImpActivity.class, bundleMsg, false);
                break;
            case R.id.filter_business_tv:
                if (mBusinessPickViewData.size() > 0) {
                    mBusinessPicker.show();
                }
                break;
            case R.id.filter_dis_tv:
                if (mCityPickViewData1.size() > 0) {
                    mCityAndHosPicker.show();
                }
                break;
            case R.id.filter_office_tv:
                if (mDepartPickViewData.size() > 0) {
                    mOfficePicker.show();
                }

        }
    }
}
