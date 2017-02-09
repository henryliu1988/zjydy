package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.IdentityInfoContract;
import com.zhjydy.presenter.presenterImp.IdentityInfoPresenterImp;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.ScreenUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class IdentityInfoFragment extends PageImpBaseFragment implements IdentityInfoContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.step_upload)
    TextView stepUpload;
    @BindView(R.id.step_wait_verify)
    TextView stepWaitVerify;
    @BindView(R.id.step_wait)
    TextView stepWait;
    @BindView(R.id.step_verify)
    TextView stepVerify;
    @BindView(R.id.edit_info)
    TextView editInfo;
    @BindView(R.id.photo1)
    ImageView photo1;
    @BindView(R.id.photo2)
    ImageView photo2;
    private IdentityInfoContract.Presenter mPresenter;

    private int status = 1;
    private List<String> path = new ArrayList<>();

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            back();
            return;
        }
        String info = bundle.getString(IntentKey.FRAG_INFO);
        if (TextUtils.isEmpty(info)) {
            back();
            return;
        }
        Map<String, Object> data = Utils.parseObjectToMapString(info);
        if (data == null || data.size() < 1) {
            back();
            return;
        }
        status = Utils.toInteger(data.get("msg"));
        path = Utils.parseObjectToListString(data.get("path"));


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identity_info;
    }

    @Override
    protected void afterViewCreate() {
        new IdentityInfoPresenterImp(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleCenterTv.setText("认证信息");
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragment(FragKey.identify_new_fragment);
            }
        });
        initInfoImage();
        initStatusView();
    }


    private void initInfoImage() {
        int size = path.size();
        if (size == 1) {
            ImageUtils.getInstance().displayFromRemote(path.get(0), photo1);
        } else if (size > 1) {
            ImageUtils.getInstance().displayFromRemote(path.get(0), photo1);
            ImageUtils.getInstance().displayFromRemote(path.get(1), photo2);
        }

    }

    private void initStatusView() {
        stepUpload.setText("未上传");
        stepWaitVerify.setText("未审核");
        stepWait.setText("审核中");
        stepVerify.setText("审核\n结果");
        int strokWidth = ScreenUtils.getScreenWidth() / 100;
        ViewUtil.setOverViewDrawbleBg(stepUpload, "#CCCCCC", "#EEEEEE", strokWidth);
        ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#CCCCCC", "#EEEEEE", strokWidth);
        ViewUtil.setOverViewDrawbleBg(stepWait, "#CCCCCC", "#EEEEEE", strokWidth);
        ViewUtil.setOverViewDrawbleBg(stepVerify, "#CCCCCC", "#EEEEEE", strokWidth);
        if (status == 1) {
            stepUpload.setText("上传" + "\n" + "成功");
            stepWaitVerify.setText("等待\n审核");
            stepWait.setText("等待中");
            stepVerify.setText("审核\n通过");

            ViewUtil.setOverViewDrawbleBg(stepUpload, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWait, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepVerify, "#FFAD0E", "#FFE3B9", strokWidth);
        } else if (status == 2) {
            stepUpload.setText("上传" + "\n" + "成功");
            stepWaitVerify.setText("等待\n审核");
            stepWait.setText("等待中");
            stepVerify.setText("审核\n结果");
            ViewUtil.setOverViewDrawbleBg(stepUpload, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWait, "#FFAD0E", "#FFE3B9", strokWidth);
        } else  if (status == 3) {

        } else if (status == 4) {

            stepUpload.setText("上传" + "\n" + "成功");
            stepWaitVerify.setText("等待\n审核");
            stepWait.setText("等待中");
            stepVerify.setText("审核\n失败");
            ViewUtil.setOverViewDrawbleBg(stepUpload, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWaitVerify, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepWait, "#FFAD0E", "#FFE3B9", strokWidth);
            ViewUtil.setOverViewDrawbleBg(stepVerify, "#FFAD0E", "#FFE3B9", strokWidth);

        }

    }

    @Override
    public void setPresenter(IdentityInfoContract.Presenter presenter) {
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


    @Override
    public void updateIdentifyInfo(int status, List<String> path) {
        this.status = status;
        this.path = path;
        initInfoImage();
        initStatusView();
    }
}
