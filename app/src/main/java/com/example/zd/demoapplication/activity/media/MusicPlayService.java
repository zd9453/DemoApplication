package com.example.zd.demoapplication.activity.media;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import zlc.season.rxdownload2.RxDownload;

/**
 * 播发音频的service，利用bindService启动，和当前activity绑定，页面关闭时销毁
 * bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);--生命周期
 * MusicService() 构造
 * onCreate()     创建
 * onBind()       绑定
 * onUnbind()     解绑
 * onDestroy()    销毁
 * <p>
 * Created by zhangdong on 2017/12/13 0013.
 */

public class MusicPlayService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener {

    public static final String EXTRA_URL = "theMusicPlayUrl";//开启服务的传参
    public static final String STATE_COMPLETION = "playCompletion";//播放结束
    public static final String STATE_ERROR = "playError";//播放出错
    public static final String STATE_START = "playStart";//播放开始
    public static final String STATE_PAUSE = "playPause";//播放暂停
    private final IBinder binder = new MusicBinder();
    private boolean isPrepared;//音频是否装载完毕
    private boolean isChosePlay = true;//是否选择播放,默认是true，服务创建时是用户选择播放的时候
    private MediaPlayer player;
    private TelephonyManager phonyManager;//来电管理
    private PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_IDLE) {//无任何状态
                return;
            }
            //来电状态改变，这里统一处理为暂停播放，用户结束后再次点击播放
            isChosePlay = false;
            if (player != null && isPrepared) {
                player.pause();
                sendMusicBroadcast(STATE_PAUSE);
            }
        }
    };

    public MusicPlayService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //来电管理
        phonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        phonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        //用资源文件创建MediaPlayer对象
//        player = MediaPlayer.create(this, R.raw.jazz_in_paris);
        //创建MediaPlayer对象
        player = new MediaPlayer();
        //设置音频播放类型
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //intent传递过来的音频播放url
        String url = intent.getStringExtra(EXTRA_URL);

        RxDownload rxDownload = RxDownload.getInstance(this);
        File[] realFiles = rxDownload.getRealFiles(url);

        if (realFiles != null && realFiles.length != 0) {//已存在本地下载的音频文件
            File downFile = realFiles[0];
            try {
                //设置本地资源
                player.setDataSource(this, Uri.fromFile(downFile));
                //同步装载本地音频资源
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                //加载资源出错
                isChosePlay = false;
                sendMusicBroadcast(STATE_ERROR);
            }
        } else {//为下载
            try {
                //将网络播放链接设置给MediaPlayer
                player.setDataSource(url);
                //异步装载网络音频资源
                player.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
                //加载资源出错
                isChosePlay = false;
                sendMusicBroadcast(STATE_ERROR);
            }
        }

        //装载资源的监听，成功时就可以调用播放
        player.setOnPreparedListener(this);

        //音频播放完成的监听
        player.setOnCompletionListener(this);

        //音频播放出错
        player.setOnErrorListener(this);

        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (player.isPlaying()) {
            player.stop();
        }
        player.reset();
        player.release();

        phonyManager.listen(null, 0);

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        phoneListener = null;
        player = null;
        phonyManager = null;
    }

    /**
     * 播放音频
     *
     * @param isPlay 是否播放
     */
    public void playMusic(boolean isPlay) {
        isChosePlay = isPlay;
        if (player != null && isPrepared) {//装载完成的状态才能播放
            if (player.isPlaying()) {
                player.pause();
                sendMusicBroadcast(STATE_PAUSE);
            } else {
                player.start();
                sendMusicBroadcast(STATE_START);
            }
        }
    }

    /**
     * 音频资源装载完成
     *
     * @param mp MediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        isPrepared = true;
        if (isChosePlay) {
            //播放
            mp.start();
            sendMusicBroadcast(STATE_START);
        }
    }

    /**
     * 播放完成
     *
     * @param mp MediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        //重置到开时状态
        isChosePlay = false;
        mp.seekTo(0);
        sendMusicBroadcast(STATE_COMPLETION);
    }

    /**
     * 播放出错
     *
     * @param mp    MediaPlayer
     * @param what  .
     * @param extra .
     * @return .
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //重置
        isChosePlay = false;
        mp.seekTo(0);
        sendMusicBroadcast(STATE_ERROR);
        return false;
    }

    /**
     * 发送广播通知播放状态
     *
     * @param state 播放状态
     */
    private void sendMusicBroadcast(@MusicState String state) {
        Intent intent = new Intent(MusicReceiver.MUSIC_FILTER);
        intent.putExtra(MusicReceiver.EXTRA_STATE, state);
        sendBroadcast(intent);
    }

    public class MusicBinder extends Binder {
        public MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }

    /**
     * 限定发送广播的参数
     */
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({STATE_START, STATE_PAUSE, STATE_COMPLETION, STATE_ERROR})
    @interface MusicState {
    }

}
