package com.example.zd.demobase.media;

/**
 * 音频播放状态改变的监听接口
 * Created by zhangdong on 2017/12/19 0019.
 */

public interface IMusicPlayListener {
    /**
     * 根据播放状态更新UI
     *
     * @param state 播放状态
     */
    void updateUI(String state);
}
