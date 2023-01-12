package com.myapps.onlysratchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.passbook.PassbookResponse;

import java.util.ArrayList;

public class PassbookAdapter extends RecyclerView.Adapter<PassbookAdapter.MyHolder> {
    public Context context;
    private ArrayList<PassbookResponse> passbookResponseArrayList;



    public PassbookAdapter(Context context, ArrayList<PassbookResponse> passbookResponseArrayList) {
        this.context = context;
        this.passbookResponseArrayList = passbookResponseArrayList;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_passbook, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        PassbookResponse model = passbookResponseArrayList.get(i);

        if (model.getCredit().equals("0"))
        {
            myHolder.crdt_textView.setText("Debit:  Rs. "+model.getDebit());
        }else if (model.getDebit().equals("0"))
        {
            myHolder.crdt_textView.setText("Credit:  Rs. "+model.getCredit());
        }

        myHolder.currbal_textView.setText("Current Balance:  Rs. "+model.getCurBalance());
        myHolder.datetime_textView.setText("Date: "+model.getCreateat());
        myHolder.remark_textView.setText("Remark: "+model.getNarration());


    }

    @Override
    public int getItemCount() {
        return passbookResponseArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView crdt_textView,currbal_textView;
        public TextView datetime_textView,remark_textView;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            crdt_textView = itemView.findViewById(R.id.crdt_textView);
            currbal_textView = itemView.findViewById(R.id.currbal_textView);
            datetime_textView = itemView.findViewById(R.id.datetime_textView);
            remark_textView = itemView.findViewById(R.id.remark_textView);

        }
    }
}
