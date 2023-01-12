package com.myapps.onlysratchapp.mobileRecharge

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.mobileRecharge.adapter.RecentRechargesAdapter
import com.myapps.onlysratchapp.mobileRecharge.model.RecentRechargeHistoryModal
import com.myapps.onlysratchapp.network_calls.UserModel
import com.myapps.onlysratchapp.utils.AppCommonMethods.Companion.getCurrentDateTime
import com.myapps.onlysratchapp.network_calls.AppApiCalls
import com.myapps.onlysratchapp.utils.*
import kotlinx.android.synthetic.main.activity_all_recharge_reports.*
import kotlinx.android.synthetic.main.activity_all_recharge_reports.rvRechargeHistory
import kotlinx.android.synthetic.main.activity_all_recharge_reports.view.*

import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AllRechargeReportsActivity : AppCompatActivity(), AppApiCalls.OnAPICallCompleteListener,
    SwipeRefreshLayout.OnRefreshListener {


    lateinit var rechargeHistoryModal: RecentRechargeHistoryModal
    lateinit var recentRechargesAdapter: RecentRechargesAdapter
    var recentRechargeHistoryModalArrayList = ArrayList<RecentRechargeHistoryModal>()

    lateinit var cus_mobile: String
    var cus_type="retailer"
    var cus_pin=""
    var cus_pass=""
    var cus_id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimaryDark, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_all_recharge_reports)
        //custToolbar.ivBackBtn.setOnClickListener { onBackPressed() }

        cus_mobile = Constant.getString(this, Constant.USER_NUMBER)
        cus_id = Constant.getString(this, Constant.USER_ID)

        val date = getCurrentDateTime()
        val dateInString = date.toString("dd MMM yyyy")
        tvFromDateRecHistory.setText(dateInString)
        tvToDateRecHistory.setText(dateInString)

        ll_from_date.setOnClickListener { FromdatePicker() }
        ll_to_date.setOnClickListener { TodatePicker() }


        mSwipeRefresh.setOnRefreshListener(this);

        mSwipeRefresh.post(Runnable {
            if (mSwipeRefresh != null) {
                mSwipeRefresh.setRefreshing(true)
            }
            recentRechargeHistoryModalArrayList.clear()
            rechargeHistoryFromTo(
                cus_mobile,
                AppCommonMethods.convertDateFormat(
                    "dd MMM yyyy",
                    "yyyy-MM-dd", tvFromDateRecHistory.text.toString()
                ).toString(),
                AppCommonMethods.convertDateFormat(
                    "dd MMM yyyy",
                    "yyyy-MM-dd", tvToDateRecHistory.text.toString()
                ).toString()
            )
            mSwipeRefresh.setRefreshing(false)

        })


    }


    fun FromdatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dpd =
            DatePickerDialog(this, { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth + 1
                val date = "$mDay/$mmMonth/$mYear"

                tvFromDateRecHistory.setText(
                    AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy",
                        "dd MMM yyyy", date
                    ).toString()
                )
                compareTwoDates(
                    tvFromDateRecHistory.text.toString(),
                    tvToDateRecHistory.text.toString()
                )

            }, year, month, day)
        dpd.show()
    }

    fun TodatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var dpd =
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mmMonth = mMonth + 1
                val date = "$mDay/$mmMonth/$mYear"

                tvToDateRecHistory.setText(
                    AppCommonMethods.convertDateFormat(
                        "dd/MM/yyyy",
                        "dd MMM yyyy", date
                    ).toString()
                )


                compareTwoDates(
                    tvFromDateRecHistory.text.toString(),
                    tvToDateRecHistory.text.toString()
                )


            }, year, month, day)
        dpd.show()
    }

    private fun compareTwoDates(date: String, dateafter: String) {


        val dateFormat = SimpleDateFormat(
            "dd MMM yyyy"
        )
        var convertedDate: Date? = Date()
        var convertedDate2 = Date()
        try {
            convertedDate = dateFormat.parse(date)
            convertedDate2 = dateFormat.parse(dateafter)
            if (convertedDate2.after(convertedDate) || convertedDate2.equals(convertedDate)) {
                recentRechargeHistoryModalArrayList.clear()
                rechargeHistoryFromTo(
                    cus_mobile,
                    AppCommonMethods.convertDateFormat(
                        "dd MMM yyyy",
                        "yyyy-MM-dd", tvFromDateRecHistory.text.toString()
                    ).toString(),
                    AppCommonMethods.convertDateFormat(
                        "dd MMM yyyy",
                        "yyyy-MM-dd", tvToDateRecHistory.text.toString()
                    ).toString()
                )


            } else {
                toast("Invalid Date")
            }
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    //API CALL FUNCTION DEFINITION
    private fun rechargeHistoryFromTo(
        cus_mobile: String,
        fromDate: String,
        toDate: String,

        ) {
        progress_bar.visibility = View.VISIBLE
        if (AppCommonMethods(this).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                this,
                AppConstants.REC_HIST_API,
                this
            )
            mAPIcall.rechargeHistoryFromTo(
                cus_mobile,
                fromDate,
                toDate
            )

        } else {
            toast(getString(R.string.error_internet))
        }
    }

    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(AppConstants.REC_HIST_API)) {
            progress_bar.visibility = View.GONE
            Log.e(AppConstants.REC_HIST_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode)
            if (status.contains(AppConstants.TRUE)) {
                val cast = jsonObject.getJSONArray(AppConstants.RESULT)
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    rechargeHistoryModal = Gson()
                        .fromJson(notifyObjJson.toString(), RecentRechargeHistoryModal::class.java)
                    recentRechargeHistoryModalArrayList.add(rechargeHistoryModal)

                }



                rvRechargeHistory.apply {

                    layoutManager = LinearLayoutManager(this@AllRechargeReportsActivity)
                    recentRechargesAdapter = RecentRechargesAdapter(
                        context, recentRechargeHistoryModalArrayList
                    )
                    rvRechargeHistory.adapter = recentRechargesAdapter
                }

            } else {
                toast(messageCode.trim())
            }
        }


    }

    override fun onRefresh() {
        recentRechargeHistoryModalArrayList.clear()
        rechargeHistoryFromTo(
            cus_mobile,
            AppCommonMethods.convertDateFormat(
                "dd MMM yyyy",
                "yyyy-MM-dd", tvFromDateRecHistory.text.toString()
            ).toString(),
            AppCommonMethods.convertDateFormat(
                "dd MMM yyyy",
                "yyyy-MM-dd", tvToDateRecHistory.text.toString()
            ).toString()
        )

        mSwipeRefresh.setRefreshing(false)

    }

}