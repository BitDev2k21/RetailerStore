package com.retailer.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.PendingOrderResponse


class PendingOrderListAdapter : RecyclerView.Adapter<PendingOrderListAdapter.PlanningViewHolder>() {

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
        val rlShare = itemView.findViewById<RelativeLayout>(R.id.rlShare)
        val txtDate = itemView.findViewById<TextView>(R.id.txtDate)
        val tv_order_waiting = itemView.findViewById<TextView>(R.id.tv_order_waiting)
        val tv_order_accepted = itemView.findViewById<TextView>(R.id.tv_order_accepted)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_order_list, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
        val orderListAdapter = OrderItemListAdapter()
        val order_status = searchItem?.status
        holder.txtShopName.text = searchItem?.shop?.name
        holder.txtOrderNo.text = searchItem?.order_no
        holder.txtTotalPrice.text = "" + searchItem?.total
        holder.txtDate.text = "" + searchItem?.createdAt?.substringBeforeLast(" ")
        holder.rvOrderItem.adapter = orderListAdapter
        orderListAdapter.setData(searchItem?.orderitems!!)
        holder.rlItem.setOnClickListener {
        }
        holder.rlShare.setOnClickListener {
            myCallback?.invoke(position)
        }

        if(order_status!=null &&  order_status.equals("1")){
            holder.tv_order_waiting.visibility = View.GONE
            holder.tv_order_accepted.visibility = View.VISIBLE
        }else{
            holder.tv_order_waiting.visibility = View.VISIBLE
            holder.tv_order_accepted.visibility = View.GONE
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