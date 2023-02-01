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
import com.myapps.onlysratchapp.mobileRecharge.model.CircleListModel;
import com.myapps.onlysratchapp.mobileRecharge.model.CircleListModel;

import java.util.ArrayList;
import java.util.List;

public class CircleArrayAdapter extends ArrayAdapter<CircleListModel> {
    private List<CircleListModel> dataList;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<CircleListModel> dataListAllItems;



    public CircleArrayAdapter(Context context, int resource, List<CircleListModel> storeDataLst) {
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
    public CircleListModel getItem(int position) {

        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.text_auto_item);
        strName.setText(getItem(position).getState());

      
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
                    dataListAllItems = new ArrayList<CircleListModel>(dataList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<CircleListModel> matchValues = new ArrayList<CircleListModel>();

                for (CircleListModel dataItem : dataListAllItems) {
                    if (dataItem.getState().toLowerCase().startsWith(searchStrLowerCase)) {
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
                dataList = (ArrayList<CircleListModel>)results.values;
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
    ArrayList<CircleListModel> items, tempItems, suggestions;

    public CustomArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList<CircleListModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<CircleListModel>(items); // this makes the difference.
        suggestions = new ArrayList<CircleListModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_auto, parent, false);
        }
        TextView lblName = (TextView) view.findViewById(R.id.text_auto_item);
        CircleListModel people = items.get(position);
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
            String str = ((CircleListModel) resultValue).getText();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (CircleListModel people : tempItems) {
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
            ArrayList<CircleListModel> filterList = (ArrayList<CircleListModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                try {
                    for (CircleListModel people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }catch (Exception e){}
            }
        }
    };*/




}
