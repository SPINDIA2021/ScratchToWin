package com.myapps.onlysratchapp.activity;

import static com.myapps.onlysratchapp.utils.Constant.hideKeyboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
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
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;

import java.util.ArrayList;

/*import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;*/
import in.aabhasjindal.otptextview.OtpTextView;

public class WithdrawFirstVerifyActivity extends AppCompatActivity implements  LoginContract.View{
    private LoginContract.Presenter presenter;
    private AppCompatButton verify_btn;
   // private ACProgressFlower alertDialog;
    private OtpTextView otp_edit_text;
    private ImageView back,btnVerifyRefer;
    private RelativeLayout baseLyt;
    private TextInputEditText referEditText;
    private String userflag,refercode="";
    TextView tvTitle;

    private String mobileno, wid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_otp);

        initView();



    }

    private void initView() {
        mobileno = getIntent().getStringExtra("mobile");
        wid = getIntent().getStringExtra("wid");
        userflag = getIntent().getStringExtra("userflag");

        new LoginPresenter(Injection.provideLoginRepository(this), this);



        verify_btn = findViewById(R.id.verify_btn);
        referEditText = findViewById(R.id.refer_edit_text);
        otp_edit_text = findViewById(R.id.otpEditText);
        baseLyt = findViewById(R.id.otp_base_lyt);
        tvTitle = findViewById(R.id.login_txt_forgot);
        btnVerifyRefer=findViewById(R.id.btn_verify);
        tvTitle.setText("Verify OTP");


        baseLyt = findViewById(R.id.otp_base_lyt);


        referEditText.setVisibility(View.GONE);
        btnVerifyRefer.setVisibility(View.GONE);

      /*  alertDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();*/
        back = findViewById(R.id.back_img_forgot);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        referEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 7) {

                    refercode = referEditText.getText().toString();
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
                if (Constant.isNetworkAvailable(getApplicationContext())) {

                    String otp = otp_edit_text.getOTP().toString().trim();
                    if (otp.length() == 0) {
                        Toast.makeText(getApplicationContext(),"Please Enter OTP",Toast.LENGTH_LONG).show();
                    }else {
                        hideKeyboard(WithdrawFirstVerifyActivity.this);
                        // showProgressDialog();
                        presenter.withdrawVerifyFirst(userflag, wid,otp, WithdrawFirstVerifyActivity.this);
                        // signInWithEmailandPassword(name, mobile);
                    }
                } else {
                    Constant.showInternetErrorDialog(WithdrawFirstVerifyActivity.this, getResources().getString(R.string.no_internet_connection));
                }
            }
        });
    }

        private void callServiceGetReferalUser() {
            if (Constant.isNetworkAvailable(this)) {
                presenter.getReferUser(referEditText.getText().toString(),this);
            } else {
                Constant.showInternetErrorDialog(this, getResources().getString(R.string.no_internet_connection));
            }

        }


        public void showProgressDialog() {
          /*  if (alertDialog != null && !alertDialog.isShowing()) {
                alertDialog.show();
            }*/
        }

        public void hideProgressDialog() {
           /* if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }*/
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
     /*   Intent intent=new Intent(this, VerifySecondOTPActivity.class);
        intent.putExtra("mobile", mobileno);
        intent.putExtra("newuser",newuser);
        intent.putExtra("userflag",userflag);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();*/
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
        Intent intent=new Intent(this, WithdrawSecondVerifyActivity.class);
        intent.putExtra("mobile", mobileno);
        intent.putExtra("wid",wid);
        intent.putExtra("userflag",withdrawalFirstResponse.getUserflag());
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);

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