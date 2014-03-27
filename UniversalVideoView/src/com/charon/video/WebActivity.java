
package com.charon.video;

import com.charon.video.fragment.OnlineFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class WebActivity extends Activity {
    protected static final String TAG = "WebActivity";
    private WebView mWebView;
    private View mLoadingView;
    private ImageView mLoadingImage;
    private AnimationDrawable mLoadingAnimation;
    private TextView mTipTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        findView();
        initView();
    }

    private void findView() {
        mWebView = (WebView) findViewById(R.id.wv);
        mLoadingView = findViewById(R.id.loading);
        mLoadingImage = (ImageView) findViewById(R.id.iv_loading);
        mTipTextView = (TextView) findViewById(R.id.tv_loading_tip);
        mLoadingImage.setBackgroundResource(R.drawable.play_loading_anim);
        mLoadingAnimation = (AnimationDrawable) mLoadingImage.getBackground();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mTipTextView.setText(R.string.loading_web);
        showLoadingView();
        Intent intent = getIntent();
        String url = "http://3g.youku.com";
        if (intent != null) {
            url = intent.getStringExtra(OnlineFragment.URL);
        }
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition,
                    String mimetype, long contentLength) {
                Log.e(TAG, "onDownload Start :; url is :" + url + ":" + userAgent + "contentDi: "
                        + contentDisposition + ":mine:" + mimetype + ":length::" + contentLength);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "on page finished.");
                hideLoadingView();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.w(TAG, "page jump  should override url loading... url is : " + url);
                // if (FileUtils.isVideoOrAudio(url)) {
                // Intent intent = new Intent(getActivity(),
                // VideoPlayerActivity.class);
                // intent.putExtra("path", url);
                // startActivity(intent);
                // return true;
                // }
                // 内部加载，不做外部跳转
                // view.loadUrl(url);
                return false;
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideLoadingView();
                } /*else {
                    showLoadingView();
                }*/
            }
        });

        mWebView.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView != null
                        && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }
        });

        mWebView.loadUrl(url);
        mWebView.clearHistory();
    }

    private void showLoadingView() {
        mLoadingAnimation.start();
        if (mLoadingView.getVisibility() != View.VISIBLE) {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    private void hideLoadingView() {
        if (mLoadingAnimation != null && mLoadingAnimation.isRunning()) {
            mLoadingAnimation.stop();
        }
        mLoadingView.setVisibility(View.GONE);
    }
}
