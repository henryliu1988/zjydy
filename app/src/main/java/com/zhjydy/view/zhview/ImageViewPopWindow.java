package com.zhjydy.view.zhview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.ImageUtils;
import com.zhjydy.util.ScreenUtils;
import com.zhjydy.util.ViewKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/11.
 */
public class ImageViewPopWindow extends PopupWindow {
    private View mMenuView;
    private ImageView mImage;
    private LinearLayout mInfoLayout;
    private List<ItemData> items = new ArrayList<>();
    private String url;
    private Context context;
    private int mImageType = ViewKey.TYPE_FILE_URL;

    public ImageViewPopWindow(Context context) {
        super(context);
        this.context = context;
        this.items = new ArrayList<>();
        this.url = "";
        initView();
    }

    public ImageViewPopWindow(final Activity context, String url, List<ItemData> items) {
        super(context);
        this.items = items;
        this.url = url;
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.image_view_pop_layout, null);
        mImage = (ImageView) mMenuView.findViewById(R.id.img_view);
        mInfoLayout = (LinearLayout) mMenuView.findViewById(R.id.img_info_ly);
        this.setContentView(mMenuView);
        this.setWidth(ScreenUtils.getScreenWidth());
        this.setHeight(ScreenUtils.getScreenHeight());
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //   this.setAnimationStyle(R.style.mystyle);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImageViewPopWindow.this.isShowing()) {
                    ImageViewPopWindow.this.dismiss();
                }
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlType(int Type) {
        this.mImageType = Type;
    }

    public void setInfos(List<ItemData> itemDatas) {
        this.items = itemDatas;
    }

    private void refreshView() {
        mInfoLayout.removeAllViews();
        if (this.mImageType == ViewKey.TYPE_FILE_PATH) {
            ImageUtils.getInstance().displayFromSDCard(url, mImage);
        } else {
            ImageUtils.getInstance().displayFromRemote(url, mImage);
        }
        for (int i = 0; i < items.size(); i++) {
            View view = View.inflate(context, R.layout.image_view_pop_info_item, null);
            TextView titleTv = (TextView) view.findViewById(R.id.title);
            TextView msgTv = (TextView) view.findViewById(R.id.msg);
            titleTv.setText(items.get(i).getTitle());
            msgTv.setText(items.get(i).getMsg());
            mInfoLayout.addView(view);
        }
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            refreshView();
        } else {
            this.dismiss();
        }
    }

    static class ItemData {
        String title;
        String msg;

        public ItemData(String title, String msg) {
            this.title = title;
            this.msg = msg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
