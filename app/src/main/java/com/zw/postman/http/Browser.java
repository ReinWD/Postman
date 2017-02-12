package com.zw.postman.http;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zw.postman.R;
import java.net.URL;
import java.util.Map;

/**
 * Created by 张巍 on 2017/2/12.
 */

public class Browser extends Activity {


    private Toolbar mToolBar;
    private WebView mWebView;
    private Intent mMainActicity;
    private ProgressBar mProgress;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.web_browser);

        /**ToolBar*/
        mToolBar = (Toolbar) findViewById(R.id.browser_toolbar);
        /**Intent*/
        mMainActicity = getIntent();
        /**String*/
        url = mMainActicity.getStringExtra("url");
        /**progressBar*/
        mProgress = (ProgressBar) findViewById(R.id.browser_progressbar);
        /**WebView*/
        mWebView = (WebView) findViewById(R.id.web_browser);
        mWebView.getProgress();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(mWebView.getProgress()==100){
                    mProgress.setVisibility(View.GONE);
                }else {
                    mProgress.setProgress(newProgress);
                }
            }
        });
        mWebView.loadUrl(url);
        mWebView.setVisibility(View.VISIBLE);


    }

    private void setContains(URL url) {
        mWebView.loadUrl(url.getPath());
    }

    private void setContains(URL url, Map<String, String> additionalHttpHeaders) {
        mWebView.loadUrl(url.getPath(), additionalHttpHeaders);
    }

    /**
     * Disable JavaScript in order to save power.
     */
    @Override
    protected void onStop() {
        super.onStop();
    }
}
