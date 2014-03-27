
package com.charon.video;

import com.charon.video.util.DenstyUtil;
import com.charon.video.view.MediaController;
import com.charon.video.view.VideoView;
import com.charon.video.view.VideoView.OnStateChangeListener;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

/*
 * We can use setOnPreparedListener to set the loading view or use setOnStateChangeListener to get 
 * the preparing or prepared state to control the loading view
 */
public class VideoViewActivity extends Activity {
    private static final String TAG = "VideoViewActivity";
    private VideoView mVideoView;
    private RelativeLayout rl_video;
    private View mLoadingView;
    private ImageView mLoadingImage;
    private AnimationDrawable mLoadingAnimation;
    private FrameLayout fl_root;

    /*
     * TODO Set the path variable to a streaming video URL or a local media file
     * path.
     */
//    private String path = "http://3gs.ifeng.com/userfiles/video01/2014/03/15/1769126-280-068-1442.mp4";
    private String path = "http://123.126.104.24/sohu/vod/MS8Ym5kXmRqZF7S4GCUznLs0uRg4mrWmGD0YtbQiuBmXNrgaHH.mp4?key=XYD5muZxNp0cbZi8sgPJfS3VQJRnCWNqBdxj4A..&n=2&a=4002&cip=61.135.169.74&uid=1395903424307866&plat=17&pg=1&pt=5&ch=tv&vid=1678763&prod=h5&eye=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLandScape(this)) {
            setFullscreen(true);
        }

        setContentView(R.layout.activity_videoview);
        findView();
        initView();
    }

    private void findView() {
        mVideoView = (VideoView) findViewById(R.id.uvv);
        mLoadingView = findViewById(R.id.loading);
        mLoadingImage = (ImageView) findViewById(R.id.iv_loading);
        mLoadingImage.setBackgroundResource(R.drawable.play_loading_anim);
        mLoadingAnimation = (AnimationDrawable) mLoadingImage.getBackground();
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
    }

    private void initView() {
        mVideoView.setMediaController(new MediaController(this));
        // Set the real height of this video view, so it will not use the window
        // height to calculate the ratio
        if (!isLandScape(this)) {
            mVideoView.setRealHeight((int) DenstyUtil.convertDpToPixel(240,
                    VideoViewActivity.this));
        }
        mVideoView.setVideoPath(path);
        // pb.setVisibility(View.VISIBLE);
        mVideoView.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // mVideoView.setVideoPath("mnt/sdcard/1.mp4");
                // pb.setVisibility(View.VISIBLE);
            }
        });

        // mVideoView.setOnPreparedListener(new OnPreparedListener() {
        //
        // @Override
        // public void onPrepared(MediaPlayer mp) {
        // pb.setVisibility(View.GONE);
        // }
        // });

        mVideoView.setOnStateChangeListener(new OnStateChangeListener() {

            @Override
            public void stateChange(State state) {
                if (state == State.STATE_PREPARING) {
                    showLoadingView();
                } else if (state == State.STATE_PREPARED) {
                    hideLoadingView();
                } else if (state == State.STATE_ERROR) {
                    hideLoadingView();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "change to landscape..");
            // When is landscape, make it full screen, so the status bar is
            // unVisible.
            setFullscreen(true);
            // Make other view invisible
            int count = fl_root.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = fl_root.getChildAt(i);

                if (v.getId() == R.id.rl_video_root) {
                    continue;
                }
                v.setVisibility(View.GONE);
            }

            LayoutParams layoutParams = (LayoutParams) rl_video
                    .getLayoutParams();
            layoutParams.width = LayoutParams.MATCH_PARENT;
            layoutParams.height = LayoutParams.MATCH_PARENT;
            rl_video.setLayoutParams(layoutParams);
            rl_video.requestLayout();

            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0, true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setFullscreen(false);
            int count = fl_root.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = fl_root.getChildAt(i);
                if (v.getId() == R.id.rl_video_root) {
                    LayoutParams params = (LayoutParams) rl_video
                            .getLayoutParams();
                    params.height = (int) DenstyUtil.convertDpToPixel(240,
                            VideoViewActivity.this);
                    params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                    params.gravity = Gravity.CENTER;
                    rl_video.setLayoutParams(params);
                    rl_video.requestLayout();
                    continue;
                }
                v.setVisibility(View.VISIBLE);
            }

            // Set the real height of this video view, so it will not use the
            // window height to calculate the ratio
            mVideoView.setRealHeight((int) DenstyUtil.convertDpToPixel(240,
                    VideoViewActivity.this));
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0, true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (mVideoView != null)
            mVideoView.suspend();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mVideoView != null)
            mVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mVideoView != null)
            mVideoView.stopPlayback();
    }

    public static boolean isLandScape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void setFullscreen(boolean on) {
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    private void showLoadingView() {
        mLoadingAnimation.start();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoadingView() {
        if (mLoadingAnimation != null && mLoadingAnimation.isRunning()) {
            mLoadingAnimation.stop();
        }
        mLoadingView.setVisibility(View.GONE);
    }
}
