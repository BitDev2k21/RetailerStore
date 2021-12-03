package com.retailer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.products.Company

class CompanyListAdapter(callBack: (pos: Company) -> Unit) :
        RecyclerView.Adapter<CompanyListAdapter.Holder>() {
    var callBack: (pos: Company) -> Unit
    var businessLineModels: ArrayList<Company>? = null

    init {
        this.callBack = callBack
    }

    fun setData(businessLineModels: ArrayList<Company>) {
        this.businessLineModels = businessLineModels
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_select, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val businessLineModel = businessLineModels?.get(position)
        holder.txtTitle.text = businessLineModel?.name
        if (!businessLineModel?.is_selected!!) {
            holder.imgSelect.setBackgroundResource(R.drawable.icon_un_check)
        } else {
            holder.imgSelect.setBackgroundResource(R.drawable.icon_check)
        }
        holder.llItemSelect.setOnClickListener {
            businessLineModel.is_selected =! businessLineModel.is_selected
            callBack.invoke(businessLineModel!!)
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