package com.myapps.onlysratchapp.category;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;

import java.util.ArrayList;

public class InnerCategoryActivity extends AppCompatActivity implements CategoryContract.View {
    private CategoryContract.Presenter presenter;
    RecyclerView recyclerViewCategory;
    WebView webView;

    private Toolbar toolbar;
    private TextView points_textView;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();

    }

    private void initView() {
        recyclerViewCategory=findViewById(R.id.recyclerview_category);
        webView=findViewById(R.id.webview_category);
        toolbar = findViewById(R.id.toolbar);

        url=getIntent().getStringExtra("url");


        recyclerViewCategory.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        try {
            ((AppCompatActivity) this).setSupportActionBar(toolbar);
            ((AppCompatActivity) this).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Category");

            points_textView = toolbar.findViewById(R.id.points_text_in_toolbar);
            setPointsText();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        new CategoryPresenter(Injection.provideLoginRepository(this), this);

        webView.loadUrl(url);

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

    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(this, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }

    @Override
    public void categoryResponse(ArrayList<CategoryResponse> categoryResponse) {



    }



    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.presenter=presenter;
    }
}