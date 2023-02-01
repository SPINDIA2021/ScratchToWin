package com.myapps.onlysratchapp.mobileRecharge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.onlysratchapp.R
import com.myapps.onlysratchapp.mobileRecharge.model.OperatorsModel

import com.myapps.onlysratchapp.network_calls.AppApiUrl.IMAGE_URL
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class OperatorListAdapter(
    context: Context?,
    recievedMoneyHistoryModelList: ArrayList<OperatorsModel>,
    mListener: ListAdapterListener
) : RecyclerView.Adapter<OperatorListAdapter.ViewHolder>(), Filterable {
    private val recievedMoneyHistoryModelList: ArrayList<OperatorsModel>
    private val mInflater: LayoutInflater
    private val mListener: ListAdapterListener
    var mContext: Context? = null

    var fullArrayList: ArrayList<OperatorsModel>? = ArrayList()

    interface ListAdapterListener {
        // create an interface
        fun onClickAtOKButton(operatorsModel: OperatorsModel?) // create callback function
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem: View =
            layoutInflater.inflate(R.layout.layout_list_operators, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val operatorsModel: OperatorsModel = recievedMoneyHistoryModelList[position]
        holder.tvOperatorName.setText(operatorsModel.operatorname)
        holder.itemView.setOnClickListener { mListener.onClickAtOKButton(operatorsModel) }

        Glide.with(mContext!!)
            .load(IMAGE_URL + operatorsModel.operator_image)
            .into(holder.ivOperator)

    }

    override fun getItemCount(): Int {
        return recievedMoneyHistoryModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivOperator: CircleImageView
        var tvOperatorName: TextView

        init {
            tvOperatorName = itemView.findViewById(R.id.tvOperatorName)
            ivOperator = itemView.findViewById(R.id.ivOperator)
        }
    }

    // RecyclerView recyclerView;
    init {
        mInflater = LayoutInflater.from(context)
        this.recievedMoneyHistoryModelList = recievedMoneyHistoryModelList
        this.mListener = mListener // receive mListener from Fragment (or Activity)
        this.fullArrayList=recievedMoneyHistoryModelList
    }

    override fun getFilter(): Filter {
        return  exampleFilter
    }


    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredLst: java.util.ArrayList<OperatorsModel> =
                java.util.ArrayList<OperatorsModel>()
            if (constraint == null || constraint.length == 0) {
                filteredLst.addAll(fullArrayList!!)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (response in fullArrayList!!) {
                    if (response.operatorname.toLowerCase().trim().contains(filterPattern)) {
                        filteredLst.add(response)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredLst
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            recievedMoneyHistoryModelList.clear()
            recievedMoneyHistoryModelList.addAll(results.values as Collection<OperatorsModel>)
            notifyDataSetChanged()
        }
    }
}
