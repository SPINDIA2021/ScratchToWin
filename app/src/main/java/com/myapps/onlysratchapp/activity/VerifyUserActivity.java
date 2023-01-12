package com.myapps.onlysratchapp.activity;

import static com.myapps.onlysratchapp.utils.Constant.REFER_CODE;
import static com.myapps.onlysratchapp.utils.Constant.hideKeyboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

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

public class VerifyUserActivity extends AppCompatActivity implements  LoginContract.View{
    private LoginContract.Presenter presenter;
  //  private ACProgressFlower alertDialog;
    private ImageView back;
    private TextInputEditText name_edit_text, number_edit_text,city_edit_text,state_edit_text, email_edit_text, password_edit_text, reffreal_code_edit_text;
    private AppCompatButton login_text_view;
    private AppCompatButton sign_up_button;

    private String mobileno, newuser,userflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up);

        initView();



    }

    private void initView() {
        mobileno = getIntent().getStringExtra("mobile");
        newuser = getIntent().getStringExtra("newuser");
        userflag = getIntent().getStringExtra("userflag");


        new LoginPresenter(Injection.provideLoginRepository(this), this);


        name_edit_text = findViewById(R.id.name_edit_text);
        number_edit_text = findViewById(R.id.number_edit_text);
        password_edit_text = findViewById(R.id.password_edit_text);
        email_edit_text = findViewById(R.id.email_edit_text);
        city_edit_text = findViewById(R.id.city_edit_text);
        state_edit_text = findViewById(R.id.state_edit_text);
        sign_up_button = findViewById(R.id.signup_btn);
        login_text_view = findViewById(R.id.login_signup_btn);
        reffreal_code_edit_text = findViewById(R.id.referal_edit_text);



        number_edit_text.setText(mobileno);




      /*  alertDialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();*/
        back = findViewById(R.id.back_img_sign);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });



        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(VerifyUserActivity.this)) {
                    String Name = name_edit_text.getText().toString().trim();
                    String Number = number_edit_text.getText().toString().trim();
                    String Email = email_edit_text.getText().toString().trim();
                    String City = city_edit_text.getText().toString().trim();
                    String State = state_edit_text.getText().toString().trim();
                    String Password = password_edit_text.getText().toString().trim();
                    String refercode = reffreal_code_edit_text.getText().toString().trim();

                    if (Name.length() == 0) {
                        name_edit_text.setError(getResources().getString(R.string.enter_name));
                        name_edit_text.requestFocus();
                    } else if (Number.length() == 0) {
                        number_edit_text.setError(getResources().getString(R.string.enter_number));
                        number_edit_text.requestFocus();
                    } else if (Number.length() != 10) {
                        number_edit_text.setError(getResources().getString(R.string.enter_valid_number));
                        number_edit_text.requestFocus();
                    } else if (Email.length() == 0) {
                        email_edit_text.setError(getResources().getString(R.string.enter_email));
                        email_edit_text.requestFocus();
                    } else if (!Constant.isValidEmailAddress(Email)) {
                        email_edit_text.setError(getResources().getString(R.string.enter_valid_email));
                        email_edit_text.requestFocus();
                    }else if (City.length() == 0) {
                        city_edit_text.setError(getResources().getString(R.string.enter_city));
                        city_edit_text.requestFocus();
                    }else if (State.length() == 0) {
                        state_edit_text.setError(getResources().getString(R.string.enter_state));
                        state_edit_text.requestFocus();
                    } else if (Password.length() == 0) {
                        password_edit_text.setError(getResources().getString(R.string.enter_password));
                        password_edit_text.requestFocus();
                    } else if (Password.length() < 6) {
                        password_edit_text.setError(getResources().getString(R.string.enter_valid_number));
                        password_edit_text.requestFocus();
                    } else {
                        hideKeyboard(VerifyUserActivity.this);
                        showProgressDialog();
                        presenter.verifyUser(Email,City,Name,State,Password,refercode,Number,VerifyUserActivity.this);


                    }
                } else {
                    Constant.showInternetErrorDialog(VerifyUserActivity.this, getResources().getString(R.string.no_internet_connection));
                }
            }
        });
    }




        public void showProgressDialog() {
          /*  if (alertDialog != null && !alertDialog.isShowing()) {
                alertDialog.show();
            }*/
        }

        public void hideProgressDialog() {
        /*    if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }*/
        }


    @Override
    public void loginResponse(VerifyMobileResponse loginResponse) {

    }

    @Override
    public void verifyUserResponse(VerifyUserResponse verifyUserResponse) {
        Intent intent=new Intent(this, VerifyFirstOTPActivity.class);
        intent.putExtra("mobile", mobileno);
        intent.putExtra("newuser",newuser);
        intent.putExtra("userflag",userflag);
        startActivity(intent);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        finish();
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