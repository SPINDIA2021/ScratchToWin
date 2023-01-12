package com.myapps.onlysratchapp.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardItem;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAd;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedAdLoadListener;
import com.bytedance.sdk.openadsdk.api.reward.PAGRewardedRequest;
import com.myapps.onlysratchapp.BuildConfig;
import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.activity.LoginContract;
import com.myapps.onlysratchapp.activity.LoginPresenter;
import com.myapps.onlysratchapp.activity.StartAdActivity;
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
import com.myapps.onlysratchapp.utils.PAGAppOpenAdManager;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import dev.skymansandy.scratchcardlayout.listener.ScratchListener;
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout;


public class GoldPangleFragment extends Fragment implements ScratchListener, LoginContract.View  {

    private LoginContract.Presenter presenter;
    private LinearLayout adLayout;
    private Context activity;
    private Toolbar toolbar;
    private TextView scratch_count_textView, points_textView, points_text;
    boolean first_time = true, scratch_first = true;
    private int scratch_count = 1;
    private final String TAG = "Silver Fragment";
    private String random_points;
    public int poiints = 0;

    ScratchCardLayout scratchCardLayout;
    private LockableScrollView scrollView;

    public boolean rewardShow = true, interstitialShow = true;
    ProgressDialog progressDialog;

    private PAGInterstitialAd interstitialAd;
    private PAGRewardedAd mPAGRewardedAd;

    public GoldPangleFragment() {

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static GoldPangleFragment newInstance() {
        GoldPangleFragment fragment = new GoldPangleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gold, container, false);
        adLayout = view.findViewById(R.id.banner_container);
        toolbar = view.findViewById(R.id.toolbar);
        points_text = view.findViewById(R.id.textView_points_show);
        scratch_count_textView = view.findViewById(R.id.limit_text);
        scratchCardLayout = view.findViewById(R.id.scratch_view_layout);
        scratchCardLayout.setScratchListener(this);
        scrollView = view.findViewById(R.id.scroll);

        PAGRewardedAd.loadAd("980088192",
                new PAGRewardedRequest(),
                new PAGRewardedAdLoadListener() {
                    @Override
                    public void onError(int code, String message) {
                        Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message) + "new");
                        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onAdLoaded(PAGRewardedAd ad) {
                        Toast.makeText(getActivity(),"rewardVideoAd loaded ",Toast.LENGTH_LONG).show();

                        mPAGRewardedAd = ad;
                    }
                });

        PAGInterstitialAd.loadAd("980088188",
                new PAGInterstitialRequest(),
                new PAGInterstitialAdLoadListener() {
                    @Override
                    public void onError(int code, String message) {
                        Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
                        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAdLoaded(PAGInterstitialAd pagInterstitialAd) {
                        Toast.makeText(getActivity(),"InterstitialAd loaded ",Toast.LENGTH_LONG).show();

                        interstitialAd = pagInterstitialAd;
                    }
                });



        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait while ad is loading");

        new LoginPresenter(Injection.provideLoginRepository(getActivity()), this);
        try {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Gold Scratch");
            points_textView = toolbar.findViewById(R.id.points_text_in_toolbar);
            setPointsText();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


       /* if (Constant.isNetworkAvailable(activity)) {
            presenter.getAdKeys(getActivity());

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
        }
*/

        onInit();
        return view;
    }




    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(activity, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }

    private void onInit() {

        String scratchCount = Constant.getString(activity, Constant.SCRATCH_COUNT_GOLD);
        if (scratchCount.equals("0")) {
            scratchCount = "";
            Log.e("TAG", "onInit: scratch card 0");
        }
        if (scratchCount.equals("")) {
            Log.e("TAG", "onInit: scratch card empty vala part");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE_SCRATCH_GOLD);
            Log.e("TAG", "Lat date" + last_date);
            if (last_date.equals("")) {
                Log.e("TAG", "onInit: last date empty part");
                setScratchCount(getResources().getString(R.string.scratch_count));
                Constant.setString(activity, Constant.SCRATCH_COUNT_GOLD, getResources().getString(R.string.scratch_count));
                Constant.setString(activity, Constant.LAST_DATE_SCRATCH_GOLD, currentDate);
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
                        Constant.setString(activity, Constant.LAST_DATE_SCRATCH_GOLD, currentDate);
                        Constant.setString(activity, Constant.SCRATCH_COUNT_GOLD, getResources().getString(R.string.scratch_count));
                        setScratchCount(Constant.getString(activity, Constant.SCRATCH_COUNT_GOLD));
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
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);
        if (Constant.isNetworkAvailable(activity)) {
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
                            Constant.setString(activity, Constant.SCRATCH_COUNT_GOLD, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.SCRATCH_COUNT_GOLD));
                            dialog.dismiss();
                        } else {
                            String current_counter = String.valueOf(counter - 1);
                            Constant.setString(activity, Constant.SCRATCH_COUNT_GOLD, current_counter);
                            setScratchCount(Constant.getString(activity, Constant.SCRATCH_COUNT_GOLD));
                            try {
                                int finalPoint;
                                if (points.equals("")) {
                                    finalPoint = 0;
                                } else {
                                    finalPoint = Integer.parseInt(points);
                                }
                                poiints = finalPoint;
                                Constant.addPoints(activity, finalPoint, 0);
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
                            showInterstitialAd();
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
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
        }
        dialog.show();
    }

    private void showInterstitialAds(String placementToShow) {

        UnityAds.show(getActivity(), placementToShow, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                progressDialog.dismiss();
                Log.e("LOGTAG", "onUnityAdsShowFailure: " + error + " - " + message);
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                Log.v("LOGTAG", "onUnityAdsShowStart: " + placementId);
                progressDialog.dismiss();
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                Log.v("LOGTAG","onUnityAdsShowClick: " + placementId);
            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                Log.v("LOGTAG","onUnityAdsShowComplete: " + placementId);

            }
        });
    }




    public void showDailog() {
        final Dialog dialog = new Dialog(activity);
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
                    String po = Constant.getString(activity, Constant.USER_POINTS);
                    if (po.equals("")) {
                        po = "0";
                    }
                    int current_Points = Integer.parseInt(po);
                    int finalPoints = current_Points - poiints;
                    Constant.setString(activity, Constant.USER_POINTS, String.valueOf(finalPoints));
                    Intent serviceIntent = new Intent(activity, PointsService.class);
                    serviceIntent.putExtra("points", Constant.getString(activity, Constant.USER_POINTS));
                    activity.startService(serviceIntent);
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
            if (Constant.isNetworkAvailable(activity)) {
                random_points = "";
                Random random = new Random();
                random_points = String.valueOf(random.nextInt(30));
                if (random_points == null || random_points.equalsIgnoreCase("null")) {
                    random_points = String.valueOf(1);
                }
                points_text.setText(random_points);
                Log.e(TAG, "onScratchStarted: " + random_points);
            } else {
                Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
            }
        }
    }

    private void callServiceConvertPoints(String points) {
        String refercode = Constant.getString(getContext(), Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }
        presenter.saveCoins(refercode,points,getActivity());
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
        //Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
  this.presenter=presenter;
    }

    private void showInterstitialAd( ) {
        if (interstitialAd != null) {
            interstitialAd.setAdInteractionListener(new MyInterstitialAdListener(getActivity()));
            interstitialAd.show(getActivity());
            interstitialAd = null;

        } else {
            Toast.makeText(getActivity(),"Please load the ad first !",Toast.LENGTH_LONG).show();

        }
    }

    private void showRewardedVideo() {
        if (mPAGRewardedAd != null) {
            progressDialog.dismiss();
            mPAGRewardedAd.setAdInteractionListener(new MyRewardedAdListener(getActivity().getApplicationContext()));
            mPAGRewardedAd.show(getActivity());
            mPAGRewardedAd = null;
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(),"Please load the ad first !",Toast.LENGTH_LONG).show();

        }

    }

    public static class MyRewardedAdListener implements PAGRewardedAdInteractionListener {
        Context mContext;

        MyRewardedAdListener(Context context) {
            mContext = context;
        }

        @Override
        public void onAdShowed() {
            Toast.makeText(mContext.getApplicationContext(), "rewardVideoAd onAdShowed",Toast.LENGTH_LONG).show();

            PAGAppOpenAdManager.setIsShowingAd(true);
        }

        @Override
        public void onAdClicked() {

        }

        @Override
        public void onAdDismissed() {

            PAGAppOpenAdManager.setIsShowingAd(false);
        }

        @Override
        public void onUserEarnedReward(PAGRewardItem item) {
            /*String count = scratch_count_textView.getText().toString();
            String[] counteee = count.split("=", 2);
            String ran = counteee[1];
            Log.e(TAG, "onScratchComplete: " + ran);
            int counter = Integer.parseInt(ran.trim());
            showDialogPoints(1, points_text.getText().toString(), counter);*/
        }

        @Override
        public void onUserEarnedRewardFail(int errorCode, String errorMsg) {

        }
    }


    public static class MyInterstitialAdListener implements PAGInterstitialAdInteractionListener {
        Context mContext;

        MyInterstitialAdListener(Context context) {
            mContext = context;
        }

        @Override
        public void onAdShowed() {

            PAGAppOpenAdManager.setIsShowingAd(true);
        }

        @Override
        public void onAdClicked() {

        }

        @Override
        public void onAdDismissed() {

            PAGAppOpenAdManager.setIsShowingAd(false);
        }
    }
}