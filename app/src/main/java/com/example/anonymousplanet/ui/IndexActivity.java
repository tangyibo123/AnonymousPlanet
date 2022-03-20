package com.example.anonymousplanet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.anonymousplanet.MainActivity;
import com.example.anonymousplanet.R;
import com.example.framework.base.BaseUIActivity;
import com.example.framework.entity.Constants;
import com.example.framework.utils.SpUtils;

/**
 * 启动页
 */
public class IndexActivity extends BaseUIActivity {

    /**
     * 1. 启动页全屏 AndroidManifest.xml android:theme="@style/Theme.Full
     * 2. 延迟进入主页
     * 3. 根据具体逻辑是进入主页还是引导页还是登录页
     * 4. 适配刘海屏
     * @param savedInstanceState
     */

    private  static final int SKIP_MAIN = 1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case SKIP_MAIN:
                    startMain();
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        mHandler.sendEmptyMessageDelayed(SKIP_MAIN,2000);
    }

    /**
     * 进入主页
     */
    private void startMain() {
        //1. 判断App是否是第一次启动 install - first run
        boolean isFirstApp = SpUtils.getInstance().getBoolean(Constants.SP_IS_FIRST_APP,true);
        Intent intent = new Intent();
        if(isFirstApp){
            //跳转到引导页
            intent.setClass(this,GuideActivity.class);
            SpUtils.getInstance().putBoolean(Constants.SP_IS_FIRST_APP,false);
        }
        else{
            //2. 如果非第一次启动，判断是否曾经登录过
            String token = SpUtils.getInstance().getString(Constants.SP_TOKEN,"");
            if(TextUtils.isEmpty(token)){
                //跳转到登录页
                intent.setClass(this,LoginActivity.class);
            }
            else{
                //跳转到主页
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}
