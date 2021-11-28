package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.ui.adapter.ProductDeailsAdapter

class ProductDetailsFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvProductDeatils: RecyclerView? = null
    private var productDeailsAdapter = ProductDeailsAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_product_details, container, false)
        rvProductDeatils = rootView?.findViewById(R.id.rvProductDeatils)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBack()
        setTitle("Product Details")
        var listOfProductDetails = arrayListOf<String>()
        listOfProductDetails.add("Purchasing Price:")
        listOfProductDetails.add("Selling Price:")
        listOfProductDetails.add("No of Units:")
        listOfProductDetails.add("Boxes:")
        listOfProductDetails.add("Total Price:")
        rvProductDeatils?.adapter = productDeailsAdapter
        productDeailsAdapter.setData(listOfProductDetails, {

        })
    }

}