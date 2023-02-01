package com.myapps.onlysratchapp.kotak

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.activity.MainActivity
import com.myapps.onlysratchapp.utils.Constant.alertDialog
import kotlinx.android.synthetic.main.activity_mlm.*


class KotaKInnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotak)

        initView()
    }

    private fun initView() {
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
      /*  mlm_webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.i("FragmentActivity.TAG", "Processing webview url click...")
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.i("FragmentActivity.TAG", "Finished loading URL: $url")

            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Log.e("FragmentActivity.TAG", "Error: $description")
                Toast.makeText(this@KotaKInnerActivity, "Oh no! $description", Toast.LENGTH_SHORT).show()

            }
        })*/
        mlm_webview.loadUrl("https://inr.deals/nLiDpx")
        mlm_webview.getSettings().setJavaScriptEnabled(true)
        mlm_webview!!.setInitialScale(1)
        mlm_webview!!.getSettings().setLoadWithOverviewMode(true)
        mlm_webview!!.getSettings().setUseWideViewPort(true)
        mlm_webview!!.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY)
        mlm_webview!!.setScrollbarFadingEnabled(false)
        mlm_webview!!.setBackgroundColor(Color.TRANSPARENT)
        mlm_webview!!.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)

        mlm_webview.setWebViewClient(object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url!!)
                return false // then it is not handled by default action
            }
        })

        lay_headermlm.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }
    }
    }