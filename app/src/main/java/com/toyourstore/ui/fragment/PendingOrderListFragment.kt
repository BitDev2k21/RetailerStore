package com.toyourstore.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.PendingOrderResponse
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.MainActivity
import com.toyourstore.ui.adapter.PendingOrderListAdapter
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.filterList
import java.io.IOException
import java.util.HashMap

class PendingOrderListFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvOrderList: RecyclerView? = null
    private var orderListAdapter = PendingOrderListAdapter()
    private lateinit var pd: MyProgressDialog
    private lateinit var txtNoItemFound: TextView
    private var orders = ArrayList<PendingOrderResponse.Order>()
    private var coming_from = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coming_from = arguments?.getString("coming_from", "")!!
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_order_list, container, false)
        rvOrderList = rootView?.findViewById(R.id.rvOrderList)
        txtNoItemFound = rootView?.findViewById(R.id.txtNoItemFound)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        hideButtomBar()
        if (coming_from.equals("main", true)) {
            setTitle("Current Orders")
            hideBack()
            showMenu()
        } else {
            setTitle("Pending Orders")
            hideMenu()
            showBack()
        }

        mainActivity.edtSearch.setText("")
        mainActivity.edtSearch.clearFocus()
        rvOrderList?.adapter = orderListAdapter
        mainActivity.imgSearchText.setOnClickListener {
            val search = mainActivity.edtSearch.text.trim()
            if (!TextUtils.isEmpty(search)) {
                val newList = orders.filter { order -> order.shop?.name?.contains(search)!! || order.order_no.contains(search) }
                if (!newList.isNullOrEmpty()) {
                    rvOrderList?.visibility = View.VISIBLE
                    txtNoItemFound.visibility = View.GONE
                    orderListAdapter.setData(newList, {
                        mainActivity.navController.navigate(R.id.orderDetailsFragment)
                    })
                } else {
                    rvOrderList?.visibility = View.GONE
                    txtNoItemFound.visibility = View.VISIBLE
                }
                mainActivity.edtSearch.clearFocus()
            } else {
                rvOrderList?.visibility = View.VISIBLE
                txtNoItemFound.visibility = View.GONE
                orderListAdapter.setData(orders, {
//                    mainActivity.navController.navigate(R.id.orderDetailsFragment)
                    val order = orders.get(it)
                    codeForeShare("Order no: " + order.order_no)
                })
                mainActivity.edtSearch.clearFocus()
            }
        }
        apiCallingForOrders()
    }

    private fun codeForeShare(share: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Order")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, share)
        mainActivity.startActivity(sharingIntent)
    }


    fun apiCallingForOrders() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            try {
                val responseOfOrderResponse = apiCallingRequest.apiCallingForRetailerOrderList(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (!responseOfOrderResponse.orders.isNullOrEmpty()) {
                        orders = responseOfOrderResponse.orders as ArrayList<PendingOrderResponse.Order>
                        if (!orders.isNullOrEmpty()) {
                            rvOrderList?.visibility = View.VISIBLE
                            txtNoItemFound.visibility = View.GONE
                            orderListAdapter.setData(orders, {
                                val order = orders.get(it)
                                codeForeShare("Order No: " + order.order_no)
                            })
                        } else {
                            rvOrderList?.visibility = View.GONE
                            txtNoItemFound.visibility = View.VISIBLE
                        }
                    } else {
                        rvOrderList?.visibility = View.GONE
                        txtNoItemFound.visibility = View.VISIBLE
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    rvOrderList?.visibility = View.GONE
                    txtNoItemFound.visibility = View.VISIBLE
                }
            }
        }

    }

}