package com.myapps.onlysratchapp.fragments;

import static com.myapps.onlysratchapp.utils.Constant.hideKeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.android.material.textfield.TextInputEditText;
import com.myapps.onlysratchapp.App;
import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.activity.LoginContract;
import com.myapps.onlysratchapp.activity.LoginPresenter;
import com.myapps.onlysratchapp.activity.MainActivity;
import com.myapps.onlysratchapp.activity.VerifyFirstOTPActivity;
import com.myapps.onlysratchapp.activity.VerifySecondOTPActivity;
import com.myapps.onlysratchapp.activity.VerifyUserActivity;
import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.ReferUserResponse;
import com.myapps.onlysratchapp.models.TransactionResponse;
import com.myapps.onlysratchapp.models.User;
import com.myapps.onlysratchapp.models.VerifyMobileResponse;
import com.myapps.onlysratchapp.models.VerifyOTPResponse;
import com.myapps.onlysratchapp.models.VerifyUserResponse;
import com.myapps.onlysratchapp.models.WithdrawalFirstResponse;
import com.myapps.onlysratchapp.models.WithdrawalResponse;
import com.myapps.onlysratchapp.utils.BaseUrl;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.CustomVolleyJsonRequest;
import com.myapps.onlysratchapp.utils.Injection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;*/

public class LoginFragment extends Fragment implements  LoginContract.View {
    private LoginContract.Presenter presenter;

    private TextInputEditText user_edit_text, mobile_edit_text;
    private AppCompatButton login_button, singup_btn;
    private TextView forgot_textView;
    private Context mContext;
  //  private ACProgressFlower alertDialog;
    private ImageView back;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        user_edit_text = view.findViewById(R.id.user_edit_text);
        mobile_edit_text = view.findViewById(R.id.mobile_edit_text);
        login_button = view.findViewById(R.id.login_btn);
        singup_btn = view.findViewById(R.id.sign_up_btn);
        forgot_textView = view.findViewById(R.id.forgot_text);
      /*  alertDialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please Wait...")
                .fadeColor(Color.DKGRAY).build();*/
        back = view.findViewById(R.id.back_img_login);
        onInitView();

        return view;
    }

    private void onInitView() {
        new LoginPresenter(Injection.provideLoginRepository(getActivity()), this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().onBackPressed();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext)) {
                   // String name = user_edit_text.getText().toString().trim();
                    String mobile = mobile_edit_text.getText().toString().trim();
                   if (mobile.length() == 0) {
                        mobile_edit_text.setError(getResources().getString(R.string.enter_password));
                        mobile_edit_text.requestFocus();
                    } else {
                        hideKeyboard(getActivity());
                       // showProgressDialog();
                        presenter.verifyMobile(mobile,getActivity());
                       // signInWithEmailandPassword(name, mobile);
                    }
                } else {
                    Constant.showInternetErrorDialog(mContext, getResources().getString(R.string.no_internet_connection));
                }
            }
        });

        singup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Login, SignUpFragment.newInstance()).addToBackStack(null).commit();

            }
        });

        forgot_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Login, ForgotFragment.newInstance()).addToBackStack(null).commit();

            }
        });

    }

    public void showProgressDialog() {
        /*if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
        }*/
    }

    public void hideProgressDialog() {
       /* if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }*/
    }

    private void signInWithEmailandPassword(String email, final String password) {
        if (getActivity() == null) {
            showProgressDialog();
            return;
        }
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("get_login", "any");
        params.put("email", email);
        params.put("password", password);
        Log.e("TAG", "signupNewUser: " + params);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseUrl.LOGIN_API, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        JSONObject jsonObject = response.getJSONObject("0");
                        Constant.setString(mContext, Constant.USER_ID, jsonObject.getString("id"));
                        Constant.setString(mContext, Constant.USER_PASSWORD, password);
                        final User user = new User(jsonObject.getString("name"), jsonObject.getString("number"), jsonObject.getString("email"), jsonObject.getString("points"), jsonObject.getString("referraled_with"),jsonObject.getString("image"), jsonObject.getString("status"), jsonObject.getString("referral_code"));
                        if (getActivity() == null) {
                            hideProgressDialog();
                            return;
                        }

                        if (user.getName() != null) {
                            Constant.setString(mContext, Constant.USER_NAME, user.getName());
                            Log.e("TAG", "onDataChange: " + user.getName());
                        }
                        if (user.getNumber() != null) {
                            Constant.setString(mContext, Constant.USER_NUMBER, user.getNumber());
                            Log.e("TAG", "onDataChange: " + user.getNumber());
                        }
                        if (user.getEmail() != null) {
                            Constant.setString(mContext, Constant.USER_EMAIL, user.getEmail());
                            Log.e("TAG", "onDataChange: " + user.getEmail());
                        }
                        if (user.getPoints() != null) {
                            Constant.setString(mContext, Constant.USER_POINTS, "0");
                            Log.e("TAG", "onDataChange: " + user.getPoints());
                        }
                        if (user.getReferCode() != null) {
                            Constant.setString(mContext, Constant.REFER_CODE, user.getReferCode());
                            Log.e("TAG", "onDataChange: " + user.getReferCode());
                        }
                        if (user.getIsBLocked() != null) {
                            Constant.setString(mContext, Constant.USER_BLOCKED, user.getIsBLocked());
                            Log.e("TAG", "onDataChange: " + user.getIsBLocked());
                        }
                        if (user.getUserReferCode() != null) {
                            Constant.setString(mContext, Constant.USER_REFFER_CODE, user.getUserReferCode());
                            Log.e("TAG", "onDataChange: " + user.getUserReferCode());
                        }

                        hideProgressDialog();
                        if (Constant.getString(mContext, Constant.USER_BLOCKED).equals("0")) {
                            Constant.showBlockedDialog(mContext, getResources().getString(R.string.you_are_blocked));
                        } else {
                          //  Constant.setString(mContext, Constant.IS_LOGIN, "true");
                            Constant.showToastMessage(mContext, getResources().getString(R.string.login_successfully));
                            updateUI();
                        }
                    } else {
                        Constant.showToastMessage(mContext, "Invalid Email Or Password");
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Constant.showToastMessage(mContext, getResources().getString(R.string.slow_internet_connection));
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                1000 * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.Companion.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void updateUI() {
        if (getActivity() == null) {
            return;
        }
        Constant.GotoNextActivity(mContext, MainActivity.class, "");
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        getActivity().finish();
    }

    @Override
    public void loginResponse(VerifyMobileResponse loginResponse) {
       // Toast.makeText(getActivity(),loginResponse,Toast.LENGTH_LONG).show();



        if (loginResponse.getUserflag().equals("A"))
        {
            Intent intent=new Intent(getActivity(), VerifyUserActivity.class);
            intent.putExtra("mobile", mobile_edit_text.getText().toString());
            intent.putExtra("newuser",loginResponse.getNewuser());
            intent.putExtra("userflag",loginResponse.getUserflag());
            //intent.putExtra("username",user_edit_text.getText().toString());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }else if (loginResponse.getUserflag().equals("B"))
        {
            Intent intent=new Intent(getActivity(), VerifyFirstOTPActivity.class);
            intent.putExtra("mobile", mobile_edit_text.getText().toString());
            intent.putExtra("newuser",loginResponse.getNewuser());
            intent.putExtra("userflag",loginResponse.getUserflag());
            //intent.putExtra("username",user_edit_text.getText().toString());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }else {
            Constant.setString(mContext, Constant.IS_LOGIN, "true");
            Constant.showToastMessage(mContext, getResources().getString(R.string.login_successfully));
            Constant.GotoNextActivity(mContext, MainActivity.class, "");
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }

       /* if (loginResponse.getUserflag().equals("A"))
        {
            Intent intent=new Intent(getActivity(), VerifyUserActivity.class);
            intent.putExtra("mobile", mobile_edit_text.getText().toString());
            intent.putExtra("newuser","Y");
            intent.putExtra("userflag","A");
            //intent.putExtra("username",user_edit_text.getText().toString());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }else if (loginResponse.getUserflag().equals("B"))
        {
            Intent intent=new Intent(getActivity(), VerifyUserActivity.class);
            intent.putExtra("mobile", mobile_edit_text.getText().toString());
            intent.putExtra("newuser","Y");
            intent.putExtra("userflag","B");
            //intent.putExtra("username",user_edit_text.getText().toString());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }else if (loginResponse.getUserflag().equals("C"))
        {
            Intent intent=new Intent(getActivity(), VerifySecondOTPActivity.class);
            intent.putExtra("mobile", mobile_edit_text.getText().toString());
            intent.putExtra("newuser","Y");
            intent.putExtra("userflag","C");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }else if (loginResponse.getUserflag().equals("D"))
        {
            Intent intent=new Intent(getActivity(), VerifyFirstOTPActivity.class);
            intent.putExtra("mobile", mobile_edit_text.getText().toString());
            intent.putExtra("newuser","N");
            intent.putExtra("userflag","D");
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }else if (loginResponse.getUserflag().equals("E"))
        {
            Constant.setString(mContext, Constant.IS_LOGIN, "true");
            Constant.showToastMessage(mContext, getResources().getString(R.string.login_successfully));
            Constant.GotoNextActivity(mContext, MainActivity.class, "");
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            getActivity().finish();
        }*/

    }

    @Override
    public void verifyUserResponse(VerifyUserResponse verifyUserResponse) {

    }


    @Override
    public void getReferUserResponse(ReferUserResponse referUserResponse, String Message) {

    }

    @Override
    public void verifyFirstOTPResponse(VerifyOTPResponse verifyOTPResponse) {

    }

    @Override
    public void verifySecondOTPResponse(VerifyOTPResponse verifyOTPResponse) {

    }


    @Override
    public void adKeysResponse(ArrayList<AdKeysResponse> adKeysResponse) {

    }

    @Override
    public void withdrawResponse(WithdrawalResponse withdrawalResponse) {

    }

    @Override
    public void withdrawalFirstResponse(WithdrawalFirstResponse withdrawalFirstResponse) {

    }

    @Override
    public void withdrawalSecondResponse(WithdrawalFirstResponse withdrawalFirstResponse) {

    }

    @Override
    public void transactionResponse(ArrayList<TransactionResponse> transactionResponse) {

    }

    @Override
    public void saveConvertEarningResponse(ConversionResponse message) {

    }

    @Override
    public void saveCoinsResponse(String message) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter=presenter;
    }
}