package com.myapps.onlysratchapp.transferPoints;


import android.content.Context;

import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.utils.BaseView;

import java.util.ArrayList;


public interface TransferContract {
    interface View extends BaseView<Presenter> {

        void settRansferUserResponse(TRansferUserResponse tRansferUserResponse);
        void savePointsTransferResponse(TransferPointsResponse transferPointsResponse);

    }

    interface Presenter {


        void getTransferUser(String mobile, Context context);

        void savePoinsrTransfer(String point,String transferTo, String transferFrom, Context context);

    }
}
