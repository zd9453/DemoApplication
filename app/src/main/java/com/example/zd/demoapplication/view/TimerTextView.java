package com.example.zd.demoapplication.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 倒计时的textView
 * Created by zhangdong on 2017/12/18 0018.
 */

public class TimerTextView extends AppCompatTextView {

    private int timer;

    public int getTimer() {
        return timer;
    }

    /**
     * 文字随时间变化而改变
     *
     * @param timer 时间
     */
    public void setTimer(int timer) {
        this.timer = timer;
        setText(timer == 0 ? "跳过" : timer + "s跳过");
        invalidate();
    }

    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
