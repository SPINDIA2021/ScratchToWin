package com.myapps.onlysratchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.models.TransactionResponse;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyHolder> {
    public Context context;
    private ArrayList<TransactionResponse> transactionResponseArrayList;
    private View.OnClickListener itemClick;


    public TransactionAdapter(Context context, ArrayList<TransactionResponse> transactionResponseArrayList, View.OnClickListener itemClick) {
        this.context = context;
        this.transactionResponseArrayList = transactionResponseArrayList;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_wallet_history, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        TransactionResponse model = transactionResponseArrayList.get(i);

        myHolder.amount_textView.setText("Rs. "+model.getAmount());
        myHolder.name_textView.setText(model.getName());
        myHolder.mobile_textView.setText(model.getMobile());
        myHolder.upi_textView.setText(model.getUpiid());

        if (model.getStatus().equals("F"))
        {
            myHolder.btnWithdraw.setVisibility(View.VISIBLE);
        }else {
            myHolder.btnWithdraw.setVisibility(View.GONE);
        }

        myHolder.btnWithdraw.setTag(i);
        myHolder.btnWithdraw.setOnClickListener(itemClick);

    }

    @Override
    public int getItemCount() {
        return transactionResponseArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView amount_textView,upi_textView;
        public TextView name_textView,mobile_textView;
        public AppCompatButton btnWithdraw;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name_textView = itemView.findViewById(R.id.name_textView);
            amount_textView = itemView.findViewById(R.id.amount_textView);
            upi_textView = itemView.findViewById(R.id.upi_textView);
            mobile_textView = itemView.findViewById(R.id.mobile_textView);
            btnWithdraw = itemView.findViewById(R.id.withdraw_btn);
        }
    }
}
