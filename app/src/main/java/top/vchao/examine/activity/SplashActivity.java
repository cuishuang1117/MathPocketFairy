package top.vchao.examine.activity;

import android.content.Intent;
import android.os.Handler;
import top.vchao.examine.R;
import top.vchao.examine.constants.SPkey;
import top.vchao.examine.utils.SPUtils;

/**
 * @
 */

public class SplashActivity extends BaseActivity {

    @Override
    int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    void initData() {
        //延时3s，跳转。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String username = (String) SPUtils.get(SplashActivity.this, SPkey.UserName, "");

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

            }
        }, 3000);
    }

}
