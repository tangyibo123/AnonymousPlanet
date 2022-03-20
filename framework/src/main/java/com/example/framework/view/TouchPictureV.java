package com.example.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.framework.R;


/**
 * 图片验证view
 */
public class TouchPictureV extends View {

    //背景
    private Bitmap bgBitmap;
    //背景画笔
    private Paint mPaintbg;

    //空白块
    private Bitmap mNullBitmap;
    //空白块画笔
    private Paint mPaintNull;

    //移动方块
    private Bitmap mMoveBitmap;
    //移动画笔
    private Paint mPaintMove;

    private int mWidth;
    private int mHeight;

    public TouchPictureV(Context context){
        super(context);
        init();
    }

    public TouchPictureV(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public TouchPictureV(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintbg = new Paint();
        mPaintNull = new Paint();
        mPaintMove = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);
    }

    /**
     * 绘制背景
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        //1. 获取图片
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg);
        //2. 创建一个空的Bitmap
        bgBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //3. 将图片绘制到空的Bitmap
        Canvas bgCanvas = new Canvas(bgBitmap);
        bgCanvas.drawBitmap(mBitmap, null, new Rect(0,0,mWidth,mHeight), mPaintbg);
        //4. 将bgBitmap绘制到View上
        canvas.drawBitmap(bgBitmap, null, new Rect(0,0,mWidth,mHeight), mPaintbg);
    }
}
