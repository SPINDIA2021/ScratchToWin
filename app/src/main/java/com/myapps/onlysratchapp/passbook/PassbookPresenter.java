package com.myapps.onlysratchapp.passbook;

import android.content.Context;

import androidx.annotation.NonNull;

import com.myapps.onlysratchapp.data.DataSource;
import com.myapps.onlysratchapp.data.remote.RemoteDataSource;
import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
import com.myapps.onlysratchapp.network.BaseResponse;
import com.myapps.onlysratchapp.network.IApi;
import com.myapps.onlysratchapp.network.NetworkCall;
import com.myapps.onlysratchapp.network.ServiceCallBack;
import com.myapps.onlysratchapp.utils.Util;

import java.util.ArrayList;

import retrofit2.Response;


public class PassbookPresenter implements PassbookContract.Presenter, ServiceCallBack {
    private final DataSource loginDataSource;
    private final PassbookContract.View mLoginView;
    private Context context;


    public PassbookPresenter(@NonNull RemoteDataSource listDataSource, PassbookContract.View loginFragment) {
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
                    //   Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    ArrayList<PassbookResponse> userData = (ArrayList<PassbookResponse>) response.getResponsePacket();

                    mLoginView.passbookResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }


        if (tag == IApi.COMMON_TAG1) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    //   Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    ArrayList<CoinsResponse> userData = (ArrayList<CoinsResponse>) response.getResponsePacket();

                    mLoginView.coinsResponse(userData);


                } else {
                    Util.showAlertBox(context, response.getResponseMessage(), null);
                }

            }
        }

        if (tag == IApi.COMMON_TAG2) {
            BaseResponse response = baseResponse.body();
            if (response != null) {
                if (response.isResponseStatus()==true) {
                    //   Toast.makeText(context,response.getResponseMessage(),Toast.LENGTH_LONG).show();

                    ArrayList<LevelResponse> userData = (ArrayList<LevelResponse>) response.getResponsePacket();

                    mLoginView.levelResponse(userData);


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
    public void getPassbookData(String referCode, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getPassbookData(referCode,  this, networkCall);
    }

    @Override
    public void getCoinsData(String referCode, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getCoinsData(referCode,  this, networkCall);
    }

    @Override
    public void getLevelData(String referCode, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getLevelData(referCode,  this, networkCall);
    }


}
