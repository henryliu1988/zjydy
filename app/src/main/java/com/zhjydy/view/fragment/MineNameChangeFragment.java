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
import com.zhjydy.presenter.contract.MineNameChangContract;
import com.zhjydy.presenter.presenterImp.MineNamePresenterIml;
import com.zhjydy.view.zhview.zhToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class MineNameChangeFragment extends PageImpBaseFragment implements MineNameChangContract.View {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    private MineNameChangContract.Presenter mPresenter;

    @Override
    public void setPresenter(MineNameChangContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine_name_change;
    }

    @Override
    protected void afterViewCreate() {
        titleCenterTv.setText("修改用户名");
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    zhToast.showToast("请输入用户名");
                }
                mPresenter.submitChangeConfirm(name);
            }
        });
        new MineNamePresenterIml(this);
    }

    @Override
    public void refreshView() {

    }


    @Override
    public void submitResult(boolean result, String msg) {
        if (result) {
            back();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
