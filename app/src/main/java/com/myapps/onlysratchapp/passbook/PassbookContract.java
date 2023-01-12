package com.myapps.onlysratchapp.passbook;


import android.content.Context;

import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
import com.myapps.onlysratchapp.utils.BaseView;

import java.util.ArrayList;


public interface PassbookContract {
    interface View extends BaseView<Presenter> {

        void passbookResponse(ArrayList<PassbookResponse> passbookResponses);
        void coinsResponse(ArrayList<CoinsResponse> coinsResponses);
        void levelResponse(ArrayList<LevelResponse> levelResponses);

    }

    interface Presenter {


        void getPassbookData(String referCode, Context context);

        void getCoinsData(String referCode, Context context);

        void getLevelData(String referCode, Context context);


    }
}
