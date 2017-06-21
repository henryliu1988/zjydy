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
import com.zhjydy.presenter.contract.OrderBackContract;
import com.zhjydy.presenter.presenterImp.OrderBackPresenterImp;
import com.zhjydy.util.Utils;
import com.zhjydy.view.avtivity.IntentKey;
import com.zhjydy.view.zhview.ViewUtil;
import com.zhjydy.view.zhview.zhToast;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class OrderBackFragment extends PageImpBaseFragment implements OrderBackContract.View {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.back_money_title)
    TextView backMoneyTitle;
    @BindView(R.id.reason_money_value)
    TextView reasonMoneyValue;
    @BindView(R.id.back_money_layout)
    LinearLayout backMoneyLayout;
    @BindView(R.id.back_reason_title)
    TextView backReasonTitle;
    @BindView(R.id.reason_edit_value)
    EditText reasonEditValue;
    @BindView(R.id.back_reason_layout)
    LinearLayout backReasonLayout;
    @BindView(R.id.comment_edit_value)
    EditText commentEditValue;
    @BindView(R.id.back_comment_layout)
    LinearLayout backCommentLayout;
    @BindView(R.id.confirm)
    TextView confirm;
    private OrderBackContract.Presenter mPresenter;

    private OptionsPickerView mCancelReasonPicker;
    private ArrayList<NormalPickViewData> mCancelPickViewData = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_back;
    }

    @Override
    protected void afterViewCreate() {
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        titleCenterTv.setText("申请退单");
        ViewUtil.setCornerViewDrawbleBg(backMoneyLayout, "#EEEEEE");
        ViewUtil.setCornerViewDrawbleBg(backReasonLayout, "#EEEEEE");
        ViewUtil.setCornerViewDrawbleBg(backCommentLayout, "#EEEEEE");
        String orderId = "";
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString(IntentKey.FRAG_INFO))) {
            orderId = getArguments().getString(IntentKey.FRAG_INFO);
        }
        mCancelReasonPicker = new OptionsPickerView<NormalDicItem>(getContext());
        backReasonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelPickViewData.size() > 0) {
                    mCancelReasonPicker.show();
                }
            }
        });
        new OrderBackPresenterImp(this, orderId);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCancel();
            }
        });
    }

    private void confirmCancel() {
        String reason = reasonEditValue.getText().toString();
        if (TextUtils.isEmpty(reason)) {
            zhToast.showToast("请输入取消原因");
            return;
        }
        String commentStr = commentEditValue.getText().toString();
        if (commentStr.length() > 30) {
            zhToast.showToast("备注长度超过30！");
            return;
        }

        mPresenter.confirmBack(reason,commentStr);
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void backResult(boolean result) {
        if (result) {
            zhToast.showToast("退单申请提交成功");
            back();
        } else {
            zhToast.showToast("退单申请提交失败");
        }
    }



    @Override
    public void setPresenter(OrderBackContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void updateOrder(Map<String, Object> orderinfo) {
        Map<String,Object> hui = Utils.parseObjectToMapString(orderinfo.get("hui_comment"));
        String moneyValue = Utils.toString(hui.get("money"));
        reasonMoneyValue.setText("￥  " + moneyValue + "元");
    }
}
