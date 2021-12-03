package com.retailer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.retailer.R
import com.retailer.ui.adapter.SpecialOfferAdapter

class SpecialOfferFragment : BaseFragment() {

    private var rootView: View? = null
    private lateinit var rvOffer: RecyclerView
    private var specialOfferAdapter = SpecialOfferAdapter()
    private var listOfString = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_special_offer, container, false)
        rvOffer = rootView?.findViewById(R.id.rvOffer)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOfString.add("one")
        listOfString.add("two")
        listOfString.add("three")
        rvOffer.adapter = specialOfferAdapter
        specialOfferAdapter.setData(listOfString, {

        })
    }

}