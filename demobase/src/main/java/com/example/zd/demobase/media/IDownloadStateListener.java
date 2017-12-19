package com.example.zd.demobase.media;

/**
 * RxDownload下载状态的回调监听
 * Created by zhangdong on 2017/12/19 0019.
 */

public interface IDownloadStateListener {
    /**
     * 下载状态改变
     *
     * @param state
     */
    void downloadStateChange(@DownloadState int state);
}
