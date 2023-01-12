package com.myapps.onlysratchapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.adapter.PassbookAdapter;
import com.myapps.onlysratchapp.models.CoinsResponse;
import com.myapps.onlysratchapp.models.LevelResponse;
import com.myapps.onlysratchapp.passbook.PassbookContract;
import com.myapps.onlysratchapp.passbook.PassbookPresenter;
import com.myapps.onlysratchapp.passbook.PassbookResponse;
import com.myapps.onlysratchapp.utils.Constant;
import com.myapps.onlysratchapp.utils.Injection;

import java.util.ArrayList;

public class PassbookFragment extends Fragment implements  PassbookContract.View {
    private RecyclerView historyRecyclerView;
    private PassbookContract.Presenter presenter;
    private Context activity;
    private Toolbar toolbar;
    private TextView  points_textView;

    ArrayList<PassbookResponse> passbookResponseArrayList=new ArrayList<>();

    private final String TAG = "Passbook Fragment";

    public PassbookFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = context;
    }

    public static PassbookFragment newInstance() {
        PassbookFragment fragment = new PassbookFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallet_history, container, false);
        historyRecyclerView = view.findViewById(R.id.transaction_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(manager);
        toolbar = view.findViewById(R.id.toolbar);

        new PassbookPresenter(Injection.provideLoginRepository(getActivity()), this);

        try {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            TextView titleText = toolbar.findViewById(R.id.toolbarText);
            titleText.setText("Passbook");

            points_textView = toolbar.findViewById(R.id.points_text_in_toolbar);
            setPointsText();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        String refercode = Constant.getString(getContext(), Constant.USER_REFFER_CODE);
        if (refercode.equalsIgnoreCase("")) {
            refercode = "";
        }

        presenter.getPassbookData(refercode,getContext());


        onInit();
        return view;
    }

    private void setPointsText() {
        if (points_textView != null) {
            String userPoints = Constant.getString(activity, Constant.USER_POINTS);
            if (userPoints.equalsIgnoreCase("")) {
                userPoints = "0";
            }
            points_textView.setText(userPoints);
        }
    }

    private void onInit() {


    }




    @Override
    public void setPresenter(PassbookContract.Presenter presenter) {
      this.presenter=presenter;
    }


    @Override
    public void passbookResponse(ArrayList<PassbookResponse> passbookResponses) {
        if (passbookResponses.size()!=0)
        {
            historyRecyclerView.setHasFixedSize(true);
            passbookResponseArrayList=passbookResponses;
            PassbookAdapter adapter = new PassbookAdapter(getContext(), passbookResponseArrayList);
            historyRecyclerView.setAdapter(adapter);
        }
        else {
            Toast.makeText(getContext(),"No Data Avialable",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void coinsResponse(ArrayList<CoinsResponse> coinsResponses) {

    }

    @Override
    public void levelResponse(ArrayList<LevelResponse> levelResponses) {

    }
}