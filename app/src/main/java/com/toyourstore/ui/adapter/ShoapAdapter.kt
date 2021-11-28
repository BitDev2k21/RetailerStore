package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.model.shoplist.Shops
import com.toyourstore.preference.SessionData

class ShoapAdapter : RecyclerView.Adapter<ShoapAdapter.SalesHolder>() {

    var shops: List<Shops>? = null
    var myCallback: ((Int, String) -> Unit?)? = null
    fun setData(
        shops: List<Shops>,
        myCallback: (pos: Int, name: String) -> Unit
    ) {
        this.shops = shops
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class SalesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val imgEdit = itemView.findViewById<ImageView>(R.id.imgEdit)
        val imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)
        val imgSelectShop = itemView.findViewById<ImageView>(R.id.imgSelectShop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sales, parent, false)
        return SalesHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalesHolder, position: Int) {
        val sales = shops?.get(position)
        holder.txtName.text = sales?.name!!
        holder.imgEdit.setOnClickListener {
            myCallback?.invoke(position, "Edit")
        }
        holder.imgDelete.setOnClickListener {
            myCallback?.invoke(position, "Delete")
        }
        holder.imgSelectShop.setOnClickListener {
            myCallback?.invoke(position, "Selected")
        }
        holder.imgSelectShop.isSelected = false
        if (position == SessionData.getInstance(holder.itemView.context).getSeletedPos()) {
            holder.imgSelectShop.isSelected = true
            SessionData.getInstance(holder.itemView.context).saveSeletedShopId("" + sales.id)
        }

    }

    override fun getItemCount(): Int {
        if (shops == null) {
            return 0
        } else {
            return shops!!.size
        }
    }

}