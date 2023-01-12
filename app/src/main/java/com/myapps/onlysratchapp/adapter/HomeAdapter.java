package com.myapps.onlysratchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.models.HomeResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder>  {
    ArrayList<HomeResponse> arrayList;
    Context context;
    View.OnClickListener itemClick;

    public HomeAdapter(ArrayList<HomeResponse> arrayList, Context context, View.OnClickListener itemClick) {
        this.arrayList = arrayList;
        this.context = context;
        this.itemClick=itemClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dashboard,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        HomeResponse model=arrayList.get(i);

        myHolder.tvTitle.setText(model.getTitle());

        Picasso.get().load(model.getIcon()).into(myHolder.imgIcon);


        myHolder.card_lay.setTag(i);
        myHolder.card_lay.setOnClickListener(itemClick);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView imgIcon;
        public CardView card_lay;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            card_lay=itemView.findViewById(R.id.card_lay);
            imgIcon=itemView.findViewById(R.id.img_icon);
            tvTitle=itemView.findViewById(R.id.text_title);

        }
    }


}

