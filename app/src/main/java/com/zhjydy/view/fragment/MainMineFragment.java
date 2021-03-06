package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.zhjydy.R;
import com.zhjydy.model.data.UserData;
import com.zhjydy.model.entity.TokenInfo;
import com.zhjydy.model.net.BaseSubscriber;
import com.zhjydy.presenter.contract.MainMineContract;
import com.zhjydy.presenter.presenterImp.MainMinePresenterImp;
import com.zhjydy.util.ActivityUtils;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.view.avtivity.PagerImpActivity;
import com.zhjydy.view.zhview.ImageTipsView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MainMineFragment extends StatedFragment implements MainMineContract.MainMineView {

    @BindView(R.id.left_img)
    ImageView leftImg;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_img)
    ImageTipsView rightImg;
    @BindView(R.id.right_l_img)
    ImageTipsView rightLImg;
    @BindView(R.id.mine_image)
    ImageView mineImage;
    @BindView(R.id.mine_status)
    TextView mineStatus;
    @BindView(R.id.mine_info_layout)
    RelativeLayout mineInfoLayout;
    @BindView(R.id.account_safe_layout)
    RelativeLayout accountSafeLayout;
    @BindView(R.id.mine_confirm_msg_layout)
    RelativeLayout mineConfirmMsgLayout;
    @BindView(R.id.mine_history_layout)
    RelativeLayout mineHistoryLayout;
    @BindView(R.id.mine_common_layout)
    RelativeLayout mineCommonLayout;
    @BindView(R.id.mine_about_layout)
    RelativeLayout mineAboutLayout;
    @BindView(R.id.mine_confirm_msg_status)
    TextView mineConfirmMsgStatus;
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
    public void onResume() {
        super.onResume();
        initMinePhotoView();
    }

    @Override
    protected void afterViewCreate() {
        new MainMinePresenterImp(this);
        initMinePhotoView();
    }

    private void initMinePhotoView() {
        TokenInfo tokenInfo = UserData.getInstance().getToken();
        if (TextUtils.isEmpty(tokenInfo.getId())) {
            mineStatus.setText("请登录");
            return;
        }
        if (!TextUtils.isEmpty(tokenInfo.getPhotoUrl())) {
            ImageUtils.getInstance().displayFromRemoteOver(tokenInfo.getPhotoUrl(), mineImage);
        }
        if (!TextUtils.isEmpty(tokenInfo.getNickname())) {
            mineStatus.setText(tokenInfo.getNickname());
        }

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
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.mine_info_fragment, null, false);
                break;
            case R.id.account_safe_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.account_safe_fragment, null, false);
                break;
            case R.id.mine_confirm_msg_layout:
                loadIdentifyInfo();
                break;
            case R.id.mine_history_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.patient_case_fragment, null, false);

                break;
            case R.id.mine_common_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.common_fragment, null, false);

                break;
            case R.id.mine_about_layout:
                ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class,FragKey.about_app_main_fragment,null,false);

                break;
        }
    }

    @Override
    public void updateIdentiFyStatus(int status, String msg) {
        mineConfirmMsgStatus.setVisibility(View.VISIBLE);
        String text = "";
        switch (status) {
            case 1:
                text = "已认证";
                break;
            case 2:
                text = "认证审核中";
                break;
            case 3:
                text = "未上传";
                break;
            case 4:
                text = "审核未通过";
                break;
        }
        mineConfirmMsgStatus.setText(text);
    }

    @Override
    public void updateTokenInfo() {
        initMinePhotoView();
    }

    private void loadIdentifyInfo() {

        if (mPresenter != null) {
            mPresenter.getIdentifyInfo(getContext()).subscribe(new BaseSubscriber<Map<String, Object>>() {
                @Override
                public void onNext(Map<String, Object> map) {
                    if (map == null) {
                        return;
                    }
                    if (map.size() < 1) {
                        ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.identify_new_fragment, null, false);
                    } else {
                        String info = JSONObject.toJSONString(map);
                        ActivityUtils.transToFragPagerActivity(getActivity(), PagerImpActivity.class, FragKey.identify_info_fragment, info, false);
                    }
                }
            });
        }
    }


}
