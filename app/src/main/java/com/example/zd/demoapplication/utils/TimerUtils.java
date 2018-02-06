package com.example.zd.demoapplication.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.support.annotation.StringDef;
import android.util.Log;

import com.example.zd.demoapplication.view.TimerTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 倒计时工具,用动画实现
 * Created by zhangdong on 2017/12/18 0018.
 */

public class TimerUtils implements Animator.AnimatorListener {
    public static final String STATE_START = "onAnimationStart";//动画开始
    public static final String STATE_END = "onAnimationEnd";//动画结束
    public static final String STATE_CANCEL = "onAnimationCancel";//动画取消
    public static final String STATE_REPEAT = "onAnimationRepeat";//动画重新开始
    private static final String TAG = "TimerUtils";

    private ObjectAnimator objectAnimator;
    private TimerListener timerListener;

    private TimerUtils() {
    }

    private static volatile TimerUtils instance;

    public static TimerUtils getInstance() {
        if (instance == null) {
            synchronized (TimerUtils.class) {
                if (instance == null) {
                    instance = new TimerUtils();
                }
            }
        }
        return instance;
    }

    public void setTimerListener(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    /**
     * 开始倒计时
     *
     * @param view 此view需要是TimerTextView，这个view里面处理文字更新
     * @param time 倒计时的时长 单位秒（s）
     */
    public void startTimer(TimerTextView view, int time) {
        objectAnimator = ObjectAnimator.ofInt(view, "timer", time, 0);
        objectAnimator.setEvaluator(new TextType());
        objectAnimator.setDuration(time * 1000);
        objectAnimator.addListener(this);
        objectAnimator.start();
    }

    private static class TextType implements TypeEvaluator<Integer> {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int v = startValue - (int) ((startValue - endValue) * fraction);
            return v;
        }
    }

    /**
     * 提交停止倒计时
     */
    public void stopTimer() {
        if (objectAnimator != null) {
            if (objectAnimator.isRunning()) {
                objectAnimator.cancel();
            }
            objectAnimator.addListener(null);
            objectAnimator = null;
        }
        if (timerListener != null) {
            timerListener = null;
        }
    }

    @Override
    public void onAnimationStart(Animator animation) {
        Log.d(TAG, "onAnimationStart: ---------------");
        if (timerListener != null) {
            timerListener.animationState(STATE_START);
        }
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Log.d(TAG, "onAnimationEnd: ------------------");
        if (timerListener != null) {
            timerListener.animationState(STATE_END);
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        Log.d(TAG, "onAnimationCancel: --------------");
        if (timerListener != null) {
            timerListener.animationState(STATE_CANCEL);
        }
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        Log.d(TAG, "onAnimationRepeat: ----------------");
        if (timerListener != null) {
            timerListener.animationState(STATE_REPEAT);
        }
    }

    /**
     * 限定动画执行状态的传参
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({STATE_START, STATE_END, STATE_CANCEL, STATE_REPEAT})
    public @interface TimerState {
    }

    /**
     * 动画执行的状态监听
     */
    public interface TimerListener {
        void animationState(@TimerState String state);
    }
}
