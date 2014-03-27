
package com.charon.video;

import com.charon.video.fragment.OnlineFragment;
import com.charon.video.view.LiveMediaController;
import com.charon.video.view.MediaController;
import com.charon.video.view.UniversalVideoView;
import com.charon.video.view.UniversalVideoView.OnStateChangeListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

/*
 * We can use setOnPreparedListener to set the loading view or use setOnStateChangeListener to get 
 * the preparing or prepared state to control the loading view
 */
public class UniversalVideoViewActivity extends Activity {
    private static final String TAG = "UniversalVideoViewActivity";
    private UniversalVideoView mUniversalVideoView;
    // private ProgressBar pb;
    private View mLoadingView;
    private ImageView mLoadingImage;
    private AnimationDrawable mLoadingAnimation;
    /*
     * TODO Set the path variable to a streaming video URL or a local media file
     * path.
     */
    private String path;
    private boolean mLive;

    private String mBatteryLevel;

    private MediaController mMediaController;

    private BatteryReceiver mBatteryReceiver;

    private static final IntentFilter BATTERY_FILTER = new IntentFilter(
            Intent.ACTION_BATTERY_CHANGED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universalvideoview);
        findView();
        initView();
    }

    private void findView() {
        mUniversalVideoView = (UniversalVideoView) findViewById(R.id.uvv);
        mLoadingView = findViewById(R.id.loading);
        mLoadingImage = (ImageView) findViewById(R.id.iv_loading);
        mLoadingImage.setBackgroundResource(R.drawable.play_loading_anim);
        mLoadingAnimation = (AnimationDrawable) mLoadingImage.getBackground();
    }

    private void initView() {
        showLoadingView();
        Intent intent = getIntent();
        if (intent != null) {
            path = intent.getStringExtra(OnlineFragment.URL);
            mLive = !intent.getBooleanExtra(OnlineFragment.LOCAL, true);
        }
        if (mLive) {
            mMediaController = new LiveMediaController(this);
        } else {
            mMediaController = new MediaController(this);
        }
        mUniversalVideoView.setMediaController(mMediaController);
        setBatteryLevel();
        // Set the real height of this video view, so it will not use the window
        // height to calculate the ratio
        mUniversalVideoView.setVideoPath(path, mLive, mLive);
        // pb.setVisibility(View.VISIBLE);
        mUniversalVideoView.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // mUniversalVideoView.setVideoPath("mnt/sdcard/1.mp4");
                // pb.setVisibility(View.VISIBLE);
            }
        });

        mUniversalVideoView
                .setOnStateChangeListener(new OnStateChangeListener() {

                    @Override
                    public void stateChange(State state) {
                        if (state == State.PREPARING) {
                            showLoadingView();
                        } else if (state == State.PLAYING) {
                            Log.e("UniversalVideoView", "live playing and hide the loading view");
                            hideLoadingView();
                        } else if (state == State.ERROR) {
                            hideLoadingView();
                        } else if (state == State.VITAMIO_INITIALIZING) {
                            showLoadingView();
                        } else if (state == State.PREPARED) {
                            Log.e("UniversalVideoView", "live prepared....");
                        } else if (state == State.BUFFERING_START) {
                            showLoadingView();
                        } else if (state == State.BUFFERING_END) {
                            hideLoadingView();
                        }
                    }
                });

        mUniversalVideoView.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });

        mUniversalVideoView
                .setOnCompletionListener(new io.vov.vitamio.MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(io.vov.vitamio.MediaPlayer mp) {
                        finish();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        unRegistReceiver();
        if (mUniversalVideoView != null) {
            mUniversalVideoView.suspend();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        registReceiver();
        if (mUniversalVideoView != null) {
            mUniversalVideoView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mUniversalVideoView != null)
            mUniversalVideoView.stopPlayback();
    }

    private void registReceiver() {
        if (mBatteryReceiver == null) {
            mBatteryReceiver = new BatteryReceiver();
        }
        registerReceiver(mBatteryReceiver, BATTERY_FILTER);
    }

    private void unRegistReceiver() {
        if (mBatteryLevel != null) {
            unregisterReceiver(mBatteryReceiver);
            mBatteryReceiver = null;
        }
    }

    private void showLoadingView() {
        mLoadingAnimation.start();
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoadingView() {
        Log.e("@@@", "hide loading view...");
        if (mLoadingAnimation != null && mLoadingAnimation.isRunning()) {
            mLoadingAnimation.stop();
        }
        mLoadingView.setVisibility(View.GONE);
    }

    private void setBatteryLevel() {
        if (mMediaController != null)
            mMediaController.setBatteryLevel(mBatteryLevel);
    }

    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            int percent = scale > 0 ? level * 100 / scale : 0;
            if (percent > 100)
                percent = 100;
            mBatteryLevel = String.valueOf(percent) + "%";
            setBatteryLevel();
        }
    }
}
