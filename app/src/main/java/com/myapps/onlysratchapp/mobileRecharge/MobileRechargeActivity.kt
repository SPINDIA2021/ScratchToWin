package com.myapps.onlysratchapp.mobileRecharge

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.recharge_services.mobile_recviewpager.MobilePostpaidFragment
import com.myapps.onlysratchapp.recharge_services.mobile_recviewpager.MobilePrepaidFragment
import com.myapps.onlysratchapp.mobileRecharge.adapter.OrderHistoryTabAdapter
import com.myapps.onlysratchapp.utils.Constant


class MobileRechargeActivity : AppCompatActivity() {
    lateinit var  viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    private var toolbar: Toolbar? = null
    private var points_textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.light_gray, this.theme)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
        setContentView(R.layout.activity_mobile_recharge)

        viewPager=findViewById(R.id.viewPager)
        tabLayout=findViewById(R.id.tabLayout)
        toolbar = findViewById(R.id.toolbar)



        try {
            (this as AppCompatActivity).setSupportActionBar(toolbar)
            (this as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
            (this as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            val titleText: TextView = toolbar!!.findViewById<TextView>(R.id.toolbarText)
            titleText.text = "Mobile Recharge"
            points_textView = toolbar!!.findViewById<TextView>(R.id.points_text_in_toolbar)
            setPointsText()
            toolbar!!.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
        } catch (e: Exception) {
            e.printStackTrace()
        }


        setupViewPager()
    }

    //Create View Pager
    private fun setupViewPager() {

        val adapter = OrderHistoryTabAdapter(supportFragmentManager)

        var mobilePrepaidFragment: MobilePrepaidFragment =
            MobilePrepaidFragment.newInstance("Prepaid")


        var mobilePostpaidFragment: MobilePostpaidFragment =
            MobilePostpaidFragment.newInstance("Postpaid")

        adapter.addFragment(mobilePrepaidFragment, "PREPAID")
        adapter.addFragment(mobilePostpaidFragment, "POSTPAID")
        viewPager!!.adapter = adapter

        tabLayout!!.setupWithViewPager(viewPager)

    }


    private fun setPointsText() {
        if (points_textView != null) {
            var userPoints = Constant.getString(this, Constant.USER_POINTS)
            if (userPoints.equals("", ignoreCase = true)) {
                userPoints = "0"
            }
            points_textView!!.text = userPoints
        }
    }
}