package com.zhjydy.view.zhview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.readystatesoftware.viewbadger.BadgeView;
import com.zhjydy.R;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class ViewUtil {


    public static void setCornerViewDrawbleBg(View view,String strokeColor,String fillCorlor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        int strokeWidth = 1;     // 1dp 边框宽度
        int roundRadius = 5;     // 5dp 圆角半径
        int strokeColorInt = Color.parseColor(strokeColor);//边框颜色
        int fillColorInt = Color.parseColor(fillCorlor); //内部填充颜色
        gd.setColor(fillColorInt);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColorInt);
        view.setBackgroundDrawable(gd);
    }

    public static void setCornerViewDrawbleBg(View view,String fillCorlor) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        int strokeWidth = 1;     // 1dp 边框宽度
        int roundRadius = 8;     // 5dp 圆角半径
        int strokeColorInt = Color.parseColor(fillCorlor);//边框颜色
        int fillColorInt = Color.parseColor(fillCorlor); //内部填充颜色
        gd.setColor(fillColorInt);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColorInt);
        view.setBackgroundDrawable(gd);
    }
}
