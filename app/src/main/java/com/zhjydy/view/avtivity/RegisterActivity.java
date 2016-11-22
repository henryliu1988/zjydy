package com.zhjydy.view.avtivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.phonenum_edit)
    EditText phonenumEdit;
    @BindView(R.id.confirm_code_edit)
    EditText confirmCodeEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.register_now)
    TextView registerNow;
    @BindView(R.id.confirm_code_get)
    Button confirmCodeGet;
    @BindView(R.id.legend)
    TextView legend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);
        ButterKnife.bind(this);
        titleCenterTv.setText("注册");
        legend.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @OnClick({R.id.title_back, R.id.register_now, R.id.confirm_code_get, R.id.legend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.register_now:
                break;
            case R.id.confirm_code_get:
                break;
            case R.id.legend:
                break;
        }
    }

}
