package com.example.zd.playermodel;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.zd.playermodel.bean.MediaBean;

public class MediaPlayActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private VideoView videoView;

    public static final String EXTRA = "media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_play);
        videoView = (VideoView) findViewById(R.id.video);

        MediaBean media = (MediaBean) getIntent().getSerializableExtra(EXTRA);

        if (media != null) {
            String dateUrl = media.getDateUrl();

            videoView.setVideoURI(Uri.parse(dateUrl));

            videoView.setOnCompletionListener(this);

            videoView.setOnPreparedListener(this);

            videoView.setOnErrorListener(this);

            videoView.setMediaController(new MediaController(this));
        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.seekTo(0);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        return false;
    }
}
