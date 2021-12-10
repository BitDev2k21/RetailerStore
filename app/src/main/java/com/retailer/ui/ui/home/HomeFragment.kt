package com.retailer.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.retailer.R
import com.retailer.api.ApiCallingRequest
import com.retailer.model.shoplist.Shops
import com.retailer.preference.SessionData
import com.retailer.ui.LoginActivity
import com.retailer.ui.fragment.BaseFragment
import com.retailer.utils.DateUtils
import com.retailer.utils.MyProgressDialog
import de.footprinttech.wms.db.DataBaseHelper
import kotlinx.android.synthetic.main.layout_side_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.util.HashMap

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pd: MyProgressDialog
    private lateinit var txtPendingOrder: TextView
    private lateinit var txtPendingPayment: TextView
    private lateinit var rlItemInCart: RelativeLayout
    private lateinit var rlPendingOrders: RelativeLayout
    private lateinit var txtName: TextView
    private lateinit var txtType: TextView
    private lateinit var tv_current_date: TextView

    private var shops = ArrayList<Shops>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        txtPendingOrder = root.findViewById(R.id.txtPendingOrder)
        txtPendingPayment = root.findViewById(R.id.txtPendingPayment)
        rlItemInCart = root.findViewById(R.id.rlItemInCart)
        rlPendingOrders = root.findViewById(R.id.rlPendingOrders)
        txtName = root.findViewById(R.id.txtName)
        txtType = root.findViewById(R.id.txtType)
        tv_current_date= root.findViewById(R.id.tv_current_date)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("Fragment:", "Home Fragment")
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        hideBack()
        showMenu()
        hideButtomBar()
        setTitle(" ")
        mainActivity.edtSearch.setText("")
        mainActivity.edtSearch.clearFocus()
        mainActivity.selectedSideMenu(mainActivity.txtHome)
        rlItemInCart.setOnClickListener {
            mainActivity.navController.navigate(R.id.itemInCartFragment)
        }
        rlPendingOrders.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("coming_from", "home")
            mainActivity.navController.navigate(R.id.orderListFragment, bundle)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val user = DataBaseHelper.getDatabaseDao(requireContext())
                    .getUser(SessionData.getInstance(requireContext()).getUserId()!!.toLong())
            withContext(Dispatchers.Main) {
                txtName.text = user.name
                txtType.text = user.userType
            }
        }
        tv_current_date.text = DateUtils.getCurrentDate()
//        apiCallingForShopList()
    }

    override fun onResume() {
        super.onResume()
        apiCallingForShopList()
    }

    fun apiCallingForShopList() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfSales = apiCallingRequest.apiCallingForShopList(params)
                shops.clear()
                shops = responseOfSales.shops as ArrayList<Shops>
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (shops.isNullOrEmpty()) {
                        mainActivity.navController.navigate(R.id.shopAddUpdateFragmentFirstTime)
                    } else {
                        if (TextUtils.isEmpty(
                                        SessionData.getInstance(requireContext()).getSelectedShopId()
                                )
                        ) {
                            SessionData.getInstance(requireContext()).saveSeletedPos(0)
                            SessionData.getInstance(requireContext())
                                    .saveSeletedPos(shops.get(0).id)
                        }
                        apiCallingForDashBoard()
                    }
                }
            } catch (apiEx: Exception) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    try {
                        val jsonObject = JSONObject(apiEx.message)
                        val code = jsonObject.getInt("code")
                        if (code == 401) {
                            SessionData.getInstance(requireContext()).clearData()
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            mainActivity.finishAffinity()
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

    private fun apiCallingForDashBoard() {
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = com.retailer.api.ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            try {
                val dashBoardResponse =
                        apiCallingRequest.apiCallingForDashBoard(
                                params
                        )
                Log.e("response", "" + dashBoardResponse)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if(dashBoardResponse?.data?.itemsInCart!=null){
                        txtPendingOrder.text = "" + dashBoardResponse?.data?.itemsInCart!!
                    }
                    if(dashBoardResponse?.data?.pendingOrders!=null){
                        txtPendingPayment.text = "" + dashBoardResponse?.data?.pendingOrders!!
                    }
                }
            } catch (apiEx: Exception) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    try {
                        val jsonObject = JSONObject(apiEx.message)
                        val code = jsonObject.getInt("code")
                        if (code == 401) {
                            SessionData.getInstance(requireContext()).clearData()
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            mainActivity.finishAffinity()
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }

                }
            }
        }
    }


}