package com.myapps.onlysratchapp.data.remote;



import com.myapps.onlysratchapp.addMoney.UPIResponse;
import com.myapps.onlysratchapp.category.CategoryResponse;
import com.myapps.onlysratchapp.data.DataSource;
import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
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
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.transferPoints.TRansferUserResponse;
import com.myapps.onlysratchapp.transferPoints.TransferPointsResponse;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }



    @Override
    public void verifyMobile( String mobile, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
       try{


          //  RequestBody username1 = RequestBody.create(MediaType.parse("text/plain"), username);
            RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);

            Call<BaseResponse<VerifyMobileResponse>> responceCall = networkCall.getRetrofit(true, true).verifyMobile(mobile1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyUser(String email, String city, String name, String state, String password, String refrral_with, String number, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody city1 = RequestBody.create(MediaType.parse("text/plain"), city);
            RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody state1 = RequestBody.create(MediaType.parse("text/plain"), state);
            RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), password);
            RequestBody referral_with1 = RequestBody.create(MediaType.parse("text/plain"), refrral_with);
            RequestBody number1 = RequestBody.create(MediaType.parse("text/plain"), number);

            Call<BaseResponse<VerifyUserResponse>> responceCall = networkCall.getRetrofit(true, true).verifyUser(email1,city1,name1,state1,password1,referral_with1,number1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG1);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReferUser(String referalcode, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "matchreferalcode");
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);

            Call<BaseResponse<ReferUserResponse>> responceCall = networkCall.getRetrofit(true, true).getReferUser(api,referalcode1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG2);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void verifyFirstOTP(String number, String opt, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

           /// RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "verifyotp");
          //  RequestBody userflag = RequestBody.create(MediaType.parse("text/plain"), "B");
            RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), number);
            RequestBody opt1 = RequestBody.create(MediaType.parse("text/plain"), opt);
           // RequestBody referalwith1 = RequestBody.create(MediaType.parse("text/plain"), referalwith);

            Call<BaseResponse<VerifyOTPResponse>> responceCall = networkCall.getRetrofit(true, true).verifyFirstOTP(mobile1,opt1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG3);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void verifySecondOTP(String mobile, String opt, String referalwith, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

           // RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "verifyotp");
            RequestBody userflag = RequestBody.create(MediaType.parse("text/plain"), "C");
            RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);
            RequestBody opt1 = RequestBody.create(MediaType.parse("text/plain"), opt);
            RequestBody referalwith1 = RequestBody.create(MediaType.parse("text/plain"), referalwith);

            Call<BaseResponse<VerifyOTPResponse>> responceCall = networkCall.getRetrofit(true, true).verifySecondOTP(userflag,mobile1,opt1,referalwith1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG4);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAdKeys(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "getapikeys");


            Call<BaseResponse<ArrayList<AdKeysResponse>>> responceCall = networkCall.getRetrofit(true, true).getAdKeys(api);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG5);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(String mobile, String referalcode, String userflag, String amount, String upiid, String name, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);
            //  RequestBody userflag1 = RequestBody.create(MediaType.parse("text/plain"), userflag);
            RequestBody amount1 = RequestBody.create(MediaType.parse("text/plain"), amount);
            RequestBody upiid1 = RequestBody.create(MediaType.parse("text/plain"), upiid);
            RequestBody name1 = RequestBody.create(MediaType.parse("text/plain"), name);


            Call<BaseResponse<WithdrawalResponse>> responceCall = networkCall.getRetrofit(true, true).withdrawal(mobile1,referalcode1,amount1,upiid1,name1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG6);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdrawFirstVerify(String userflag, String wid, String otp, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody userflag1 = RequestBody.create(MediaType.parse("text/plain"), userflag);
            RequestBody wid1 = RequestBody.create(MediaType.parse("text/plain"), wid);
            RequestBody otp1 = RequestBody.create(MediaType.parse("text/plain"), otp);

            Call<BaseResponse<WithdrawalFirstResponse>> responceCall = networkCall.getRetrofit(true, true).withdrawalFirstVerify(userflag1,wid1,otp1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG7);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdrawSecondVerify(String userflag, String wid, String otp, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody userflag1 = RequestBody.create(MediaType.parse("text/plain"), userflag);
            RequestBody wid1 = RequestBody.create(MediaType.parse("text/plain"), wid);
            RequestBody otp1 = RequestBody.create(MediaType.parse("text/plain"), otp);

            Call<BaseResponse<WithdrawalFirstResponse>> responceCall = networkCall.getRetrofit(true, true).withdrawalSecondVerify(userflag1,wid1,otp1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG8);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTransactionHistory(String referalcode, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);
            RequestBody for1 = RequestBody.create(MediaType.parse("text/plain"), "withdrawal");

            Call<BaseResponse<ArrayList<TransactionResponse>>> responceCall = networkCall.getRetrofit(true, true).getTransactionHistory(referalcode1,for1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG9);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getPassbookData(String referalcode, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "wallet");
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);

            Call<BaseResponse<ArrayList<PassbookResponse>>> responceCall = networkCall.getRetrofit(true, true).getPassbookData(api,referalcode1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveConvertEarningData(String referalcode, String point, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "convertpoint");
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);
            RequestBody point1 = RequestBody.create(MediaType.parse("text/plain"), point);

            Call<BaseResponse<ConversionResponse>> responceCall = networkCall.getRetrofit(true, true).saveConvertEarning(api,referalcode1,point1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG10);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveCoinsData(String referalcode, String coins, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "savecoins");
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);
            RequestBody point1 = RequestBody.create(MediaType.parse("text/plain"), coins);

            Call<BaseResponse> responceCall = networkCall.getRetrofit(true, true).saveCoins(api,referalcode1,point1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG11);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCoinsData(String referalcode, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "coins");
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);

            Call<BaseResponse<ArrayList<CoinsResponse>>> responceCall = networkCall.getRetrofit(true, true).getCoinsData(api,referalcode1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG1);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLevelData(String referalcode, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            RequestBody api = RequestBody.create(MediaType.parse("text/plain"), "selflink");
            RequestBody referalcode1 = RequestBody.create(MediaType.parse("text/plain"), referalcode);

            Call<BaseResponse<ArrayList<LevelResponse>>> responceCall = networkCall.getRetrofit(true, true).getLevelData(api,referalcode1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG2);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getTransferUser(String mobile, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);

            Call<BaseResponse<TRansferUserResponse>> responceCall = networkCall.getRetrofit(true, true).getTransferUser(mobile1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePointTransfer(String points, String transferTo, String transferFrom, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody points1 = RequestBody.create(MediaType.parse("text/plain"), points);
            RequestBody transferTo1 = RequestBody.create(MediaType.parse("text/plain"), transferTo);
            RequestBody transferFrom1 = RequestBody.create(MediaType.parse("text/plain"), transferFrom);

            Call<BaseResponse<TransferPointsResponse>> responceCall = networkCall.getRetrofit(true, true).savePointTransfer(points1,transferTo1,transferFrom1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG1);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUPI(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{


            Call<BaseResponse<UPIResponse>> responceCall = networkCall.getRetrofit(true, true).getUPI();
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCategory(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall) {
        try{

            RequestBody for1 = RequestBody.create(MediaType.parse("text/plain"), "appcategory");
            Call<BaseResponse<ArrayList<CategoryResponse>>> responceCall = networkCall.getRetrofit(true, true).getCategory(for1);
            networkCall.setServiceCallBack(myAppointmentPresenter);
            networkCall.setRequestTag(IApi.COMMON_TAG);
            responceCall.enqueue(networkCall.requestCallback());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


