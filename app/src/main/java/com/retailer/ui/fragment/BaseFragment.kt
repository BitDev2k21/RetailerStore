package com.retailer.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.retailer.ui.MainActivity

open class BaseFragment : Fragment() {

    protected lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    fun setTitle(title: String) {
        mainActivity.txtTitle.text = title
    }

    fun hideMenu() {
        mainActivity.imgMenu.visibility = View.GONE
    }


    fun hideBack() {
        mainActivity.imgBack.visibility = View.GONE

    }

    fun showMenu() {
        mainActivity.imgMenu.visibility = View.VISIBLE

    }

    fun showBack() {
        mainActivity.imgBack.visibility = View.VISIBLE
    }


    fun showButtom() {
        mainActivity.llBottom.visibility = View.VISIBLE
    }

    fun hideButtomBar() {
        mainActivity.llBottom.visibility = View.GONE
    }



}