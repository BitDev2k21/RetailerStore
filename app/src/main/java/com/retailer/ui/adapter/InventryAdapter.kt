package com.retailer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.InventoryResponse

class InventryAdapter : RecyclerView.Adapter<InventryAdapter.PlanningViewHolder>() {

    var inventryList: MutableList<InventoryResponse.Inventory>? = null
    var myCallback: ((Int) -> Unit?)? = null

    fun setData(
        inventryList: MutableList<InventoryResponse.Inventory>,
            myCallback: (pos: Int) -> Unit
    ) {
        this.inventryList = inventryList
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val llItem = itemView.findViewById<LinearLayout>(R.id.llItem)
        val rlItem = itemView.findViewById<RelativeLayout>(R.id.rlItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_invertry, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val inventory = inventryList?.get(position)
//        holder.txtSearchItem.text = searchItem
//        holder.imgSelect.isSelected = true
        holder.rlItem.setOnClickListener {
            myCallback?.invoke(position)
        }


    }

    override fun getItemCount(): Int {
        if (inventryList == null) {
            return 0
        } else {
            return inventryList!!.size
        }
    }

}