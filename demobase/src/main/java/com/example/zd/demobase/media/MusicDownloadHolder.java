package com.example.zd.demobase.media;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.zd.demobase.utils.ToastUtils;
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
    /**
     * 定义下载状态------值和RxDownload的状态值保持一致
     */
    public static final int FLAG_NORMAL = 9990;//未下载
    public static final int FLAG_WAITING = 9991;//等待中
    public static final int FLAG_STARTED = 9992;//已开始下载
    public static final int FLAG_CANCELED = 9994;//已取消
    public static final int FLAG_COMPLETED = 9995;//已完成
    public static final int FLAG_FAILED = 9996;//下载失败

    private volatile static MusicDownloadHolder musicDownloadHolder;
    private RxPermissions rxPermissions;//权限请求
    private RxDownload rxDownload;//下载
    //    private Disposable disposable;
    private IDownloadStateListener downloadStateListener;

    public void setDownloadStateListener(IDownloadStateListener downloadStateListener) {
        this.downloadStateListener = downloadStateListener;
    }

    private MusicDownloadHolder(Context context) {
        Context applicationContext = context.getApplicationContext();
        rxPermissions = new RxPermissions((Activity) applicationContext);
        rxDownload = RxDownload.getInstance(applicationContext);
        context = null;
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
                            ToastUtils.showShortT("内存读写权限被拒绝，下载失败");
                            if (downloadStateListener != null) {
                                downloadStateListener.downloadStateChange(FLAG_FAILED);
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
                                if (downloadStateListener != null) {
                                    //状态回调出去
                                    downloadStateListener.downloadStateChange(FLAG_NORMAL);
                                }
                                break;
                            case DownloadFlag.WAITING://等待中
                                Log.d(TAG, "accept: -------------- 等待中");
                                if (downloadStateListener != null) {
                                    downloadStateListener.downloadStateChange(FLAG_WAITING);
                                }
                                break;
                            case DownloadFlag.STARTED://已开始下载
                                Log.d(TAG, "accept: -------------- 已开始下载");
                                if (downloadStateListener != null) {
                                    downloadStateListener.downloadStateChange(FLAG_STARTED);
                                }
                                break;
                            case DownloadFlag.CANCELED://已取消
                                Log.d(TAG, "accept: -------------- 已取消");
                                deleteFile(url);
                                if (downloadStateListener != null) {
                                    downloadStateListener.downloadStateChange(FLAG_CANCELED);
                                }
                                break;
                            case DownloadFlag.COMPLETED://已完成
                                Log.d(TAG, "accept: -------------- 已完成");
                                if (downloadStateListener != null) {
                                    downloadStateListener.downloadStateChange(FLAG_COMPLETED);
                                }
                                break;
                            case DownloadFlag.FAILED://下载失败
                                Log.d(TAG, "accept: -------------- 下载失败");
                                deleteFile(url);
                                if (downloadStateListener != null) {
                                    downloadStateListener.downloadStateChange(FLAG_FAILED);
                                }
                                break;
                            default:
                                break;
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

}
