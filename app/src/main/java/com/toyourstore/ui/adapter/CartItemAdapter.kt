package com.toyourstore.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.model.cart.Cart

class CartItemAdapter() : RecyclerView.Adapter<CartItemAdapter.PlanningViewHolder>() {

    var searchList: List<Cart>? = null
    var myCallback: ((Int, String, String) -> Unit?)? = null

    fun setData(
        searchList: List<Cart>,
        myCallback: (Int, String, String) -> Unit
    ) {
        this.searchList = searchList
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanningViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return PlanningViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        val cart = searchList?.get(position)
        val product = cart?.product
        var minQty = 1
        if(product!=null && product.min_qty!=null){
            minQty = product.min_qty.toInt()
        }
        holder.txtPrice.setText("" + cart?.total_price)
        holder.txtItemNo.setText("" + cart?.quantity)
        holder.txtProductName.setText("" + cart?.product?.name)
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