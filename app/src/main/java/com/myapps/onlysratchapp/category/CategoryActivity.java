package com.myapps.onlysratchapp.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryContract.View {
    private CategoryContract.Presenter presenter;
    RecyclerView recyclerViewCategory;
    WebView webView;
    private Toolbar toolbar;
    private TextView points_textView;
    ArrayList<CategoryResponse> categoryResponseArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();

    }

    private void initView() {
        recyclerViewCategory=findViewById(R.id.recyclerview_category);
        toolbar = findViewById(R.id.toolbar);
        webView=findViewById(R.id.webview_category);

        recyclerViewCategory.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);

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

        presenter.getCategory(this);
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

        if (categoryResponse.size()!=0)
        {
            categoryResponseArrayList=categoryResponse;
            recyclerViewCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
            CategoryAdapter adapter=new CategoryAdapter(categoryResponse, getApplicationContext(),itemClick);
            recyclerViewCategory.setAdapter(adapter);
        }

    }

    View.OnClickListener itemClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int i= (int) v.getTag();

            Intent intent = new Intent(getApplicationContext(), InnerCategoryActivity.class);
            intent.putExtra("url",categoryResponseArrayList.get(i).getUrl());
            startActivity(intent);
        }
    };

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.presenter=presenter;
    }
}