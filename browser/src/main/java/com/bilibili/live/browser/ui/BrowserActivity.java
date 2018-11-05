package com.bilibili.live.browser.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bilibili.live.base.RxBaseActivity;
import com.bilibili.live.base.constants.ParamsConstant;
import com.bilibili.live.base.mvp.BasePresenter;
import com.bilibili.live.base.widget.CircleProgressView;
import com.bilibili.live.browser.R;
import com.bilibili.live.browser.R2;

import butterknife.BindView;

/**
 * Created by jason on 2018/11/5.
 */

public class BrowserActivity extends RxBaseActivity {

    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    @BindView(R2.id.webView)
    WebView mWebView;

    @BindView(R2.id.loading_progress)
    CircleProgressView mCircleProgressView;

    private String mUrl;
    private String mTitle;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.browser_activity_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(ParamsConstant.EXTRA_URL);
            mTitle = intent.getStringExtra(ParamsConstant.EXTRA_TITLE);
        }
        setupWebView();
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void setupWebView() {
        mCircleProgressView.spin();
        final WebSettings webSettings = mWebView.getSettings();
        WebViewClientBase webViewClient = new WebViewClientBase();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebView.getSettings().setBlockNetworkImage(true);
        mWebView.setWebViewClient(webViewClient);
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog
                        .Builder(BrowserActivity.this)
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }
        });
        mWebView.loadUrl(mUrl);
    }

    public class WebViewClientBase extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(mCircleProgressView != null) {
                mCircleProgressView.setVisibility(View.GONE);
                mCircleProgressView.stopSpinning();
            }
            mWebView.getSettings().setBlockNetworkImage(false);
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            String errorHtml = "<html><body><h2>找不到网页</h2></body></html>";
            view.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack() && mWebView.copyBackForwardList().getSize() > 0
                && !mWebView.getUrl().equals(mWebView.copyBackForwardList()
                .getItemAtIndex(0).getOriginalUrl())) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
