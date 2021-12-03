package com.retailer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retailer.R
import com.retailer.model.products.Products

class BrowseAdapter(context: Context) : RecyclerView.Adapter<BrowseAdapter.PlanningViewHolder>() {

    var searchList: List<Products>? = null
    var myCallback: ((Int) -> Unit?)? = null
    var imageBasePath:String = ""
    val mContext:Context = context
    fun setData(
            searchList: List<Products>,
            imageBasePath:String,
            myCallback: (pos: Int) -> Unit
    ) {
        this.searchList = searchList
        this.imageBasePath = imageBasePath
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAddCart = itemView.findViewById<TextView>(R.id.txtAddCart)
        val txtDistName = itemView.findViewById<TextView>(R.id.txtDistName)
        val txtMpr = itemView.findViewById<TextView>(R.id.txtMpr)
        val txtBuyingPrice = itemView.findViewById<TextView>(R.id.txtBuyingPrice)
        val txtCompany = itemView.findViewById<TextView>(R.id.txtCompany)
        val txtMiniQnty = itemView.findViewById<TextView>(R.id.txtMiniQnty)
        val txtProName = itemView.findViewById<TextView>(R.id.txtProName)
        val imgProduct = itemView.findViewById<ImageView>(R.id.img_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_browse, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val searchItem = searchList?.get(position)
        val imageName = searchItem!!.image
        val imageUrl = imageBasePath+imageName
        var minQty = 1
        if(searchItem?.min_qty!=null && !searchItem.min_qty.trim().equals("")){
            minQty = searchItem.min_qty.toInt()
        }
        searchItem?.min_qty = minQty.toString()
        holder.txtCompany.setText(searchItem?.company?.name)
        holder.txtMpr.setText("" + searchItem?.price!!)
        holder.txtBuyingPrice.setText("" + searchItem?.price!!)
        holder.txtProName.setText(searchItem.name)
        holder.txtMiniQnty.setText(searchItem.min_qty)
        holder.txtDistName.setText(searchItem.distributor_name)

        Glide.with(mContext).load(imageUrl).placeholder(R.drawable.ic_no_data).into(holder.imgProduct);
        holder.txtAddCart.setOnClickListener {
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