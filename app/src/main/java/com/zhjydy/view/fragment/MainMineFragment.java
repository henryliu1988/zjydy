package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy.R;
import com.zhjydy.presenter.contract.MainMineContract;
import com.zhjydy.presenter.presenterImp.MainMinePresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.BadgImage;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainMineFragment extends StatedFragment implements MainMineContract.MainMineView {

    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    BadgImage rightImg;
    @BindView(R.id.right_l_img)
    ImageView rightLImg;
    @BindView(R.id.mine_image)
    ImageView mineImage;
    @BindView(R.id.mine_status)
    TextView mineStatus;
    @BindView(R.id.mine_info_layout)
    LinearLayout mineInfoLayout;
    @BindView(R.id.account_safe_layout)
    LinearLayout accountSafeLayout;
    @BindView(R.id.mine_confirm_msg_layout)
    LinearLayout mineConfirmMsgLayout;
    @BindView(R.id.mine_history_layout)
    LinearLayout mineHistoryLayout;
    @BindView(R.id.mine_common_layout)
    LinearLayout mineCommonLayout;
    @BindView(R.id.mine_about_layout)
    LinearLayout mineAboutLayout;
    private MainMineContract.MainMinePresenter mPresenter;

    public static MainMineFragment instance() {
        MainMineFragment frag = new MainMineFragment();
        return frag;
    }

    @Override
    protected void initData() {
        centerTv.setText("个人");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_mine;

    }

    @Override
    protected void afterViewCreate() {
        new MainMinePresenterImp(this);
    }

    @Override
    public void setPresenter(MainMineContract.MainMinePresenter presenter) {
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

    @OnClick({R.id.mine_info_layout, R.id.account_safe_layout, R.id.mine_confirm_msg_layout, R.id.mine_history_layout, R.id.mine_common_layout, R.id.mine_about_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_info_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.mine_info_fragment,null,false);
                break;
            case R.id.account_safe_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.account_safe_fragment,null,false);
                break;
            case R.id.mine_confirm_msg_layout:
                loadIdentifyInfo();
                break;
            case R.id.mine_history_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.patient_case_fragment,null,false);

                break;
            case R.id.mine_common_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.common_fragment,null,false);

                break;
            case R.id.mine_about_layout:
                //ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.common_fragment,null,false);

                break;
        }
    }
    private void loadIdentifyInfo() {
        if (mPresenter != null) {
            Map<String,Object> map=  mPresenter.getIdentifyInfo(getContext());
            if (map == null) {
                return;
            }
            if (map .size() < 1) {
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.identify_new_fragment,null,false);
            } else {
                String info = JSONObject.toJSONString(map);
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.identify_info_fragment,info,false);
            }
        }
    }


}
