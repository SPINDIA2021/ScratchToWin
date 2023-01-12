package com.myapps.onlysratchapp.activity;


import android.content.Context;

import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.ReferUserResponse;
import com.myapps.onlysratchapp.models.TransactionResponse;
import com.myapps.onlysratchapp.models.VerifyMobileResponse;
import com.myapps.onlysratchapp.models.VerifyOTPResponse;
import com.myapps.onlysratchapp.models.VerifyUserResponse;
import com.myapps.onlysratchapp.models.WithdrawalFirstResponse;
import com.myapps.onlysratchapp.models.WithdrawalResponse;
import com.myapps.onlysratchapp.utils.BaseView;

import java.util.ArrayList;


public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginResponse(VerifyMobileResponse loginResponse);
        void verifyUserResponse(VerifyUserResponse verifyUserResponse);
        void getReferUserResponse(ReferUserResponse referUserResponse, String Message);
        void verifyFirstOTPResponse(VerifyOTPResponse verifyOTPResponse);
        void verifySecondOTPResponse(VerifyOTPResponse verifyOTPResponse);
        void adKeysResponse(ArrayList<AdKeysResponse> adKeysResponse);
        void withdrawResponse(WithdrawalResponse withdrawalResponse);
        void withdrawalFirstResponse(WithdrawalFirstResponse withdrawalFirstResponse);
        void withdrawalSecondResponse(WithdrawalFirstResponse withdrawalFirstResponse);
        void transactionResponse(ArrayList<TransactionResponse> transactionResponse);

        void saveConvertEarningResponse(ConversionResponse conversionResponse);
        void saveCoinsResponse(String message);
    }

    interface Presenter {

        void verifyMobile( String mobile, Context context);

        void verifyUser(String email, String city,String name,String state, String password,String referral_with,String number,Context context);

        void getReferUser( String referCode,Context context);

        void verifyFirstOTP(String number, String otp, Context context);

        void verifySecondOTP(String mobile, String otp,String referwith, Context context);

        void withdrawMoney(String mobile, String referalcode, String userflag,String amount,String upiid,String name, Context context);

        void withdrawVerifyFirst(String userflag,String wid,String otp, Context context);

        void withdrawVerifySecond(String userflag,String wid,String otp, Context context);

        void getAdKeys( Context context);

        void getTransactionHistory(String referCode, Context context);

        void saveConvertEarning(String refercode,String point,Context context);

        void saveCoins(String refercode,String point,Context context);

    }
}
