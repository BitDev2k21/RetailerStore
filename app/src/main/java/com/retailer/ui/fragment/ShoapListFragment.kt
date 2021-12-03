package com.retailer.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.retailer.R
import com.retailer.utils.PopupUtils

class ShoapListFragment : BaseFragment() {

    private var rootView: View? = null
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtContactNo: EditText
    private lateinit var txtSave: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_shoap_list, container, false)
        edtName = rootView?.findViewById(R.id.edtName)!!
        edtEmail = rootView?.findViewById(R.id.edtEmail)!!
        edtContactNo = rootView?.findViewById(R.id.edtContactNo)!!
        txtSave = rootView?.findViewById(R.id.txtSave)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtSave.setOnClickListener {
            if (isValidData()) {
                mainActivity.navController.popBackStack()
            }
        }
    }


    fun isValidData(): Boolean {
        val name = edtName.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val contact = edtContactNo.text.toString().trim()
        if (TextUtils.isEmpty(name)) {
            PopupUtils.alertMessage(requireContext(), "Enter name")
            return false
        } else if (TextUtils.isEmpty(email)) {
            PopupUtils.alertMessage(requireContext(), "Enter email")
            return false
        } else if (TextUtils.isEmpty(contact)) {
            PopupUtils.alertMessage(requireContext(), "Enter contact")
            return false
        } else {
            return true
        }
    }

}