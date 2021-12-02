package com.retailer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.PendingOrderResponse

class PastOrderAdapter : RecyclerView.Adapter<PastOrderAdapter.PlanningViewHolder>() {

    var searchList: List<PendingOrderResponse.Order>? = null
    var myCallback: ((Int) -> Unit?)? = null

    fun setData(
        searchList: List<PendingOrderResponse.Order>,
        myCallback: (pos: Int) -> Unit
    ) {
        this.searchList = searchList
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rlItem = itemView.findViewById<RelativeLayout>(R.id.rlItem)
        val rvOrderItem = itemView.findViewById<RecyclerView>(R.id.rvOrderItem)
        val txtShopName = itemView.findViewById<TextView>(R.id.txtShopName)
        val txtOrderNo = itemView.findViewById<TextView>(R.id.txtOrderNo)
        val txtTotalPrice = itemView.findViewById<TextView>(R.id.txtTotalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_past_order, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
        holder.txtShopName.text = searchItem?.shop?.name
        holder.txtOrderNo.text = searchItem?.order_no
        holder.txtTotalPrice.text=""+searchItem?.total
        var pastOrderItemAdapter = PastOrderItemAdapter()
        holder.rvOrderItem.adapter = pastOrderItemAdapter
        pastOrderItemAdapter.setData(searchItem?.orderitems!!)

    }

    override fun getItemCount(): Int {
        if (searchList == null) {
            return 0
        } else {
            return searchList!!.size
        }
    }

}