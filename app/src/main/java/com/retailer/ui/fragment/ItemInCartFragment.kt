package com.retailer.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.api.ApiCallingRequest
import com.retailer.model.cart.Cart
import com.retailer.preference.SessionData
import com.retailer.ui.adapter.CartItemAdapter
import com.retailer.utils.MyProgressDialog
import com.retailer.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class ItemInCartFragment : BaseFragment() {

    private var rootView: View? = null
    lateinit var rvItemIncart: RecyclerView
     lateinit var cartItemAdapter: CartItemAdapter
    private var listOfData = ArrayList<String>()
    lateinit var txtCheckOut: TextView
    lateinit var txtNoItemFound: TextView
    private lateinit var pd: MyProgressDialog
    private var totalItem = ""
    private var carts = ArrayList<Cart>()
    private var cart_total = ""
    var imagePath :String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_item_cart, container, false)
        rvItemIncart = rootView?.findViewById(R.id.rvItemIncart)!!
        txtCheckOut = rootView?.findViewById(R.id.txtCheckOut)!!
        txtNoItemFound = rootView?.findViewById(R.id.txtNoItemFound)!!
        cartItemAdapter = CartItemAdapter(requireContext())
        rvItemIncart.adapter = cartItemAdapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        setTitle("Item in Cart")
        hideMenu()
        showBack()
        hideButtomBar()
        mainActivity.edtSearch.setText("")
        mainActivity.edtSearch.clearFocus()

        mainActivity.imgSearchText.setOnClickListener {
            val search = mainActivity.edtSearch.text.trim().toString()
            if (!TextUtils.isEmpty(search)) {
                val newList = carts.filter { cart ->
                    cart.product.name.toLowerCase().contains(search.toLowerCase())
                }
                if (!newList.isNullOrEmpty()) {
                    txtNoItemFound.visibility = View.GONE
                    rvItemIncart.visibility = View.VISIBLE
                    txtCheckOut.visibility = View.VISIBLE
                    cartItemAdapter.setData(newList, imagePath,
                        { pos, status, qnt ->
                            val cart = newList.get(pos)
                            if (status.equals("delete")) {
                                PopupUtils.confirmationDailg(
                                    requireContext(),
                                    "Are you sure want to delete?",
                                    {
                                        if (it.equals("Yes")) {
                                            apiCallingForDeleteCart(
                                                cart.product_id.toString(),
                                                cart.distributor_id.toString(),
                                                qnt
                                            )
                                        }
                                    })
                            } else {
                                apiCallingForUpdateCart(
                                    qnt,
                                    cart.product_id.toString(),
                                    cart.distributor_id.toString()
                                )
                            }
                        })
                } else {
                    txtNoItemFound.visibility = View.VISIBLE
                    rvItemIncart.visibility = View.GONE
                    txtCheckOut.visibility = View.GONE
                }
                mainActivity.edtSearch.clearFocus()
            } else {
                mainActivity.edtSearch.clearFocus()
                apiCallingForCartList()
            }
        }
        txtCheckOut.setOnClickListener {
            PopupUtils.checkOutDialog(mainActivity, totalItem, cart_total, {
                callingForCreateOrder()
            })
        }
        apiCallingForCartList()
    }

    fun callingForCreateOrder() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            try {
                val createRespo = apiCallingRequest.createOrder(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (createRespo != null) {
                        Log.e("response:", "" + createRespo)
                        val bundle = Bundle()
                        bundle.putString("coming_from", "")
                        mainActivity.navController.navigate(R.id.orderListFragment,bundle)
                    } else {
                        Toast.makeText(requireContext(), "fail response", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    fun apiCallingForUpdateCart(qnt: String, productId: String, disId: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            params["product_id"] = productId
            params["distributor_id"] = disId
            params["quantity"] = qnt
            try {
                val addCartResponse = apiCallingRequest.updateCart(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (addCartResponse != null) {
                        apiCallingForCartList()
                    } else {
                        Toast.makeText(requireContext(), "fail response", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    fun apiCallingForDeleteCart(id: String, disId: String, qnt: String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            params["product_id"] = id
            params["distributor_id"] = disId
            params["quantity"] = qnt
            try {
                val addCartResponse = apiCallingRequest.deleteCart(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (addCartResponse != null) {
                        apiCallingForCartList()
                    } else {
                        Toast.makeText(requireContext(), "fail response", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    fun apiCallingForCartList() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            Log.e("params", "" + params)
            try {
                val resposneOfCartList = apiCallingRequest.apiCartList(params)
                cart_total = resposneOfCartList.cart_total
                Log.e("Response: ", "" + resposneOfCartList)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (!resposneOfCartList.carts.isNullOrEmpty()) {
                        rvItemIncart.visibility = View.VISIBLE
                        txtCheckOut.visibility = View.VISIBLE
                        txtNoItemFound.visibility = View.GONE
                        totalItem = "" + resposneOfCartList.carts.size
                        carts.clear()
                        carts = resposneOfCartList.carts as ArrayList<Cart>
                        imagePath = resposneOfCartList.image_path
                        cartItemAdapter.setData(
                            carts,
                            imagePath,
                            { pos, status, qnt ->
                                val cart = carts.get(pos)
                                if (status.equals("delete")) {
                                    PopupUtils.confirmationDailg(
                                        requireContext(),
                                        "Are you sure want to delete?",
                                        {
                                            if (it.equals("Yes")) {
                                                apiCallingForDeleteCart(
                                                    cart.product_id.toString(),
                                                    cart.distributor_id.toString(),
                                                    qnt
                                                )
                                            }
                                        })
                                } else {
                                    apiCallingForUpdateCart(
                                        qnt,
                                        cart.product_id.toString(),
                                        cart.distributor_id.toString()
                                    )
                                }
                            })
                    } else {
                        rvItemIncart.visibility = View.GONE
                        txtCheckOut.visibility = View.GONE
                        txtNoItemFound.visibility = View.VISIBLE
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }


}