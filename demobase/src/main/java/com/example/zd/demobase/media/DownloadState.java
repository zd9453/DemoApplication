package com.example.zd.demobase.media;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 下载状态的限定
 * Created by zhangdong on 2017/12/19 0019.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({MusicDownloadHolder.FLAG_NORMAL,
        MusicDownloadHolder.FLAG_WAITING,
        MusicDownloadHolder.FLAG_STARTED,
        MusicDownloadHolder.FLAG_CANCELED,
        MusicDownloadHolder.FLAG_COMPLETED,
        MusicDownloadHolder.FLAG_FAILED})
@interface DownloadState {
}
