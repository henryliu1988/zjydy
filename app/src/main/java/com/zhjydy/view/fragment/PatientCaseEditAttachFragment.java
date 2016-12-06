package com.zhjydy.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.presenter.contract.PatientCaseEditAttachContract;
import com.zhjydy.presenter.presenterImp.PatientCaseEditAttachPresenterImp;
import com.zhjydy.util.ScreenUtils;
import com.zhjydy.util.Utils;
import com.zhjydy.util.ViewKey;
import com.zhjydy.view.ActivityResultView;
import com.zhjydy.view.zhview.ItemImageAddView;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public class PatientCaseEditAttachFragment extends PageImpBaseFragment implements PatientCaseEditAttachContract.View, ActivityResultView
{


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
    private List<Map<String, Object>> mImageList = new ArrayList<>();

    private PatientCaseEditAttachContract.Presenter mPresenter;

    private int type = 0;
    private HashMap<String, Object> params = new HashMap<>();

    @Override
    protected void initData()
    {
        Bundle bundle = getArguments();
        if (bundle == null || bundle.getString("param") == null)
        {
            back();
            return;
        }
        Map<String, Object> params = Utils.parseObjectToMapString(bundle.getString("param"));
        type = bundle.getInt("type");
        this.params.putAll(params);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_patient_info_edit_attach;
    }

    @Override
    protected void afterViewCreate()
    {
        new PatientCaseEditAttachPresenterImp(this);
        addOnActivityResultView(this);
        titleBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                back();
            }
        });
        titleCenterTv.setText("上传病例");
        takePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectImg();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mPresenter.submitMsg(params, horizontalImageView.getAllFileList(), getContext(), type);
            }
        });

        int imageWidth  = ScreenUtils.getScreenWidth()/3;
        int imageHeigth = imageWidth*4/3;
        horizontalImageView.setImageSize(imageWidth,imageHeigth);
    }


    @Override
    public void setPresenter(PatientCaseEditAttachContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void refreshView()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityResult1(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            //从相册选择
            case SELECT_PICTURE:
                Uri uri = data.getData();
                String path = Utils.getPath(uri);
                horizontalImageView.addImage(path, ViewKey.TYPE_FILE_PATH);
                break;
            //拍照添加图片
            case SELECT_CAMER:
                if (mCameraPath != null)
                {
                    String p = mCameraPath.toString();
                    horizontalImageView.addImage(p, ViewKey.TYPE_FILE_PATH);
                    mCameraPath = null;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        List<String> permissionList = Arrays.asList(permissions);
        if (permissionList.contains(Manifest.permission.CAMERA))
        {
            toGetCameraImage();
        } else if (permissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            toGetLocalImage();
        }
    }

    @Override
    public void sumbitOk()
    {
        zhToast.showToast("添加患者成功");
        int[] refreshFrags = {FragKey.patient_case_detail_fragment};
        back(2, refreshFrags);
    }
}
