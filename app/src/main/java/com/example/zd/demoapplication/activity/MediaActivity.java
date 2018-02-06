package com.example.zd.demoapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zd.demoapplication.R;
import com.example.zd.demoapplication.utils.TextUtil;
import com.example.zd.demobase.media.IDownloadStateListener;
import com.example.zd.demobase.media.IMusicPlayListener;
import com.example.zd.demobase.media.MusicDownloadHolder;
import com.example.zd.demobase.media.MusicPlayService;
import com.example.zd.demobase.media.MusicReceiver;

/**
 * @see #onClick(View) {@link #clickMusicDownload()} {@link #clickMusicPlay()} {@link #createService()}
 */
public class MediaActivity extends AppCompatActivity implements View.OnClickListener, IDownloadStateListener, IMusicPlayListener {

    public static final String URL = "http://mp3.183read.com/582889/58288901.mp3";
    private ImageButton btControl;
    private Button btDownload;
    private MusicPlayService musicPlayService;
    private MusicReceiver receiver;
    private boolean isPlay;//是否播放
    private boolean isBind;//是否启动过服务
    private MusicDownloadHolder musicDownloadHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        initView();
    }

    private void initView() {
        String str = "this is test Media";
        TextView text = (TextView) findViewById(R.id.tv1);

//        ImageSpan imageSpan = new ImageSpan(this, R.drawable.ic_android_black_24dp);
//        SpannableString spannableString = new SpannableString(str);
//        spannableString.setSpan(imageSpan, 17, 18, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//
//        text.setText(spannableString);

        TextUtil.setTextEndWithIcon(this, text, str, R.drawable.ic_android_black_24dp);

        btControl = (ImageButton) findViewById(R.id.img_bt);
        btControl.setOnClickListener(this);
        btControl.setImageResource(R.drawable.ic_android_black_24dp);

        btDownload = (Button) findViewById(R.id.bt_download);
        btDownload.setOnClickListener(this);

        musicDownloadHolder = MusicDownloadHolder.newInstance(this);
        musicDownloadHolder.setDownloadStateListener(this);
        musicDownloadHolder.getDownloadStates(URL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_bt:
                clickMusicPlay();
                break;
            case R.id.bt_download:
                clickMusicDownload();
                break;
            default:
                break;
        }
    }

    /**
     * 点击下载
     */
    private void clickMusicDownload() {
        btDownload.setEnabled(false);
        musicDownloadHolder.downloadMusic(URL);
    }

    /**
     * 点击播放
     */
    private void clickMusicPlay() {
        isPlay = !isPlay;
        if (musicPlayService == null) {
            createService();
        } else {
            musicPlayService.playMusic(isPlay);
        }
    }

    /**
     * bindService的服务连接类
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof MusicPlayService.MusicBinder) {
                musicPlayService = ((MusicPlayService.MusicBinder) service).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayService = null;
        }
    };

    /**
     * 创建BroadcastReceiver和service
     *
     * @see #clickMusicPlay()
     */
    private void createService() {
        receiver = new MusicReceiver();
        receiver.setMusicPlayListener(this);
        registerReceiver(receiver, new IntentFilter(MusicReceiver.MUSIC_FILTER));

        Intent intent = new Intent(this, MusicPlayService.class);
        intent.putExtra(MusicPlayService.EXTRA_URL, URL);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        isBind = true;
    }

    /**
     * 根据下载状态更新UI
     *
     * @param state 状态
     */
    @Override
    public void downloadStateChange(int state) {
        switch (state) {
            case MusicDownloadHolder.FLAG_NORMAL://未下载
                btDownload.setText("未下载");
                break;
            case MusicDownloadHolder.FLAG_WAITING://等待中
                btDownload.setText("等待中");
                break;
            case MusicDownloadHolder.FLAG_STARTED://已开始下载
                btDownload.setText("已开始下载");
                break;
            case MusicDownloadHolder.FLAG_CANCELED://已取消
                btDownload.setText("已取消");
                btDownload.setEnabled(true);
                break;
            case MusicDownloadHolder.FLAG_COMPLETED://已完成
                btDownload.setText("已完成");
                btDownload.setEnabled(false);
                break;
            case MusicDownloadHolder.FLAG_FAILED://下载失败
                btDownload.setText("下载失败");
                btDownload.setEnabled(true);
                break;
            default:
                break;
        }
    }

    /**
     * 更新ui
     *
     * @param state 播放状态
     */
    @Override
    public void updateUI(String state) {
        switch (state) {
            case MusicPlayService.STATE_ERROR://播放出错
                Toast.makeText(this, "播放出错，请稍后重试", Toast.LENGTH_SHORT).show();
            case MusicPlayService.STATE_PAUSE://播放暂停
            case MusicPlayService.STATE_COMPLETION://播放结束
                isPlay = false;
                break;
            default:
                break;
        }
        btControl.setImageResource(isPlay ?
                R.drawable.ic_file_upload_black_24dp :
                R.drawable.ic_android_black_24dp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //离开页面时讲回调监听置空，防止再回调更新UI崩溃
        if (musicDownloadHolder != null) {
            musicDownloadHolder.setDownloadStateListener(null);
            musicDownloadHolder = null;
        }

        if (isBind) {
            unregisterReceiver(receiver);
            unbindService(serviceConnection);
        }
    }
}
