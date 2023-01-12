package com.myapps.onlysratchapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.fragments.ContactFragment;
import com.myapps.onlysratchapp.fragments.ForgotFragment;
import com.myapps.onlysratchapp.fragments.GoldAppnxtFragment;
import com.myapps.onlysratchapp.fragments.GoldChartboostFragment;
import com.myapps.onlysratchapp.fragments.GoldFragment;
import com.myapps.onlysratchapp.fragments.GoldInmobiFragment;
import com.myapps.onlysratchapp.fragments.GoldIronFragment;
import com.myapps.onlysratchapp.fragments.GoldPangleFragment;
import com.myapps.onlysratchapp.fragments.GoldStartFragment;
import com.myapps.onlysratchapp.fragments.GoldUnityFragment;
import com.myapps.onlysratchapp.fragments.LeaderBoardFragment;
import com.myapps.onlysratchapp.fragments.PlatinumAppnxtFragment;
import com.myapps.onlysratchapp.fragments.PlatinumChartboostFragment;
import com.myapps.onlysratchapp.fragments.PlatinumFragment;
import com.myapps.onlysratchapp.fragments.PlatinumInmobiFragment;
import com.myapps.onlysratchapp.fragments.PlatinumIronFragment;
import com.myapps.onlysratchapp.fragments.PlatinumPangleFragment;
import com.myapps.onlysratchapp.fragments.PlatinumStartFragment;
import com.myapps.onlysratchapp.fragments.PlatinumUnityFragment;
import com.myapps.onlysratchapp.fragments.PlatinumVungleFragment;
import com.myapps.onlysratchapp.fragments.ProfileFragment;
import com.myapps.onlysratchapp.fragments.ReferFragment;
import com.myapps.onlysratchapp.fragments.SilverAppnxtFragment;
import com.myapps.onlysratchapp.fragments.SilverChartboostFragment;
import com.myapps.onlysratchapp.fragments.SilverFragment;
import com.myapps.onlysratchapp.fragments.SilverInmobiFragment;
import com.myapps.onlysratchapp.fragments.SilverIronFragment;
import com.myapps.onlysratchapp.fragments.SilverPangleFragment;
import com.myapps.onlysratchapp.fragments.SilverStartFragment;
import com.myapps.onlysratchapp.fragments.SilverUnityFragment;
import com.myapps.onlysratchapp.fragments.SilverVungleFragment;
import com.myapps.onlysratchapp.fragments.WalletFragment;
import com.myapps.onlysratchapp.utils.Constant;

public class ReferActivity extends AppCompatActivity {

    private String type;
    private ReferActivity activity;
    private Fragment fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        activity = this;
        type = getIntent().getStringExtra("type");

        if (type != null) {
            switch (type) {
                case "changePassword":
                    fm = ForgotFragment.newInstance();
                    break;

                case "wallet":
                    fm = WalletFragment.newInstance();
                    break;
                case "contact":
                    fm = ContactFragment.newInstance();
                    break;
                case "Profile":
                    fm = ProfileFragment.newInstance();
                    break;
                case "refer":
                    fm = ReferFragment.newInstance();
                    break;
                case "Silver Scratch":
                    fm = SilverFragment.newInstance();
                    break;
                case "Silver Scratch Unity":
                    fm = SilverUnityFragment.newInstance();
                    break;
                case "Platinum Scratch Unity":
                    fm = PlatinumUnityFragment.newInstance();
                    break;
                case "Gold Scratch Unity":
                    fm = GoldUnityFragment.newInstance();
                    break;
                case "Platinum Scratch":
                    fm = PlatinumFragment.newInstance();
                    break;
                case "Gold Scratch":
                    fm = GoldFragment.newInstance();
                    break;

                case "Silver Scratch Start":
                    fm = SilverStartFragment.newInstance();
                    break;
                case "Platinum Scratch Start":
                    fm = PlatinumStartFragment.newInstance();
                    break;
                case "Gold Scratch Start":
                    fm = GoldStartFragment.newInstance();
                    break;
                case "Silver Scratch Chartboost":
                    fm = SilverChartboostFragment.newInstance();
                    break;
                case "Platinum Scratch Chartboost":
                    fm = PlatinumChartboostFragment.newInstance();
                    break;
                case "Gold Scratch Chartboost":
                    fm = GoldChartboostFragment.newInstance();
                    break;

                case "Silver Scratch Iron":
                    fm = SilverIronFragment.newInstance();
                    break;
                case "Platinum Scratch Iron":
                    fm = PlatinumIronFragment.newInstance();
                    break;
                case "Gold Scratch Iron":
                    fm = GoldIronFragment.newInstance();
                    break;

                case "Silver Scratch Appnxt":
                    fm = SilverAppnxtFragment.newInstance();
                    break;
                case "Platinum Scratch Appnxt":
                    fm = PlatinumAppnxtFragment.newInstance();
                    break;
                case "Gold Scratch Appnxt":
                    fm = GoldAppnxtFragment.newInstance();
                    break;

                case "Silver Scratch Inmobi":
                    fm = SilverInmobiFragment.newInstance();
                    break;
                case "Platinum Scratch Inmobi":
                    fm = PlatinumInmobiFragment.newInstance();
                    break;
                case "Gold Scratch Inmobi":
                    fm = GoldInmobiFragment.newInstance();
                    break;
                case "Silver Scratch Vungle":
                    fm = SilverVungleFragment.newInstance();
                    break;
                case "Platinum Scratch Vungle":
                    fm = PlatinumVungleFragment.newInstance();
                    break;
                case "Gold Scratch Vungle":
                    fm = GoldInmobiFragment.newInstance();
                    break;
                case "Silver Scratch Pangle":
                    fm = SilverPangleFragment.newInstance();
                    break;
                case "Platinum Scratch Pangle":
                    fm = PlatinumPangleFragment.newInstance();
                    break;
                case "Gold Scratch Pangle":
                    fm = GoldPangleFragment.newInstance();
                    break;
                case "LeaderBoard":
                    fm = LeaderBoardFragment.newInstance();
                    break;

            }
            if (fm != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_layout_refer, fm).commit();
            }
        } else {
            Constant.showToastMessage(activity, "Something Went Wrong...");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}