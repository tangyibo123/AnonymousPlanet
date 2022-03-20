package com.example.framework.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.framework.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

//不仅要打印日志，还要将打印的日志保存至File
public class LogUtils {

    private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    //LogUtils.i()
    //对static修饰的类变量和方法的访问可以直接使用 classname.variablename 和 classname.methodname 的方式访问。
    //Send a VERBOSE log message.
    public static void v(String text){
        //release版本不需要打印调试信息
        //所以先确定是debug模式，再判断text是否为空
        if(BuildConfig.LOG_DEBUG){
            if(!TextUtils.isEmpty(text)){
                Log.v(BuildConfig.LOG_TAG, text);
                writeToFile(text, "VERBOSE");
            }
        }
    }

    //Send a DEBUG log message.
    public static void d(String text){
        //release版本不需要打印调试信息
        //所以先确定是debug模式，再判断text是否为空
        if(BuildConfig.LOG_DEBUG){
            if(!TextUtils.isEmpty(text)){
                Log.d(BuildConfig.LOG_TAG, text);
                writeToFile(text, "DEBUG");
            }
        }
    }

    //Send an INFO log message.
    public static void i(String text){
        //release版本不需要打印调试信息
        //所以先确定是debug模式，再判断text是否为空
        if(BuildConfig.LOG_DEBUG){
            if(!TextUtils.isEmpty(text)){
                Log.i(BuildConfig.LOG_TAG, text);
                writeToFile(text, "INFO");
            }
        }
    }

    //Send a WARN log message.
    public static void w(String text){
        //release版本不需要打印调试信息
        //所以先确定是debug模式，再判断text是否为空
        if(BuildConfig.LOG_DEBUG){
            if(!TextUtils.isEmpty(text)){
                Log.w(BuildConfig.LOG_TAG, text);
                writeToFile(text, "WARN");
            }
        }
    }

    //Send an ERROR log message.
    public static void e(String text){
        //release版本不需要打印调试信息
        //所以先确定是debug模式，再判断text是否为空
        if(BuildConfig.LOG_DEBUG){
            if(!TextUtils.isEmpty(text)){
                Log.e(BuildConfig.LOG_TAG, text);
                writeToFile(text, "ERROR");
            }
        }
    }

    public static void writeToFile(String text, String level) {
        //文件路径
        String fileName = "/sdcard/AP/ap.log";
        // 时间+等级+内存
        String log = mSimpleDateFormat.format(new Date()) + " " + level + ":" + text + "\n";
        //检查父路径
        File fileGroup = new File("/sdcard/AP/");
        if(!fileGroup.exists()){
            fileGroup.mkdirs();
        }
        //开始写入
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(fileName,true);
            //对fileOutputStream包装，编码问题，GBK编码可以正确存入中文
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream, Charset.forName("gbk")));
            bufferedWriter.write(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
