package com.example.zd.demoapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * .
 * Created by zhangdong on 2017/12/19 0019.
 */

public class ScrollEventView extends ScrollView {
    private static final String TAG = "ScrollEventView";
    private float mLastX;
    private float mLastY;


    public ScrollEventView(Context context) {
        super(context);
    }

    public ScrollEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollEventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isDo = false;
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDo = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //x 移动大于 y 水平
                if (Math.abs(ev.getX() - mLastX) > Math.abs(ev.getY() - mLastY)) {
                    Log.d(TAG, "dispatchTouchEvent: -------------");
                    isDo = true;
                } else {//竖直
                    isDo = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isDo = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return isDo;
    }

    /**
     * 处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent: ----------------");
        return true;
    }
}
