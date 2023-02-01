package com.myapps.onlysratchapp.addMoney;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.myapps.onlysratchapp.data.DataSource;
import com.myapps.onlysratchapp.data.remote.RemoteDataSource;
import com.myapps.onlysratchapp.models.VerifyMobileResponse;
import com.myapps.onlysratchapp.network.BaseResponse;
import com.myapps.onlysratchapp.network.IApi;

import com.myapps.onlysratchapp.network.NetworkCall;
import com.myapps.onlysratchapp.network.ServiceCallBack;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Util;

import retrofit2.Response;


public class AddMoneyPresenter implements AddMoneyContract.Presenter, ServiceCallBack {
    private final DataSource loginDataSource;
    private final AddMoneyContract.View mLoginView;
    private Context context;


    public AddMoneyPresenter(@NonNull RemoteDataSource listDataSource, AddMoneyContract.View loginFragment) {
        loginDataSource = listDataSource;
        mLoginView = loginFragment;
        mLoginView.setPresenter(this);

    }


    @Override
    public void onSuccess(int tag, Response<BaseResponse> baseResponse) {
        if (tag == IApi.COMMON_TAG) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus() == true) {
                    //Toast.makeText(context, response.getResponseMessage(), Toast.LENGTH_LONG).show();

                    UPIResponse userData = (UPIResponse) response.getResponsePacket();


                    mLoginView.upiResponse(userData);


                } else {

                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }

        }}


    @Override
    public void onFail(int requestTag, Throwable t) {

    }




    @Override
    public void getUPI( Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getUPI(this, networkCall);
    }



}
