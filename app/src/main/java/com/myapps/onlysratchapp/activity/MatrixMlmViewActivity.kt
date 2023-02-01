package com.myapps.onlysratchapp.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

import com.myapps.onlysratchapp.R

import kotlinx.android.synthetic.main.activity_mlm.*


class MatrixMlmViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mlm)

        initView()
    }

    private fun initView() {
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        mlm_webview.loadUrl("https://mewar.sahayatamoney.com/assets/mlm/matrix2.php")
        mlm_webview.getSettings().setJavaScriptEnabled(true)
        mlm_webview!!.setInitialScale(1)
        mlm_webview!!.getSettings().setLoadWithOverviewMode(true)
        mlm_webview!!.getSettings().setUseWideViewPort(true)
        mlm_webview!!.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY)
        mlm_webview!!.setScrollbarFadingEnabled(false)
        mlm_webview!!.setBackgroundColor(Color.TRANSPARENT)
        mlm_webview!!.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)



        lay_headermlm.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }
    }
    }