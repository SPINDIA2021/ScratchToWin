package com.myapps.onlysratchapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.activity.LoginContract;
import com.myapps.onlysratchapp.activity.LoginPresenter;
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

/*import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;*/
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;


public class VerifyOTPFragment extends Fragment implements  LoginContract.View {
    private LoginContract.Presenter presenter;

    private AppCompatButton verify_btn;
    private Context mContext;
   // private ACProgressFlower alertDialog;
    private TextView resend_text;
    private OtpTextView otp_edit_text;
    private String type;
    private ImageView back;
    private RelativeLayout baseLyt;
    private TextInputEditText  referEditText;
    private String OTP,refercode;

    public VerifyOTPFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static VerifyOTPFragment newInstance() {
        VerifyOTPFragment fragment = new VerifyOTPFragment();
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
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        new LoginPresenter(Injection.provideLoginRepository(getActivity()), this);
       verify_btn = view.findViewById(R.id.verify_btn);
        referEditText = view.findViewById(R.id.refer_edit_text);
        otp_edit_text = view.findViewById(R.id.otpEditText);
      //  resend_text = view.findViewById(R.id.resend_otp);
        baseLyt = view.findViewById(R.id.otp_base_lyt);


       baseLyt = view.findViewById(R.id.otp_base_lyt);

       /* alertDialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();*/
        back = view.findViewById(R.id.back_img_forgot);
        if (getActivity().getIntent() != null) {
            type = getActivity().getIntent().getStringExtra("type");
        }

        onClick();

        return view;
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().onBackPressed();
            }
        });
        /*resend_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                showProgressDialog();
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                resetPasswordEmail(email_EditText.getText().toString(), String.format("%06d", number));
            }
        });*/

        otp_edit_text.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {


            }

            @Override
            public void onOTPComplete(String otp) {
                if (otp.equalsIgnoreCase(OTP)) {
                    baseLyt.setVisibility(View.GONE);

                } else {
                    Constant.showToastMessage(mContext, "Enter Correct Otp...");
                }
            }
        });


        referEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  if (count==7)
                  {

                      refercode=referEditText.getText().toString();
                      callServiceGetReferalUser();
                  }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext)) {

                } else {
                    Constant.showInternetErrorDialog(mContext, getResources().getString(R.string.no_internet_connection));
                }
            }
        });

    }

    private void callServiceGetReferalUser() {
        if (Constant.isNetworkAvailable(mContext)) {

        } else {
            Constant.showInternetErrorDialog(mContext, getResources().getString(R.string.no_internet_connection));
        }

    }


    public void showProgressDialog() {
       /* if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
        }*/
    }

    public void hideProgressDialog() {
/*
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
*/
    }




    @Override
    public void loginResponse(VerifyMobileResponse loginResponse) {

    }

    @Override
    public void verifyUserResponse(VerifyUserResponse verifyUserResponse) {

    }

    @Override
    public void getReferUserResponse(ReferUserResponse referUserResponse, String Message) {
     referEditText.setText(refercode+" ("+referUserResponse.getUser()+")");
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

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
      this.presenter=presenter;
    }
}