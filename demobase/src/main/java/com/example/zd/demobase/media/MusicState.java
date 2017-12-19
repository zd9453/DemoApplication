package com.example.zd.demobase.media;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 限定发送播放状态的广播
 * Created by zhangdong on 2017/12/19 0019.
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({MusicPlayService.STATE_START,
        MusicPlayService.STATE_PAUSE,
        MusicPlayService.STATE_COMPLETION,
        MusicPlayService.STATE_ERROR})
@interface MusicState {
}
