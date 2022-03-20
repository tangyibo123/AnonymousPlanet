package com.example.anonymousplanet.test;

import android.os.Bundle;

import androidx.annotation.Nullable;


import com.example.anonymousplanet.R;
import com.example.framework.base.BaseActivity;

/**
 * 测试专用类
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
