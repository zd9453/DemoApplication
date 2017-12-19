package com.example.zd.demoapplication.activity.media;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadEvent;
import zlc.season.rxdownload2.entity.DownloadFlag;

/**
 * 将RxDownload的一些方法仍这里面，不然全部丢activity里面看着太乱
 * Created by zhangdong on 2017/12/14 0014.
 */

public class MusicDownloadHolder {

    private static final String TAG = "MusicDownloadHolder";
    private volatile static MusicDownloadHolder musicDownloadHolder;
    private Context context;
    private RxPermissions rxPermissions;//权限请求
    private RxDownload rxDownload;//下载
    //    private Disposable disposable;
    private DownloadStateListener downloadStateListener;

    public void setDownloadStateListener(DownloadStateListener downloadStateListener) {
        this.downloadStateListener = downloadStateListener;
    }

    private MusicDownloadHolder(Context context) {
        this.context = context.getApplicationContext();
        rxPermissions = new RxPermissions((Activity) context);
        rxDownload = RxDownload.getInstance(context);
    }

    public static MusicDownloadHolder newInstance(Context context) {
        if (musicDownloadHolder == null) {
            synchronized (MusicDownloadHolder.class) {
                if (musicDownloadHolder == null) {
                    musicDownloadHolder = new MusicDownloadHolder(context);
                }
            }
        }
        return musicDownloadHolder;
    }

    /**
     * 下载音频
     *
     * @param url url
     */
    public void downloadMusic(String url) {
        requestPermission(url);
    }

    /**
     * 请求权限
     *
     * @param url .
     */
    private void requestPermission(final String url) {
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {//有权限
                            downloading(url);
                        } else {//拒绝权限
                            Toast.makeText(context, "内存读写权限被拒绝，下载失败", Toast.LENGTH_SHORT).show();
                            if (downloadStateListener != null) {
                                downloadStateListener.downloadStateChange(DownloadFlag.FAILED);
                            }
                        }
                    }
                });
    }

    /**
     * 下载
     *
     * @param url 下载的url
     */
    private void downloading(String url) {
//        disposable =
        rxDownload.serviceDownload(url)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Log.d("MusicDownloadHolder", "accept: ------------ Download start");
                    }
                });
    }

    /**
     * 初始化下载状态
     */
    public void getDownloadStates(final String url) {
        rxDownload.receiveDownloadStatus(url)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        switch (downloadEvent.getFlag()) {
                            case DownloadFlag.NORMAL://未下载
                                Log.d(TAG, "accept: -------------- 未下载");
                                break;
                            case DownloadFlag.WAITING://等待中
                                Log.d(TAG, "accept: -------------- 等待中");
                                break;
                            case DownloadFlag.STARTED://已开始下载
                                Log.d(TAG, "accept: -------------- 已开始下载");
                                break;
                            case DownloadFlag.CANCELED://已取消
                                Log.d(TAG, "accept: -------------- 已取消");
                                deleteFile(url);
                                break;
                            case DownloadFlag.COMPLETED://已完成
                                Log.d(TAG, "accept: -------------- 已完成");
                                break;
                            case DownloadFlag.FAILED://下载失败
                                Log.d(TAG, "accept: -------------- 下载失败");
                                deleteFile(url);
                                break;
                            default:
                                break;
                        }
                        if (downloadStateListener != null) {
                            //状态回调出去
                            downloadStateListener.downloadStateChange(downloadEvent.getFlag());
                        }
                    }
                });
    }

    /**
     * 失败或取消时，删除未完成的文件
     */
    private void deleteFile(String url) {
        File[] realFiles = rxDownload.getRealFiles(url);
        if (realFiles != null) {
            rxDownload.deleteServiceDownload(url, true);
        }
    }

    /**
     * 将下载状态回调出去更新UI
     */
    public interface DownloadStateListener {
        void downloadStateChange(int state);
    }
}
