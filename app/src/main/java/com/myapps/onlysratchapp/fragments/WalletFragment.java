package com.myapps.onlysratchapp.fragments;

import static com.myapps.onlysratchapp.utils.Constant.hideKeyboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.textfield.TextInputLayout;
import com.myapps.onlysratchapp.App;
import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.activity.LoginActivity;
import com.myapps.onlysratchapp.activity.LoginContract;
import com.myapps.onlysratchapp.activity.LoginPresenter;
import com.myapps.onlysratchapp.activity.MainActivity;
import com.myapps.onlysratchapp.activity.WithdrawFirstVerifyActivity;
import com.myapps.onlysratchapp.models.AdKeysResponse;
import com.myapps.onlysratchapp.models.ConversionResponse;
import com.myapps.onlysratchapp.models.ReferUserResponse;
import com.myapps.onlysratchapp.models.TransactionResponse;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;*/

public class WalletFragment extends Fragment implements  LoginContract.View {
    private LoginContract.Presenter presenter;
    private TextView points_textView, amount_textView,minimum_redeem_text;
    private TextInputEditText name_editText, number_edit_text, upi_edit_text, points_edit_text;
    private TextInputLayout email_lyt, number_lyt;
    private AppCompatButton submit_btn;
    private ImageView backImage;
    private RadioButton upiBtn, paypalBtn, paytmBtn;
   // private ACProgressFlower progressDialog;
    private Context mContext;
    private RelativeLayout layConvert;


    public WalletFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static WalletFragment newInstance() {
        WalletFragment fragment = new WalletFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        backImage = view.findViewById(R.id.back_img);
        minimum_redeem_text = view.findViewById(R.id.minimum_redeem_text);
        points_textView = view.findViewById(R.id.points_textView);
        amount_textView = view.findViewById(R.id.points_textView);
        name_editText = view.findViewById(R.id.name_edit_text_redeem);
        number_lyt = view.findViewById(R.id.redeem_number_lyt);
        number_edit_text = view.findViewById(R.id.number_edit_text_redeem);
        email_lyt = view.findViewById(R.id.redeem_email_lyt);
        upi_edit_text = view.findViewById(R.id.upi_edit_text_redeem);
        points_edit_text = view.findViewById(R.id.points_edit_text_redeem);
        submit_btn = view.findViewById(R.id.redeem_btn);
        paypalBtn = view.findViewById(R.id.radio_paypal);
        upiBtn = view.findViewById(R.id.radio_upi);
        paytmBtn = view.findViewById(R.id.radio_paytm);
        layConvert=view.findViewById(R.id.lay_convert);

        new LoginPresenter(Injection.provideLoginRepository(getActivity()), this);
        onClick();

        return view;
    }

    private void onClick() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().onBackPressed();
            }
        });

        String amount = Constant.getString(mContext, Constant.USER_AMOUNT);
        if (amount.equalsIgnoreCase("")) {
            amount = "0";
        }
        amount_textView.setText(amount);


        String points = Constant.getString(mContext, Constant.USER_POINTS);
        if (points.equalsIgnoreCase("")) {
            points = "0";
        }
        points_textView.setText(points);

        String payoutActive = Constant.getString(mContext, Constant.PAYOUT_ACTIVE);

        if (amount.equals("0")|| payoutActive.equals("N"))
        {
            submit_btn.setVisibility(View.GONE);
        }else {
            submit_btn.setVisibility(View.VISIBLE);
        }

        String user_name = Constant.getString(mContext, Constant.USER_NAME);
        if (user_name.equalsIgnoreCase("")) {
            user_name = "";
        }
        name_editText.setText(user_name);
        String minimumRedeemText = getResources().getString(R.string.minimum_redeem_points);
        if (minimumRedeemText == null || minimumRedeemText.equalsIgnoreCase("")) {
            minimum_redeem_text.setText("0");
            Constant.showToastMessage(mContext, "Enter Minimum Redeem Amount in String File");
        } else {
            minimum_redeem_text.setText("Minimum Redeem: " + minimumRedeemText);
        }

        layConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String points = Constant.getString(mContext, Constant.USER_POINTS);
                if (points.equalsIgnoreCase("")) {
                    points = "0";
                }
                if (Integer.parseInt(points)<1000)
                {
                    Toast.makeText(getActivity(),"Insufficient coins",Toast.LENGTH_LONG).show();
                }else {
                    callServiceConvertPoints(points.toString());
                }
            }
        });

        paypalBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    email_lyt.setVisibility(View.VISIBLE);
                    number_lyt.setVisibility(View.GONE);
                    email_lyt.setHint("Enter Paypal Email Id");
                    paytmBtn.setChecked(false);
                    upiBtn.setChecked(false);
                } else {
                    email_lyt.setVisibility(View.GONE);
                    number_lyt.setVisibility(View.VISIBLE);
                }
            }
        });

        paytmBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    email_lyt.setVisibility(View.GONE);
                    number_lyt.setVisibility(View.VISIBLE);
                    number_lyt.setHint("Enter Paytm Number");
                    upiBtn.setChecked(false);
                    paypalBtn.setChecked(false);
                }
            }
        });

        upiBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    email_lyt.setVisibility(View.VISIBLE);
                    number_lyt.setVisibility(View.GONE);
                    email_lyt.setHint("Enter Upi Id");
                    paytmBtn.setChecked(false);
                    paypalBtn.setChecked(false);
                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String is_login = Constant.getString(mContext, Constant.IS_LOGIN);
                if (is_login.equalsIgnoreCase("true")) {
                    callServiceWithdrawal();
                    //attemptRedeem();
                    hideKeyboard(getActivity());
                } else {
                    Constant.showToastMessage(mContext, "Login First");
                    Constant.GotoNextActivity(mContext, LoginActivity.class, "");
                }
            }
        });
    }

    private void callServiceWithdrawal() {
        String refercode = Constant.getString(mContext, Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }
        if (Constant.isNetworkAvailable(mContext)) {
            String name = name_editText.getText().toString().trim();
            String number = number_edit_text.getText().toString().trim();
            String upi = upi_edit_text.getText().toString().trim();
            String points = points_edit_text.getText().toString().trim();
            if (name.length() == 0) {
                name_editText.setError("Enter name");
                name_editText.requestFocus();
            }else  if (number.length() == 0) {
                number_edit_text.setError("Enter mobile number");
                number_edit_text.requestFocus();
            } else if (upi.length() == 0) {
                upi_edit_text.setError("Enter UPI Id");
                upi_edit_text.requestFocus();
            } else if (points.length() == 0) {
                points_edit_text.setError("Enter points");
                points_edit_text.requestFocus();
            }else {
                hideKeyboard(getActivity());
                // showProgressDialog();
                presenter.withdrawMoney(number,refercode,"F",points,upi,name,getActivity());
                // signInWithEmailandPassword(name, mobile);
            }
        } else {
            Constant.showInternetErrorDialog(mContext, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void attemptRedeem() {
        boolean isCanceled = false;
        View focusView = null;
        String name = "", number = "", upiId = "", points = "", selectedtext = "";

        if (paytmBtn.isChecked()) {
            selectedtext = "Paytm";
        }
        if (upiBtn.isChecked()) {
            selectedtext = "Upi";
        }

        if (paypalBtn.isChecked()) {
            selectedtext = "Paypal";
        }

        if (TextUtils.isEmpty(selectedtext)) {
            isCanceled = true;
            Log.d("TAG", "attemptRedeem: " + " " + selectedtext);
            Constant.showToastMessage(mContext, "Please Select Payment Method");
        } else {
            Log.d("TAG", "attemptRedeem: " + selectedtext);
            name = name_editText.getText().toString().trim();
            number = number_edit_text.getText().toString().trim();
            upiId = upi_edit_text.getText().toString().trim();
            points = points_edit_text.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                focusView = name_editText;
                focusView.requestFocus();
                Constant.showToastMessage(mContext, "Enter Name");
                return;
            }
            if (selectedtext.equalsIgnoreCase("Paypal") || selectedtext.equalsIgnoreCase("Upi")) {
                if (TextUtils.isEmpty(upiId)) {
                    focusView = upi_edit_text;
                    focusView.requestFocus();
                    Constant.showToastMessage(mContext, "Enter Email");
                    return;
                }
                if (selectedtext.equalsIgnoreCase("Paypal")) {
                    if (!Constant.isValidEmailAddress(upiId)) {
                        focusView = upi_edit_text;
                        focusView.requestFocus();
                        Constant.showToastMessage(mContext, "Enter Correct Email");
                        return;
                    }
                }
            } else {
                if (TextUtils.isEmpty(number)) {
                    focusView = number_edit_text;
                    focusView.requestFocus();
                    Constant.showToastMessage(mContext, "Enter Number");
                    return;
                }
            }
            if (TextUtils.isEmpty(points)) {
                focusView = points_edit_text;
                focusView.requestFocus();
                Constant.showToastMessage(mContext, "Enter Points");
                return;
            }
            if (points.equalsIgnoreCase("")) {
                points = "0";
            }
            if (Integer.parseInt(points) < Integer.parseInt(getResources().getString(R.string.minimum_redeem_points))) {
                Constant.showToastMessage(mContext, "Minimum Redeem Coins is " + getResources().getString(R.string.minimum_redeem_points));
                focusView = points_edit_text;
                focusView.requestFocus();
                return;
            }
            String userPoints = Constant.getString(mContext, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            if (Integer.parseInt(userPoints) < Integer.parseInt(points)) {
                Constant.showToastMessage(mContext, "You Have Not Enough Coins");
                focusView = points_edit_text;
                focusView.requestFocus();
                return;
            }
        }

        if (isCanceled) {

        } else {
            if (selectedtext.equalsIgnoreCase("Paypal") || selectedtext.equalsIgnoreCase("Upi")) {
                RedeemPointsDialog(name, upiId, points, selectedtext);
            } else {
                RedeemPointsDialog(name, number, points, selectedtext);
            }
        }
    }

    private void RedeemPointsDialog(final String name, final String numberOrUpiId, final String points, final String type) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_points_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView imageView = dialog.findViewById(R.id.points_image);
        TextView title_text = dialog.findViewById(R.id.title_text_points);
        TextView points_text = dialog.findViewById(R.id.points);
        AppCompatButton confirm_btn = dialog.findViewById(R.id.add_btn);
        AppCompatButton cancel_btn = dialog.findViewById(R.id.cancel_btn);

        imageView.setImageResource(R.drawable.coin);
        title_text.setVisibility(View.VISIBLE);
        points_text.setVisibility(View.VISIBLE);
        confirm_btn.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.VISIBLE);

        title_text.setText(getResources().getString(R.string.redeem_tag_line_1));
        String points_text_string = getResources().getString(R.string.redeem_tag_line_2) + " " + numberOrUpiId + " " + getResources().getString(R.string.redeem_tag_line_3) + " " + points + " " + getResources().getString(R.string.redeem_tag_line_4) + " " + type;
        points_text.setText(points_text_string);
        confirm_btn.setText(getResources().getString(R.string.yes));
        cancel_btn.setText(getResources().getString(R.string.cancel));

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
              /*  progressDialog = new ACProgressFlower.Builder(mContext)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Please Wait...")
                        .fadeColor(Color.DKGRAY).build();*/
                submit_btn.setEnabled(false);
                showProgressDialog();
                atteptRequest(numberOrUpiId, points,type, Constant.getString(mContext, Constant.REFER_CODE));
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_btn.setEnabled(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void atteptRequest(String numberOrUpiId, String points, String type, final String refer_by) {
        try {
            submit_btn.setEnabled(true);
            final String user_previous_points = Constant.getString(mContext, Constant.USER_POINTS);
            final int current_points = Integer.parseInt(user_previous_points) - Integer.parseInt(points);
            Constant.setString(mContext, Constant.USER_POINTS, String.valueOf(current_points));
            points_textView.setText(String.valueOf(current_points));
            String tag_json_obj = "json_login_req";
            Map<String, String> params = new HashMap<String, String>();
            params.put("redeem_point", "redeem");
            if (!refer_by.equalsIgnoreCase("")) {
                params.put("referraled_with", refer_by);
            }
            params.put("user_id", Constant.getString(mContext, Constant.USER_ID));
            params.put("new_point", String.valueOf(current_points));
            params.put("redeemed_point", points);
            params.put("payment_mode", type);
            params.put("payment_info", numberOrUpiId);
            Log.e("TAG", "signupNewUser: " + params);

            CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                    BaseUrl.UPDATE_POINTS, params, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d("TAG", response.toString());

                    try {
                        hideProgressDialog();
                        boolean status = response.getBoolean("status");
                        if (status) {
                            Constant.showToastMessage(mContext, getResources().getString(R.string.redeem_successfully));
                            Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.USER_POINTS)), 1);
                        } else {
                            Constant.showToastMessage(mContext, response.getString("message"));
                            points_textView.setText(user_previous_points);
                            Constant.setString(mContext, Constant.USER_POINTS, user_previous_points);
                            Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.USER_POINTS)), 1);
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
                    points_textView.setText(user_previous_points);
                    Constant.setString(mContext, Constant.USER_POINTS, user_previous_points);
                    Constant.addPoints(mContext, Integer.parseInt(Constant.getString(mContext, Constant.USER_POINTS)), 1);
                }
            });
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                    1000 * 20,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            App.Companion.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        } catch (Exception ex) {
            hideProgressDialog();
            Log.e("TAG", "makeRedeemRequest: " + ex.getMessage());
        }

    }

    public void showProgressDialog() {
      /*  if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }*/
    }

    public void hideProgressDialog() {
      /*  if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }*/
    }

    private void callServiceConvertPoints(String points) {
        String refercode = Constant.getString(getContext(), Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }
        presenter.saveConvertEarning(refercode,points,getActivity());
    }

    @Override
    public void loginResponse(VerifyMobileResponse loginResponse) {

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

        if (withdrawalResponse.getUserflag().equals("B"))
        {
            Intent intent=new Intent(getActivity(), WithdrawFirstVerifyActivity.class);
            intent.putExtra("mobile", number_edit_text.getText().toString());
            intent.putExtra("wid",withdrawalResponse.getId());
            intent.putExtra("userflag",withdrawalResponse.getUserflag());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

        }else {
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }
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
    public void saveConvertEarningResponse(ConversionResponse conversionResponse) {
        DecimalFormat df = new DecimalFormat("#,##,##,###.##");
        points_textView.setText(""+df.format(conversionResponse.getAmount()));

        if (conversionResponse.getAmount() != null) {
            Constant.setString(getActivity(), Constant.USER_AMOUNT,conversionResponse.getAmount().toString());

        }

        if (conversionResponse.getCoin() != null) {
            Constant.setString(getActivity(), Constant.USER_POINTS,conversionResponse.getCoin().toString());

        }
    }

    @Override
    public void saveCoinsResponse(String message) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
       this.presenter=presenter;
    }
}