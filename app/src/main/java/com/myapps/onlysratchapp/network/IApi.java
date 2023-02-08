package com.myapps.onlysratchapp.network;



import com.myapps.onlysratchapp.addMoney.UPIResponse;
import com.myapps.onlysratchapp.category.CategoryResponse;
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
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.transferPoints.TRansferUserResponse;
import com.myapps.onlysratchapp.transferPoints.TransferPointsResponse;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface IApi {


   /*String BASE_URL="https://mewar.sahayatamoney.com/Applogin/";*/
   String BASE_URL="http://mewarpe.com/Applogin/";
//pocket.spindiabazaar.com

    int COMMON_TAG = 10031;
    int COMMON_TAG1 = 10032;
    int COMMON_TAG2 = 10034;
    int COMMON_TAG3 = 10035;
    int COMMON_TAG4 = 10036;
    int COMMON_TAG5 = 10037;
    int COMMON_TAG6 = 10038;
    int COMMON_TAG7 = 10039;
    int COMMON_TAG8 = 10040;
    int COMMON_TAG9 = 10041;
    int COMMON_TAG10 = 10042;
    int COMMON_TAG11 = 10043;
    int COMMON_TAG12 = 10044;


    @Multipart
    @POST("checkmobile")
    Call<BaseResponse<VerifyMobileResponse>> verifyMobile( @Part("number") RequestBody mobile);

    @Multipart
    @POST("registeration")
    Call<BaseResponse<VerifyUserResponse>> verifyUser(@Part("email") RequestBody email, @Part("city") RequestBody city, @Part("name") RequestBody name,
                                                     @Part("state") RequestBody state, @Part("password") RequestBody password, @Part("referral_with") RequestBody referral_with
                                                    , @Part("number") RequestBody number);


    @Multipart
    @POST("mobile.php")
    Call<BaseResponse<ReferUserResponse>> getReferUser(@Part("api") RequestBody api, @Part("referalcode") RequestBody referalcode );

    @Multipart
    @POST("verifyotp")
    Call<BaseResponse<VerifyOTPResponse>> verifyFirstOTP(@Part("number") RequestBody number, @Part("otp") RequestBody otp);

    @Multipart
    @POST("otpverification.php")
    Call<BaseResponse<VerifyOTPResponse>> verifySecondOTP(@Part("userflag") RequestBody userflag,@Part("mobile") RequestBody mobile, @Part("otp") RequestBody otp,
                                                    @Part("referalwith") RequestBody referalwith);

    @Multipart
    @POST("apikeys.php")
    Call<BaseResponse<ArrayList<AdKeysResponse>>> getAdKeys(@Part("api") RequestBody api );


   @Multipart
   @POST("withdrawal.php")
   Call<BaseResponse<WithdrawalResponse>> withdrawal(@Part("mobile") RequestBody mobile, @Part("referalcode") RequestBody referalcode,
                                                     @Part("amount") RequestBody amount, @Part("upiid") RequestBody upiid, @Part("name") RequestBody name);


   @Multipart
   @POST("withdrawal.php")
   Call<BaseResponse<WithdrawalFirstResponse>> withdrawalFirstVerify(@Part("userflag") RequestBody userflag, @Part("wid") RequestBody wid, @Part("otp") RequestBody otp);

    @Multipart
    @POST("withdrawal.php")
    Call<BaseResponse<WithdrawalFirstResponse>> withdrawalSecondVerify(@Part("userflag") RequestBody userflag, @Part("wid") RequestBody wid, @Part("otp") RequestBody otp);

    @Multipart
    @POST("historylist")
    Call<BaseResponse<ArrayList<TransactionResponse>>> getTransactionHistory(@Part("referalcode") RequestBody referalcode,@Part("for") RequestBody forwhom );

    @Multipart
    @POST("historylist")
    Call<BaseResponse<ArrayList<PassbookResponse>>> getPassbookData(@Part("for") RequestBody api, @Part("referalcode") RequestBody referalcode );

   @Multipart
   @POST("convertearning.php")
   Call<BaseResponse<ConversionResponse>> saveConvertEarning(@Part("apicase") RequestBody api, @Part("referalcode") RequestBody referalcode , @Part("point") RequestBody point);

   @Multipart
   @POST("coins")
   Call<BaseResponse> saveCoins(@Part("api") RequestBody api, @Part("referalcode") RequestBody referalcode , @Part("coins") RequestBody coins);


    @Multipart
    @POST("historylist")
    Call<BaseResponse<ArrayList<CoinsResponse>>> getCoinsData(@Part("for") RequestBody api, @Part("referalcode") RequestBody referalcode );

    @Multipart
    @POST("link.php")
    Call<BaseResponse<ArrayList<LevelResponse>>> getLevelData(@Part("api") RequestBody api, @Part("referalcode") RequestBody referalcode );


    @Multipart
    @POST("getnamefrommobile")
    Call<BaseResponse<TRansferUserResponse>> getTransferUser( @Part("mobile") RequestBody mobile );


    @Multipart
    @POST("pointtransfertootheruser")
    Call<BaseResponse<TransferPointsResponse>> savePointTransfer(@Part("point") RequestBody point, @Part("transferto") RequestBody transferto, @Part("transferfrom") RequestBody transferfrom );

    @POST("adminupiid")
    Call<BaseResponse<UPIResponse>> getUPI();

    @Multipart
    @POST("historylist")
    Call<BaseResponse<ArrayList<CategoryResponse>>> getCategory(@Part("for") RequestBody foruser );

}
