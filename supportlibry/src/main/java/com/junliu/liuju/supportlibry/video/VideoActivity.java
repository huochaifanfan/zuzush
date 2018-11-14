package com.junliu.liuju.supportlibry.video;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;

import com.junliu.liuju.supportlibry.R;
import com.junliu.liuju.supportlibry.retrofit.BaseActivity;

/**
 * Created by liuju on 2018/4/8.
 */

public class VideoActivity extends BaseActivity{
    private VideoView videoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_video);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("http://video.eastday.com/a/180314232711655317680.html?qid=01359"));
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);

    }
}
