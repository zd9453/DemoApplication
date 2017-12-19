package com.example.zd.demobase.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zd.demobase.R;

/**
 * Created by zhangdong on 2017/12/4 0004.
 */

public class CircleImg extends View {
    public CircleImg(Context context) {
        super(context);
    }

    public CircleImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImg(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // View.java 的 draw() 方法的简化版大致结构（是大致结构，不是源码哦）：
//    public void draw(Canvas canvas) {
//    ...
//
//        drawBackground(Canvas); // 绘制背景（不能重写）
//        onDraw(Canvas); // 绘制主体
//        dispatchDraw(Canvas); // 绘制子 View
//        onDrawForeground(Canvas); // 绘制滑动相关和前景
//    }
     /*
         * Draw traversal performs several drawing steps which must be executed
         * in the appropriate order:
         *
         *      1. Draw the background
         *      2. If necessary, save the canvas' layers to prepare for fading
         *      3. Draw view's content
         *      4. Draw children
         *      5. If necessary, draw the fading edges and restore layers
         *      6. Draw decorations (scrollbars for instance)
         */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//        canvas.scale(2f, 2f);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_test);
//        canvas.drawBitmap(bitmap, 0, 0, new Paint());
//        bitmap.recycle();
//        canvas.restore();

        Matrix matrix = new Matrix();

        matrix.postTranslate(10, 10);
        canvas.setMatrix(matrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        canvas.restore();
    }

    /**
     * 绘制子View的方法
     *
     * @param canvas .
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 绘制前景 进度条
     *
     * @param canvas .
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
