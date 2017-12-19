package com.example.zd.demoapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 自定义一个倒计时的view
 * Created by zhangdong on 2017/12/11 0011.
 */

public class ZdTimerView extends View {
    private static final String TAG = "TimerView";
    private Paint mPaint;
    private int scaledEdgeSlop;//最小的可识别滑动距离

    public ZdTimerView(Context context) {
        super(context);
        init();
    }

    public ZdTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZdTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        scaledEdgeSlop = ViewConfiguration.get(getContext()).getScaledEdgeSlop();
        Log.d(TAG, "init: -----------" + scaledEdgeSlop);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            measure(widthSize, heightSize);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measure(widthSize, heightMeasureSpec);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measure(widthMeasureSpec, heightSize);
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("this is my view", 0, 10, mPaint);
    }
}
