package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.ui.adapter.PendingOrderListAdapter
import com.toyourstore.ui.adapter.PendingPaymentListAdapter

class PendingPaymentListFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvOrderList: RecyclerView? = null
    private var pendingPaymentListAdapter =PendingPaymentListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_payment_list, container, false)
        rvOrderList = rootView?.findViewById(R.id.rvOrderList)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Pending Orders")
        hideMenu()
        showBack()
        var listOforder = arrayListOf<String>()
        listOforder.add("one")
        listOforder.add("Two")
        listOforder.add("Three")
        rvOrderList?.adapter = pendingPaymentListAdapter
        pendingPaymentListAdapter.setData(listOforder, {
            mainActivity.navController.navigate(R.id.orderDetailsFragment)
         })

    }

}