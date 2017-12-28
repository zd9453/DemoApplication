package com.example.zd.demoapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.zd.demoapplication.BuildConfig;
import com.example.zd.demoapplication.R;
import com.example.zd.demobase.utils.SizeUtils;

/**
 * 自定义一个倒计时的view
 * Created by zhangdong on 2017/12/11 0011.
 */

public class ZdTimerView extends View {
    private static final String TAG = "TimerView";
    private Paint mPaint;
    private int scaledEdgeSlop;//最小的可识别滑动距离
    private String info = "this is my view";
    private Drawable drawable;

    public void setInfo(String info) {
        this.info = info;
        if (mPaint != null) {
            mPaint.setTextScaleX(1.0f);
        }
        invalidate();
    }

    public ZdTimerView(Context context) {
        super(context);
        init();
    }

    public ZdTimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZdTimerView, 0, 0);
        int string = typedArray.getInt(R.styleable.ZdTimerView_showInfo, 1);
        if (string == 1) {
            info = "this is test information for my view";
        } else if (string == 2) {
            info = "this is test information";
        }
        drawable = typedArray.getDrawable(R.styleable.ZdTimerView_img);
        typedArray.recycle();
        init();
    }

    public ZdTimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZdTimerView, 0, 0);
        int string = typedArray.getInt(R.styleable.ZdTimerView_showInfo, 1);
        if (string == 1) {
            info = "this is test information for my view";
        } else if (string == 2) {
            info = "this is test information";
        }
        drawable = typedArray.getDrawable(R.styleable.ZdTimerView_img);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(SizeUtils.dpToPx(14));
        scaledEdgeSlop = ViewConfiguration.get(getContext()).getScaledEdgeSlop();
        Log.d(TAG, "init: -----------" + scaledEdgeSlop);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingBottom = getPaddingBottom();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();

        Log.d(TAG, "onMeasure: --------- " + (int) mPaint.measureText(info) + "  " + ((int) mPaint.getFontMetrics().bottom));
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float ascent = metrics.ascent;
        float bottom = metrics.bottom;
        float descent = metrics.descent;
        float leading = metrics.leading;
        float top = metrics.top;
        Log.d(TAG, "onMeasure: ---------ascent = " + ascent + " bottom = " + bottom + " descent = " + descent + " leading = " + leading + " top = " + top);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) (mPaint.measureText(info)) + paddingLeft + paddingRight,
                    (int) (mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) + paddingTop + paddingBottom);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) mPaint.measureText(info) + paddingLeft + paddingRight, h);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(w,
                    (int) (mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top) + paddingTop + paddingBottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();


        canvas.drawText(info, paddingLeft, getHeight() - paddingBottom, mPaint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (BuildConfig.DEBUG) {//debug模式
            canvas.drawColor(Color.parseColor("#5500ff00"));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ------------- ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ------------- ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ------------- ACTION_UP");
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 分发事件
     *
     * @param event .
     * @return .
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

}
