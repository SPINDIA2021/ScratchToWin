package com.myapps.onlysratchapp

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bytedance.sdk.openadsdk.api.init.PAGSdk
import com.bytedance.sdk.openadsdk.api.init.PAGSdk.PAGInitCallback
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.security.ProviderInstaller
import com.myapps.onlysratchapp.utils.AdManagerHolder
import com.myapps.onlysratchapp.utils.PAGAppOpenAdManager.ManagerOpenAdInteractionListener
import com.myapps.onlysratchapp.utils.PAGAppOpenAdManager.RealTimeFetchListener
import com.myapps.onlysratchapp.utils.Preferences

class App : Application() {
    private var mRequestQueue: RequestQueue? = null

    //   private PAGAppOpenAdManager mPAGAppOpenAdManager;
    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        updateAndroidSecurityProvider()
        Preferences.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val typeOfAds = resources.getString(R.string.ad_network)
        MobileAds.initialize(
            this
        ) { Log.e("TAG", "onInitializationComplete: Initialize Successfully...") }
        when (typeOfAds) {
        }
        PAGSdk.addPAGInitCallback(object : PAGInitCallback {
            override fun success() {
                Log.i(TAG, "PAGInitCallback success: addPAGInitCallback")
            }

            override fun fail(code: Int, msg: String) {
                Log.i(TAG, "PAGInitCallback fail: addPAGInitCallback")
            }
        })
        initSdk()
        //app open ad
        //  mPAGAppOpenAdManager = new PAGAppOpenAdManager(this);
    }

    fun initSdk() {
        // It is strongly recommended to call in Application #onCreate method,
        // to avoid content as null
        AdManagerHolder.doInitNewApi(this)
    }

    /**
     * fetch an app open ad.
     */
    fun fetchAd(realTimeFetchListener: RealTimeFetchListener?) {
        // mPAGAppOpenAdManager.fetchAd(realTimeFetchListener);
    }

    /**
     * Shows an app open ad.
     */
    fun showAdIfAvailable(managerOpenAdInteractionListener: ManagerOpenAdInteractionListener?) {
        //  mPAGAppOpenAdManager.showAdIfAvailable(managerOpenAdInteractionListener);
    }

    val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue!!
        }

    fun <T> addToRequestQueue(req: Request<T>, tag: String?) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue.add(req)
    }

    companion object {
        @get:Synchronized
       open var instance: App? = null
            private set
        val TAG = App::class.java.simpleName
        val context: Context?
            get() = instance
    }




    private fun updateAndroidSecurityProvider() {
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: Exception) {
            e.message
        }
    }

    fun addToRequestQueue(req: StringRequest) {
        req.tag = TAG
        requestQueue?.add(req)
    }


}