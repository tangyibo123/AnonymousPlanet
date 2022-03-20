package com.example.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.utils.SystemUI;

/**
 * - BaseActivity: 所有统一功能-语言切换、请求权限
 *      - BaseUIActivity: 单一功能：沉浸式
 *      - BaseBackActivity: 返回键
 *      - ……
 */
public class BaseUIActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this传入，把BaseUIActivity自己传入，做成沉浸式状态栏
        SystemUI.fixSystemUI(this);
    }
}
