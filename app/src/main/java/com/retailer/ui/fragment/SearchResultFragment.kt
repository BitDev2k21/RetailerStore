package com.retailer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.ui.CustomEditText
import com.retailer.ui.DrawableClickListener
import com.retailer.ui.adapter.SearchResultAdapter

class SearchResultFragment : BaseFragment() {

    private var edtSearch: CustomEditText? = null
    private var rootView: View? = null
    private var nestedScroll: NestedScrollView? = null
    private var rvSelectItem: RecyclerView? = null
    private var searchResultAdapter = SearchResultAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_search_result, container, false)
        edtSearch = rootView?.findViewById(R.id.edtSearch)
        nestedScroll = rootView?.findViewById(R.id.nestedScroll)
        rvSelectItem = rootView?.findViewById(R.id.rvSelectItem)
        rvSelectItem?.adapter = searchResultAdapter
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Search Result")
        showBack()
        hideMenu()
        edtSearch?.setDrawableClickListener(object : DrawableClickListener {
            override fun onClick(target: DrawableClickListener.DrawablePosition?) {
                when (target) {
                    DrawableClickListener.DrawablePosition.LEFT -> {
                        Toast.makeText(requireContext(), "Left click", Toast.LENGTH_SHORT).show()
                    }
                    DrawableClickListener.DrawablePosition.RIGHT -> {
                        Toast.makeText(requireContext(), "Right click", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        var listOfSearchItem = arrayListOf<String>()
        listOfSearchItem.add("Shelf Life")
        listOfSearchItem.add("Product")
        listOfSearchItem.add("Product Type")
        listOfSearchItem.add("Company")
        listOfSearchItem.add("Price Range/Profit Margin")
        listOfSearchItem.add("Most Ordered Product in Area")
        searchResultAdapter.setData(listOfSearchItem, {
            mainActivity.navController.navigate(R.id.nav_product_details)
        })
    }

}