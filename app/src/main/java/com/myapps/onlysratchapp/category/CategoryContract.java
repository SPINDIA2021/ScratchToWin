package com.myapps.onlysratchapp.category;


import android.content.Context;


import com.myapps.onlysratchapp.utils.BaseView;

import java.util.ArrayList;


public interface CategoryContract {
    interface View extends BaseView<Presenter> {

        void categoryResponse(ArrayList<CategoryResponse> categoryResponse);

    }

    interface Presenter {


        void getCategory( Context context);


    }
}
