package com.retailer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.retailer.R
import com.retailer.model.cart.Cart

class CartItemAdapter(context: Context) : RecyclerView.Adapter<CartItemAdapter.PlanningViewHolder>() {

    var searchList: List<Cart>? = null
    var myCallback: ((Int, String, String) -> Unit?)? = null
    var imageBasePath:String = ""
    val mContext: Context = context

    fun setData(
        searchList: List<Cart>,
        imageBasePath: String,
        myCallback: (Int, String, String) -> Unit
    ) {
        this.searchList = searchList
        this.imageBasePath = imageBasePath
        this.myCallback = myCallback
        notifyDataSetChanged()
    }

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgDeleteCart = itemView.findViewById<ImageView>(R.id.imgDeleteCart)
        val imgMinus = itemView.findViewById<ImageView>(R.id.imgMinus)
        val imgPluse = itemView.findViewById<ImageView>(R.id.imgPluse)
        val txtItemNo = itemView.findViewById<TextView>(R.id.txtItemNo)
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtPrice = itemView.findViewById<TextView>(R.id.txtPrice)
        val imgProduct = itemView.findViewById<ImageView>(R.id.imgProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val cart = searchList?.get(position)
        val product = cart?.product
        val imageUrl = imageBasePath+product!!.image
        var minQty = 1
        if(product!=null && product.min_qty!=null){
            minQty = product.min_qty.toInt()
        }
        holder.txtPrice.setText("Price: ₹" + cart?.total_price)
        holder.txtItemNo.setText("" + cart?.quantity)
        holder.txtProductName.setText("" + cart?.product?.name)
        Glide.with(mContext).load(imageUrl).placeholder(R.drawable.ic_no_data).into(holder.imgProduct);

        holder.imgDeleteCart.setOnClickListener {
            myCallback?.invoke(
                position,
                "delete",
                ""
            )
        }
        holder.imgMinus.setOnClickListener {
            var itemNo = holder.txtItemNo.text.toString().trim().toInt()
            if (itemNo > minQty) {
                itemNo--
                holder.txtItemNo.setText("" + itemNo)
                itemNo = holder.txtItemNo.text.toString().trim().toInt()
                myCallback?.invoke(
                    position,
                    "minus",
                    "" + itemNo

                )
            }
        }

        holder.imgPluse.setOnClickListener {
            var itemNo = holder.txtItemNo.text.toString().trim().toInt()
            itemNo++
            holder.txtItemNo.setText("" + itemNo)
            itemNo = holder.txtItemNo.text.toString().trim().toInt()
            myCallback?.invoke(
                position,
                "pluse",
                "" + itemNo

            )
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