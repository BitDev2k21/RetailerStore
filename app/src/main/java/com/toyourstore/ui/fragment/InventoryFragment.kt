package com.toyourstore.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.InventoryResponse
import com.toyourstore.model.SalesTeamResponse
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.adapter.InventryAdapter
import com.toyourstore.utils.MyProgressDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class InventoryFragment : BaseFragment() {

    private var rootView: View? = null
    private var rvInverntry: RecyclerView? = null
    private var inventryAdapter = InventryAdapter()
    private lateinit var pd: MyProgressDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_inverntroy, container, false)
        rvInverntry = rootView?.findViewById(R.id.rvInverntry)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Inventory")
        hideMenu()
        showBack()
        showButtom()
        rvInverntry?.adapter = inventryAdapter
        apiCallingForInventry()
    }

    fun apiCallingForInventry() {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfInventry =
                    apiCallingRequest.apiCallingInventry(
                        params
                    )
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    inventryAdapter.setData(responseOfInventry.inventory as ArrayList<InventoryResponse.Inventory>, {
                        mainActivity.navController.navigate(R.id.orderDetailsFragment)
                    })
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }

    }


}