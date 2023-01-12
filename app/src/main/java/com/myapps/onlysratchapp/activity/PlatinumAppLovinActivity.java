package com.myapps.onlysratchapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyReward;
import com.adcolony.sdk.AdColonyRewardListener;
import com.adcolony.sdk.AdColonyZone;
import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.adview.AppLovinInterstitialAd;
import com.applovin.adview.AppLovinInterstitialAdDialog;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinAdSize;
import com.applovin.sdk.AppLovinAdVideoPlaybackListener;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.ReferUserResponse;
import com.myapps.onlysratchapp.models.TransactionResponse;
import com.myapps.onlysratchapp.models.VerifyMobileResponse;
import com.myapps.onlysratchapp.models.VerifyOTPResponse;
import com.myapps.onlysratchapp.models.VerifyUserResponse;
import com.myapps.onlysratchapp.models.WithdrawalFirstResponse;
import com.myapps.onlysratchapp.models.WithdrawalResponse;
import com.myapps.onlysratchapp.services.PointsService;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;
import com.myapps.onlysratchapp.utils.LockableScrollView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;

public class PlatinumAppLovinActivity extends AppCompatActivity implements ScratchListener, LoginContract.View , AppLovinAdLoadListener, AppLovinAdVideoPlaybackListener, AppLovinAdDisplayListener, AppLovinAdClickListener, AppLovinAdRewardListener {

    private LoginContract.Presenter presenter;
    private LinearLayout adLayout;

    private Toolbar toolbar;
    private TextView scratch_count_textView, points_textView, points_text;
    boolean first_time = true, scratch_first = true;
    private int scratch_count = 1;
    private final String TAG = "Silver Fragment";
    private String random_points;
    public int poiints = 0;
    public boolean rewardShow = true, interstitialShow = true;
    ScratchCardLayout scratchCardLayout;
    private LockableScrollView scrollView;
    ProgressDialog progressDialog;

    private AppLovinIncentivizedInterstitial rewardedAd;

    private AppLovinAd currentAd;
    private AppLovinInterstitialAdDialog interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_platinum);


        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                rewardedAd = AppLovinIncentivizedInterstitial.create( getApplicationContext() );
                rewardedAd.preload( PlatinumAppLovinActivity.this );

                interstitialAd = AppLovinInterstitialAd.create( AppLovinSdk.getInstance( PlatinumAppLovinActivity.this ), PlatinumAppLovinActivity.this );
                interstitialAd.setAdDisplayListener( PlatinumAppLovinActivity.this );
                interstitialAd.setAdClickListener( PlatinumAppLovinActivity.this );
                interstitialAd.setAdVideoPlaybackListener( PlatinumAppLovinActivity.this ); // This will only ever be used if you have video ads enabled.
                AppLovinSdk.getInstance( getApplicationContext() ).getAdService().loadNextAd( AppLovinAdSize.INTERSTITIAL, PlatinumAppLovinActivity.this );

            }
        } );


        initView();

    }

    private void initView() {

        adLayout = findViewById(R.id.banner_container_platinum);
        toolbar = findViewById(R.id.toolbar);
        points_text = findViewById(R.id.textView_points_show_platinum);
        scratch_count_textView = findViewById(R.id.limit_text_platinum);
        scratchCardLayout = findViewById(R.id.scratch_view_layout_platinum);
        scratchCardLayout.setScratchListener(this);
        scrollView = findViewById(R.id.scroll);




        new LoginPresenter(Injection.provideLoginRepository(this), this);
        try {
            ((AppCompatActivity) this).setSupportActionBar(toolbar);
            ((AppCompatActivity) this).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) this).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Platinum Scratch");
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

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait while ad is loading");



      /*  if (Constant.isNetworkAvailable(this)) {
            presenter.getAdKeys(this);

        } else {
            Constant.showInternetErrorDialog(this, getResources().getString(R.string.no_internet_connection));
        }

*/
        onInit();

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

    private void onInit() {

        String scratchCount = Constant.getString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM);
        if (scratchCount.equals("0")) {
            scratchCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (scratchCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(PlatinumAppLovinActivity.this, Constant.LAST_DATE_SCRATCH_PLATINUM);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(getResources().getString(R.string.scratch_count));
                Constant.setString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM, getResources().getString(R.string.scratch_count));
                Constant.setString(PlatinumAppLovinActivity.this, Constant.LAST_DATE_SCRATCH_PLATINUM, currentDate);
            } else {
                Log.e("TAG", "onInit: last date not empty part");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date current_date = sdf.parse(currentDate);
                    Date lastDate = sdf.parse(last_date);
                    long diff = current_date.getTime() - lastDate.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Difference" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(PlatinumAppLovinActivity.this, Constant.LAST_DATE_SCRATCH_PLATINUM, currentDate);
                        Constant.setString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM, getResources().getString(R.string.scratch_count));
                        setScratchCount(Constant.getString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM));
                        Log.e("TAG", "onClick: today date added to preference" + currentDate);
                    } else {
                        setScratchCount("0");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("TAG", "onInit: scracth card in preference part");
            setScratchCount(scratchCount);
        }
    }

    private void setScratchCount(String string) {
        if (string != null || !string.equalsIgnoreCase(""))
            scratch_count_textView.setText("Your Today Scratch Count left = " + string);
    }




    private void showDialogPoints(final int addOrNoAddValue, final String points, final int counter) {
        final Dialog dialog = new Dialog(PlatinumAppLovinActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        if (Constant.isNetworkAvailable(PlatinumAppLovinActivity.this)) {
            if (addOrNoAddValue == 1) {
                if (points.equals("0")) {
                    Log.e("TAG", "showDialogPoints: 0 points");
                    imageView.setImageResource(R.drawable.sad);
                    title_text.setText(getResources().getString(R.string.better_luck));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    add_btn.setText(getResources().getString(R.string.okk));
                } else {
                    Log.e("TAG", "showDialogPoints: points");
                    imageView.setImageResource(R.drawable.congo);
                    title_text.setText(getResources().getString(R.string.you_won));
                    points_text.setVisibility(View.VISIBLE);
                    points_text.setText(points);
                    callServiceConvertPoints(points);
                    add_btn.setText(getResources().getString(R.string.add_to_wallet));
                }
            } else {
                Log.e("TAG", "showDialogPoints: chance over");
                imageView.setImageResource(R.drawable.donee);
                title_text.setText(getResources().getString(R.string.today_chance_over));
                points_text.setVisibility(View.GONE);
                add_btn.setText(getResources().getString(R.string.okk));
            }
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    first_time = true;
                    scratch_first = true;
                    scratchCardLayout.resetScratch();
                    poiints = 0;
                    if (addOrNoAddValue == 1) {
                        if (points.equals("0")) {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM, current_counter);
                            setScratchCount(Constant.getString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM));
                            dialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM, current_counter);
                            setScratchCount(Constant.getString(PlatinumAppLovinActivity.this, Constant.SCRATCH_COUNT_PLATINUM));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;
                                Constant.addPoints(PlatinumAppLovinActivity.this, finalPoint, 0);
                                setPointsText();
                            } catch (NumberFormatException ex) {
                                Log.e("TAG", "onScratchComplete: " + ex.getMessage());
                            }
                            dialog.dismiss();
                        }
                    } else {
                        dialog.dismiss();
                    }
                    if (scratch_count == Integer.parseInt(getResources().getString(R.string.rewarded_and_interstitial_ads_between_count))) {
                        if (rewardShow) {
                            Log.e(TAG, "onReachTarget: rewaded ads showing method");
                            showDailog();
                            rewardShow = false;
                            interstitialShow = true;
                            scratch_count = 1;
                        } else if (interstitialShow) {
                            Log.e(TAG, "onReachTarget: interstital ads showing method");
                            showInterstitialAds();
                            rewardShow = true;
                            interstitialShow = false;
                            scratch_count = 1;
                        }
                    } else {
                        scratch_count += 1;
                    }
                }
            });
        } else {
            Constant.showInternetErrorDialog(PlatinumAppLovinActivity.this, getResources().getString(R.string.no_internet_connection));
        }
        dialog.show();
    }




    public void showDailog() {
        final Dialog dialog = new Dialog(PlatinumAppLovinActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        AppCompatButton cancel_btn = dialog.findViewById(R.id.cancel_btn);
        cancel_btn.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.watched);
        add_btn.setText("Yes");
        title_text.setText("Watch Full Video");
        points_text.setText("To Unlock this Reward Points");

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                progressDialog.show();
                showRewardedVideo();


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (poiints != 0) {
                    String po = Constant.getString(PlatinumAppLovinActivity.this, Constant.USER_POINTS);
                    if (po.equals("")) {
                        po = "0";
                    }
                    int current_Points = Integer.parseInt(po);
                    int finalPoints = current_Points - poiints;
                    Constant.setString(PlatinumAppLovinActivity.this, Constant.USER_POINTS, String.valueOf(finalPoints));
                    Intent serviceIntent = new Intent(PlatinumAppLovinActivity.this, PointsService.class);
                    serviceIntent.putExtra("points", Constant.getString(PlatinumAppLovinActivity.this, Constant.USER_POINTS));
                    PlatinumAppLovinActivity.this.startService(serviceIntent);
                    setPointsText();
                }
            }
        });

        dialog.show();
    }




    @Override
    public void onScratchComplete() {
        if (first_time) {
            first_time = false;
            scrollView.setScrollingEnabled(true);
            Log.e("onScratch", "Complete");
            Log.e("onScratch", "Complete" + random_points);
            String count = scratch_count_textView.getText().toString();
            String[] counteee = count.split("=", 2);
            String ran = counteee[1];
            Log.e(TAG, "onScratchComplete: " + ran);
            int counter = Integer.parseInt(ran.trim());
            if (counter == 0) {
                showDialogPoints(0, "0", counter);
            } else {
                showDialogPoints(1, points_text.getText().toString(), counter);
            }
        }
    }

    @Override
    public void onScratchProgress(@NonNull ScratchCardLayout scratchCardLayout, int i) {
        if (scratch_first) {
            scratch_first = false;
            scrollView.setScrollingEnabled(false);
            Log.e(TAG, "onScratchStarted: " + random_points);
            if (Constant.isNetworkAvailable(PlatinumAppLovinActivity.this)) {
                random_points = "";
                Random random = new Random();
                random_points = String.valueOf(random.nextInt(20));
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onScratchStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(PlatinumAppLovinActivity.this, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void callServiceConvertPoints(String points) {
        String refercode = Constant.getString(this, Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }
        presenter.saveCoins(refercode,points,this);
    }

    @Override
    public void onScratchStarted() {

    }

    @Override
    public void loginResponse(VerifyMobileResponse loginResponse) {

    }

    @Override
    public void verifyUserResponse(VerifyUserResponse verifyUserResponse) {

    }

    @Override
    public void getReferUserResponse(ReferUserResponse referUserResponse, String Message) {

    }

    @Override
    public void verifyFirstOTPResponse(VerifyOTPResponse verifyOTPResponse) {

    }

    @Override
    public void verifySecondOTPResponse(VerifyOTPResponse verifyOTPResponse) {

    }

    String rewardkey,interestailKey,bannerkey;
    @Override
    public void adKeysResponse(ArrayList<AdKeysResponse> adKeysResponse) {

        /*for (int i=0;i<adKeysResponse.size();i++)
        {
            if (adKeysResponse.get(i).getId().equals("1"))
            {
                bannerkey=adKeysResponse.get(i).getApikey();
            }
            if (adKeysResponse.get(i).getId().equals("2"))
            {
                interestailKey=adKeysResponse.get(i).getApikey();
                Constant.loadInterstitialAd(adKeysResponse.get(i).getApikey());
            }
            if (adKeysResponse.get(i).getId().equals("3"))
            {
                rewardkey=adKeysResponse.get(i).getApikey();
                Constant.loadRewardedVideo(getActivity(),adKeysResponse.get(i).getApikey());
            }
        }
        String typeOfAds = getResources().getString(R.string.ad_network);
        if (typeOfAds.equals("admob")) {
            final AdView adView = new AdView(activity);
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            adView.setAdUnitId(bannerkey);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adLayout.addView(adView);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.v("admob", "error" + loadAdError.getMessage());
                }
            });
            adView.loadAd(new AdRequest.Builder().build());
        } else if (typeOfAds.equals("facebook")) {
            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(activity, getResources().getString(R.string.facebook_banner_ads), com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
            adView.loadAd();
            adLayout.addView(adView);
        }*/
    }

    @Override
    public void withdrawResponse(WithdrawalResponse withdrawalResponse) {

    }

    @Override
    public void withdrawalFirstResponse(WithdrawalFirstResponse withdrawalFirstResponse) {

    }

    @Override
    public void withdrawalSecondResponse(WithdrawalFirstResponse withdrawalFirstResponse) {

    }

    @Override
    public void transactionResponse(ArrayList<TransactionResponse> transactionResponse) {

    }

    @Override
    public void saveConvertEarningResponse(ConversionResponse message) {

    }

    @Override
    public void saveCoinsResponse(String message) {
      //  Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter=presenter;
    }


    private void showInterstitialAds() {
        if ( currentAd != null )
        {
            progressDialog.dismiss();
            interstitialAd.showAndRender( currentAd );
        }
    }


    private void showRewardedVideo() {
        progressDialog.dismiss();
        rewardedAd.show( this, this, this, this, this );

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void adClicked(AppLovinAd ad) {

    }

    @Override
    public void adDisplayed(AppLovinAd ad) {

    }

    @Override
    public void adHidden(AppLovinAd ad) {

    }

    @Override
    public void adReceived(AppLovinAd ad) {
        currentAd = ad;
    }

    @Override
    public void failedToReceiveAd(int errorCode) {

    }

    @Override
    public void userRewardVerified(AppLovinAd ad, Map<String, String> response) {
        String count = scratch_count_textView.getText().toString();
        String[] counteee = count.split("=", 2);
        String ran = counteee[1];
        Log.e(TAG, "onScratchComplete: " + ran);
        int counter = Integer.parseInt(ran.trim());
        showDialogPoints(1, points_text.getText().toString(), counter);
    }

    @Override
    public void userOverQuota(AppLovinAd ad, Map<String, String> response) {

    }

    @Override
    public void userRewardRejected(AppLovinAd ad, Map<String, String> response) {

    }

    @Override
    public void validationRequestFailed(AppLovinAd ad, int errorCode) {

    }

    @Override
    public void videoPlaybackBegan(AppLovinAd ad) {

    }

    @Override
    public void videoPlaybackEnded(AppLovinAd ad, double percentViewed, boolean fullyWatched) {

    }
}