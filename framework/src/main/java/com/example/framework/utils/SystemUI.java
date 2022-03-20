package com.example.framework.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

//把某个activity设置为沉浸式状态栏
public class SystemUI {

    public static void fixSystemUI(Activity mActivity){
        //判断当前app所在手机的API号，对应版本必须大于等于Android 5.0（api为21）才能做沉浸式状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //获取窗口中的最顶层的View，并设置为全屏
            /**
             * SYSTEM_UI_FLAG_FULLSCREEN 全屏
             * SYSTEM_UI_FLAG_LAYOUT_STABLE 显示状态栏
             * SYSTEM_UI_FLAG_HIDE_NAVIGATION 隐藏状态栏
             * 状态栏：带有WiFi、信号标识的
             * ActionBar：APP内顶部显示“返回、退出”按钮的
             * 导航栏：最下方“返回”、“主页”、“任务”
             */
            mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            mActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
