package com.myapps.onlysratchapp.activity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.myapps.onlysratchapp.data.DataSource;
import com.myapps.onlysratchapp.data.remote.RemoteDataSource;
import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.ReferUserResponse;
import com.myapps.onlysratchapp.models.TransactionResponse;
import com.myapps.onlysratchapp.models.VerifyMobileResponse;
import com.myapps.onlysratchapp.models.VerifyOTPResponse;
import com.myapps.onlysratchapp.models.VerifyUserResponse;
import com.myapps.onlysratchapp.models.WithdrawalFirstResponse;
import com.myapps.onlysratchapp.models.WithdrawalResponse;
import com.myapps.onlysratchapp.network.BaseResponse;
import com.myapps.onlysratchapp.network.IApi;
import com.myapps.onlysratchapp.network.NetworkCall;
import com.myapps.onlysratchapp.network.ServiceCallBack;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Util;

import java.util.ArrayList;

import retrofit2.Response;


public class LoginPresenter implements LoginContract.Presenter, ServiceCallBack {
    private final DataSource loginDataSource;
    private final LoginContract.View mLoginView;
    private Context context;


    public LoginPresenter(@NonNull RemoteDataSource listDataSource, LoginContract.View loginFragment) {
        loginDataSource = listDataSource;
        mLoginView = loginFragment;
        mLoginView.setPresenter(this);

    }


    @Override
    public void onSuccess(int tag, Response<BaseResponse> baseResponse) {
        if (tag == IApi.COMMON_TAG) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    VerifyMobileResponse userData = (VerifyMobileResponse) response.getResponsePacket();

                    if (userData.getReferralCode() != null) {
                        Constant.setString(context, Constant.USER_REFFER_CODE,userData.getReferralCode());
                        Log.e("TAG", "onDataChange: " + userData.getReferralCode());
                    }

                    if (userData.getId() != null) {
                        Constant.setString(context, Constant.USER_ID,userData.getId());
                        Log.e("TAG", "onDataChange: " + userData.getId());
                    }

                    if (userData.getName() != null) {
                        Constant.setString(context, Constant.FIRST_NAME,userData.getName());
                        Log.e("TAG", "onDataChange: " + userData.getName());
                    }



                    if (userData.getNumber() != null) {
                        Constant.setString(context, Constant.USER_NUMBER,userData.getNumber());
                        Log.e("TAG", "onDataChange: " + userData.getNumber());
                    }

                 /*   if (userData.getCoins() != null) {
                        Constant.setString(context, Constant.USER_POINTS,userData.getCoins());
                        Log.e("TAG", "onDataChange: " + userData.getCoins());
                    }*/

                    if (userData.getPoints() != null) {
                        Constant.setString(context, Constant.USER_POINTS,userData.getPoints());
                        Log.e("TAG", "onDataChange: " + userData.getPoints());
                    }

                    if (userData.getBalance() != null) {
                        Constant.setString(context, Constant.USER_AMOUNT,userData.getBalance());
                        Log.e("TAG", "onDataChange: " + userData.getBalance());
                    }

                    if (userData.getToken() != null) {
                        Constant.setString(context, Constant.TOKEN,userData.getToken());
                        Log.e("TAG", "onDataChange: " + userData.getToken());
                    }

                    if (userData.getPayoutactive() != null) {
                        Constant.setString(context, Constant.PAYOUT_ACTIVE,userData.getPayoutactive());
                        Log.e("TAG", "onDataChange: " + userData.getPayoutactive());
                    }

                    mLoginView.loginResponse(userData);


                } else {
                    VerifyMobileResponse userData = (VerifyMobileResponse) response.getResponsePacket();
                    mLoginView.loginResponse(userData);

                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG1) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    VerifyUserResponse userData = (VerifyUserResponse) response.getResponsePacket();
                    Toast.makeText(context,userData.getOtp().toString(),Toast.LENGTH_LONG).show();
                    if (userData.getReferralCode() != null) {
                        Constant.setString(context, Constant.USER_REFFER_CODE,userData.getReferralCode());
                        Log.e("TAG", "onDataChange: " + userData.getReferralCode());
                    }

                    if (userData.getName() != null) {
                        Constant.setString(context, Constant.USER_NAME,userData.getName());
                        Log.e("TAG", "onDataChange: " + userData.getName());
                    }

                    if (userData.getId() != null) {
                        Constant.setString(context, Constant.USER_ID,userData.getId());
                        Log.e("TAG", "onDataChange: " + userData.getId());
                    }

                    if (userData.getNumber() != null) {
                        Constant.setString(context, Constant.USER_NUMBER,userData.getNumber());
                        Log.e("TAG", "onDataChange: " + userData.getNumber());
                    }

                    if (userData.getToken() != null) {
                        Constant.setString(context, Constant.TOKEN,userData.getToken());
                        Log.e("TAG", "onDataChange: " + userData.getToken());
                    }

                    if (userData.getPoints() != null) {
                        Constant.setString(context, Constant.USER_POINTS,userData.getPoints());
                        Log.e("TAG", "onDataChange: " + userData.getPoints());
                    }

                    if (userData.getBalance() != null) {
                        Constant.setString(context, Constant.USER_AMOUNT,userData.getBalance());
                        Log.e("TAG", "onDataChange: " + userData.getBalance());
                    }

                    if (userData.getPayoutactive() != null) {
                        Constant.setString(context, Constant.PAYOUT_ACTIVE,userData.getPayoutactive());
                        Log.e("TAG", "onDataChange: " + userData.getPayoutactive());
                    }

                    mLoginView.verifyUserResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }


        if (tag == IApi.COMMON_TAG2) {
            BaseResponse response = baseResponse.body();
            try{
                if (response != null) {
                    if (response.isResponseStatus()==true) {


                        ReferUserResponse userData = (ReferUserResponse) response.getResponsePacket();

                        mLoginView.getReferUserResponse(userData,response.getResponseMessage());


                    } else {
                        Util.showAlertBox(context, response.getResponseMessage(), null);
                    }

                }else {
                    ReferUserResponse userData=new ReferUserResponse("N");
                    mLoginView.getReferUserResponse(userData,response.getResponseMessage());
                }
            }catch (Exception e){
                ReferUserResponse userData=new ReferUserResponse("N");
                mLoginView.getReferUserResponse(userData,response.getResponseMessage());}

        }

        if (tag == IApi.COMMON_TAG3) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();


                    VerifyOTPResponse userData = (VerifyOTPResponse) response.getResponsePacket();

                    if (userData.getReferralCode() != null) {
                        Constant.setString(context, Constant.USER_REFFER_CODE,userData.getReferralCode());
                        Log.e("TAG", "onDataChange: " + userData.getReferralCode());
                    }

                    if (userData.getName() != null) {
                        Constant.setString(context, Constant.USER_NAME,userData.getName());
                        Log.e("TAG", "onDataChange: " + userData.getName());
                    }

                    if (userData.getId() != null) {
                        Constant.setString(context, Constant.USER_ID,userData.getId());
                        Log.e("TAG", "onDataChange: " + userData.getId());
                    }

                    if (userData.getNumber() != null) {
                        Constant.setString(context, Constant.USER_NUMBER,userData.getNumber());
                        Log.e("TAG", "onDataChange: " + userData.getNumber());
                    }

                    if (userData.getToken() != null) {
                        Constant.setString(context, Constant.TOKEN,userData.getToken());
                        Log.e("TAG", "onDataChange: " + userData.getToken());
                    }

                    if (userData.getPoints() != null) {
                        Constant.setString(context, Constant.USER_AMOUNT,userData.getPoints());
                        Log.e("TAG", "onDataChange: " + userData.getPoints());
                    }
                    mLoginView.verifyFirstOTPResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }


        if (tag == IApi.COMMON_TAG4) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {

                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();
                    VerifyOTPResponse userData = (VerifyOTPResponse) response.getResponsePacket();
                    if (userData.getReferralCode() != null) {
                        Constant.setString(context, Constant.USER_REFFER_CODE,userData.getReferralCode());
                        Log.e("TAG", "onDataChange: " + userData.getReferralCode());
                    }

                    if (userData.getName() != null) {
                        Constant.setString(context, Constant.USER_NAME,userData.getName());
                        Log.e("TAG", "onDataChange: " + userData.getName());
                    }

                    if (userData.getId() != null) {
                        Constant.setString(context, Constant.USER_ID,userData.getId());
                        Log.e("TAG", "onDataChange: " + userData.getId());
                    }

                    if (userData.getNumber() != null) {
                        Constant.setString(context, Constant.USER_NUMBER,userData.getNumber());
                        Log.e("TAG", "onDataChange: " + userData.getNumber());
                    }

                    if (userData.getToken() != null) {
                        Constant.setString(context, Constant.TOKEN,userData.getToken());
                        Log.e("TAG", "onDataChange: " + userData.getToken());
                    }

                    if (userData.getPoints() != null) {
                        Constant.setString(context, Constant.USER_POINTS,userData.getPoints());
                        Log.e("TAG", "onDataChange: " + userData.getPoints());
                    }

                    if (userData.getBalance() != null) {
                        Constant.setString(context, Constant.USER_AMOUNT,userData.getBalance());
                        Log.e("TAG", "onDataChange: " + userData.getBalance());
                    }

                    if (userData.getPayoutactive() != null) {
                        Constant.setString(context, Constant.PAYOUT_ACTIVE,userData.getPayoutactive());
                        Log.e("TAG", "onDataChange: " + userData.getPayoutactive());
                    }

                    mLoginView.verifySecondOTPResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG5) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {


                    ArrayList<AdKeysResponse> userData = (ArrayList<AdKeysResponse>) response.getResponsePacket();

                    mLoginView.adKeysResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }


        if (tag == IApi.COMMON_TAG6) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    WithdrawalResponse userData = (WithdrawalResponse) response.getResponsePacket();

                    mLoginView.withdrawResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG7) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    WithdrawalFirstResponse userData = (WithdrawalFirstResponse) response.getResponsePacket();

                    mLoginView.withdrawalFirstResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG8) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    WithdrawalFirstResponse userData = (WithdrawalFirstResponse) response.getResponsePacket();

                    mLoginView.withdrawalSecondResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }


        if (tag == IApi.COMMON_TAG9) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                 //   Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    ArrayList<TransactionResponse> userData = (ArrayList<TransactionResponse>) response.getResponsePacket();

                    mLoginView.transactionResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG10) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    //   Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    ConversionResponse userData = (ConversionResponse) response.getResponsePacket();

                    mLoginView.saveConvertEarningResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG11) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    //   Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    String userData = (String) response.getResponseMessage();

                    mLoginView.saveCoinsResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }


    }

    @Override
    public void onFail(int requestTag, Throwable t) {

    }


    @Override
    public void verifyMobile( String MobileNo, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.verifyMobile( MobileNo, this, networkCall);
    }

    @Override
    public void verifyUser(String email, String city, String name, String state, String password,String referral_with,String number, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.verifyUser( email,city,name,state,password,referral_with,number, this, networkCall);
    }

    @Override
    public void getReferUser(String referCode, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getReferUser( referCode, this, networkCall);
    }

    @Override
    public void verifyFirstOTP(String number, String otp, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.verifyFirstOTP( number, otp,this, networkCall);
    }

    @Override
    public void verifySecondOTP(String mobile, String otp, String refercode, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.verifySecondOTP( mobile, otp,refercode,this, networkCall);
    }

    @Override
    public void withdrawMoney(String mobile, String referalcode, String userflag, String amount, String upiid, String name, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.withdraw( mobile, referalcode,userflag,amount,upiid,name,this, networkCall);
    }

    @Override
    public void withdrawVerifyFirst(String userflag, String wid, String otp, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.withdrawFirstVerify(userflag,wid,otp,this, networkCall);
    }

    @Override
    public void withdrawVerifySecond(String userflag, String wid, String otp, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.withdrawSecondVerify(userflag,wid,otp,this, networkCall);
    }

    @Override
    public void getAdKeys( Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getAdKeys(  this, networkCall);
    }

    @Override
    public void getTransactionHistory(String referCode, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getTransactionHistory(referCode,  this, networkCall);
    }

    @Override
    public void saveConvertEarning(String refercode, String point, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.saveConvertEarningData(refercode,point , this, networkCall);
    }

    @Override
    public void saveCoins(String refercode, String point, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.saveCoinsData(refercode,point , this, networkCall);
    }

}
