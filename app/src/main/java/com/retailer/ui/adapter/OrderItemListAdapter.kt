package com.retailer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.PendingOrderResponse

class OrderItemListAdapter : RecyclerView.Adapter<OrderItemListAdapter.OrderItemHolder>() {

    var listOfOrderItem: List<PendingOrderResponse.Order.Orderitem>? = null

    fun setData(
            listOfOrderItem: List<PendingOrderResponse.Order.Orderitem>
    ) {
        this.listOfOrderItem = listOfOrderItem
        notifyDataSetChanged()
    }

    class OrderItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtPrice = itemView.findViewById<TextView>(R.id.txtPrice)
        val txtQnt = itemView.findViewById<TextView>(R.id.txtQnt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_product_item, parent, false)
        return OrderItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderItemHolder, position: Int) {
        val orderItem = listOfOrderItem?.get(position)
        val no = position + 1
        holder.txtProductName.text = "" + no + ". " + orderItem?.product?.name
        holder.txtPrice.text = "Price: "+orderItem?.unitPrice
        holder.txtQnt.text = "Qty: "+ orderItem?.requestedQty.toString()
    }

    override fun getItemCount(): Int {
        if (listOfOrderItem == null) {
            return 0
        } else {
            return listOfOrderItem!!.size
        }
    }

}