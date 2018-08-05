package com.fenjin.sandfactory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.sandfactory.viewmodel.PlayViewModel;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.IOException;

public class PlayActivity extends BaseActivity {

    private PlayViewModel viewModel;

    private QMUITipDialog loadingDialog;


    // 播放器的对象
    private KSYMediaPlayer ksyMediaPlayer;
    // 播放SDK提供的监听器
    // 播放器在准备完成，可以开播时会发出onPrepared回调
    //private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    // 播放完成时会发出onCompletion回调
//    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
//    // 播放器遇到错误时会发出onError回调
//    private IMediaPlayer.OnErrorListener mOnErrorListener;
//    private IMediaPlayer.OnInfoListener mOnInfoListener;
//    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener;
//    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener;
    // SurfaceView需在Layout中定义，此处不在赘述
    private SurfaceView mVideoSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if(ksyMediaPlayer != null) {
                ksyMediaPlayer.setDisplay(holder);
                ksyMediaPlayer.setScreenOnWhilePlaying(true);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // 此处非常重要，必须调用!!!
            if(ksyMediaPlayer != null) {
                ksyMediaPlayer.setDisplay(null);
            }
        }
    };

    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            loadingDialog.dismiss();
            if(ksyMediaPlayer != null) {
                // 设置视频伸缩模式，此模式为裁剪模式
                ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                // 开始播放视频
                ksyMediaPlayer.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ksyMediaPlayer = new KSYMediaPlayer.Builder(this.getApplicationContext()).build();

//        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
//        ksyMediaPlayer.setOnInfoListener(mOnInfoListener);
//        ksyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
//        ksyMediaPlayer.setOnErrorListener(mOnErrorListener);
//        ksyMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener);

        setContentView(R.layout.activity_play);

        viewModel = ViewModelProviders.of(this).get(PlayViewModel.class);

        QMUITopBar topBar = findViewById(R.id.top_bar);
        topBar.addLeftBackImageButton()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        mVideoSurfaceView  = findViewById(R.id.surface_view);
        mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);

        Intent intent = getIntent();
        topBar.setTitle(intent.getStringExtra("channelName"));
        int channel = intent.getIntExtra("channel", -1);

        init();


        viewModel.getChannel(channel);

        viewModel.touchChannel(channel);
    }

    private void init(){
        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    loadingDialog = new QMUITipDialog.Builder(PlayActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("正在加载")
                            .create();
                    loadingDialog.setCancelable(true);
                    loadingDialog.show();
                }else {
                    if (loadingDialog != null && loadingDialog.isShowing()){
                        loadingDialog.dismiss();
                    }
                }
            }
        });

        viewModel.urlLive.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                LogUtils.d("url：" + s);

                if (!TextUtils.isEmpty(s)){
                    loadingDialog = new QMUITipDialog.Builder(PlayActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("正在缓冲")
                            .create();
                    loadingDialog.setCancelable(true);
                    loadingDialog.show();
                    try {
                        ksyMediaPlayer.setDataSource(s);
                        ksyMediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ksyMediaPlayer != null){
            ksyMediaPlayer.stop();
        }
    }
}
