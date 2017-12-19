package com.example.zd.demoapplication.activity.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 音频状态改变的广播接收器
 * Created by zhangdong on 2017/12/13 0013.
 */

public class MusicReceiver extends BroadcastReceiver {

    public static final String EXTRA_STATE = "state";
    public static final String MUSIC_FILTER = "thisIsMusicStateReceiver";//广播意图过滤标志
    private IUpdateUi IUpdateUi;

    public void setIUpdateUi(IUpdateUi IUpdateUi) {
        this.IUpdateUi = IUpdateUi;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getStringExtra(EXTRA_STATE);

        if (IUpdateUi != null && extra != null) {
            IUpdateUi.updateUi(extra);
        }
    }

    public interface IUpdateUi {
        /**
         * 更新UI
         *
         * @param state 收到的状态
         */
        void updateUi(String state);
    }
}
