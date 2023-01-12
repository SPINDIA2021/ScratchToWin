package com.myapps.onlysratchapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
import com.myapps.onlysratchapp.passbook.PassbookContract;
import com.myapps.onlysratchapp.passbook.PassbookPresenter;
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;

import java.util.ArrayList;

public class LevelFragment extends Fragment implements  PassbookContract.View{
    private PassbookContract.Presenter presenter;

    private CardView levelCardView1, levelCardView2, levelCardView3, levelCardView4,
            levelCardView5, levelCardView6,levelCardView7,levelCardView8,
            levelCardView9,levelCardView10;
    private Context activity;
    private Toolbar toolbar;
    private TextView  points_textView;
    LinearLayout layLevel;
    ArrayList<LevelResponse> levelResponseArrayList=new ArrayList<>();
    WebView webView;
    boolean cardClick1=true,cardClick2=true,cardClick3=true,cardClick4=true,cardClick5=true,
            cardClick6=true,cardClick7=true,cardClick8=true,cardClick9=true,cardClick10=true;


    public LevelFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static LevelFragment newInstance() {
        LevelFragment fragment = new LevelFragment();
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
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        levelCardView1 = view.findViewById(R.id.card_level1);
        levelCardView2 = view.findViewById(R.id.card_level2);
        levelCardView3 = view.findViewById(R.id.card_level3);
        levelCardView4 = view.findViewById(R.id.card_level4);
        levelCardView5 = view.findViewById(R.id.card_level5);
        levelCardView6 = view.findViewById(R.id.card_level6);
        levelCardView7 = view.findViewById(R.id.card_level7);
        levelCardView8 = view.findViewById(R.id.card_level8);
        levelCardView9 = view.findViewById(R.id.card_level9);
        levelCardView10=view.findViewById(R.id.card_level10);
        layLevel=view.findViewById(R.id.lay_level);
        webView = view.findViewById(R.id.webview);

        onClick();

        new PassbookPresenter(Injection.provideLoginRepository(getActivity()), this);

        String refercode = Constant.getString(getContext(), Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }

        presenter.getLevelData(refercode,getContext());

        try {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Levels");

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

    private void onClick() {
        levelCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(0).getUrl());*/
                if (cardClick1==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(0).getUrl())));
                }
            }
        });

        levelCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(1).getUrl());*/
                if (cardClick2==true)
                {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(1).getUrl())));

                }
            }
        });
        levelCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(2).getUrl());*/
                if (cardClick3==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(2).getUrl())));
                }
            }
        });

        levelCardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(3).getUrl());*/
                if (cardClick4 == true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(3).getUrl())));
                }
            }
        });
        levelCardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(4).getUrl());*/
                if (cardClick5==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(4).getUrl())));
                }
            }
        });

        levelCardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(5).getUrl());*/
                if (cardClick6==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(5).getUrl())));
                }
            }
        });

        levelCardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(6).getUrl());*/
                if (cardClick7==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(6).getUrl())));
                }
            }
        });

        levelCardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(7).getUrl());*/
                if (cardClick8==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(7).getUrl())));
                }
            }
        });

        levelCardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(8).getUrl());*/
                if (cardClick9==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(8).getUrl())));
                }
            }
        });
        levelCardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   layLevel.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVerticalScrollBarEnabled(true);
                webView.setHorizontalScrollBarEnabled(true);
                webView.getSettings().getBuiltInZoomControls();
                webView.loadUrl(levelResponseArrayList.get(9).getUrl());*/
                if (cardClick10==true) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(levelResponseArrayList.get(9).getUrl())));
                }
            }
        });




    }


    @Override
    public void passbookResponse(ArrayList<PassbookResponse> passbookResponses) {

    }

    @Override
    public void coinsResponse(ArrayList<CoinsResponse> coinsResponses) {

    }

    @Override
    public void levelResponse(ArrayList<LevelResponse> levelResponses) {

   levelResponseArrayList=levelResponses;


       try {
           if (levelResponseArrayList.get(0).getFlag().equals("E"))
           {
               cardClick1=true;
               levelCardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
           }else {
               cardClick1=false;
               levelCardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
           }
       }catch (Exception e){
           cardClick1=true;
           levelCardView1.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
       }

        try {
            if (levelResponseArrayList.get(1).getFlag().equals("E"))
            {
                cardClick2=true;
                levelCardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick2=false;
                levelCardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick2=true;
            levelCardView2.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(2).getFlag().equals("E"))
            {
                cardClick3=true;
                levelCardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick3=false;
                levelCardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick3=true;
            levelCardView3.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(3).getFlag().equals("E"))
            {
                cardClick4=true;
                levelCardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick4=false;
                levelCardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick4=true;
            levelCardView4.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(4).getFlag().equals("E"))
            {
                cardClick5=true;
                levelCardView5.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick5=false;
                levelCardView5.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick5=true;
            levelCardView5.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(5).getFlag().equals("E"))
            {
                cardClick6=true;
                levelCardView6.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick6=false;
                levelCardView6.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick6=true;
            levelCardView6.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(6).getFlag().equals("E"))
            {
                cardClick7=true;
                levelCardView7.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick7=false;
                levelCardView7.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick7=true;
            levelCardView7.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(7).getFlag().equals("E"))
            {
                cardClick8=true;
                levelCardView8.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick8=false;
                levelCardView8.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick8=true;
            levelCardView8.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(8).getFlag().equals("E"))
            {
                cardClick9=true;
                levelCardView9.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick9=false;
                levelCardView9.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick9=true;
            levelCardView9.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }

        try {
            if (levelResponseArrayList.get(9).getFlag().equals("E"))
            {
                cardClick10=true;
                levelCardView10.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
            }else {
                cardClick10=false;
                levelCardView10.setCardBackgroundColor(getContext().getResources().getColor(R.color.light_gray));
            }
        }catch (Exception e){
            cardClick10=true;
            levelCardView10.setCardBackgroundColor(getContext().getResources().getColor(R.color.white));
        }


    }

    @Override
    public void setPresenter(PassbookContract.Presenter presenter) {
      this.presenter=presenter;
    }
}