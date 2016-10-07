package com.zhjydy.view.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjydy.R;
import com.zhjydy.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_center_tv)
    TextView titleCenterTv;
    @BindView(R.id.phonenum_edit)
    EditText phonenumEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.input_layout)
    LinearLayout inputLayout;
    @BindView(R.id.forget_psw_tv)
    TextView forgetPswTv;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.rigister_now)
    TextView rigisterNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        titleCenterTv.setText("登录");
    }

    @OnClick({R.id.title_back, R.id.forget_psw_tv, R.id.btnLogin, R.id.rigister_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.forget_psw_tv:
                ActivityUtils.transActivity(LoginActivity.this,ForgetPassWordActivity.class,false);
                break;
            case R.id.btnLogin:
                tryLogin();
                break;
            case R.id.rigister_now:
                ActivityUtils.transActivity(LoginActivity.this,RegisterActivity.class,false);
                break;
        }
    }

    private void tryLogin() {
        ActivityUtils.transActivity(LoginActivity.this,MainTabsActivity.class,true);
    }
}
