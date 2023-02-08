package com.myapps.onlysratchapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.privacy.model.CCPA;
import com.chartboost.sdk.privacy.model.GDPR;
import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.activity.GoldActivity;

import com.myapps.onlysratchapp.activity.GoldAdColonyActivity;
import com.myapps.onlysratchapp.activity.GoldAppLovinActivity;
import com.myapps.onlysratchapp.activity.GoldFBActivity;
import com.myapps.onlysratchapp.activity.GoldInmobiActivity;
import com.myapps.onlysratchapp.activity.LevelActivity;
import com.myapps.onlysratchapp.activity.MainActivity;

import com.myapps.onlysratchapp.activity.PlatinumAdColonyActivity;
import com.myapps.onlysratchapp.activity.PlatinumAppLovinActivity;
import com.myapps.onlysratchapp.activity.PlatinumFBActivity;
import com.myapps.onlysratchapp.activity.PlatinumInmobiActivity;
import com.myapps.onlysratchapp.activity.ReferActivity;

import com.myapps.onlysratchapp.activity.SilverAdColonyActivity;
import com.myapps.onlysratchapp.activity.SilverAdMobActivity;
import com.myapps.onlysratchapp.activity.SilverApplovinActivity;
import com.myapps.onlysratchapp.activity.SilverInMobiActivity;
import com.myapps.onlysratchapp.activity.SilverFBActivity;
import com.myapps.onlysratchapp.adapter.HomeAdapter;
import com.myapps.onlysratchapp.addMoney.AddMoneyActivity;
import com.myapps.onlysratchapp.addMoney.AddMoneyActivityNew;
import com.myapps.onlysratchapp.candycrush.CandyMainActivity;
import com.myapps.onlysratchapp.category.CategoryActivity;
import com.myapps.onlysratchapp.dth.DthRechargeActivity;
import com.myapps.onlysratchapp.electricityRecharge.ElectricityRechargeActivity;
import com.myapps.onlysratchapp.kotak.KotaKInnerActivity;
import com.myapps.onlysratchapp.kotak.KotakWebviewActivity;
import com.myapps.onlysratchapp.landlineRecharge.LandLineRechargeActivity;
import com.myapps.onlysratchapp.ludo.LudoMainActivity;
import com.myapps.onlysratchapp.mobileRecharge.MobileRechargeActivity;
import com.myapps.onlysratchapp.models.HomeResponse;
import com.myapps.onlysratchapp.network_calls.UserModel;
import com.myapps.onlysratchapp.puzzle.PuzzleMainActivity;
import com.myapps.onlysratchapp.transferPoints.TransferPointsActivity;
import com.myapps.onlysratchapp.utils.AppCommonMethods;
import com.myapps.onlysratchapp.utils.AppConstants;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.network_calls.AppApiCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements AppApiCalls.OnAPICallCompleteListener {


    /*private CardView referCardView, walletCardView, dailyCheckIn, silverCardView,
            platinumCardView, goldCardView,silverCardView1,platinumCardView1,
            goldCardView1,levelCardView;*/
    private Context activity;
    RecyclerView recyclerViewHome;
    TextView text_amount;
    String service = "";
    ArrayList<HomeResponse> homeResponseArrayList=new ArrayList<>();

    RelativeLayout addmoney_lyt,transferPtLay, layKotak;

    String serviceName = "";

    String cus_mobile;
    String cus_type="retailer";
    String cus_pin="";
    String cus_pass="";
    String cus_id="";

    WebView webView;
    public HomeFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        cus_mobile = Constant.getString(getActivity(), Constant.USER_NUMBER);
        cus_id = Constant.getString(getActivity(), Constant.USER_ID);

        // Needs to be set before SDK init
        Chartboost.addDataUseConsent(getActivity(), new GDPR(GDPR.GDPR_CONSENT.BEHAVIORAL));
        Chartboost.addDataUseConsent(getActivity(), new CCPA(CCPA.CCPA_CONSENT.OPT_IN_SALE));

        //start SDK with app id and app signature
        String[] ids = loadSelectedAppId(sharedPreferences);
        initSDK(ids);

        recyclerViewHome = view.findViewById(R.id.recyclerview);
        addmoney_lyt=view.findViewById(R.id.addmoney_lyt);
        transferPtLay=view.findViewById(R.id.transferamt_lyt);
        text_amount=view.findViewById(R.id.text_amount);
        layKotak=view.findViewById(R.id.lay_kotak);
        webView=view.findViewById(R.id.mlm_webview);


     /*   walletCardView = view.findViewById(R.id.walletCardView);
        dailyCheckIn = view.findViewById(R.id.daily_check_in);
        silverCardView = view.findViewById(R.id.silverCardView);
        platinumCardView = view.findViewById(R.id.platinumCardView);
        goldCardView = view.findViewById(R.id.goldCardView);
        silverCardView1 = view.findViewById(R.id.silverCardView1);
        platinumCardView1 = view.findViewById(R.id.platinumCardView1);
        goldCardView1 = view.findViewById(R.id.goldCardView1);
        levelCardView=view.findViewById(R.id.card_level);*/
        homeResponseArrayList.add(new HomeResponse(R.drawable.smartphone, "Mobile Recharge"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.dth, "DTH Recharge"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.electricity, "Services Payment"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.landline, "Landline Recharge"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.level, "Levels"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 1 AD"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 2 AD"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 3 AD"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 4 Unity"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 5 Unity"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 6 Unity"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 7 start"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 8 start"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 9 start"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 10 FB"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 11 FB"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 12 FB"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 13 CO"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 14 CO"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 15 CO"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 16 IS"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 17 IS"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 18 IS"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 19 CH"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 20 CH"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 21 CH"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 22 NX"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 23 NX"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 24 NX"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 25 MOB"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 26 MOB"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 27 MOB"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 28 VUN"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 29 VUN"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 30 VUN"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 31 PAN"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 32 PAN"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 33 PAN"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.silver_scratch, "Scratch Task 34 Lov"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.platinum_scratch, "Scratch Task 35 Lov"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.gold_scratch, "Scratch Task 36 Lov"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.wallet, "Wallet"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.refer_and_earn, "Refer and Earn"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.daily_checkin, "Daily Checkin"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.ludo, "LUDO"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.puzzle, "PUZZLE"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.candy, "Candy Crush"));
        homeResponseArrayList.add(new HomeResponse(R.drawable.category, "Category"));

        recyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(),2));
         HomeAdapter adapter=new HomeAdapter(homeResponseArrayList, getActivity(),itemClick);
        recyclerViewHome.setAdapter(adapter);

        String userAmount = Constant.getString(activity, Constant.USER_AMOUNT);
        if (userAmount.equalsIgnoreCase("")) {
            userAmount = "0";
        }
        text_amount.setText(userAmount);


        getBalanceApi(cus_mobile);

        onClick();
        return view;
    }

    private void getBalanceApi(String cus_mobile) {

        if (new AppCommonMethods(getActivity()).isNetworkAvailable()) {
            AppApiCalls mAPIcall = new AppApiCalls(
                    requireContext(),
                    AppConstants.BALANCE_API,
                    this
            );
            mAPIcall.getBalance(cus_mobile);

        } else {
            Toast.makeText(activity, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();

        }
    }




    private void onClick() {

        layKotak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent policyintent = new Intent(activity, KotakWebviewActivity.class);
                startActivity(policyintent);*/
                webView.loadUrl("https://inr.deals/nLiDpx");
                //webView.loadUrl("https://www.kotak811.com/open-zero-balance-savings-account?source=811NewILSIX&banner=ILAFSix&pubild=normal811_3001_61213");

                webView.getSettings().setJavaScriptEnabled(true);
                webView.setInitialScale(1);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                webView.setScrollbarFadingEnabled(false);
                webView.setBackgroundColor(Color.TRANSPARENT);
                webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);


               /* webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url){
                        // do your handling codes here, which url is the requested url
                        // probably you need to open that url rather than redirect:
                        view.loadUrl(url);
                        return false; // then it is not handled by default action
                    }
                });*/
            }
        });


        addmoney_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent policyintent = new Intent(activity, AddMoneyActivityNew.class);
                startActivity(policyintent);
            }
        });

        transferPtLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent policyintent = new Intent(activity, TransferPointsActivity.class);
                startActivity(policyintent);
            }
        });

      /*  referCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "refer");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }
        });

        walletCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "wallet");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }
        });

        levelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, LevelActivity.class);
                    policyintent.putExtra("type", "wallet");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }
        });

        platinumCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }
        });

        platinumCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Unity");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }
        });


        dailyCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDaily();
            }
        });

        silverCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }
        });

        silverCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Unity");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }
        });

        goldCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, GoldActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }
        });

        goldCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Unity");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }
        });*/
    }

    private void checkDaily() {
        if (Constant.isNetworkAvailable(activity)) {
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Log.e("TAG", "onClick: Current Date" + currentDate);
            String last_date = Constant.getString(activity, Constant.LAST_DATE);
            Log.e("TAG", "onClick: last_date Date" + last_date);
            if (last_date.equals("")) {
                Constant.setString(activity, Constant.LAST_DATE, currentDate);
                Constant.addPoints(activity, Integer.parseInt(getResources().getString(R.string.daily_check_in_points)), 0);
                showDialogOfPoints(Integer.parseInt(getResources().getString(R.string.daily_check_in_points)));
                if (getActivity() == null) {
                    return;
                }
                ((MainActivity) getActivity()).setPointsText();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date pastDAte = sdf.parse(last_date);
                    Date currentDAte = sdf.parse(currentDate);
                    long diff = currentDAte.getTime() - pastDAte.getTime();
                    long difference_In_Days = (diff / (1000 * 60 * 60 * 24)) % 365;
                    Log.e("TAG", "onClick: Days Diffrernce" + difference_In_Days);
                    if (difference_In_Days > 0) {
                        Constant.setString(activity, Constant.LAST_DATE, currentDate);
                        Constant.addPoints(activity, Integer.parseInt(getResources().getString(R.string.daily_check_in_points)), 0);
                        showDialogOfPoints(Integer.parseInt(getResources().getString(R.string.daily_check_in_points)));
                        if (getActivity() == null) {
                            return;
                        }
                        ((MainActivity) getActivity()).setPointsText();
                    } else {
                        showDialogOfPoints(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
        }
    }

    public void showDialogOfPoints(int points) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton add_btn = dialog.findViewById(R.id.add_btn);

        if (points == Integer.parseInt(getResources().getString(R.string.daily_check_in_points))) {
            imageView.setImageResource(R.drawable.congo);
            title_text.setText(getResources().getString(R.string.you_won));
            points_text.setVisibility(View.VISIBLE);
            points_text.setText(String.valueOf(points));
            add_btn.setText(getResources().getString(R.string.add_to_wallet));
        } else {
            imageView.setImageResource(R.drawable.donee);
            title_text.setText(getResources().getString(R.string.today_checkin_over));
            points_text.setVisibility(View.GONE);
            add_btn.setText(getResources().getString(R.string.okk));
        }
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    View.OnClickListener itemClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int i= (int) v.getTag();

            if (i==0)
            {
                Intent intent = new Intent(getActivity(), MobileRechargeActivity.class);
                startActivity(intent);
              /*  service = "recharge_service";
                getServiceStatus(cus_id, "recharge_service");*/
            }else if (i==1)
            {
                Intent intent = new Intent(getActivity(), DthRechargeActivity.class);
                startActivity(intent);
            }
            else if (i==2)
            {
                Intent intent = new Intent(getActivity(), ElectricityRechargeActivity.class);
                startActivity(intent);
              /*  service = "bill_service";
                getServiceStatus(cus_id, "bill_service");*/
            } else if (i==3)
            {
                Intent intent = new Intent(getActivity(), LandLineRechargeActivity.class);
                startActivity(intent);
            }
            else  if (i==4)
            {
                try {
                    Intent policyintent = new Intent(activity, LevelActivity.class);
                    policyintent.putExtra("type", "wallet");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if(i==5)
            {
                try {
                    Intent policyintent = new Intent(activity, SilverAdMobActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==6)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==7)
            {
                try {
                    Intent policyintent = new Intent(activity, GoldActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==8)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Unity");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==9)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Unity");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==10)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Unity");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==11)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Start");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==12)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Start");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==13)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Start");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==14)
            {
                try {
                    Intent policyintent = new Intent(activity, SilverFBActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==15)
            {
                try {
                    Intent policyintent = new Intent(activity, PlatinumFBActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==16)
            {
                try {
                    Intent policyintent = new Intent(activity, GoldFBActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==17)
            {
                try {
                    Intent policyintent = new Intent(activity, SilverAdColonyActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==18)
            {
                try {
                    Intent policyintent = new Intent(activity, PlatinumAdColonyActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==19)
            {
                try {
                    Intent policyintent = new Intent(activity, GoldAdColonyActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==20)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Iron");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==21)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Iron");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==22)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Iron");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==23)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Chartboost");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==24)
            {
               try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Chartboost");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==25)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Chartboost");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==26)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Appnxt");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==27)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Appnxt");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==28)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Appnxt");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==29)
            {
                try {
                    Intent policyintent = new Intent(activity, SilverInMobiActivity.class);
                   // policyintent.putExtra("type", "Silver Scratch Inmobi");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==30)
            {
                try {
                    Intent policyintent = new Intent(activity, PlatinumInmobiActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Inmobi");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==31)
            {
                try {
                    Intent policyintent = new Intent(activity, GoldInmobiActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Inmobi");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==32)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Vungle");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==33)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Vungle");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==34)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Vungle");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==35)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Pangle");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==36)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Pangle");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==37)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Pangle");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==38)
            {
                try {
                    Intent policyintent = new Intent(activity, SilverApplovinActivity.class);
                    policyintent.putExtra("type", "Silver Scratch Applovin");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==39)
            {
                try {
                    Intent policyintent = new Intent(activity, PlatinumAppLovinActivity.class);
                    policyintent.putExtra("type", "Platinum Scratch Applovin");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }

            }else if (i==40)
            {
                try {
                    Intent policyintent = new Intent(activity, GoldAppLovinActivity.class);
                    policyintent.putExtra("type", "Gold Scratch Applovin");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==41)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "wallet");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==42)
            {
                try {
                    Intent policyintent = new Intent(activity, ReferActivity.class);
                    policyintent.putExtra("type", "refer");
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==43)
            {
               checkDaily();
            }else if (i==44)
            {
                try {
                    Intent policyintent = new Intent(activity, LudoMainActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==45)
            {
                try {
                    Intent policyintent = new Intent(activity, PuzzleMainActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==46)
            {
                try {
                    Intent policyintent = new Intent(activity, CandyMainActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }else if (i==47)
            {
                try {
                    Intent policyintent = new Intent(activity, CategoryActivity.class);
                    startActivity(policyintent);
                } catch (Exception e) {
                    Constant.showToastMessage(activity, e.getMessage());
                }
            }
        }
    };

    private void initSDK(String[] ids) {
        String appId = ids[0];
        String appSignature = ids[1];
        Chartboost.startWithAppId(getActivity(), appId, appSignature, startError -> {
            if (startError == null) {
                //Toast.makeText(getContext(), "SDK is initialized", Toast.LENGTH_SHORT).show();
               // displayGDPRConsentInLogs();
            } else {
                Toast.makeText(getContext(), "SDK initialized with error: "+startError.getCode().name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String[] loadSelectedAppId(SharedPreferences sharedPreferences) {
        String[] ids = new String[2];
        String appId = sharedPreferences.getString(getString(R.string.key_app_id_selected), "");
        String appSignature = sharedPreferences.getString(getString(R.string.key_app_signature_selected), "");

        //in case there were no selected values, use default and save them as selected
        if(appId.isEmpty() || appSignature.isEmpty()) {
            appId = getResources().getString(R.string.chartboost_appId);
            appSignature = getResources().getString(R.string.chartboost_appSignature);
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit()
                    .putString(getString(R.string.key_app_id_selected), appId)
                    .putString(getString(R.string.key_app_signature_selected), appSignature)
                    .apply();
        }
        ids[0] = appId;
        ids[1] = appSignature;
        return ids;
    }


    private void getServiceStatus(
           String cusid,
           String service
    ) {

        if (Constant.isNetworkAvailable(activity)) {
            AppApiCalls mAPIcall =
                    new AppApiCalls(getActivity(), "SERVICE_STATUS", this);
            mAPIcall.getServiceStatus(
                    cusid, service
            );

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
        }

    }


    @Override
    public void onAPICallCompleteListner(@Nullable Object item, @Nullable String flag, @NonNull String result) throws JSONException {

        if (flag.equals(AppConstants.BALANCE_API)) {
            Log.e(AppConstants.BALANCE_API, result);
            Log.v("MobPrep","BALANCE_API: "+result);
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(AppConstants.STATUS);
            String messageCode = jsonObject.getString(AppConstants.MESSAGE);

            //   val token = jsonObject.getString(AppConstants.TOKEN)
            Log.e(AppConstants.STATUS, status);
            Log.e(AppConstants.MESSAGE, messageCode);
            Log.v("MobPrep","BALANCE_API STATUS: "+status);
            Log.v("MobPrep","BALANCE_API MESSAGE: "+messageCode);
            if (status.contains(AppConstants.TRUE)) {

                text_amount.setText(getString(R.string.Rupee)+jsonObject.getString(AppConstants.WALLETBALANCE));

                /* tvAepsBalance.text =
                     "${getString(R.string.Rupee)} ${jsonObject.getString(AEPSBALANCE)}"*/


            } else {

                if (messageCode.equals(getString(R.string.error_expired_token))) {
                   //AppCommonMethods.logoutOnExpiredDialog(requireContext())
                } else {
                   // requireContext().toast(messageCode.trim())
                }
            }
        }

        if (flag.equals("SERVICE_STATUS")) {
            Log.e("SERVICE_STATUS", result);
            JSONObject jsonObject = new JSONObject(result);
            String status = jsonObject.getString(AppConstants.STATUS);
            String message = jsonObject.getString(AppConstants.MESSAGE);
            Log.e(AppConstants.STATUS, status);
            if (status.contains("true")) {

                JSONArray cast = jsonObject.getJSONArray("result");

                for (int i=0;i<cast.length();i++) {
                    JSONObject notifyObjJson = cast.getJSONObject(i);
                    //Log.e("TAG",notifyObjJson.getString("name"))
                 if(service.equals("recharge_service")) {
                        String recharge_service = notifyObjJson.getString("recharge_service");
                        if(recharge_service.equals("inactivate")) {
                            serviceName = "Recharge Service";
                            getServiceAmount(serviceName);
                        } else {
                            Intent intent = new Intent(getActivity(), MobileRechargeActivity.class);
                            startActivity(intent);
                        }
                    } else if(service.equals("bill_service")) {
                        String billpay_service = notifyObjJson.getString("bill_service");
                        if(billpay_service.equals("inactivate")) {
                            serviceName = "Bill Service";
                            getServiceAmount(serviceName);
                        } else {
                            Intent intent = new Intent(getActivity(), ElectricityRechargeActivity.class);
                            startActivity(intent);

                        }
                    }
                }
            } else {

                String response = jsonObject.getString("message");
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();


            }
        }

    }

    private void getServiceAmount(String serviceName) {
        if (Constant.isNetworkAvailable(activity)) {
            AppApiCalls mAPIcall =
                    new AppApiCalls(getActivity(), "SERVICE_AMOUNT", this);
            mAPIcall.getServiceAmount(
                    service
            );

        } else {
            Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
        }

    }
}