package com.example.zd.demoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.utils.TimerUtils;
import com.example.zd.demoapplication.view.TimerTextView;

import static com.example.zd.demoapplication.utils.TimerUtils.STATE_END;

public class TimerViewActivity extends AppCompatActivity
        implements TimerUtils.TimerListener, View.OnClickListener {

    private TimerUtils timerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_view);
        TimerTextView timeView = (TimerTextView) findViewById(R.id.tv_time);
        timeView.setOnClickListener(this);      //倒计时view的点击监听
        timerUtils = TimerUtils.getInstance();  //获取倒计时辅助工具类实例
        timerUtils.setTimerListener(this);      //监听动画状态
        timerUtils.startTimer(timeView, 10);    //开始倒计时，10秒
    }

    @Override
    public void animationState(@TimerUtils.TimerState String state) {
        switch (state) {
            case STATE_END://动画结束，做我们想做的事
                if (timerUtils != null) {
                    timerUtils = null;
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (timerUtils != null) {
            //点击时调用停止，此时动画会回调STATE_END，就会执行上面动画状态的监听方法，在里面做我们想要的操作，eg：跳转
            timerUtils.stopTimer();
        }
    }
}
