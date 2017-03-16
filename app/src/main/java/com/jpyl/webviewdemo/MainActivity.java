package com.jpyl.webviewdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DownloadListener {
    private WebView webView;
    private Button back;
    private Button refresh;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webview);
        back = (Button) findViewById(R.id.back);
        refresh = (Button) findViewById(R.id.refresh);
        editText = (EditText) findViewById(R.id.edt);
        webView.loadUrl("https://m.baidu.com/?from=844b&vit=fps\n");
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        //   settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

// 设置可以支持缩放
        settings.setSupportZoom(true);
//// 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);
//扩大比例的缩放
        settings.setUseWideViewPort(true);
//支持javascript

        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                editText.setText(view.getUrl());
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.i("M-TAG", "" + newProgress);

                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                view.loadUrl("file://android_asset/erro.html");
                super.onReceivedError(view, request, error);
            }
        });
        webView.setDownloadListener(this);
        back.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                webView.goBack();
                break;
            case R.id.refresh:
                webView.reload();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Log.i("M-TAG", "" + url);
        //if (url.endsWith(".apk"))
        new DownThread(url).start();
//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
    }

    @Override
//设置回退
//覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }
//    websetting.setDomStorageEnabled(true);    //开启DOM形式存储
//
//    websetting.setDatabaseEnabled(true);   //开启数据库形式存储
//
//    String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();   //缓存数据的存储地址
//
//    websetting.setAppCachePath(appCacheDir);
//
//    websetting.setAppCacheEnabled(true);  //开启缓存功能
//
//    websetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);      //缓存模式
//
//    websetting.setAllowFileAccess(true);
//
//    websetting.setAppCacheMaxSize(size);      //设置缓存文件大小，但现在已不再提倡这个方法
//
//    关于缓存模式，有五种，根据不同需求可以进行设置：
//
//    LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
//
//    LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
//
//    LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
//
//    LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
//
//            LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
}
