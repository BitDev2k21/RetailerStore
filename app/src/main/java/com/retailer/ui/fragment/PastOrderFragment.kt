package com.retailer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.model.PendingOrderResponse
import com.retailer.preference.SessionData
import com.retailer.ui.adapter.PastOrderAdapter
import com.retailer.ui.adapter.PendingOrderListAdapter
import com.retailer.utils.MyProgressDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class PastOrderFragment : BaseFragment() {

    private var rootView: View? = null
    private lateinit var rvPastOrder: RecyclerView
    private var pastOrderAdapter = PastOrderAdapter()
    private var listOfData = ArrayList<String>()
    private lateinit var pd: MyProgressDialog
    private var orders = ArrayList<PendingOrderResponse.Order>()
    private lateinit var txtNoItemFound: TextView
    private var orderListAdapter = PendingOrderListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_past_order, container, false)
        rvPastOrder = rootView?.findViewById(R.id.rvPastOrder)!!
        txtNoItemFound = rootView?.findViewById(R.id.txtNoItemFound)!!
        rvPastOrder.adapter = pastOrderAdapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        setTitle("Past Order")
        showMenu()
        hideBack()
        hideButtomBar()
        listOfData.add("abs")
        listOfData.add("abs")
        listOfData.add("abs")
        listOfData.add("abs")
        apiCallingForOrders()

    }

    fun apiCallingForOrders() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            try {
                val responseOfOrderResponse =
                    apiCallingRequest.apiCallingForRetailerPastOrder(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (!responseOfOrderResponse.orders.isNullOrEmpty()) {
                        orders =
                            responseOfOrderResponse.orders as ArrayList<PendingOrderResponse.Order>
                        if (!orders.isNullOrEmpty()) {
                            rvPastOrder.visibility = View.VISIBLE
                            txtNoItemFound.visibility = View.GONE
                            orderListAdapter.setData(orders, {
                                mainActivity.navController.navigate(R.id.orderDetailsFragment)
                            })
                        } else {
                            rvPastOrder.visibility = View.GONE
                            txtNoItemFound.visibility = View.VISIBLE
                        }
                    } else {
                        rvPastOrder.visibility = View.GONE
                        txtNoItemFound.visibility = View.VISIBLE
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    rvPastOrder.visibility = View.GONE
                    txtNoItemFound.visibility = View.VISIBLE
                }
            }
        }

    }


}