package com.myapps.onlysratchapp.recharge_services.mobile_recviewpager

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.mobileRecharge.*
import com.myapps.onlysratchapp.mobileRecharge.adapter.CircleListAdapter
import com.myapps.onlysratchapp.mobileRecharge.adapter.OfferDetailsAdapter
import com.myapps.onlysratchapp.mobileRecharge.adapter.OperatorListAdapter
import com.myapps.onlysratchapp.mobileRecharge.model.CircleListModel
import com.myapps.onlysratchapp.mobileRecharge.model.OfferSModel
import com.myapps.onlysratchapp.mobileRecharge.model.OperatorsModel
import com.myapps.onlysratchapp.network_calls.AppApiCalls
import com.myapps.onlysratchapp.network_calls.AppApiUrl.IMAGE_URL
import com.myapps.onlysratchapp.utils.*
import com.myapps.onlysratchapp.utils.AppConstants.Companion.OPERATOR_API
import com.myapps.onlysratchapp.utils.AppConstants.Companion.OPERATOR_MOBILE
import kotlinx.android.synthetic.main.fragment_mobile_prepaid.*
import kotlinx.android.synthetic.main.fragment_mobile_prepaid.view.*
import kotlinx.android.synthetic.main.layout_dialog_offers.view.*
import kotlinx.android.synthetic.main.layout_list_bottomsheet.view.*
import org.json.JSONObject
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MobilePrepaidFragment : Fragment(), AppApiCalls.OnAPICallCompleteListener,
    OperatorListAdapter.ListAdapterListener, OfferDetailsAdapter.ListAdapterListener,
    CircleListAdapter.ListAdapterListener {
    lateinit var operatorAdapter: OperatorListAdapter
    var operatorsModelArrayList = ArrayList<OperatorsModel>()
    var bottomSheetDialog: BottomSheetDialog? = null

    lateinit var root: View

    var circleId: String = ""

    lateinit var circleListAdapter: CircleListAdapter
    var circleListModelArrayList = ArrayList<CircleListModel>()

    var operator_code: String = ""
    lateinit var dialog: Dialog
    private val MOBILEOFFERS_API: String = "MOBILEOFFERS_API"
    private val CIRCLE: String = "CIRCLE"
    var bottomSheetDialogOffers: BottomSheetDialog? = null
    var offersModalArrayList = ArrayList<OfferSModel>()
    lateinit var offerDetailsAdapter: OfferDetailsAdapter

    lateinit var cus_mobile: String
    var cus_type="retailer"
    var cus_pin=""
    var cus_pass=""
    var cus_id=""

    companion object {
        fun newInstance(message: String): MobilePrepaidFragment {

            val f = MobilePrepaidFragment()

            val bdl = Bundle(1)

            bdl.putString(AlarmClock.EXTRA_MESSAGE, message)

            f.setArguments(bdl)

            return f

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_mobile_prepaid, container, false)


        cus_mobile = Constant.getString(activity, Constant.USER_NUMBER)
        cus_id = Constant.getString(activity, Constant.USER_ID)

       // Toast.makeText(requireContext(), cus_mobile, Toast.LENGTH_SHORT).show()

        initView(root)
        getBalanceApi(cus_mobile)

        circle()
        root.tvCircle.setOnClickListener {
            ShowBottomSheetState()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        getOperatorApi(OPERATOR_MOBILE)


        view.tvChooseOperator.setOnClickListener {
            showOperatorsBottomSheet()
        }

        view.cvRechargePrepaidBtn.setOnClickListener {

            if (!AppCommonMethods.checkForMobile(view.etMobileNumberPrepaid)) {

                view.etMobileNumberPrepaid.requestFocus()
                view.etMobileNumberPrepaid.error =
                    requireContext().getString(R.string.error_mobile_number)
                return@setOnClickListener
            } else

                if (view.etAmountPrepaid.text.isNullOrEmpty()) {

                    view.etAmountPrepaid.requestFocus()
                    view.etAmountPrepaid.error =
                        requireContext().getString(R.string.error_invalid_amount)
                    return@setOnClickListener
                } else {


                    checkIfSameRecharge(
                        cus_mobile,
                        view.etMobileNumberPrepaid.text.toString(),
                        view.etAmountPrepaid.text.toString(), cus_type
                    )

                }


        }

        view.cvBrowsePlans.setOnClickListener {

            if (!AppCommonMethods.checkForMobile(view.etMobileNumberPrepaid)) {

                view.etMobileNumberPrepaid.requestFocus()
                view.etMobileNumberPrepaid.setError("Invalid Mobile")
            } else if (view.tvChooseOperator.text.toString().isEmpty()) {
                view.tvChooseOperator.requestFocus()
                view.tvChooseOperator.setError("Invalid Operator")
                Toast.makeText(requireContext(), "Invalid Operator", Toast.LENGTH_SHORT).show()
            } else if(view.tvCircle.text.toString().isNullOrEmpty()) {
                view.tvCircle.requestFocus()
                view.tvCircle.setError("Invalid State")
                Toast.makeText(requireContext(), "Invalid Circle", Toast.LENGTH_SHORT).show()
            } else {

                offersApi(
                    view.etMobileNumberPrepaid.text.toString(),
                    view.tvChooseOperator.text.toString(),
                    AppPrefs.getStringPref("deviceId", requireContext()).toString(),
                    AppPrefs.getStringPref("deviceName", requireContext()).toString(),
                    cus_pin,
                    cus_pass,
                    cus_mobile,
                    cus_type,
                    circleId
                )

            }

        }
    }


    fun initView(root: View) {

        root.cvWalletBalancePrepaid.setBackgroundResource(R.drawable.bg_leftcurved)
        root.cvBrowsePlans.setBackgroundResource(R.drawable.bg_rightcurved)
    }


    //API CALL FUNCTION DEFINITION
    private fun getOperatorApi(
        operator_type: String
    ) {
        Log.v("MobPrep","Operator Api: Call")
        root.progress_bar_mobpre.visibility = View.VISIBLE
        if (AppCommonMethods(context).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                requireContext(),
                AppConstants.OPERATOR_API,
                this
            )
            mAPIcall.getOperators(operator_type)

        } else {
            requireContext().toast(getString(R.string.error_internet))
        }
    }

    private fun getBalanceApi(
        cus_id: String
    ) {

        root.progress_bar_mobpre.visibility = View.VISIBLE
        if (AppCommonMethods(context).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                requireContext(),
                AppConstants.BALANCE_API,
                this
            )
            mAPIcall.getBalance(cus_id)

        } else {
            requireContext().toast(getString(R.string.error_internet))
        }
    }

    private fun checkIfSameRecharge(
        cus_id: String,
        rec_mobile: String,
        amount: String,
        operator: String
    ) {
        root.progress_bar_mobpre.visibility = View.VISIBLE

        if (AppCommonMethods(requireContext()).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                requireContext(),
                AppConstants.CHECK_SAME_RECHARGE_API,
                this
            )
            mAPIcall.checkIfSameRecharge(cus_id, rec_mobile, amount, operator)

        } else {
            requireContext().toast(getString(R.string.error_internet))
        }
    }

    private fun verifyPin(
        cus_mobile: String,
        pin: String
    ) {
        root.progress_bar_mobpre.visibility = View.VISIBLE

        if (AppCommonMethods(requireContext()).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                requireContext(),
                AppConstants.VERFY_PIN_API,
                this
            )
            mAPIcall.verifyPin(cus_mobile, pin)

        } else {
            requireContext().toast(getString(R.string.error_internet))
        }
    }

    private fun rechargeApi(
        cus_id: String,
        rec_mobile: String,
        amount: String,
        operator: String,
        cus_type: String,
    ) {
        root.progress_bar_mobpre.visibility = View.VISIBLE

        if (AppCommonMethods(requireContext()).isNetworkAvailable) {
            val mAPIcall = AppApiCalls(
                requireContext(),
                AppConstants.RECHARGE_API,
                this
            )
            mAPIcall.rechargeApi(cus_id, rec_mobile, amount, operator, cus_type)

        } else {
            requireContext().toast(getString(R.string.error_internet))
        }
    }

    private fun offersApi(
        mobile: String, operator: String,
        deviceId: String,
        deviceName: String,
        pin: String,
        pass: String,
        cus_mobile: String,
        cus_type: String,
        circle_code: String
    ) {
        progress_bar_mobpre.visibility = View.VISIBLE

        if (AppCommonMethods(requireContext()).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(requireContext(), MOBILEOFFERS_API, this)
            mAPIcall.mobileOffers(
                mobile, operator, deviceId, deviceName, pin, pass, cus_mobile,
                cus_type, circle_code
            )
        } else {
            Toast.makeText(requireContext(), "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun circle() {
        //progress_bar_mobpre.visibility = View.VISIBLE

        if (AppCommonMethods(requireContext()).isNetworkAvailable) {
            val mAPIcall =
                AppApiCalls(requireContext(), CIRCLE, this)
            mAPIcall.circle()
            Log.v("MobPrep","Circle Api: Call")
        } else {

            Toast.makeText(requireContext(), "Internet Error", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onAPICallCompleteListner(item: Any?, flag: String?, result: String) {
        if (flag.equals(OPERATOR_API)) {
            Log.e(OPERATOR_API, result)
            //Log.v("MobPrep","Operator Api: "+result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
          //  Log.v("MobPrep","Operator STATUS: "+status)
          //  Log.v("MobPrep","BALANCE_API MESSAGE: "+messageCode)
            if (status.contains("true")) {
                root.progress_bar_mobpre.visibility = View.GONE

                val cast = jsonObject.getJSONArray("result")

                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val operatorname = notifyObjJson.getString("operatorname")
                    Log.e("operator_name ", operatorname)
                    val operatorsModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            OperatorsModel::class.java
                        )

                    operatorsModelArrayList.add(operatorsModel)
                }


            } else {

                root.progress_bar_mobpre.visibility = View.GONE


            }
        }
        if (flag.equals(AppConstants.BALANCE_API)) {
            root.progress_bar_mobpre.visibility = View.GONE
            Log.e(AppConstants.BALANCE_API, result)
            Log.v("MobPrep","BALANCE_API: "+result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val messageCode = jsonObject.getString(AppConstants.MESSAGE)

            //   val token = jsonObject.getString(AppConstants.TOKEN)
            Log.e(AppConstants.STATUS, status)
            Log.e(AppConstants.MESSAGE, messageCode)
            Log.v("MobPrep","BALANCE_API STATUS: "+status)
            Log.v("MobPrep","BALANCE_API MESSAGE: "+messageCode)
            if (status.contains(AppConstants.TRUE)) {


                root.tvWalletBalance.text =
                    "${getString(R.string.Rupee)} ${jsonObject.getString(AppConstants.WALLETBALANCE)}"
                /* tvAepsBalance.text =
                     "${getString(R.string.Rupee)} ${jsonObject.getString(AEPSBALANCE)}"*/


            } else {
                root.progress_bar_mobpre.visibility = View.GONE
                if (messageCode.equals(getString(R.string.error_expired_token))) {
                    AppCommonMethods.logoutOnExpiredDialog(requireContext())
                } else {
                    requireContext().toast(messageCode.trim())
                }
            }
        }
        if (flag.equals(AppConstants.CHECK_SAME_RECHARGE_API)) {
            Log.e(AppConstants.CHECK_SAME_RECHARGE_API, result)
            Log.v("MobPrep","CHECK_SAME_RECHARGE_API: "+result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString(AppConstants.MESSAGE)
            Log.e(AppConstants.STATUS, status)
            Log.v("MobPrep","CHECK_SAME_RECHARGE_API: STATUS "+status)
            if (status.contains("true")) {
                root.progress_bar_mobpre.visibility = View.GONE
                rechargeApi(
                    cus_id, root.etMobileNumberPrepaid.text.toString(),
                    root.etAmountPrepaid.text.toString(), operator_code, cus_type
                )
             //   verifyPin(cus_mobile, AppPrefs.getStringPref("AppPassword",context).toString())
                //confirmPinDialog()

            } else {

                root.progress_bar_mobpre.visibility = View.GONE
                showMessageDialog(getString(R.string.error_attention), message)

            }
        }
        if (flag.equals(AppConstants.RECHARGE_API)) {
            Log.e(AppConstants.RECHARGE_API, result)
            val jsonObject = JSONObject(result)
            Log.v("MobPrep","RECHARGE_API: RESULT "+result)
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
            root.progress_bar_mobpre.visibility = View.GONE
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            if (status.contains(AppConstants.TRUE)) {
                root.progress_bar_mobpre.visibility = View.GONE

                val resultObject = jsonObject.getJSONObject("result")
                val message = resultObject.getString(AppConstants.MESS)
                Log.v("MobPrep","RECHARGE_API result: "+result)
                showSuccessOrFailedDialog(getString(R.string.status), message)

            } else {
                val resultObject = jsonObject.getJSONObject("result")
                Log.v("MobPrep","RECHARGE_API: result "+resultObject)
                val message = resultObject.getString(AppConstants.MESS)
                root.progress_bar_mobpre.visibility = View.GONE
                showSuccessOrFailedDialog(getString(R.string.status), message)
                Log.v("MobPrep","RECHARGE_API: STATUS "+message)
            }
        }
        if (flag.equals(AppConstants.VERFY_PIN_API)) {
            Log.e(AppConstants.VERFY_PIN_API, result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            val message = jsonObject.getString(AppConstants.MESSAGE)
            Log.e(AppConstants.STATUS, status)
            if (status.contains(AppConstants.TRUE)) {
                root.progress_bar_mobpre.visibility = View.GONE
                rechargeApi(
                    cus_id, root.etMobileNumberPrepaid.text.toString(),
                    root.etAmountPrepaid.text.toString(), operator_code, cus_type
                )

            } else {

                root.progress_bar_mobpre.visibility = View.GONE
                showMessageDialog(getString(R.string.error_attention), message)

            }
        }
        if (flag.equals(MOBILEOFFERS_API)) {
            Log.e("MOBILEOFFERS_API", result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
            //Log.e(AppConstants.MESSAGE_CODE, messageCode);
            if (status.contains("true")) {
                progress_bar_mobpre.visibility = View.INVISIBLE

                val resultObject = jsonObject.getJSONObject("result")
                val cast = resultObject.getJSONArray("PlanDescription")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val recharge_amount = notifyObjJson.getString("recharge_amount")
                    Log.e("price", recharge_amount)
                    val offerSModel = Gson()
                        .fromJson(notifyObjJson.toString(), OfferSModel::class.java)
                    offersModalArrayList.add(offerSModel)
                }

                ShowBottomSheetOffers()

            } else {

                progress_bar_mobpre.visibility = View.INVISIBLE

            }
        }
        if (flag.equals(CIRCLE)) {
            circleListModelArrayList.clear()
            Log.e("CIRCLE", result)
        //    Log.v("MobPrep","CIRCLE: "+result)
            val jsonObject = JSONObject(result)
            val status = jsonObject.getString(AppConstants.STATUS)
            Log.e(AppConstants.STATUS, status)
       //     Log.v("MobPrep","CIRCLE: STATUS "+status)
            if (status.contains("true")) {
                progress_bar_mobpre.visibility = GONE
                //progress_bar_state.visibility = View.INVISIBLE

                val cast = jsonObject.getJSONArray("result")
                for (i in 0 until cast.length()) {
                    val notifyObjJson = cast.getJSONObject(i)
                    val stateModel = Gson()
                        .fromJson(
                            notifyObjJson.toString(),
                            CircleListModel::class.java
                        )
                    circleListModelArrayList.add(stateModel)
                }
                //ShowBottomSheetState()
            } else {
                progress_bar_mobpre.visibility = GONE
                //progress_bar_state.visibility = View.INVISIBLE
            }
        }

    }

    private fun ShowBottomSheetState() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet, null)
        view.rvspinner.apply {

            layoutManager = LinearLayoutManager(requireContext())
            circleListAdapter = CircleListAdapter(
                context, circleListModelArrayList, this@MobilePrepaidFragment
            )
            view.rvspinner.adapter = circleListAdapter
        }

        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.SheetDialog)
        bottomSheetDialog!!.setContentView(view)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 600
        bottomSheetDialog!!.show()
    }


    private fun showOperatorsBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.layout_list_bottomsheet, null)
        view.rvspinner.apply {

            layoutManager = LinearLayoutManager(requireContext())
            view.rvspinner.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

            operatorAdapter = OperatorListAdapter(
                context, operatorsModelArrayList, this@MobilePrepaidFragment
            )

            view.rvspinner.adapter = operatorAdapter
        }

        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.SheetDialog)
        bottomSheetDialog!!.setContentView(view)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 600
        bottomSheetDialog!!.show()
    }

    override fun onClickAtOKButton(operatorsModel: OperatorsModel?) {
        if (operatorsModel != null) {
            root.tvChooseOperator.text = operatorsModel.operatorname


            operator_code = operatorsModel.opcodenew!!.trim()
//            opName = mobileRechargeModal.operatorname!!.trim()
            Glide.with(this)
                .load(IMAGE_URL + operatorsModel.operator_image)
                .into(root.ivOperatorImagePrepaid)
            bottomSheetDialog!!.dismiss()
        }
    }

    private fun clearData(root: View) {

        root.etMobileNumberPrepaid.setText("")
        root.tvChooseOperator.setText("")
        root.ivOperatorImagePrepaid.setImageDrawable(resources.getDrawable(R.drawable.icons_cellulartower))
        root.etAmountPrepaid.setText("")

    }

    override fun onResume() {
        super.onResume()
        clearData(root)
    }

    private fun showMessageDialog(title: String, message: String) {
        val builder1 =
            AlertDialog.Builder(requireContext())
        builder1.setTitle("" + title)
        builder1.setMessage("" + message)
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->

            dialog.dismiss()
        }
        /*
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        val alert11 = builder1.create()
        alert11.show()
    }

    private fun showSuccessOrFailedDialog(title: String, message: String) {
        val builder1 =
            AlertDialog.Builder(requireContext())
        builder1.setTitle("" + title)
        builder1.setMessage("" + message)
        builder1.setCancelable(false)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->

            clearData(root)
            val intent = Intent(requireContext(), AllRechargeReportsActivity::class.java)
            requireContext().startActivity(intent)
            dialog.dismiss()
        }
        /*
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        val alert11 = builder1.create()
        alert11.show()
    }



    private fun ShowBottomSheetOffers() {
        val view: View = layoutInflater.inflate(R.layout.layout_dialog_offers, null)
        view.rvOffers.apply {

            layoutManager = LinearLayoutManager(requireContext())

            offerDetailsAdapter = OfferDetailsAdapter(
                context, offersModalArrayList, this@MobilePrepaidFragment
            )
            view.rvOffers.adapter = offerDetailsAdapter
        }
        view.ivCloseTab.setOnClickListener {
            bottomSheetDialogOffers!!.dismiss()
        }

        bottomSheetDialogOffers = BottomSheetDialog(requireContext(), R.style.SheetDialog)
        bottomSheetDialogOffers!!.setContentView(view)
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 1000
        bottomSheetDialogOffers!!.show()
    }

    override fun onClickAtOKButton(offerSModel: OfferSModel?) {
        if (offerSModel != null) {
            root.etAmountPrepaid.setText(offerSModel.recharge_amount)
            bottomSheetDialogOffers!!.dismiss()
        }
    }

    override fun onClickAtOKButton(circleListModel: CircleListModel?) {
        if (circleListModel != null) {
            root.tvCircle.setText(circleListModel.state)
            circleId = circleListModel.code
            bottomSheetDialog!!.dismiss()
        }
    }

}