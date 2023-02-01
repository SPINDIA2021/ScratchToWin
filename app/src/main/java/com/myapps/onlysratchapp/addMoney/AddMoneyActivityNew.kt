package com.myapps.onlysratchapp.addMoney

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.addMoney.activities_upi.PaymentSuccess
import com.myapps.onlysratchapp.network_calls.AppApiCalls
import com.myapps.onlysratchapp.utils.AppCommonMethods
import com.myapps.onlysratchapp.utils.AppConstants
import com.myapps.onlysratchapp.utils.Constant
import com.myapps.onlysratchapp.utils.Injection
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.PaymentApp
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus
import kotlinx.android.synthetic.main.activity_add_money.*
import org.json.JSONObject
import java.util.*


class AddMoneyActivityNew  : AppCompatActivity(),
        PaymentStatusListener , AppApiCalls.OnAPICallCompleteListener, AddMoneyContract.View{
    private var presenter: AddMoneyContract.Presenter? = null
   // sahayataudr@ybl
    var upiid: String = ""
    private var radioAppChoice: RadioGroup? = null
    val GETUPIAPI = "GETUPIAPI"
    val UPDATE_BALANCE = "UPDATE_BALANCE"

    var minimumamount: String? = null


    lateinit var transactionId: String
    lateinit var responseCode: String
    lateinit var approvalRefNo: String
    lateinit var txnRef: String

    lateinit var cus_mobile: String
    var cus_type="retailer"
    var cus_pin=""
    var cus_pass=""
    var cus_id=""
    var cus_name=""


    private var easyUpiPayment: EasyUpiPayment? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }

            cus_mobile = Constant.getString(this, Constant.USER_NUMBER)
            cus_id = Constant.getString(this, Constant.USER_ID)
            cus_name = Constant.getString(this, Constant.FIRST_NAME)

            // on below line we are getting date and then we are setting this date as transaction id.

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_add_money)
            initViews()
            cvProceedAddMoneyBtn!!.setOnClickListener { v: View? ->
               // launchUPI()
                pay()
            }
        }

        private fun initViews() {
            AddMoneyPresenter(Injection.provideLoginRepository(this), this)

            presenter!!.getUPI(this)

            val transactionId = "TID" + System.currentTimeMillis()
            radioAppChoice = findViewById(R.id.radioAppChoice)

            getBalanceApi(cus_mobile)
        }

        @SuppressLint("NonConstantResourceId")
        private fun pay() {

            val payeeVpa = upiid
            val payeeName = "Sushil Kumar Saini"
            val payeeMerchantCode = "Q745680599"
             transactionId = "TID" + System.currentTimeMillis()
            val transactionRefId = transactionId
            val description = "Payment"
            val amount: String = et_ammount.getText().toString()+".00"
            val paymentAppChoice = findViewById<RadioButton>(
                radioAppChoice!!.checkedRadioButtonId
            )
            val paymentApp: PaymentApp
            paymentApp = when (paymentAppChoice.id) {
                R.id.app_default -> PaymentApp.ALL
                R.id.app_amazonpay -> PaymentApp.AMAZON_PAY
                R.id.app_bhim_upi -> PaymentApp.BHIM_UPI
                R.id.app_google_pay -> PaymentApp.GOOGLE_PAY
                R.id.app_phonepe -> PaymentApp.PHONE_PE
                R.id.app_paytm -> PaymentApp.PAYTM
                else -> throw IllegalStateException("Unexpected value: " + paymentAppChoice.id)
            }


            if (TextUtils.isEmpty(amount) && TextUtils.isEmpty(upiid) && TextUtils.isEmpty(cus_id) && TextUtils.isEmpty(
                    description
                )
            ) {
                Toast.makeText(
                    this@AddMoneyActivityNew,
                    "Please enter amount..",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // if the edit text is not empty then
                // we are calling method to make payment.
                // START PAYMENT INITIALIZATION
                val builder: EasyUpiPayment.Builder = EasyUpiPayment.Builder(this)
                    .with(paymentApp)
                    .setPayeeVpa(payeeVpa)
                    .setPayeeName(payeeName)
                    .setTransactionId(transactionId)
                    .setTransactionRefId(transactionRefId)
                    .setPayeeMerchantCode(payeeMerchantCode)
                    .setDescription(description)
                    .setAmount(amount)
                // END INITIALIZATION
                try {
                    // Build instance
                    easyUpiPayment = builder.build()

                    // Register Listener for Events
                    easyUpiPayment!!.setPaymentStatusListener(this)

                    // Start payment / transaction
                    easyUpiPayment!!.startPayment()
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    toast("Error: " + exception.message)
                }
            }

            val toolbar = findViewById<Toolbar>(R.id.custToolbar)
            val ivBackBtn = toolbar.findViewById<ImageView>(R.id.ivBackBtn)
            ivBackBtn.setOnClickListener { onBackPressed() }

        }




        override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
            // Transaction Completed
            Log.d("TransactionDetails", transactionDetails.toString())

            try {
                // Get transactions details from response.

                if (transactionDetails!!.transactionStatus.toString().toLowerCase() == "success") {

                    addFundsApi(
                        cus_id, et_ammount.text.toString(), "UPI Payment",
                        transactionDetails!!.transactionId.toString(),
                        transactionDetails!!.transactionRefId.toString(),
                        "",
                        "",
                        cus_pin,
                        cus_pass,
                        cus_mobile, cus_type
                    )
                    transactionId = transactionDetails!!.transactionId.toString()
                    txnRef = transactionDetails!!.transactionRefId.toString()
                    responseCode = transactionDetails!!.responseCode.toString()
                    approvalRefNo = transactionDetails!!.approvalRefNo.toString()
                    Log.e("transactionId", transactionId)
                    Log.e("responseCode ", responseCode)
                    Log.e("approvalRefNo", approvalRefNo)
                    Log.e("txnRef       ", txnRef)

                } else if (transactionDetails!!.transactionStatus.toString().toLowerCase(Locale.getDefault()) == "submitted") {
                    val intent = Intent(
                        this@AddMoneyActivityNew,
                        PaymentSuccess::class.java
                    )
                    intent.putExtra("status", 2)
                    intent.putExtra("amount", et_ammount.text.toString())
                    intent.putExtra("transactionId", transactionDetails.transactionId)
                    intent.putExtra("responseCode", transactionDetails.responseCode)
                    intent.putExtra("approvalRefNo", transactionDetails.approvalRefNo)
                    intent.putExtra("txnRef", transactionDetails.transactionRefId)
                    startActivity(intent)
                } else {
                    val intent = Intent(
                        this@AddMoneyActivityNew,
                        PaymentSuccess::class.java
                    )
                    intent.putExtra("status", 0)
                    intent.putExtra("amount", et_ammount.text.toString())
                    intent.putExtra("transactionId", transactionDetails.transactionId)
                    intent.putExtra("responseCode", transactionDetails.responseCode)
                    intent.putExtra("approvalRefNo", transactionDetails.approvalRefNo)
                    intent.putExtra("txnRef", transactionDetails.transactionRefId)
                    startActivity(intent)
                }
            } catch (e: Exception) {

            }


           // statusView!!.text = transactionDetails.toString()
            when (transactionDetails.transactionStatus) {
                TransactionStatus.SUCCESS -> onTransactionSuccess()
                TransactionStatus.FAILURE -> onTransactionFailed()
                TransactionStatus.SUBMITTED -> onTransactionSubmitted()
            }
        }

        override fun onTransactionCancelled() {
            // Payment Cancelled by User
            toast("Cancelled by user")

        }

        private fun onTransactionSuccess() {
            // Payment Success
            toast("Success")

        }

        private fun onTransactionSubmitted() {
            // Payment Pending
            toast("Pending | Submitted")

        }

        private fun onTransactionFailed() {
            // Payment Failed
            toast("Failed")

        }

        private fun toast(message: String) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } //    Uncomment this if you have inherited [android.app.Activity] and not [androidx.appcompat.app.AppCompatActivity]
        //
        //    @Override
        //    protected void onDestroy() {
        //        super.onDestroy();
        //        easyUpiPayment.removePaymentStatusListener();
        //    }


    private fun getBalanceApi(cus_id: String) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                AppConstants.BALANCE_API,
                this
            )
            mAPIcall.getBalance(cus_id)

        } else {
            toast(getString(R.string.error_internet))
        }
    }


    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(GETUPIAPI)) {
            Log.e("GETUPIAPI", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                val cast = jsonObject.getJSONArray("data")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    // upi_id = notifyObjJson.getString("upi_id")

                    minimumamount = notifyObjJson.getString("minimun_amount")
                    tvMinimumAmount.text =
                        "( Minimum Amount - ₹ " + notifyObjJson.getString("minimun_amount") + ")"

                    Log.e("upi_id", upiid!!)


                }


            } else {
                progress_bar.visibility = View.GONE


            }
        }
        if (flag.equals(UPDATE_BALANCE)) {
            Log.e("UPDATE_BALANCE", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains("true")) {
                progress_bar.visibility = View.GONE

                /*    val intent = Intent(
                        this@AddMoneyActivity,
                        PaymentSuccess::class.java
                    )
                    intent.putExtra("status", 1)
                    intent.putExtra("amount", et_ammount.text.toString())
                    intent.putExtra("transactionId", transactionId)
                    intent.putExtra("responseCode", responseCode)
                    intent.putExtra("approvalRefNo", approvalRefNo)
                    intent.putExtra("txnRef", txnRef)
                    startActivity(intent)*/


            } else {
                progress_bar.visibility = View.GONE


            }
        }
        if (flag.equals(AppConstants.BALANCE_API)) {
            progress_bar.visibility = View.GONE
            Log.e(AppConstants.BALANCE_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)

            //   val token = jsonObject.getString(AppConstants.TOKEN)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode)
            if (status.contains(AppConstants.TRUE)) {


                tvWalletBalance.text =
                    "${getString(R.string.Rupee)} ${jsonObject.getString(AppConstants.WALLETBALANCE)}"
                /* tvAepsBalance.text =
                     "${getString(R.string.Rupee)} ${jsonObject.getString(AEPSBALANCE)}"*/


            } else {
                progress_bar.visibility = View.GONE
                if (messageCode.equals(getString(R.string.error_expired_token))) {
                    AppCommonMethods.logoutOnExpiredDialog(this)
                } else {
                    toast(messageCode.trim())
                }
            }
        }

    }


    private fun addFundsApi(
        cus_id: String,
        amount: String,
        bank: String,
        transactionId: String,
        txnRef: String, deviceId: String, deviceName: String, pin: String,
        pass: String, cus_mobile: String, cus_type: String
    ) {
        progress_bar.visibility = View.VISIBLE

        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(this, UPDATE_BALANCE, this)
            mAPIcall.addFundsApi(
                cus_id, amount, bank, transactionId, txnRef, deviceId, deviceName, pin,
                pass, cus_mobile, cus_type
            )
        } else {

            Toast.makeText(this, "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun setPresenter(presenter: AddMoneyContract.Presenter?) {
       this.presenter=presenter
    }

    override fun upiResponse(upiResponse: UPIResponse) {
     //   upiid= "BHARATPE.90054738793@fbpe"
      upiid= upiResponse.getUpiid()!!
        Log.v("UPIREsp", upiResponse.getUpiid()!!)
    }


    private fun launchUPI() {
        // look below for a reference to these parameters
        val uri: Uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", "paytm-58378037@paytm")
            .appendQueryParameter("pn", "Rajesh Gurjar")
            .appendQueryParameter("tn", "Pay for in-app purchase")
            .appendQueryParameter("am", "1")
            .appendQueryParameter("cu", "INR")
            .build()
        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")
        if (null != chooser.resolveActivity(packageManager)) {
            Log.d("FragmentActivity.TAG", "UPI Payment resolved to activity")
            startActivityForResult(chooser, 0)
        } else {
            Log.d("FragmentActivity.TAG", "No activity found to handle UPI Payment")
        }



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (0 === requestCode) {
            if (RESULT_OK == resultCode) {
                Log.d("FragmentActivity.TAG", "UPI Payment successfull")
                val transId = data!!.getStringExtra("response")
            } else {
                Log.d("FragmentActivity.TAG", "UPI Payment failed")
            }
        }
    }



}

