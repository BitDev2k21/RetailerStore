package com.toyourstore.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.preference.SessionData
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class ReviewFragment : BaseFragment() {

    private lateinit var txtConfirm: TextView
    private lateinit var txtCancel: TextView
    private var fullName = ""
    private var mobileNo = ""
    private var shopName = ""
    private var agency = ""
    private var officeContact = ""
    private var operationArea = ""
    private var gstName = ""
    private var state = ""
    private var city = ""
    private var pinCode = ""
    private var from = ""
    private var storeType = ""
    private var businessLine = ""
    private var address = ""
    private var to = ""

    private lateinit var txtShopName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtGstNo: TextView
    private lateinit var txtState: TextView
    private lateinit var txtcity: TextView
    private lateinit var txtAddress: TextView
    private lateinit var txtPinCode: TextView
    private lateinit var txtWorkingHours: TextView
    private lateinit var txtStoreType: TextView
    private lateinit var txtBusinessLine: TextView
    private lateinit var txtFirstName: TextView
    private lateinit var txtMobileNo: TextView

    private lateinit var pd: MyProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullName = arguments?.getString("fullName", "")!!
        mobileNo = arguments?.getString("mobileNo", "")!!
        shopName = arguments?.getString("shopName", "")!!
        address = arguments?.getString("address", "")!!
        gstName = arguments?.getString("gstName", "")!!
        state = arguments?.getString("state", "")!!
        city = arguments?.getString("city", "")!!
        pinCode = arguments?.getString("pinCode", "")!!
        from = arguments?.getString("from", "")!!
        storeType = arguments?.getString("store", "")!!
        to = arguments?.getString("to", "")!!
        city = arguments?.getString("city", "")!!
        businessLine = arguments?.getString("businessLine", "")!!

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_review, container, false)
        txtShopName = root.findViewById(R.id.txtShopName)
        txtEmail = root.findViewById(R.id.txtEmail)
        txtGstNo = root.findViewById(R.id.txtGstNo)
        txtState = root.findViewById(R.id.txtState)
        txtcity = root.findViewById(R.id.txtcity)
        txtAddress = root.findViewById(R.id.txtAddress)
        txtPinCode = root.findViewById(R.id.txtPinCode)
        txtWorkingHours = root.findViewById(R.id.txtWorkingHours)
        txtStoreType = root.findViewById(R.id.txtStoreType)
        txtBusinessLine = root.findViewById(R.id.txtBusinessLine)
        txtFirstName = root.findViewById(R.id.txtFirstName)
        txtMobileNo = root.findViewById(R.id.txtMobileNo)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtConfirm = view.findViewById(R.id.txtConfirm)
        txtCancel = view.findViewById(R.id.txtCancel)
        showBack()
        setTitle("Retailer Reg. Process")
        hideMenu()
        hideButtomBar()
        setDefaultValue()
        txtConfirm.setOnClickListener {
//            apiCallingForDistributorProcess()
            mainActivity.navController.navigate(R.id.registerDistributorComplete)
            SessionData.getInstance(requireContext()).saveIsRegister(true)
        }
    }

    private fun setDefaultValue() {
        txtFirstName.text = fullName
        txtMobileNo.text = mobileNo
        txtShopName.text = shopName
        txtGstNo.text = gstName
        txtState.text = state
        txtcity.text = city
        txtAddress.text = address
        txtPinCode.text = pinCode
        txtWorkingHours.text = from +" TO " +to
        txtStoreType.text = storeType
        txtBusinessLine.text = businessLine
    }

    private fun apiCallingForDistributorProcess() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["name"] = "Demo 1"
            params["owner_name"] = fullName
            params["contact"] = shopName
            params["working_hours"] = "9-8"
            params["gst_no"] = gstName
            params["routes"] = to
            params["credit_period"] = "30"
            params["address"] = address
            params["state_id"] = "1"
            params["city_id"] = "1"
            params["latitude"] = "0.00"
            params["longitude"] = "0.00"
            params["companies"] = "1,2"
            params["businesslines"] = "1,2"
            params["locations"] = "1,2"
            try {
                val responseOfLogin =
                        apiCallingRequest.apiCallingForUpdateDist(
                                params
                        )
                val message = responseOfLogin.message
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    mainActivity.navController.navigate(R.id.registerDistributorComplete)
                    SessionData.getInstance(requireContext()).saveIsRegister(true)
                    if (TextUtils.isEmpty(message)) {
                        Toast.makeText(requireContext(), "Success response", Toast.LENGTH_SHORT).show()
                    } else {
                        PopupUtils.alertMessage(requireContext(), message!!)
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    mainActivity.navController.navigate(R.id.registerDistributorComplete)

//                    Toast.makeText(requireContext(), "Something went wrong..", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}