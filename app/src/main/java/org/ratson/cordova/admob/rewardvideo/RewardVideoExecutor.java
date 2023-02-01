package org.ratson.cordova.admob.rewardvideo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.ads.RewardedVideoAd;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import org.json.JSONObject;
import org.ratson.cordova.admob.AbstractExecutor;
import org.ratson.cordova.admob.AdMob;

public class RewardVideoExecutor extends AbstractExecutor {
    /**
     * RewardVideo
     */
    private RewardedAd rewardedVideoAd;
    boolean isRewardedVideoLoading = false;
    final Object rewardedVideoLock = new Object();

    public RewardVideoExecutor(AdMob plugin) {
        super(plugin);
    }

    @Override
    public String getAdType() {
        return "rewardvideo";
    }

    public PluginResult prepareAd(JSONObject options, CallbackContext callbackContext) {
        CordovaInterface cordova = plugin.cordova;
        plugin.config.setRewardVideoOptions(options);

        final CallbackContext delayCallback = callbackContext;
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CordovaInterface cordova = plugin.cordova;
                clearAd();

                Bundle extras = new Bundle();
                extras.putString("npa", "0");
                AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

                RewardedAd.load(cordova.getActivity(), plugin.config.getRewardedVideoAdUnitId(),
                        adRequest, new RewardedAdLoadCallback() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error.
                                Log.d("TAG", loadAdError.toString());
                                rewardedVideoAd = null;
                                Toast.makeText(cordova.getActivity(), "onAdFailedToLoad"+loadAdError.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdLoaded(@NonNull RewardedAd rewardedAd1) {
                                rewardedVideoAd = rewardedAd1;
                                Log.d("TAG", "Ad was loaded.");
                                Toast.makeText(cordova.getActivity(), "Ad was loaded", Toast.LENGTH_SHORT).show();
                            }
                        });




                synchronized (rewardedVideoLock) {
                    if (!isRewardedVideoLoading) {
                        isRewardedVideoLoading = true;
                        Bundle extras1 = new Bundle();
                        extras.putBoolean("_noRefresh", true);
                        AdRequest adRequest1 = new AdRequest.Builder()
                                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                                .build();

                        delayCallback.success();
                    }
                }
            }
        });
        return null;
    }



    public void clearAd() {
        if (rewardedVideoAd == null) {
            return;
        }

        rewardedVideoAd = null;
    }

    public PluginResult showAd(final boolean show, final CallbackContext callbackContext) {
        if (rewardedVideoAd == null) {
            return new PluginResult(PluginResult.Status.ERROR, "rewardedVideoAd is null, call createRewardVideo first.");
        }
        CordovaInterface cordova = plugin.cordova;

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (rewardedVideoAd instanceof RewardedAd) {
                    RewardedAd rvad = rewardedVideoAd;
                    if (rvad!=null) {
                        rvad.show(
                                cordova.getActivity(),
                                new OnUserEarnedRewardListener() {
                                    @Override
                                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                        // Handle the reward.
                                        Log.v("ADTAG", "REW The user earned the reward.");
                                        int rewardAmount = rewardItem.getAmount();
                                        String rewardType = rewardItem.getType();
                                    }
                                });
                    }
                }

                if (callbackContext != null) {
                    callbackContext.success();
                }
            }
        });

        return null;
    }

    @Override
    public void destroy() {
        this.clearAd();
    }

    public PluginResult isReady(final CallbackContext callbackContext) {
        CordovaInterface cordova = plugin.cordova;

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (rewardedVideoAd != null ) {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
                } else {
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
                }
            }
        });

        return null;
    }

    boolean shouldAutoShow() {
        return plugin.config.autoShowRewardVideo;
    }
}
