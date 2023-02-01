package com.myapps.onlysratchapp.kotak;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.myapps.onlysratchapp.R;

public class KotakWebviewActivity extends AppCompatActivity {
    WebView webView;
    RelativeLayout layHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotak);
        initView();
    }

    private void initView() {
        
        webView=findViewById(R.id.mlm_webview);
        layHeader=findViewById(R.id.lay_headermlm);

      //  webView.loadUrl("https://inr.deals/nLiDpx");
        webView.loadUrl("https://www.kotak811.com/open-zero-balance-savings-account?source=811NewILSIX&banner=ILAFSix&pubild=normal811_3001_61213");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);


        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
    }
}
