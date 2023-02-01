package com.myapps.onlysratchapp.transferPoints;

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
import com.myapps.onlysratchapp.passbook.PassbookContract;
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.utils.Util;

import java.util.ArrayList;

import retrofit2.Response;


public class TransferPresenter implements TransferContract.Presenter, ServiceCallBack {
    private final DataSource loginDataSource;
    private final TransferContract.View mLoginView;
    private Context context;


    public TransferPresenter(@NonNull RemoteDataSource listDataSource, TransferContract.View loginFragment) {
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

                   TRansferUserResponse userData = (TRansferUserResponse) response.getResponsePacket();

                    mLoginView.settRansferUserResponse(userData);


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

                    TransferPointsResponse userData = (TransferPointsResponse) response.getResponsePacket();

                    mLoginView.savePointsTransferResponse(userData);


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
    public void getTransferUser(String mobile, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.getTransferUser(mobile,  this, networkCall);
    }

    @Override
    public void savePoinsrTransfer(String point, String transferTo, String transferFrom, Context context) {
        NetworkCall networkCall = new NetworkCall(context);
        this.context = context;
        loginDataSource.savePointTransfer(point,transferTo,transferFrom,  this, networkCall);
    }


}
