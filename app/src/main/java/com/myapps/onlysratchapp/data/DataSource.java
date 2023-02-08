package com.myapps.onlysratchapp.data;




import com.myapps.onlysratchapp.network.NetworkCall;
import com.myapps.onlysratchapp.network.ServiceCallBack;


public interface DataSource {

    void verifyMobile(String mobile, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void verifyUser(String email, String city,String name,String state,String password,String referral_with,String number, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getReferUser( String referalcode,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void verifyFirstOTP(String number, String opt, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void verifySecondOTP(String mobile, String opt,String referalwith, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getAdKeys( ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void withdraw(String mobile, String referalcode,String userflag,String amount,String upiid,String name, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void withdrawFirstVerify(String userflag,String wid,String otp, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void withdrawSecondVerify(String userflag,String wid,String otp, ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getTransactionHistory( String referalcode,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getPassbookData( String referalcode,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void saveConvertEarningData( String referalcode,String point,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void saveCoinsData( String referalcode,String coins,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getCoinsData( String referalcode,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getLevelData( String referalcode,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getTransferUser( String mobile,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void savePointTransfer( String points,String transferTo,String transferFrom,ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getUPI(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

    void getCategory(ServiceCallBack myAppointmentPresenter, NetworkCall networkCall);

}

