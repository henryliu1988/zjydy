package com.zhjydy.view.zhview;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zhjydy.R;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class CustomDialog extends AlertDialog {

    private TextView mMsgTv;
    private String msg;

    public CustomDialog(Context context) {
        super(context);
        initView();
    }

    public CustomDialog(Context context, String msg) {
        super(context);
        this.msg = msg;
        initView();

    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        setContentView(R.layout.confirm_dlg_layout);
        mMsgTv = (TextView) findViewById(R.id.dlg_msg);
        if (!TextUtils.isEmpty(msg)) {
            mMsgTv.setText(msg);
        }
        TextView cancelTv = (TextView) findViewById(R.id.cancel);
        TextView confirmTv = (TextView) findViewById(R.id.confirm);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onConfirmListner != null) {
                    onConfirmListner.onConfirm();
                }
            }
        });
    }

    private void setTitle(String msg) {
        this.msg = msg;
        if (mMsgTv != null) {
            mMsgTv.setText(msg);
        }
    }

    private OnConfirmListner onConfirmListner;

    private void setOnConfirmListner(OnConfirmListner listner) {
        this.onConfirmListner = listner;
    }

    public interface OnConfirmListner {
        public void onConfirm();
    }
}
