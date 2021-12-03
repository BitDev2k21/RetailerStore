package com.retailer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.ShelfLife

class ShelfLifeAdapter(callBack: (pos: ShelfLife) -> Unit, selectedShelfLife:ShelfLife) :
        RecyclerView.Adapter<ShelfLifeAdapter.Holder>() {
    var callBack: (pos: ShelfLife) -> Unit
    var mselectedShelfLife = selectedShelfLife
    var businessLineModels: ArrayList<ShelfLife>? = null

    init {
        this.callBack = callBack
    }

    fun setData(businessLineModels: ArrayList<ShelfLife>,selData:ShelfLife) {
        this.businessLineModels = businessLineModels
        this.mselectedShelfLife = selData
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_select, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val businessLineModel = businessLineModels?.get(position)
        holder.txtTitle.text = businessLineModel?.name

        if(mselectedShelfLife.name.equals(businessLineModel!!.name)){
            if (!businessLineModel?.isSelected!!) {
                holder.imgSelect.setBackgroundResource(R.drawable.icon_un_check)
            } else {
                holder.imgSelect.setBackgroundResource(R.drawable.icon_check)
            }
        }else{
            holder.imgSelect.setBackgroundResource(R.drawable.icon_un_check)
        }

        holder.llItemSelect.setOnClickListener {
            callBack.invoke(businessLineModel)
        }

    }

    override fun getItemCount(): Int {
        if (businessLineModels != null) {
            return businessLineModels?.size!!
        } else {
            return 0
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llItemSelect = itemView.findViewById<LinearLayout>(R.id.llItemSelect)
        val imgSelect = itemView.findViewById<ImageView>(R.id.imgSelect)
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)

    }

}