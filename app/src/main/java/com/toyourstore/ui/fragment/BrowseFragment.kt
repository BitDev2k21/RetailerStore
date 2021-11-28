package com.toyourstore.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.ShelfLife
import com.toyourstore.model.products.Company
import com.toyourstore.model.products.ProductType
import com.toyourstore.model.products.Products
import com.toyourstore.model.shoplist.Shops
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.MainActivity
import com.toyourstore.ui.adapter.BrowseAdapter
import com.toyourstore.utils.MyProgressDialog
import com.toyourstore.utils.PopupUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.HashMap

class BrowseFragment : BaseFragment() {

    private lateinit var rvBrowse: RecyclerView
    private lateinit var txtNoFound: TextView
    lateinit var browseAdapter :BrowseAdapter
    var listOfItem = ArrayList<Products>()
    var imagePath :String = ""
    var TAG = "@@@MainActivity"
    private lateinit var pd: MyProgressDialog
    var selectedShelfStr=""
    var selectedShelfStrUnit=""
    var selectedProductTypeStr=ArrayList<ProductType>()
    var selectedCompanyStr=ArrayList<Company>()
    var mostOrderedProductBl=false
    var selPriceRangeMin=""
    var selPriceRangeMax=""
    var selPriceMarginMin=""
    var selPriceMarginMax=""
    lateinit var productTypeList: List<ProductType>
    lateinit var companyList:List<Company>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_browse, container, false)
        rvBrowse = root.findViewById(R.id.rvBrowse)
        txtNoFound = root.findViewById(R.id.txtNoFound)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pd = MyProgressDialog(requireContext(), R.drawable.icons8_loader)
        setTitle("Products")
        showMenu()
        hideBack()
        showButtom()
        if(!this::companyList.isInitialized || companyList.isEmpty()){
            getCompanyList()
        }
        if(!this::productTypeList.isInitialized || productTypeList.isEmpty()){
            getProductTypeList()
        }

        mainActivity.edtSearch.setText("")
        mainActivity.edtSearch.clearFocus()
        browseAdapter = BrowseAdapter(requireContext())
        rvBrowse.adapter = browseAdapter
        mainActivity.imgSearchText.setOnClickListener {
            val search = mainActivity.edtSearch.text.trim()
            searchProductList(search.toString())
        }
        mainActivity.rlFilter.setOnClickListener {
            PopupUtils.fillterDialog(
                selectedShelfStr,
                mostOrderedProductBl,
                selPriceRangeMin,
                selPriceRangeMax,
                selPriceMarginMin,
                selPriceMarginMax,
                productTypeList,
                companyList,
                requireActivity(), {
                    selectedShelfStr = it.shelfLife!!
                    selectedShelfStrUnit = it.shelfLifeUnit!!
                    selectedProductTypeStr= it.productType!!
                    selectedCompanyStr= it.company!!
                    mostOrderedProductBl= it.mostOrderedProduct!!
                    selPriceRangeMin= it.priceRangeMin!!
                    selPriceRangeMax= it.priceRangeMax!!
                    selPriceMarginMin= it.priceMarginMin!!
                    selPriceMarginMax= it.priceMarginMax!!
                    Log.v(TAG,"Filter  Values : shelfLife ${it.shelfLife},priceMarginMin${it.priceMarginMin}" )
                    apiCallingForProductList(selectedShelfStr,selectedShelfStrUnit,mostOrderedProductBl,selectedProductTypeStr,selectedCompanyStr,selPriceRangeMin,selPriceRangeMax,selPriceMarginMin,selPriceMarginMax)
            })
        }
        apiCallingForProductList(selectedShelfStr,selectedShelfStrUnit,mostOrderedProductBl,selectedProductTypeStr,selectedCompanyStr,selPriceRangeMin,selPriceRangeMax,selPriceMarginMin,selPriceMarginMax)
    }

    fun apiCallingForProductList(selectedShelf:String,
                                 shelfLifeUnit:String,
                                 mostOrderedProduct:Boolean,
                                 typeId:ArrayList<ProductType>,
                                 companyId:ArrayList<Company>,
                                 priceMinRange:String,priceMaxRange:String,priceMinMargin:String,priceMaxMargin:String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            var type_id_array = ""
            var company_id_array = ""
            typeId.forEachIndexed { index, productType ->
                if(productType.is_selected){
                    type_id_array = type_id_array+","+productType.id.toString()
                }
            }

            companyId.forEachIndexed { index, productType ->
                if(productType.is_selected){
                    company_id_array = company_id_array+","+productType.id.toString()
                }
            }
            type_id_array = type_id_array.replaceFirst(",","")
            company_id_array = company_id_array.replaceFirst(",","")
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shelf_life"] = selectedShelf
            params["shelf_life_unit"] = shelfLifeUnit
            params["most_ordered_product"] = if(mostOrderedProduct)"1" else "0"
            params["type_id"] = type_id_array
            params["company_id"] = company_id_array
            params["price_min_range"] = priceMinRange
            params["price_max_range"] = priceMaxRange
            params["price_min_margin"] = priceMinMargin
            params["price_max_margin"] = priceMaxMargin
            try {
                val responseOfSales = apiCallingRequest.apiCallingForProducts(params)
                listOfItem.clear()
                listOfItem = responseOfSales.products as ArrayList<Products>
                imagePath = responseOfSales.image_path
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if(listOfItem.isNullOrEmpty()){
                        rvBrowse.visibility = View.GONE
                        txtNoFound.visibility = View.VISIBLE
                    }else{
                        txtNoFound.visibility = View.GONE
                        rvBrowse.visibility = View.VISIBLE
                    }
                    browseAdapter.setData(listOfItem,imagePath, {
                        val product = listOfItem.get(it)
                        apiCallingForAddCart(product)
                    })
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    fun searchProductList(searchText:String) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["search"] = searchText
            try {
                val responseOfSales = apiCallingRequest.apiCallingForProducts(params)
                listOfItem.clear()
                listOfItem = responseOfSales.products as ArrayList<Products>
                imagePath = responseOfSales.image_path
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    browseAdapter.setData(listOfItem, imagePath,{
                        val product = listOfItem.get(it)
                        apiCallingForAddCart(product)
                    })
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    fun apiCallingForAddCart(products: Products) {
        pd.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["user_id"] = SessionData.getInstance(requireContext()).getUserId()!!
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            params["shop_id"] = SessionData.getInstance(requireContext()).getSelectedShopId()!!
            params["product_id"] = "" + products.id
            params["distributor_id"] = products.distributor_id
            params["quantity"] = products.min_qty
            Log.e("params", "" + params)
            try {
                val addCartResponse = apiCallingRequest.addCart(params)
                withContext(Dispatchers.Main) {
                    pd.cancel()
                    if (addCartResponse != null) {
                        Toast.makeText(requireContext(), "add successfully", Toast.LENGTH_SHORT)
                            .show()
                        mainActivity.navController.navigate(R.id.itemInCartFragment)
                    } else {
                        Toast.makeText(requireContext(), "fail response", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (apiEx: IOException) {
                withContext(Dispatchers.Main) {
                    pd.cancel()
                }
            }
        }
    }

    public fun getCompanyList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.getCompanyList(params)
                withContext(Dispatchers.Main) {
                    companyList = responseOfCity.companies as java.util.ArrayList<Company>
                    Log.v("@@@"," companyList.size ${companyList.size}")
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                }
            }
        }
    }

    public fun getProductTypeList() {
        lifecycleScope.launch(Dispatchers.IO) {
            val apiCallingRequest = ApiCallingRequest()
            val params = HashMap<String, String>()
            params["api_token"] = SessionData.getInstance(requireContext()).getToken()!!
            try {
                val responseOfCity = apiCallingRequest.getTypeList(params)
                withContext(Dispatchers.Main) {
                    productTypeList = responseOfCity.types as java.util.ArrayList<ProductType>
                    Log.v("@@@"," productTypeList.size ${productTypeList.size}")
                }
            } catch (apiEx: ApiException) {
                apiEx.printStackTrace()
                withContext(Dispatchers.Main) {
                }
            }
        }
    }

}