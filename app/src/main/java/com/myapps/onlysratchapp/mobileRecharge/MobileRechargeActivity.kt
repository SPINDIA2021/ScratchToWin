package com.myapps.onlysratchapp.mobileRecharge

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.recharge_services.mobile_recviewpager.MobilePostpaidFragment
import com.myapps.onlysratchapp.recharge_services.mobile_recviewpager.MobilePrepaidFragment
import com.myapps.onlysratchapp.mobileRecharge.adapter.OrderHistoryTabAdapter


class MobileRechargeActivity : AppCompatActivity() {
    lateinit var  viewPager: ViewPager
    lateinit var tabLayout: TabLayout

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

}