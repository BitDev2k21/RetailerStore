package com.toyourstore.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.toyourstore.R

class RegisterDistributorComplete : BaseFragment() {

    private var rootView: View? = null
    private lateinit var llOkay: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_register_distributor_complete, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Retailer Reg. Process")
        hideMenu()
        hideButtomBar()
        Handler().postDelayed({
            mainActivity.navController.popBackStack()
            mainActivity.navController.popBackStack()
            mainActivity.navController.popBackStack()

        }, 2000)
    }


}