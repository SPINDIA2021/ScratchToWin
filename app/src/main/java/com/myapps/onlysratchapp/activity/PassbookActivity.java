package com.myapps.onlysratchapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.fragments.PassbookFragment;

public class PassbookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_Login, PassbookFragment.newInstance()).commit();

    }
}