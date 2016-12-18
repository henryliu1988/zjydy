package com.zhjydy.view.zhview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.readystatesoftware.viewbadger.BadgeView;
import com.zhjydy.R;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class BadgImage extends RelativeLayout {
    private Context context;
    private ImageView image;
    private BadgeView bageView;

    public BadgImage(Context context) {
        super(context);
        initView(context);
    }

    public BadgImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        initImageView();
    }

    private void initImageView() {
        image = new ImageView(context);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        image.setPadding(0, 0, 15, 0);
        image.setLayoutParams(params);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.addView(image);

    }

    public BadgeView getBadgeView(Context context, View target) {
        BadgeView badge = new BadgeView(context, target);
        badge.setBadgeBackgroundColor(context.getResources().getColor(R.color.badge_bg_color));
        badge.setTextColor(context.getResources().getColor(R.color.white_text));
        badge.setTextSize(10);
        badge.toggle(true);
        badge.setBadgeMargin(0, 5);
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge.invalidate();
        return badge;
    }

    public void setImageSrc(int resId) {
        image.setImageResource(resId);
    }

    public void setText(String text) {
        bageView = getBadgeView(context, this);
        bageView.setText(text);
        bageView.show();
    }
}
