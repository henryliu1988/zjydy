package com.zhjydy.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhjydy.R;
import com.zhjydy.model.entity.NormalDicItem;
import com.zhjydy.model.entity.NormalPickViewData;
import com.zhjydy.presenter.contract.OrderCancelContract;
import com.zhjydy.presenter.presenterImp.OrderCancelPresenterImp;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.MapTextView;
import com.zhjydy.view.zhview.ViewUtil;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class OrderCancelFragment extends PageImpBaseFragment implements OrderCancelContract.View {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.cancel_reason_title)
    TextView cancelReasonTitle;
    @BindView(R.id.cancel_reason_value)
    MapTextView cancelReasonValue;
    @BindView(R.id.cancel_reason_layout)
    LinearLayout cancelReasonLayout;
    @BindView(R.id.comment_edit_value)
    EditText commentEditValue;
    @BindView(R.id.cancel_comment_layout)
    LinearLayout cancelCommentLayout;
    @BindView(R.id.confirm)
    TextView confirm;

    private OrderCancelContract.Presenter mPresenter;

    private OptionsPickerView mCancelReasonPicker;
    private ArrayList<NormalPickViewData> mCancelPickViewData = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_cancel;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("申请取消");
        ViewUtil.setCornerViewDrawbleBg(cancelReasonLayout, "#EEEEEE");
        ViewUtil.setCornerViewDrawbleBg(cancelCommentLayout, "#EEEEEE");
        String orderId = "";
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            orderId = getArguments().getString(IntentKey.FRAG_INFO);
        }
        mCancelReasonPicker = new OptionsPickerView<NormalDicItem>(getContext());
        cancelReasonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelPickViewData.size() > 0) {
                    mCancelReasonPicker.show();
                }
            }
        });
        new OrderCancelPresenterImp(this, orderId);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCancel();
            }
        });
    }

    private void confirmCancel() {
        String cancelId = cancelReasonValue.getTextId();
        if (TextUtils.isEmpty(cancelId)) {
            zhToast.showToast("请选择取消原因");
            return;
        }
        String commentStr = commentEditValue.getText().toString();
        if (commentStr.length() > 30) {
            zhToast.showToast("备注长度超过30！");
            return;
        }

        mPresenter.confirmCancel(cancelId, commentStr);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void cancelResult(boolean result) {
        if (result) {
            zhToast.showToast("取消预约成功");
            back();
        } else {
            zhToast.showToast("取消预约失败");
        }
    }

    @Override
    public void updateCancelResonList(ArrayList<NormalPickViewData> resons) {
        if (mCancelReasonPicker == null) {
            mCancelReasonPicker = new OptionsPickerView<NormalDicItem>(getContext());
        }
        mCancelPickViewData = resons;
        mCancelReasonPicker.setPicker(mCancelPickViewData);
        mCancelReasonPicker.setCyclic(false);
        mCancelReasonPicker.setSelectOptions(0);
        mCancelReasonPicker.setCancelable(true);
        mCancelReasonPicker.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String officeName = mCancelPickViewData.get(options1).getmItem().getName();
                String officeId = mCancelPickViewData.get(options1).getmItem().getId();
                if (TextUtils.isEmpty(officeId)) {
                    cancelReasonValue.setMap("", "");
                } else {
                    cancelReasonValue.setMap(officeId, officeName);
                }
            }
        });

    }

    @Override
    public void setPresenter(OrderCancelContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
