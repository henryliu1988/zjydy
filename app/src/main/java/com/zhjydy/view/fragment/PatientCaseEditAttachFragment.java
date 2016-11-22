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
import com.zhjydy.presenter.contract.IdentityInfoContract;
import com.zhjydy.presenter.contract.PatientCaseDetailContract;
import com.zhjydy.presenter.contract.PatientCaseEditAttachContract;
import com.zhjydy.presenter.presenterImp.IdentityInfoPresenterImp;
import com.zhjydy.presenter.presenterImp.PatientCaseEditAttachPresenterImp;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;
import com.zhjydy.view.ActivityResultView;
import com.zhjydy.view.adapter.HorizontalScrollViewAdapter;
import com.zhjydy.view.zhview.ItemImageAddView;
import com.zhjydy.view.zhview.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseEditAttachFragment extends PageImpBaseFragment implements PatientCaseEditAttachContract.View, ActivityResultView {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.take_photo)
    ImageView takePhoto;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.horizontal_view)
    ItemImageAddView horizontalImageView;
    private List<Map<String,Object>> mImageList = new ArrayList<>();

    private PatientCaseEditAttachContract.Presenter mPresenter;

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patient_info_edit_attach;
    }

    @Override
    protected void afterViewCreate() {
        new PatientCaseEditAttachPresenterImp(this);
        addOnActivityResultView(this);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        titleCenterTv.setText("上传病例");
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImg();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mPresenter.uploadFiles(mImageList);
            }
        });
    }


    @Override
    public void setPresenter(PatientCaseEditAttachContract.Presenter presenter) {
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
    public void onActivityResult1(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //从相册选择
            case SELECT_PICTURE:
                Uri uri = data.getData();
                String path = Utils.getPath(uri);
                Map<String,Object> item = new HashMap<>();
                item.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
                item.put(ViewKey.FILE_KEY_URL,path);
                mImageList.add(item);
                horizontalImageView.setItems(mImageList);
                break;
            //拍照添加图片
            case SELECT_CAMER:
                if (mCameraPath != null) {
                    String p = mCameraPath.toString();
                    Map<String, Object> m = new HashMap<>();
                    Map<String,Object> item1 = new HashMap<>();
                    item1.put(ViewKey.FILE_KEY_TYPE,ViewKey.TYPE_FILE_PATH);
                    item1.put(ViewKey.FILE_KEY_URL,p);
                    mImageList.add(item1);
                    horizontalImageView.setItems(mImageList);

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
