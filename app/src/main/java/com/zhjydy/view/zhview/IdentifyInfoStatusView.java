package com.zhjydy.view.zhview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.DensityUtil;
import com.zhjydy.util.ScreenUtils;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class IdentifyInfoStatusView extends RelativeLayout {
    private RelativeLayout mOutlineView;
    private TextView mStatusText;

    public IdentifyInfoStatusView(Context context) {
        super(context);
        initView();
    }

    public IdentifyInfoStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    private void initView() {
        this.setGravity(CENTER_IN_PARENT);
        mOutlineView = new RelativeLayout(getContext());
        mOutlineView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        int padding = DensityUtil.dip2px(getContext(), 3);
        mOutlineView.setPadding(padding, padding, padding, padding);
        mStatusText = new TextView(getContext());
        mStatusText.setTextColor(getContext().getResources().getColor(R.color.white_text));
        mStatusText.setTextSize(ScreenUtils.getScreenWidth() / 60);
        mStatusText.setMaxEms(3);
        mStatusText.setPadding(padding * 2, padding * 2, padding * 2, padding * 2);
        mOutlineView.setGravity(CENTER_IN_PARENT);
        mOutlineView.addView(mStatusText);
        this.addView(mOutlineView);
    }

    public void setStatusText(String status) {
        mStatusText.setText(status);
    }

    public void setStatus(boolean finish) {
        if (finish) {
            mOutlineView.setBackgroundResource(R.drawable.bg_circle_identify22);
            mStatusText.setBackgroundResource(R.drawable.bg_circle_identify2);
        } else {
            mOutlineView.setBackgroundResource(R.drawable.bg_circle_identify11);
            mStatusText.setBackgroundResource(R.drawable.bg_circle_identify1);

        }
    }
}
