package com.toyourstore.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.shoplist.Shops
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.adapter.ShoapAdapter
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class ShopsFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvSales: RecyclerView? = null
    private var salesAdapter = ShoapAdapter()
    private lateinit var rlPlus: RelativeLayout
    private lateinit var pd: MyProgressDialog
    private var shops = ArrayList<Shops>()
    private lateinit var txtNoItemFound: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_sales, container, false)
        rvSales = rootView?.findViewById(R.id.rvSales)
        rlPlus = rootView?.findViewById(R.id.rlPlus)!!
        txtNoItemFound = rootView?.findViewById(R.id.txtNoItemFound)!!
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rlPlus.setOnClickListener {
            mainActivity.navController.navigate(R.id.shopAddUpdateFragment)
        }
        rvSales?.adapter = salesAdapter
        setTitle("Shops")
        showMenu()
        hideBack()
        hideButtomBar()
        apiCallingForShopList()
    }

    fun apiCallingForShopList() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
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
                        rvSales?.visibility = View.GONE
                        txtNoItemFound.visibility = View.VISIBLE
                        SessionData.getInstance(requireContext()).saveSeletedPos(0)
                        SessionData.getInstance(requireContext()).saveSeletedShopId("")
                    } else {
                        rvSales?.visibility = View.VISIBLE
                        txtNoItemFound.visibility = View.GONE
                    }
                    salesAdapter.setData(shops) { pos, name ->
                        if (name.equals("Edit", true)) {
                            val bundle = Bundle()
                            bundle.putParcelable("shop", shops.get(pos))
                            mainActivity.navController.navigate(R.id.shopAddUpdateFragment,bundle)
                        } else if (name.equals("Delete", true)) {
                            PopupUtils.confirmationDailg(
                                    requireContext(),
                                    "Are you sure want to delete?",
                                    {
                                        if (it.equals("Yes")) {
//                                    Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()
                                            var shop_id = shops.get(pos).id
                                            apiCallingForDelete(shop_id)
                                        }
                                    })
                        } else if (name.equals("Selected", true)) {
                            SessionData.getInstance(requireContext()).saveSeletedPos(pos)
                            SessionData.getInstance(requireContext())
                                    .saveSeletedShopId("" + shops.get(pos).id)
                            salesAdapter.notifyDataSetChanged()
                        }
                    }

                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    fun apiCallingForDelete(shop_id: Int) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = "" + shop_id
            try {
                val responseOfSales = apiCallingRequest.apiDeleteShop(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    Log.e("response :", responseOfSales)
                    if (responseOfSales.equals("1", true)) {
                        apiCallingForShopList()
                    } else {
                        PopupUtils.alertMessage(
                                requireContext(),
                                "Something went wrong,please try again."
                        )
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }


}