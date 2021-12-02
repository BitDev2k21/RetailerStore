package com.retailer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.OperatorsResponse

class OperatorsAdapter : RecyclerView.Adapter<OperatorsAdapter.SalesHolder>() {

    var operatorsTeams: MutableList<OperatorsResponse.OperatorTeam>? = null
    var myCallback: ((Int) -> Unit?)? = null
    fun setData(
        operatorsTeams: MutableList<OperatorsResponse.OperatorTeam>,
        myCallback: (pos: Int) -> Unit
    ) {
        this.operatorsTeams = operatorsTeams
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class SalesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llItemSale = itemView.findViewById<LinearLayout>(R.id.llItemSale)
        val txtName = itemView.findViewById<TextView>(R.id.txtName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sales, parent, false)
        return SalesHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalesHolder, position: Int) {
        val operator = operatorsTeams?.get(position)
        holder.txtName.text = operator?.name!!
        holder.llItemSale.setOnClickListener {
            myCallback?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        if (operatorsTeams == null) {
            return 0
        } else {
            return operatorsTeams!!.size
        }
    }

}