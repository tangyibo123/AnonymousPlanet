package com.example.anonymousplanet.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.anonymousplanet.R;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.BasePageAdapter;
import com.example.framework.base.BaseUIActivity;
import com.example.framework.manager.MediaPlayerManager;
import com.example.framework.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuideActivity extends BaseUIActivity implements View.OnClickListener {

    /**
     * 1. ViewPager : 适配器
     * 2. 小圆点的逻辑
     * 3. 歌曲的播放
     * 4. 属性动画旋转
     * 5. 跳转
     * @param savedInstanceState
     */
    //activity_guide中的view
    private ImageView iv_music_switch;
    private TextView tv_guide_skip;
    private ImageView iv_guide_point_1;
    private ImageView iv_guide_point_2;
    private ImageView iv_guide_point_3;
    private ViewPager mViewPager;

    //layout_pager_guide_1中的imageview
    private ImageView iv_guide_page1;
    //layout_pager_guide_2中的imageview
    private ImageView iv_guide_page2;
    //layout_pager_guide_3中的imageview
    private ImageView iv_guide_page3;

    private View view1;
    private View view2;
    private View view3;

    private List<View> mPageList = new ArrayList<>();
    private BasePageAdapter mPageAdapter;

    private MediaPlayerManager mGuideMusic;
    private ObjectAnimator mAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();

    }

    private void initView() {
        iv_music_switch = findViewById(R.id.iv_music_switch);
        tv_guide_skip = findViewById(R.id.tv_guide_skip);
        iv_guide_point_1 = findViewById(R.id.iv_guide_point_1);
        iv_guide_point_2 = findViewById(R.id.iv_guide_point_2);
        iv_guide_point_3 = findViewById(R.id.iv_guide_point_3);
        mViewPager = findViewById(R.id.mViewPager);

        //给音乐图标写点击事件
        iv_music_switch.setOnClickListener(this);
        tv_guide_skip.setOnClickListener(this);

        //把布局文件转换为View对象
        view1 = View.inflate(this,R.layout.layout_pager_guide_1,null);
        view2 = View.inflate(this,R.layout.layout_pager_guide_2,null);
        view3 = View.inflate(this,R.layout.layout_pager_guide_3,null);

        mPageList.add(view1);
        mPageList.add(view2);
        mPageList.add(view3);

        //预加载
        mViewPager.setOffscreenPageLimit(mPageList.size());
        //实例化一个adapter
        mPageAdapter = new BasePageAdapter(mPageList);
        //为viewpager设置adater
        mViewPager.setAdapter(mPageAdapter);

        //帧动画
        iv_guide_page1 = view1.findViewById(R.id.iv_guide_p1);
        iv_guide_page2 = view2.findViewById(R.id.iv_guide_p2);
        iv_guide_page3 = view3.findViewById(R.id.iv_guide_p3);

        //播放帧动画
        AnimationDrawable ani_p1 = (AnimationDrawable) iv_guide_page1.getBackground();
        ani_p1.start();

        AnimationDrawable ani_p2 = (AnimationDrawable) iv_guide_page2.getBackground();
        ani_p2.start();

        AnimationDrawable ani_p3 = (AnimationDrawable) iv_guide_page3.getBackground();
        ani_p3.start();

        //小圆点逻辑
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //歌曲的逻辑
        startMusic();

    }


    /**
     * 动态选择小圆点
     * @param position
     */

    private void selectPoint(int position) {
        switch (position){
            case 0:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point_p);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
                break;

            case 1:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point_p);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
                break;

            case 2:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point_p);
                break;
        }
    }

    /**
     * 播放音乐
     */
    private void startMusic() {
        mGuideMusic = new MediaPlayerManager();
        mGuideMusic.setLooping(true);
        AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.canon);
        mGuideMusic.startPlay(file);

        mAnim = AnimUtils.rotation(iv_music_switch);
        mAnim.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_music_switch:
                if(mGuideMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PAUSE){
                    mAnim.start();
                    mGuideMusic.continuePlay();
                    iv_music_switch.setImageResource(R.drawable.img_guide_music);
                }
                else if(mGuideMusic.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PLAY){
                    mAnim.pause();
                    mGuideMusic.pausePlay();
                    iv_music_switch.setImageResource(R.drawable.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGuideMusic.stopPlay();
    }
}
