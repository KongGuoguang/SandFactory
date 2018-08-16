package com.fenjin.sandfactory;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.sandfactory.viewmodel.PlayViewModel;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.io.IOException;

public class PlayActivity extends BaseActivity {

    private TextView title;

    private PlayViewModel viewModel;

    private QMUITipDialog loadingDialog;


    // 播放器的对象
    private KSYMediaPlayer ksyMediaPlayer;

    private SurfaceView mVideoSurfaceView;

    private ImageView enlarge, shrink;//放大、缩小按钮

    private String url;//视频地址

    //视频宽高比
    private float aspectRatio = ((float)704) / ((float) 576);

    private int screenWidth, screenHeight;//屏幕宽高

    private boolean isPlayingHalfScreen;//半屏播放标识

    private boolean isPlayingFullScreen;//全屏播放标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);

        setContentView(R.layout.activity_play);

        viewModel = ViewModelProviders.of(this).get(PlayViewModel.class);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        initPlayer();

        initView();

        registerObserver();

        loadVideo();

        //registerOrientationListener();

    }

    /**
     * 初始化播放器
     */
    private void initPlayer(){
        ksyMediaPlayer = new KSYMediaPlayer.Builder(this.getApplicationContext()).build();


        ksyMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                LogUtils.d("PlayActivity", "onPrepared()");
                if(ksyMediaPlayer != null) {
                    // 设置视频伸缩模式，此模式为填充模式，会有黑边
                    ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    // 开始播放视频
                    ksyMediaPlayer.start();
                }
            }
        });
        ksyMediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int info, int i1) {
                LogUtils.d("PlayActivity", "onInfo(), info = " + info);
                switch (info) {
                    case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        LogUtils.d("PlayActivity", "开始缓冲数据()");
                        break;
                    case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        LogUtils.d("PlayActivity", "数据缓冲完毕");
                        break;
                    case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                        LogUtils.d("PlayActivity", "开始播放音频");
                        break;
                    case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        LogUtils.d("PlayActivity", "开始渲染视频");
                        loadingDialog.dismiss();
                        enlarge.setVisibility(View.VISIBLE);
                        isPlayingHalfScreen = true;
                        break;
                    case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                        LogUtils.d("PlayActivity", "调用reload接口");
                        // 播放SDK有做快速开播的优化，在流的音视频数据交织并不好时，可能只找到某一个流的信息
                        // 当播放器读到另一个流的数据时会发出此消息通知
                        // 请务必调用reload接口
                        if(ksyMediaPlayer != null)
                            ksyMediaPlayer.reload(url, false);
                        break;
                    case KSYMediaPlayer.MEDIA_INFO_RELOADED:
                        LogUtils.d("PlayActivity", "reload成功");
                        break;
                }
                return false;
            }
        });

        ksyMediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int what, int i1) {
                LogUtils.d("PlayActivity", "onError(), what = " + what);
                loadingDialog.dismiss();
                switch (what)
                {
                    case KSYMediaPlayer.MEDIA_ERROR_IO:
                        showToast("视频播放失败，请返回重试");
                        break;
                }
                return false;
            }
        });
    }

    private void initView(){
        title = findViewById(R.id.tv_title);

        ImageView back = findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mVideoSurfaceView  = findViewById(R.id.surface_view);
        SurfaceHolder mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
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
        });

        enlarge = findViewById(R.id.iv_enlarge);
        enlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playFullScreen();
            }
        });

        shrink = findViewById(R.id.iv_shrink);
        shrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playHalfScreen();
            }
        });

        RelativeLayout.LayoutParams params  = (RelativeLayout.LayoutParams) mVideoSurfaceView.getLayoutParams();
        params.height = (int) (screenWidth / aspectRatio);
        mVideoSurfaceView.setLayoutParams(params);
    }

    private void registerObserver(){
        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    loadingDialog = new QMUITipDialog.Builder(PlayActivity.this)
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord("获取视频地址")
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

                LogUtils.d("PlayActivity", "rtmp url：" + s);



                if (!TextUtils.isEmpty(s)){
                    url = s;
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

    private void loadVideo(){
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("channelName"));
        int channel = intent.getIntExtra("channel", -1);
        viewModel.getChannel(channel);
        viewModel.touchChannel(channel);
    }

    private void registerOrientationListener(){
        OrientationEventListener mOrEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                LogUtils.d("PlayActivity", "rotation = " + rotation);
                if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
                    if (isPlayingFullScreen){
                        playHalfScreen();
                    }
                } else if (rotation > 225) {
                    if (isPlayingHalfScreen){
                        playFullScreen();
                    }
                }

            }
        };
        mOrEventListener.enable();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ksyMediaPlayer != null){
            ksyMediaPlayer.stop();
            ksyMediaPlayer.release();
        }
    }


    private void playFullScreen(){
        //hideNavigationBar();

        RelativeLayout.LayoutParams params  = (RelativeLayout.LayoutParams) mVideoSurfaceView.getLayoutParams();
        params.height = screenHeight;
        mVideoSurfaceView.setLayoutParams(params);

        ksyMediaPlayer.setRotateDegree(270);
        // 设置视频伸缩模式，此模式为全屏模式，视频宽高比例与手机频幕宽高比例不一致时，使用此模式视频播放会有画面拉伸
        ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_NOSCALE_TO_FIT);

        enlarge.setVisibility(View.GONE);
        shrink.setVisibility(View.VISIBLE);

        isPlayingFullScreen = true;
        isPlayingHalfScreen = false;
    }

    private void playHalfScreen(){
        //showNavigationBar();

        RelativeLayout.LayoutParams params  = (RelativeLayout.LayoutParams) mVideoSurfaceView.getLayoutParams();
        params.height = (int) (screenWidth / aspectRatio);
        mVideoSurfaceView.setLayoutParams(params);

        ksyMediaPlayer.setRotateDegree(0);
        // 设置视频伸缩模式，此模式为填充模式，会有黑边
        ksyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);

        enlarge.setVisibility(View.VISIBLE);
        shrink.setVisibility(View.GONE);

        isPlayingHalfScreen = true;
        isPlayingFullScreen = false;
    }

    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
