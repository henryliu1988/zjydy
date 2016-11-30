package com.zhjydy.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.AccountSafeContract;
import com.zhjydy.presenter.contract.IdentityInfoNewContract;
import com.zhjydy.presenter.presenterImp.AccountSafePresenterImp;
import com.zhjydy.presenter.presenterImp.IdentityInfoNewPresenterImp;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;
import com.zhjydy.view.ActivityResultView;
import com.zhjydy.view.zhview.zhToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class IdentityInfoNewFragment extends PageImpBaseFragment implements IdentityInfoNewContract.View, ActivityResultView {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.identify_1)
    ImageView identify1;
    @BindView(R.id.camera_postive)
    ImageView cameraPostive;
    @BindView(R.id.identify_2)
    ImageView identify2;
    @BindView(R.id.camera_nagtive)
    ImageView cameraNagtive;
    @BindView(R.id.submit)
    TextView submit;
    private IdentityInfoNewContract.Presenter mPresenter;


    private int imgPos = 0;

    private Map<Integer, String> mImageMap = new HashMap<>();

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_identity_new;
    }

    @Override
    protected void afterViewCreate() {
        new IdentityInfoNewPresenterImp(this);
        addOnActivityResultView(this);
        titleCenterTv.setText("认证信息");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        identify1.setVisibility(View.GONE);
        identify2.setVisibility(View.GONE);
        cameraPostive.setVisibility(View.VISIBLE);
        cameraNagtive.setVisibility(View.VISIBLE);
        cameraPostive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPos = 0;
                selectImg();
            }
        });
        cameraNagtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPos = 1;
                selectImg();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageMap.size() < 2){
                    zhToast.showToast("请检查身份证正面和背面是否都已经上传");
                    return;
                }
                mPresenter.submitIdentifymsg(mImageMap);
            }
        });
    }

    @Override
    public void onSubmitSuccess() {
        zhToast.showToast("上传认证信息成功");
        back();
    }

    @Override
    public void setPresenter(IdentityInfoNewContract.Presenter presenter) {
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


    private void updateImageMap(String path) {
        switch (imgPos) {
            case 0:
                identify1.setVisibility(View.VISIBLE);
                cameraPostive.setVisibility(View.GONE);
                ImageUtils.getInstance().displayFromSDCard(path, identify1);
                mImageMap.put(0, path);
                break;
            case 1:
                identify2.setVisibility(View.VISIBLE);
                cameraNagtive.setVisibility(View.GONE);
                ImageUtils.getInstance().displayFromSDCard(path, identify2);
                mImageMap.put(1, path);
                break;

        }
    }

    @Override
    public void onActivityResult1(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册选择
            case SELECT_PICTURE:
                Uri uri = data.getData();
                String path = Utils.getPath(uri);
                Map<String, Object> item = new HashMap<>();
                item.put(ViewKey.FILE_KEY_TYPE, ViewKey.TYPE_FILE_PATH);
                item.put(ViewKey.FILE_KEY_URL, path);
                updateImageMap(path);
                break;
            //拍照添加图片
            case SELECT_CAMER:
                if (mCameraPath != null) {
                    String p = mCameraPath.toString();
                    Map<String, Object> m = new HashMap<>();
                    Map<String, Object> item1 = new HashMap<>();
                    item1.put(ViewKey.FILE_KEY_TYPE, ViewKey.TYPE_FILE_PATH);
                    item1.put(ViewKey.FILE_KEY_URL, p);
                    updateImageMap(p);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        toGetCameraImage();
    }
}
