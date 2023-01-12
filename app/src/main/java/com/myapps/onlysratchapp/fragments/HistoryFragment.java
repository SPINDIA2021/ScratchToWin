package com.myapps.onlysratchapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.activity.LoginContract;
import com.myapps.onlysratchapp.activity.LoginPresenter;
import com.myapps.onlysratchapp.activity.MainActivity;
import com.myapps.onlysratchapp.activity.WithdrawFirstVerifyActivity;
import com.myapps.onlysratchapp.adapter.TransactionAdapter;
import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.ReferUserResponse;
import com.myapps.onlysratchapp.models.TransactionResponse;
import com.myapps.onlysratchapp.models.VerifyMobileResponse;
import com.myapps.onlysratchapp.models.VerifyOTPResponse;
import com.myapps.onlysratchapp.models.VerifyUserResponse;
import com.myapps.onlysratchapp.models.WithdrawalFirstResponse;
import com.myapps.onlysratchapp.models.WithdrawalResponse;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;

import java.util.ArrayList;

public class HistoryFragment extends Fragment implements  LoginContract.View {
    private RecyclerView historyRecyclerView;
    private LoginContract.Presenter presenter;
    private Context activity;
    private Toolbar toolbar;
    private TextView  points_textView;

    ArrayList<TransactionResponse> transactionResponseArrayList=new ArrayList<>();

    private final String TAG = "Silver Fragment";

    public HistoryFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallet_history, container, false);
        historyRecyclerView = view.findViewById(R.id.transaction_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(manager);
        toolbar = view.findViewById(R.id.toolbar);

        new LoginPresenter(Injection.provideLoginRepository(getActivity()), this);

        try {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Transaction History");

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

        String refercode = Constant.getString(getContext(), Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }

        presenter.getTransactionHistory(refercode,getContext());


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

    @Override
    public void adKeysResponse(ArrayList<AdKeysResponse> adKeysResponse) {

    }

    @Override
    public void withdrawResponse(WithdrawalResponse withdrawalResponse) {
        if (withdrawalResponse.getUserflag().equals("B"))
        {
            Intent intent=new Intent(getActivity(), WithdrawFirstVerifyActivity.class);
            intent.putExtra("mobile", "");
            intent.putExtra("wid",withdrawalResponse.getId());
            intent.putExtra("userflag",withdrawalResponse.getUserflag());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        }else {
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }
    }

    @Override
    public void withdrawalFirstResponse(WithdrawalFirstResponse withdrawalFirstResponse) {

    }

    @Override
    public void withdrawalSecondResponse(WithdrawalFirstResponse withdrawalFirstResponse) {

    }

    @Override
    public void transactionResponse(ArrayList<TransactionResponse> transactionResponse) {
        if (transactionResponse.size()!=0)
        {
            historyRecyclerView.setHasFixedSize(true);
            transactionResponseArrayList=transactionResponse;
            TransactionAdapter adapter = new TransactionAdapter(getContext(), transactionResponse,itemClick);
            historyRecyclerView.setAdapter(adapter);
        }
        else {
            Toast.makeText(getContext(),"No Transactions Avialable",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void saveConvertEarningResponse(ConversionResponse message) {

    }

    @Override
    public void saveCoinsResponse(String message) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
      this.presenter=presenter;
    }

    View.OnClickListener itemClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i= (int) v.getTag();
            String mobile=transactionResponseArrayList.get(i).getMobile();
            String refercode=transactionResponseArrayList.get(i).getReferalcode();
            String points=transactionResponseArrayList.get(i).getAmount();
            String upi=transactionResponseArrayList.get(i).getUpiid();
            String name=transactionResponseArrayList.get(i).getName();
            presenter.withdrawMoney(mobile,refercode,"F",points,upi,name,getActivity());
        }
    };
}