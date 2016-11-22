package com.zhjydy.view.zhview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by admin on 2016/9/18.
 */
public abstract class BaseItemView extends LinearLayout
{
    public BaseItemView(Context context)
    {
        super(context);
    }

    public BaseItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public abstract void setItems(Object items);

    public abstract Object getItems();

}
