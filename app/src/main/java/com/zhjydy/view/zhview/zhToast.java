package com.zhjydy.view.zhview;

import android.widget.Toast;

import com.zhjydy.app.zhjApplication;

/**
 * Created by Administrator on 2016/11/8 0008.
 */
public class zhToast {

    public static void showToast(String text) {
        Toast.makeText(zhjApplication.getInstance().getContext(), text, Toast.LENGTH_SHORT).show();
    }

}
