package com.example.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.framework.utils.LogUtils;

import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * MediaPlayer的公开方法
 * create                 //创建实例
 * setDataSource          //设置播放资源
 * start                  //开始或恢复播放
 * stop                   //停止播放
 * pause                  //暂停播放
 * getDuration            //获取流媒体的总播放时长
 * getCurrentPosition     //获取当前流媒体的播放位置
 * seekTo                 //设置当前MediaPlayer的播放位置
 * setLooping             //设置是否循环播放
 * isLooping              //判断是否循环播放
 * isPlaying              //判断是否正在播放
 * prepare                //同步的方式装载流媒体文件
 * prepareAsync           //异步的方式装载流媒体文件
 * release                //回收流媒体资源
 * setAudioStreamType     //设置播放流媒体类型
 * setWakeMode            //设置CPU唤醒的状态
 *
 * MediaPlayer的公开接口
 * setOnCompletionListener    //当流媒体播放完毕的时候回调
 * setOnErrorListener         //当播放中发生错误时回调
 * setOnPreparedListener      //当装载流媒体完毕时回调
 * setOnSeekCompleteListener  //当使用seekTo()设置播放位置时回调
 *
 */

public class MediaPlayerManager {

    //播放
    public static final int MEDIA_STATUS_PLAY = 0;
    //暂停
    public static final int MEDIA_STATUS_PAUSE = 1;
    //停止
    public static final int MEDIA_STATUS_STOP = 2;
    //默认状态
    public int MEDIA_STATUS = MEDIA_STATUS_STOP;

    private static final int H_PROGRESS = 1000;

    //成员
    private MediaPlayer mMediaPlayer;

    //不是接口变量，而是一个接口类型的引用指向了一个实现该接口的对象，调用MediaPlayerManager.
    private OnMusicProgressListener musicProgressListener;

    /**
     * 计算歌曲的进度
     * 1. 开始播放的时候就开启循环计算时长
     * 2. 将计算结果对外抛出
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case H_PROGRESS:
                    //3. 正常musicProgressListener初始化是null的，只有获取进度函数被调用时才会被赋值，也就是不为null
                    if(musicProgressListener != null){
                        //拿到当前时长
                        int currentPosition = getCurrentPosition();
                        //计算百分比
                        int percent = (int) (((float) currentPosition) / ((float) getDuration()) * 100);
                        musicProgressListener.OnProgress(currentPosition, percent);
                        mHandler.sendEmptyMessageDelayed(H_PROGRESS,1000);
                    }
                    break;
            }
            return false;
        }
    });

            //构造函数

    public MediaPlayerManager(){
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 开始播放
     * @param path
     */
    public void startPlay(AssetFileDescriptor path){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            LogUtils.e(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 判断是否正在播放
     * @return isPlaying
     */
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    /**
     * 暂停播放
     */
    public void pausePlay(){
        if(isPlaying()){
            mMediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
            mHandler.removeMessages(H_PROGRESS);
        }
    }

    /**
     * 继续播放
     */
    public void continuePlay(){
        mMediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage(H_PROGRESS);
    }

    /**
     * 停止播放
     */
    public void stopPlay(){
        mMediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        mHandler.removeMessages(H_PROGRESS);
    }

    /**
     *获取当前位置
     * @return CurrentPosition
     */
    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     *获取当前播放时长
     * @return Duration
     */
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    //是否循环播放
    public void setLooping(Boolean isLooping){
        mMediaPlayer.setLooping(isLooping);
    }

    //跳转播放
    public void seekTo(int ms){
        mMediaPlayer.seekTo(ms);
    }

    //播放结束，播放完毕当流媒体的时候回调
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener){
        mMediaPlayer.setOnCompletionListener(listener);
    }
    //播放错误，当播放中发生错误时回调
    public void setOnErrorListener(MediaPlayer.OnErrorListener listener){
        mMediaPlayer.setOnErrorListener(listener);
    }
    //播放进度
    //2. 获取进度的回调函数被activity调用，传进来一个新的进度监听器接口对象，并已经实现了OnProgress函数，将这个对象赋值给musicProgressListener
    public void setOnProgressListener(OnMusicProgressListener listener){
        musicProgressListener = listener;
    }


    //2. 进度监听器接口
    public interface OnMusicProgressListener{
        void OnProgress(int progress, int pos);
    }


}


