package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.PlanningViewHolder>() {

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
        val txtSearchItem = itemView.findViewById<TextView>(R.id.txtSearchItem)
        val rlItemSearch = itemView.findViewById<RelativeLayout>(R.id.rlItemSearch)
        val imgSelect = itemView.findViewById<ImageView>(R.id.imgSelect)
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_item, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
        holder.txtSearchItem.text = searchItem
        holder.imgSelect.isSelected = true
        holder.rlItemSearch.setOnClickListener {
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