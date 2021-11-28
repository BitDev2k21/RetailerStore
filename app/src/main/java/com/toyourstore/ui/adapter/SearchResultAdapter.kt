package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.PlanningViewHolder>() {

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
        val llItem = itemView.findViewById<LinearLayout>(R.id.llItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_result_item, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
//        holder.txtSearchItem.text = searchItem
//        holder.imgSelect.isSelected = true
//        holder.rlItemSearch.setOnClickListener {
//            myCallback?.invoke(position)
//        }

        holder.llItem.setOnClickListener {
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