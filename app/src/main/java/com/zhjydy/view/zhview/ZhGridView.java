package com.zhjydy.view.zhview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by admin on 2016/8/8.
 */
public class ZhGridView extends GridView
{
    public ZhGridView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ZhGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ZhGridView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}