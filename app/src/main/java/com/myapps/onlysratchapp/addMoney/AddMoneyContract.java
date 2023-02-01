package com.myapps.onlysratchapp.addMoney;


import android.content.Context;

import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.utils.BaseView;

import java.util.ArrayList;


public interface AddMoneyContract {
    interface View extends BaseView<Presenter> {

        void upiResponse(UPIResponse upiResponses);

    }

    interface Presenter {


        void getUPI( Context context);


    }
}
