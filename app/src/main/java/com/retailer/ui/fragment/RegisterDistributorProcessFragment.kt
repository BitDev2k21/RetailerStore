package com.retailer.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.retailer.R
import com.retailer.api.ApiCallingRequest
import com.retailer.model.BusinessLineModel
import com.retailer.model.CityResponse
import com.retailer.model.StateResponse
import com.retailer.model.businessline.Businessline
import com.retailer.preference.SessionData
import com.retailer.utils.MyProgressDialog
import com.retailer.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

class RegisterDistributorProcessFragment : BaseFragment() {

    private var rootView: View? = null
    private lateinit var rlMulti: RelativeLayout
    private lateinit var txtBusinessLine: TextView
    private lateinit var txtCity: TextView
    private lateinit var scrollNest: NestedScrollView
    private var businessLineModels: ArrayList<Businessline>? = null
    private var stateNames: ArrayList<StateResponse.State>? = null
    private var cityNames: ArrayList<CityResponse.City>? = null
    private var selectedCity: CityResponse.City? = null

    private var selectedState: StateResponse.State? = null
    private var creditDays: ArrayList<BusinessLineModel>? = null
    private var routines: ArrayList<BusinessLineModel>? = null
    private var storeType: ArrayList<BusinessLineModel>? = null
    private var workingHours: ArrayList<BusinessLineModel>? = null
    private var operationAreas: ArrayList<BusinessLineModel>? = null

    private lateinit var rvMultiSelect: RecyclerView
    private lateinit var txtState: TextView
    private lateinit var edtGstNumber: EditText
    private lateinit var edtAddress: EditText
    private lateinit var pd: MyProgressDialog
    private lateinit var edtFullName: EditText
    private lateinit var edtMobile: EditText
    private lateinit var edtPinCode: EditText
    private lateinit var edtShop: EditText
    private lateinit var txFrom: TextView
    private lateinit var txtTo: TextView
    private lateinit var txtStoreType: TextView
    private lateinit var llSave: LinearLayout


    private var fullName = ""
    private var mobileNo = ""
    private var shopName = ""
    private var contactNum = ""
    private var agency = ""
    private var officeContact = ""
    private var operationArea = ""
    private var gstName = ""
    private var state = ""
    private var city = ""
    private var area = ""
    private var creditDay = ""
    private var routes = ""
    private var businessLine = ""
    private var address = ""
    private var pinCode = ""
    private var from = ""
    private var to = ""
    private var store = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_register_distributor, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        businessLineModels = ArrayList()
        stateNames = ArrayList()
        cityNames = ArrayList()
        creditDays = ArrayList()
        routines = ArrayList()
        operationAreas = ArrayList()
        workingHours = ArrayList()
        storeType = ArrayList()
        setTitle("Retailer Reg. Process")
        hideMenu()
        hideButtomBar()
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        rlMulti = view.findViewById(R.id.rlMulti)
        txtBusinessLine = view.findViewById(R.id.txtBusinessLine)
        scrollNest = view.findViewById(R.id.scrollNest)
        rvMultiSelect = view.findViewById(R.id.rvMultiSelect)
        txtState = view.findViewById(R.id.txtState)
        txtCity = view.findViewById(R.id.txtCity)
        edtGstNumber = view.findViewById(R.id.edtGstNumber)
        edtAddress = view.findViewById(R.id.edtAddress)
        llSave = view.findViewById(R.id.llSave)
        txFrom = view.findViewById(R.id.txFrom)
        txtTo = view.findViewById(R.id.txtTo)
        txtStoreType = view.findViewById(R.id.txtStoreType)
        edtFullName = view.findViewById(R.id.edtFullName)
        edtMobile = view.findViewById(R.id.edtMobile)
        edtPinCode = view.findViewById(R.id.edtPinCode)
        edtShop = view.findViewById(R.id.edtShop)

        creditDays?.add(BusinessLineModel("10 days", false))
        creditDays?.add(BusinessLineModel("20 days", false))
        creditDays?.add(BusinessLineModel("30 days", false))
        routines?.add(BusinessLineModel("Daily", false))
        routines?.add(BusinessLineModel("Weekly", false))
        routines?.add(BusinessLineModel("Monthly", false))
        operationAreas?.add(BusinessLineModel("Sola", false))
        operationAreas?.add(BusinessLineModel("Radio mirchi", false))
        operationAreas?.add(BusinessLineModel("GandhiNagar", false))
        operationAreas?.add(BusinessLineModel("prahlad nagar ahmedabad", false))
        storeType?.add(BusinessLineModel("Store Type: 1", false))
        storeType?.add(BusinessLineModel("Store Type: 2", false))
        storeType?.add(BusinessLineModel("Store Type: 3", false))
        workingHours?.add(BusinessLineModel("1 AM", false))
        workingHours?.add(BusinessLineModel("2 AM", false))
        workingHours?.add(BusinessLineModel("3 AM", false))
        workingHours?.add(BusinessLineModel("4 AM", false))
        workingHours?.add(BusinessLineModel("5 AM", false))
        workingHours?.add(BusinessLineModel("6 AM", false))
        workingHours?.add(BusinessLineModel("7 AM", false))
        workingHours?.add(BusinessLineModel("8 AM", false))
        workingHours?.add(BusinessLineModel("9 AM", false))
        workingHours?.add(BusinessLineModel("10 AM", false))
        workingHours?.add(BusinessLineModel("11 AM", false))
        workingHours?.add(BusinessLineModel("12 AM", false))

        workingHours?.add(BusinessLineModel("1 PM", false))
        workingHours?.add(BusinessLineModel("2 PM", false))
        workingHours?.add(BusinessLineModel("3 PM", false))
        workingHours?.add(BusinessLineModel("4 PM", false))
        workingHours?.add(BusinessLineModel("5 PM", false))
        workingHours?.add(BusinessLineModel("6 PM", false))
        workingHours?.add(BusinessLineModel("7 PM", false))
        workingHours?.add(BusinessLineModel("8 PM", false))
        workingHours?.add(BusinessLineModel("9 PM", false))
        workingHours?.add(BusinessLineModel("10 PM", false))
        workingHours?.add(BusinessLineModel("11 PM", false))
        workingHours?.add(BusinessLineModel("12 PM", false))

        txFrom.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select Hours ", workingHours, {
                txFrom.text = it
            })
        }

        txtTo.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select Hours ", workingHours, {
                txtTo.text = it
            })
        }

        llSave.setOnClickListener {
            if (isValidate()) {
                val bundle = Bundle()
                bundle.putString("fullName", fullName)
                bundle.putString("mobileNo", mobileNo)
                bundle.putString("shopName", shopName)
                bundle.putString("address", address)
                bundle.putString("gstName", gstName)
                bundle.putString("state", state);
                bundle.putString("city", city)
                bundle.putString("businessLine", businessLine)
                bundle.putString("pinCode", pinCode)
                bundle.putString("from", from)
                bundle.putString("store", store)
                bundle.putString("to", to)
                mainActivity.navController.navigate(R.id.reviewFragment, bundle)
            }
        }

        txtBusinessLine.setOnClickListener {
            if (businessLineModels.isNullOrEmpty()) {
                apiCallingForBusinessLine()
            } else {
                PopupUtils.multiChoiceDailogForBusinessLine(
                        mainActivity,
                        "Select business line ",
                        businessLineModels,
                        { name, id ->
                            txtBusinessLine.text = name

                        })
            }
        }

        txtState.setOnClickListener {
            if (stateNames.isNullOrEmpty()) {
                apiCallingForState()
            } else {
                PopupUtils.singleChoiceDailogForState(
                        mainActivity,
                        "Select state ",
                        stateNames,
                        selectedState,
                        {
                            selectedState = it
                            txtState.text = selectedState?.name
                        })
            }
        }

        txtCity.setOnClickListener {
            if (selectedState == null) {
                Toast.makeText(requireContext(), "Please select state first", Toast.LENGTH_SHORT)
                        .show()
            } else {
                apiCallingForCity()
            }
        }


        txtStoreType.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select store type ", storeType, {
                txtStoreType.text = it
            })
        }

    }

    private fun apiCallingForCity() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["state_id"] = "" + selectedState?.id
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.apiCallingForCityResponse(params)
                cityNames = responseOfCity.cities as ArrayList<CityResponse.City>
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    PopupUtils.singleChoiceDailogForCity(
                            mainActivity,
                            "Select City ",
                            cityNames,
                            selectedCity,
                            {
                                selectedCity = it
                                txtCity.text = selectedCity?.name
                            })
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }


    private fun apiCallingForState() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfState = apiCallingRequest.apiCallingForSateResponse(params)
                stateNames = responseOfState.states as ArrayList<StateResponse.State>?
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    PopupUtils.singleChoiceDailogForState(
                            mainActivity,
                            "Select state ",
                            stateNames,
                            selectedState,
                            {
                                selectedState = it
                                txtState.text = selectedState?.name
                            })
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    private fun apiCallingForBusinessLine() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfBusinessResponse =
                        apiCallingRequest.apiCallingBusinessLine(
                                params
                        )
                withContext(Dispatchers.Main) {
                    businessLineModels = responseOfBusinessResponse.businessline as ArrayList<Businessline>
                    pd.cancel()
                    PopupUtils.multiChoiceDailogForBusinessLine(
                            mainActivity,
                            "Select business line ",
                            businessLineModels,
                            { name, id ->
                                txtBusinessLine.text = name
                            })
                    Log.e("Response", "" + responseOfBusinessResponse)
                }
            } catch (apiEx: ApiException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    mainActivity.navController.popBackStack()
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        fullName = edtFullName.text.toString().trim()
        mobileNo = edtMobile.text.toString().trim()
        shopName = edtShop.text.toString().trim()
        address = edtAddress.text.toString().trim()
        gstName = edtGstNumber.text.toString().trim()
        state = txtState.text.toString().trim()
        city = txtCity.text.toString().trim()
        businessLine = txtBusinessLine.text.toString().trim()
        pinCode = edtPinCode.text.toString().trim()
        from = txFrom.text.toString().trim()
        store = txtStoreType.text.toString().trim()
        to = txtTo.text.toString().trim()

        if (TextUtils.isEmpty(fullName)) {
            PopupUtils.alertMessage(requireContext(), "Enter full name")
            return false
        } else if (TextUtils.isEmpty(mobileNo)) {
            PopupUtils.alertMessage(requireContext(), "Enter mobile number")
            return false
        } else if (TextUtils.isEmpty(shopName)) {
            PopupUtils.alertMessage(requireContext(), "Enter shop name")
            return false
        } else if (TextUtils.isEmpty(address)) {
            PopupUtils.alertMessage(requireContext(), "Enter address")
            return false
        } else if (TextUtils.isEmpty(gstName)) {
            PopupUtils.alertMessage(requireContext(), "Enter gst number")
            return false
        }
        else if (gstName.length!=15) {
            PopupUtils.alertMessage(requireContext(), "please check gst number, it must be 15 digits")
            return false
        }
        else if (TextUtils.isEmpty(state)) {
            PopupUtils.alertMessage(requireContext(), "Select state")
            return false
        } else if (TextUtils.isEmpty(city)) {
            PopupUtils.alertMessage(requireContext(), "Select city")
            return false
        } else if (TextUtils.isEmpty(pinCode)) {
            PopupUtils.alertMessage(requireContext(), "Enter pin code")
            return false
        } else if (TextUtils.isEmpty(from)) {
            PopupUtils.alertMessage(requireContext(), "Enter address")
            return false
        } else if (TextUtils.isEmpty(to)) {
            PopupUtils.alertMessage(requireContext(), "Select credit day")
            return false
        } else if (TextUtils.isEmpty(store)) {
            PopupUtils.alertMessage(requireContext(), "Select store type")
            return false
        } else if (TextUtils.isEmpty(businessLine)) {
            PopupUtils.alertMessage(requireContext(), "Select business line")
            return false
        } else {
            return true
        }
    }


}



