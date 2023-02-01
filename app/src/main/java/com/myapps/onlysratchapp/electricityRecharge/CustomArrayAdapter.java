package com.myapps.onlysratchapp.electricityRecharge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.myapps.onlysratchapp.R;
import com.myapps.onlysratchapp.mobileRecharge.model.OperatorsModel;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<OperatorsModel> {
    private List<OperatorsModel> dataList;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<OperatorsModel> dataListAllItems;



    public CustomArrayAdapter(Context context, int resource, List<OperatorsModel> storeDataLst) {
        super(context, resource, storeDataLst);
        dataList = storeDataLst;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public OperatorsModel getItem(int position) {

        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.text_auto_item);
        strName.setText(getItem(position).getOperatorname());


        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<OperatorsModel>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<OperatorsModel> matchValues = new ArrayList<OperatorsModel>();

                for (OperatorsModel dataItem : dataListAllItems) {
                    if (dataItem.getOperatorname().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                dataList = (ArrayList<OperatorsModel>)results.values;
            } else {
                dataList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }


  /*  Context context;
    int resource, textViewResourceId;
    ArrayList<OperatorsModel> items, tempItems, suggestions;

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<OperatorsModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<OperatorsModel>(items); // this makes the difference.
        suggestions = new ArrayList<OperatorsModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_auto, parent, false);
        }
        TextView lblName = (TextView) view.findViewById(R.id.text_auto_item);
        OperatorsModel people = items.get(position);
        if (people != null) {

            if (lblName != null)
                lblName.setText(people.getText());
        }

        LinearLayout linearLayout=view.findViewById(R.id.main_item);
        if (people.getIsGeneric().equals("Y"))
        {
            lblName.setBackground(context.getResources().getDrawable(R.drawable.background_spinner_yellow));
            lblName.setTextColor(context.getResources().getColor(R.color.white));
        }
        else if (people.getHighrisk().equals("Y"))
        {
            lblName.setBackground(context.getResources().getDrawable(R.drawable.background_spinnergrey));
            lblName.setTextColor(context.getResources().getColor(R.color.white));
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    *//**
     * Custom Filter implementation for custom suggestions we provide.
     *//*
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((OperatorsModel) resultValue).getText();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (OperatorsModel people : tempItems) {
                    if (people.getText().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<OperatorsModel> filterList = (ArrayList<OperatorsModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                try {
                    for (OperatorsModel people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }catch (Exception e){}
            }
        }
    };*/




}
