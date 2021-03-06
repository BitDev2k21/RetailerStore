package com.retailer.ui.fragment

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.api.ApiException
import com.retailer.R
import com.retailer.api.ApiCallingRequest
import com.retailer.model.BusinessLineModel
import com.retailer.model.CityResponse
import com.retailer.model.GetLocation
import com.retailer.model.StateResponse
import com.retailer.model.businessline.Businessline
import com.retailer.model.shoplist.Shops
import com.retailer.preference.SessionData
import com.retailer.service.CurrentLocationService
import com.retailer.utils.MyProgressDialog
import com.retailer.utils.PermissionUtils
import com.retailer.utils.PopupUtils
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class ShopAddUpdateFragment : BaseFragment() {

    var pd: MyProgressDialog? = null
    private var isLastLocation = false
    private lateinit var llSave: LinearLayout
    private var rootView: View? = null
    private lateinit var edtShop: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtGstNumber: EditText
    private lateinit var txtState: TextView
    private lateinit var txtCity: TextView
    private lateinit var txFrom: TextView
    private lateinit var txtTo: TextView
    private lateinit var txtLocationId: TextView
    private lateinit var txtBusinessLine: TextView
    private var creditDays: ArrayList<BusinessLineModel>? = null
    private var workingHours: ArrayList<BusinessLineModel>? = null


    private val mCurrentLocationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get extra data included in the Intent
            val latitude = intent.getDoubleExtra("latitude", 0.0);
            val longitude = intent.getDoubleExtra("longitude", 0.0);
            val speed = intent.getFloatExtra("speed", 0.0f);
            Log.e("Location:", "lat + long " + latitude + ":" + longitude)
            if (!isLastLocation) {
                isLastLocation = true
                pd!!.cancel()
                stopServiceForCurrentLocation()
                if (shop == null) {
                    apiCallingForSaveShop("" + latitude, "" + longitude)
                } else {
                    apiCallingForUpdate("" + latitude, "" + longitude)
                }
            }
        }
    }

    private fun apiCallingForSaveShop(lat: String, long: String) {
        pd!!.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["name"] = shopName
            params["owner_name"] = DataBaseHelper.getDatabaseDao(requireContext())
                .getUser(SessionData.getInstance(requireContext()).getUserId()!!.toLong()).name!!
            params["contact"] = "123456789"
            params["location_id"] = "" + location?.id
            params["state_id"] = "" + selectedState?.id
            params["city_id"] = "" + selectedCity?.id
            params["working_hours"] = from + " - " + to
            params["latitude"] = lat
            params["longitude"] = long
            params["gst_no"] = gstNo
            params["address"] = address
            try {
                val responseOfLogin =
                    apiCallingRequest.apiCallingAddShop(
                        params
                    )
                val message = responseOfLogin.message
                withContext(Dispatchers.Main) {
                    isLastLocation = true
                    pd!!.cancel()
                    PopupUtils.alertMessageWithCallBack(
                        requireContext(),
                        "Shop added successfully.",
                        {
                            mainActivity.navController.popBackStack()
                        })
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    isLastLocation = true
                    pd!!.cancel()
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun apiCallingForUpdate(lat: String, long: String) {
        pd!!.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = "" + shop?.id
            params["name"] = shopName
            params["owner_name"] = DataBaseHelper.getDatabaseDao(requireContext())
                .getUser(SessionData.getInstance(requireContext()).getUserId()!!.toLong()).name!!
            params["contact"] = "123456789"
            params["location_id"] = "" + location?.id
            params["state_id"] = "" + selectedState?.id
            params["city_id"] = "" + selectedCity?.id
            params["working_hours"] = from + " - " + to
            params["latitude"] = lat
            params["longitude"] = long
            params["gst_no"] = gstNo
            params["address"] = address
            try {
                val responseOfLogin =
                    apiCallingRequest.apiCallingAUpdateShop(
                        params
                    )
                val message = responseOfLogin.message
                withContext(Dispatchers.Main) {
                    isLastLocation = true
                    pd!!.cancel()
                    PopupUtils.alertMessageWithCallBack(
                        requireContext(),
                        "Shop added successfully.",
                        {
                            mainActivity.navController.popBackStack()
                        })
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    isLastLocation = true
                    pd!!.cancel()
                    PopupUtils.alertMessage(requireContext(), "" + apiEx.message)
                }
            }
        }
    }

    private var stateNames: ArrayList<StateResponse.State>? = null
    private var selectedState: StateResponse.State? = null
    private var cityNames: ArrayList<CityResponse.City>? = null
    private var selectedCity: CityResponse.City? = null
    private var businessLineModels: ArrayList<Businessline>? = null
    private var shop: Shops? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shop = arguments?.getParcelable("shop")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_add_update_shop, container, false)
        edtAddress = rootView?.findViewById(R.id.edtAddress)!!
        edtShop = rootView?.findViewById(R.id.edtShop)!!
        edtGstNumber = rootView?.findViewById(R.id.edtGstNumber)!!
        txtState = rootView?.findViewById(R.id.txtState)!!
        txtCity = rootView?.findViewById(R.id.txtCity)!!
        txFrom = rootView?.findViewById(R.id.txFrom)!!
        txtTo = rootView?.findViewById(R.id.txtTo)!!
        txtLocationId = rootView?.findViewById(R.id.txtLocationId)!!
        txtBusinessLine = rootView?.findViewById(R.id.txtBusinessLine)!!
        llSave = rootView?.findViewById(R.id.llSave)!!
        if (shop != null) {
            setDefaultValue()
        }
        return rootView
    }

    private fun setDefaultValue() {
        edtShop.setText(shop?.name!!)
        edtAddress.setText(shop?.address)
        edtGstNumber.setText(shop?.gst_no)
        var listString = shop?.working_hours?.split("-")
        txFrom.setText(listString?.get(0))
        txtTo.setText(listString?.get(1))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateNames = ArrayList()
        cityNames = ArrayList()
        businessLineModels = ArrayList()
        creditDays = ArrayList()
        workingHours = ArrayList()
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        mainActivity.edtSearch.setText("")
        mainActivity.edtSearch.clearFocus()
        setTitle("Shop Details")
        hideMenu()
        showBack()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(mCurrentLocationReceiver, IntentFilter("current-location-event"))

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


        llSave.setOnClickListener {
            if (isValidData()) {
                if (PermissionUtils.isPermissionForLocation(requireContext())) {
                    startServiceForCurrentLocation()
                } else {
                    PermissionUtils.requestPermissions(this, pERMISSION_ID)
                }
            }
        }

        txFrom.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select Credit day ", workingHours, {
                txFrom.text = it
            })
        }

        txtTo.setOnClickListener {
            PopupUtils.singleChoiceDailog(mainActivity, "Select Credit day ", workingHours, {
                txtTo.text = it
            })
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

        txtLocationId.setOnClickListener {
            if (selectedCity == null) {
                Toast.makeText(requireContext(), "Please select city first", Toast.LENGTH_SHORT)
                    .show()
            } else {
                apiCallingForLocation()
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

    }

    private fun apiCallingForBusinessLine() {
        pd?.show()
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
                    businessLineModels =
                        responseOfBusinessResponse.businessline as ArrayList<Businessline>
                    pd?.cancel()
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
                    pd?.cancel()
                    mainActivity.navController.popBackStack()
                }
            }
        }
    }

    var location: GetLocation.Location? = null
    private fun apiCallingForLocation() {
        pd!!.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["state_id"] = "" + selectedState?.id
            params["city_id"] = "" + selectedCity?.id
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.getLocationList(params)
                withContext(Dispatchers.Main) {
                    pd!!.cancel()
                    val locations = responseOfCity.locations as ArrayList<GetLocation.Location>
                    if (locations.isNullOrEmpty()) {
                        PopupUtils.alertMessage(
                            requireContext(),
                            "Please select another state/city."
                        )
                    } else {
                        PopupUtils.singleChoiceDailogForLocation(
                            mainActivity,
                            "Select Location ",
                            locations,
                            location,
                            {
                                location = it
                                location_text = location?.name!!
                                txtLocationId.text = location_text
                            })
                    }
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                    pd!!.cancel()
                }
            }
        }
    }


    private fun apiCallingForCity() {
        pd?.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["state_id"] = "" + selectedState?.id
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.apiCallingForCityResponse(params)
                cityNames = responseOfCity.cities as ArrayList<CityResponse.City>
                withContext(Dispatchers.Main) {
                    pd?.cancel()
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
                    pd?.cancel()
                }
            }
        }
    }


    private fun apiCallingForState() {
        pd?.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfState = apiCallingRequest.apiCallingForSateResponse(params)
                stateNames = responseOfState.states as ArrayList<StateResponse.State>?
                withContext(Dispatchers.Main) {
                    pd?.cancel()
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
                    pd?.cancel()
                }
            }
        }
    }

    var shopName = ""
    var address = ""
    var gstNo = ""
    var pincode = ""
    var state = ""
    var city = ""
    var location_text = ""
    var from = ""
    var to = ""
    var storeType = ""
    fun isValidData(): Boolean {
        shopName = edtShop.text.toString().trim()
        address = edtAddress.text.toString().trim()
        gstNo = edtGstNumber.text.toString().trim()
        state = txtState.text.toString().trim()
        city = txtCity.text.toString().trim()
        from = txFrom.text.toString().trim()
        to = txtTo.text.toString().trim()
        storeType = txtBusinessLine.text.toString().trim()
        if (TextUtils.isEmpty(shopName)) {
            PopupUtils.alertMessage(requireContext(), "Please enter shop name")
            return false
        } else if (TextUtils.isEmpty(address)) {
            PopupUtils.alertMessage(requireContext(), "Please enter address")
            return false
        } else if (TextUtils.isEmpty(gstNo)) {
            PopupUtils.alertMessage(requireContext(), "Please enter gst no")
            return false
        } else if (gstNo.length!=15) {
            PopupUtils.alertMessage(requireContext(), "please check gst number, it must be 15 digits")
            return false
        }
        else if (TextUtils.isEmpty(state)) {
            PopupUtils.alertMessage(requireContext(), "Please enter state")
            return false
        } else if (TextUtils.isEmpty(city)) {
            PopupUtils.alertMessage(requireContext(), "Please enter city")
            return false
        } else if (TextUtils.isEmpty(location_text)) {
            PopupUtils.alertMessage(requireContext(), "Please select location/area")
            return false
        } else if (TextUtils.isEmpty(from)) {
            PopupUtils.alertMessage(requireContext(), "Please enter from")
            return false
        } else if (TextUtils.isEmpty(to)) {
            PopupUtils.alertMessage(requireContext(), "Please enter to")
            return false
        } else if (TextUtils.isEmpty(storeType)) {
            PopupUtils.alertMessage(requireContext(), "Please enter store type")
            return false
        } else {
            return true
        }
    }

    fun startServiceForCurrentLocation() {
        pd!!.show()
        val serviceIntent = Intent(requireContext(), CurrentLocationService::class.java)
        serviceIntent.putExtra("inputExtra", "Running Serice...")
        mainActivity.startService(serviceIntent)
    }

    fun stopServiceForCurrentLocation() {
        val stopSer = Intent(requireContext(), CurrentLocationService::class.java)
        mainActivity.stopService(stopSer)
    }


    private val pERMISSION_ID = 42
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            pERMISSION_ID -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) === PackageManager.PERMISSION_GRANTED)
                    ) {
                        Log.e("===", "Permission Granted")
                        startServiceForCurrentLocation()
                    }
                } else {
                    Log.e("===", "Permission Denied")
                }
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(mCurrentLocationReceiver)
        stopServiceForCurrentLocation()

    }


}