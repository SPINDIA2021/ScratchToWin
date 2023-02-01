package com.myapps.onlysratchapp.transferPoints


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.activity.MainActivity
import com.myapps.onlysratchapp.utils.Constant
import com.myapps.onlysratchapp.utils.Injection
import kotlinx.android.synthetic.main.activity_transferpoints.*

class TransferPointsActivity : AppCompatActivity(),  TransferContract.View
{
    private var presenter: TransferContract.Presenter? = null
    var TAG = javaClass.simpleName

    lateinit var cus_mobile: String
    var cus_type="retailer"
    var cus_pin=""
    var cus_pass=""
    var cus_id=""
    var cus_name=""
    var user_refer=""
    var referTo=""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_transferpoints)

        TransferPresenter(Injection.provideLoginRepository(this), this)

        cus_mobile = Constant.getString(this, Constant.USER_NUMBER)
        cus_id = Constant.getString(this, Constant.USER_ID)
        cus_name = Constant.getString(this, Constant.FIRST_NAME)


        var userPoints = Constant.getString(this, Constant.USER_POINTS)
        if (userPoints.equals("", ignoreCase = true)) {
            userPoints = "0"
        }
        tvWalletBalance.setText(userPoints)

        img_done.setOnClickListener {
            if (et_mobileno.text.toString().isEmpty())
            {
                Toast.makeText(this,"Please enter mobile number.",Toast.LENGTH_SHORT).show()
            }else if (et_mobileno.text.toString().equals(cus_mobile))
        {
            Toast.makeText(this,"You cannot transfer yourself.",Toast.LENGTH_SHORT).show()
            et_mobileno.setText("")
        }else{
                presenter!!.getTransferUser(et_mobileno.text.toString(),this)
            }
        }

        cvProceedTransferPointsBtn.setOnClickListener {
            // on below line we are getting data from our edit text.
            // on below line we are getting data from our edit text.



            val points: String = et_points.getText().toString()+".00"
            val mobileno: String = et_mobileno.getText().toString()
            val userPoints = Constant.getString(this, Constant.USER_POINTS)
            val name: String = cus_name
            val desc: String = "Payment"
            // on below line we are validating our text field.
            // on below line we are validating our text field.
            if (TextUtils.isEmpty(points) && TextUtils.isEmpty(mobileno)
            ) {
                Toast.makeText(
                    this@TransferPointsActivity,
                    "Please enter points..",
                    Toast.LENGTH_SHORT
                ).show()
            }else if (
           points>userPoints && points.toString().equals("0")
                    ) {
                android.widget.Toast.makeText(
                    this@TransferPointsActivity,
                    "Please enter valid points..",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                // if the edit text is not empty then
                // we are calling method to make payment.
                transferPoints(points)
            }
        }

        /*custToolbar.ivBackBtn.setOnClickListener {
            onBackPressed()
        }*/


        //toolbar
        val toolbar =
            findViewById<Toolbar>(R.id.custToolbar)
        val ivBackBtn =
            toolbar.findViewById<ImageView>(R.id.ivBackBtn)
        ivBackBtn.setOnClickListener { onBackPressed() }

    }

    private fun transferPoints(points: String) {
       var transferFrom = Constant.getString(this, Constant.USER_REFFER_CODE)
        presenter!!.savePoinsrTransfer(points,referTo,transferFrom,this@TransferPointsActivity)
    }


    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: TransferContract.Presenter?) {
        this.presenter=presenter
    }

    override fun settRansferUserResponse(tRansferUserResponse: TRansferUserResponse?) {
        try {
            text_user.setText(tRansferUserResponse!!.getName())
            referTo=tRansferUserResponse.getReferralCode().toString()
        }catch (e: Exception){}

    }

    override fun savePointsTransferResponse(userData: TransferPointsResponse?) {

        /*   if (userData.getCoins() != null) {
                        Constant.setString(context, Constant.USER_POINTS,userData.getCoins());
                        Log.e("TAG", "onDataChange: " + userData.getCoins());
                    }*/if (userData!!.getCoin() != null) {
            Constant.setString(this, Constant.USER_POINTS, userData.getCoin().toString())
            Log.e("TAG", "onDataChange: " + userData.getCoin())
        }

        var userPoints = Constant.getString(this, Constant.USER_POINTS)
        if (userPoints.equals("", ignoreCase = true)) {
            userPoints = "0"
        }
        tvWalletBalance.setText(userPoints)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.enter, R.anim.exit)
    }

}