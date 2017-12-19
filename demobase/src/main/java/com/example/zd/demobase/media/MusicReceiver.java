package com.example.zd.demobase.media;

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
    private IMusicPlayListener musicPlayListener;

    public void setMusicPlayListener(IMusicPlayListener musicPlayListener) {
        this.musicPlayListener = musicPlayListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String extra = intent.getStringExtra(EXTRA_STATE);

        if (musicPlayListener != null && extra != null) {
            musicPlayListener.updateUI(extra);
        }
    }
}
