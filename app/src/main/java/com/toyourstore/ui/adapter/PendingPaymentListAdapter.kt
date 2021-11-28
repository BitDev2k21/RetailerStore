package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R

class PendingPaymentListAdapter : RecyclerView.Adapter<PendingPaymentListAdapter.PlanningViewHolder>() {

    var searchList: MutableList<String>? = null
    var myCallback: ((Int) -> Unit?)? = null


    fun setData(
            searchList: MutableList<String>,
            myCallback: (pos: Int) -> Unit
     ) {
        this.searchList = searchList
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val llItem = itemView.findViewById<LinearLayout>(R.id.llItem)
        val rlItem = itemView.findViewById<RelativeLayout>(R.id.rlItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_payment_list, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
//        holder.txtSearchItem.text = searchItem
//        holder.imgSelect.isSelected = true
        holder.rlItem.setOnClickListener {
            myCallback?.invoke(position)
        }

    }

    override fun getItemCount(): Int {
        if (searchList == null) {
            return 0
        } else {
            return searchList!!.size
        }
    }

}