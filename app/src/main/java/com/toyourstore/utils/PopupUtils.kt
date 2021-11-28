package com.toyourstore.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.toyourstore.R
import com.toyourstore.api.ApiCallingRequest
import com.toyourstore.model.*
import com.toyourstore.model.businessline.Businessline
import com.toyourstore.model.distributor.BusinesslineDis
import com.toyourstore.model.products.Company
import com.toyourstore.model.products.ProductRequest
import com.toyourstore.model.products.ProductType
import com.toyourstore.preference.SessionData
import com.toyourstore.ui.MainActivity
import com.toyourstore.ui.adapter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


object PopupUtils {


    fun alertMessage(context: Context, msg: String) {
        val dialog = MaterialAlertDialogBuilder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton(
                        "Ok", null
                )
                .show()
    }

    fun alertMessageWithCallBack(context: Context, msg: String, callBack: (value: String) -> Unit) {
        val dialog = MaterialAlertDialogBuilder(context)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(msg)
                .setPositiveButton(
                        "Ok", object : DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        callBack.invoke("ok")
                    }
                }
                ).setCancelable(false)
                .show()
    }


    fun confirmationDailg(context: Context, message: String, callBack: (value: String) -> Unit) {
        val alertDialog: AlertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(context.getString(R.string.app_name))
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                { dialog, which ->
                    dialog.dismiss()
                    callBack.invoke("Yes")
                })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                { dialog, which -> dialog.dismiss() })
        alertDialog.show()
    }

    fun multiChoiceDailog(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<BusinessLineModel>? = null,
            callBack: (value: String) -> Unit
    ) {
        lateinit var multiSelectionAdapter: MultiSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = MultiSelectionAdapter({
            val businessLineModel = businessLineModels?.get(it)
            if (businessLineModel?.isSelected!!) {
                businessLineModel.isSelected = false
            } else {
                businessLineModel.isSelected = true
            }
            businessLineModels?.set(it, businessLineModel)
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayList = ArrayList<String>()
            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    newArrayList.add(businesmodel.name)
                }
            }
            val stringValue = TextUtils.join(",", newArrayList)
            callBack.invoke(stringValue)
            dialog.cancel()


        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }


    fun multiChoiceDailogForBusinessLine(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<Businessline>? = null,
            callBack: (name: String, id: String) -> Unit
    ) {
        lateinit var multiSelectionAdapter: BusinessLineSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = BusinessLineSelectionAdapter({
            val businessLineModel = businessLineModels?.get(it)
            if (businessLineModel?.isSelected!!) {
                businessLineModel.isSelected = false
            } else {
                businessLineModel.isSelected = true
            }
            businessLineModels?.set(it, businessLineModel)
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayListOfName = ArrayList<String>()
            val newArrayListOfId = ArrayList<String>()

            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    newArrayListOfName.add(businesmodel.name)
                    newArrayListOfId.add("" + businesmodel.id)
                }
            }
            val stringValueName = TextUtils.join(",", newArrayListOfName)
            val stringValueId = TextUtils.join(",", newArrayListOfName)
            callBack.invoke(stringValueName, stringValueId)
            dialog.cancel()
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun cartItemDialog(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<GetLocation.Location>? = null,
            callBack: (name: String, id: String) -> Unit
    ) {
        lateinit var multiSelectionAdapter: LocationSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog_cart)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = LocationSelectionAdapter({
            val businessLineModel = businessLineModels?.get(it)
            if (businessLineModel?.isSelected!!) {
                businessLineModel.isSelected = false
            } else {
                businessLineModel.isSelected = true
            }
            businessLineModels?.set(it, businessLineModel)
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayListOfName = ArrayList<String>()
            val newArrayListOfId = ArrayList<String>()

            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    newArrayListOfName.add(businesmodel.name!!)
                    newArrayListOfId.add("" + businesmodel.id)
                }
            }
            val stringValueName = TextUtils.join(",", newArrayListOfName)
            val stringValueId = TextUtils.join(",", newArrayListOfName)
            callBack.invoke(stringValueName, stringValueId)
            dialog.cancel()
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailog(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<BusinessLineModel>? = null,
            callBack: (value: String) -> Unit
    ) {
        lateinit var multiSelectionAdapter: MultiSelectionAdapter
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        multiSelectionAdapter = MultiSelectionAdapter({
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel.isSelected = true
                } else {
                    businessLineModel.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            multiSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = multiSelectionAdapter
        multiSelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            val newArrayList = ArrayList<String>()
            for (businesmodel in businessLineModels) {
                if (businesmodel.isSelected) {
                    newArrayList.add(businesmodel.name)
                }
            }
            val stringValue = TextUtils.join(",", newArrayList)
            callBack.invoke(stringValue)
            dialog.cancel()


        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailogForCompanyList(
        activity: Activity,
        title: String,
        selectedShelfLife: List<Company>,
        callBack: (value: List<Company>) -> Unit
    ) {
        lateinit var stateSelectionAdapter: CompanyListAdapter
        var stateResponse: List<Company> = selectedShelfLife
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        stateSelectionAdapter = CompanyListAdapter({
            stateResponse.forEachIndexed{index, company ->
                if(it.id == company.id){
                    company.is_selected = it.is_selected
                }
            }
            stateSelectionAdapter.setData(stateResponse!! as ArrayList<Company>)
        })
        rvMultiSelect.adapter = stateSelectionAdapter
        stateSelectionAdapter.setData(stateResponse!! as ArrayList<Company>)
        txtOK.setOnClickListener {
            if (stateResponse != null) {
                callBack.invoke(stateResponse!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailogForProductTypeList(
        activity: Activity,
        title: String,
        selectedShelfLife: List<ProductType>,
        callBack: (value: List<ProductType>) -> Unit
    ) {
        lateinit var stateSelectionAdapter: ProductTypeAdapter
        var stateResponse: List<ProductType> = selectedShelfLife
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        stateSelectionAdapter = ProductTypeAdapter({
            stateResponse.forEachIndexed{index, company ->
                if(it.id == company.id){
                    company.is_selected = it.is_selected
                }
            }
            stateSelectionAdapter.setData(stateResponse as ArrayList<ProductType>)
        })
        rvMultiSelect.adapter = stateSelectionAdapter
        stateSelectionAdapter.setData(stateResponse!! as ArrayList<ProductType>)
        txtOK.setOnClickListener {
            if (stateResponse != null) {
                callBack.invoke(stateResponse!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    lateinit var adapter:ShelfLifeIntegerAdapter
    fun singleChoiceDailogForShelfLife(
        activity: Activity,
        title: String,
        businessLineModels: ArrayList<ShelfLife>? = null,
        selectedShelfLife: ShelfLife? = null,
        callBack: (value: ShelfLife) -> Unit
    ) {

        lateinit var stateSelectionAdapter: ShelfLifeAdapter

        var stateResponse: ShelfLife? = selectedShelfLife
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view_multi_value)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        val et_value_shelf_life = dialog.findViewById<TextInputEditText>(R.id.et_value_shelf_life)
        txtTitle.text = title
        if(stateResponse!=null && !stateResponse.value.isNullOrBlank()){
            et_value_shelf_life.setText(stateResponse.value.toString())
        }
        stateSelectionAdapter = ShelfLifeAdapter({
            stateResponse = it
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (businessLineModel.name.equals(it.name) ) {
                    businessLineModel?.isSelected = !(businessLineModel?.isSelected!!)
                }
                businessLineModels.set(i, businessLineModel)
            }
            stateSelectionAdapter.setData(businessLineModels!!,stateResponse!!)
        },selectedShelfLife!!)
        rvMultiSelect.adapter = stateSelectionAdapter
        stateSelectionAdapter.setData(businessLineModels!!,stateResponse!!)

        txtOK.setOnClickListener {
            if(et_value_shelf_life.text.isNullOrBlank()){
                Toast.makeText(activity,"Please Enter Value",Toast.LENGTH_SHORT).show()
            }else{
                if (stateResponse != null) {
                    stateResponse!!.value = et_value_shelf_life.text.toString()
                    callBack.invoke(stateResponse!!)
                    dialog.cancel()
                }
            }
        }
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailogForState(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<StateResponse.State>? = null,
            selectedState: StateResponse.State? = null,
            callBack: (value: StateResponse.State) -> Unit
    ) {
        lateinit var stateSelectionAdapter: StateSelectionAdapter
        var stateResponse: StateResponse.State? = selectedState
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        stateSelectionAdapter = StateSelectionAdapter({
            stateResponse = businessLineModels?.get(it)
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel?.isSelected = true
                } else {
                    businessLineModel?.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            stateSelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = stateSelectionAdapter
        if (businessLineModels != null) {
            stateSelectionAdapter.setData(businessLineModels)
        }
        txtOK.setOnClickListener {
            if (stateResponse != null) {
                callBack.invoke(stateResponse!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    fun singleChoiceDailogForCity(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<CityResponse.City>? = null,
            city: CityResponse.City? = null,
            callBack: (value: CityResponse.City) -> Unit
    ) {
        lateinit var citySelectionAdapter: CitySelectionAdapter
        var selectedCity: CityResponse.City? = city
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        citySelectionAdapter = CitySelectionAdapter({
            selectedCity = businessLineModels?.get(it)
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel.isSelected = true
                } else {
                    businessLineModel.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            citySelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = citySelectionAdapter
        citySelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            if (selectedCity != null) {
                callBack.invoke(selectedCity!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

    var selectedShelf:ShelfLife? = null
    var selectedProductType = ArrayList<ProductType>()
    var selectedCompany = ArrayList<Company>()
    var priceRangeMin = "0"
    var priceRangeMax = "0"
    var priceMarginMin = "0"
    var priceMarginMax = "0"
    lateinit var  selectedFilter:ProductRequest
    var shelfLife = ArrayList<ShelfLife>()
    var mostOrderedProduct = false
    var selectedCompanyText = ""
    var selectedTypeText = ""
    fun fillterDialog(
            selectedShelfStr:String,
            mostOrderedProductBl:Boolean,
            selPriceRangeMin:String,
            selPriceRangeMax:String,
            selPriceMarginMin:String,
            selPriceMarginMax:String,
            productTypeList: List<ProductType>,
            companyList:List<Company>,
            activity: Activity,
            callBack: (ProductRequest) -> Unit
    ) {
        shelfLife = getShelfLife()

        mostOrderedProduct = mostOrderedProductBl

        priceRangeMin = selPriceRangeMin
        priceRangeMax = selPriceRangeMax
        priceMarginMin = selPriceMarginMin
        priceMarginMax = selPriceMarginMax

        shelfLife.forEachIndexed { index, value ->
            if(value.name.equals(selectedShelfStr)){
                value.isSelected = true
                selectedShelf = value
                shelfLife.set(index,value)
            }
        }

        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_filter)
        val txtApplyFilter = dialog.findViewById<TextView>(R.id.txtApplyFilter)
        val txtClearAll = dialog.findViewById<TextView>(R.id.txtClearAll)
        val imgCross = dialog.findViewById<ImageView>(R.id.imgCross)
        val imgOrderedProduct = dialog.findViewById<ImageView>(R.id.iv_most_ordered_product)
        var  imgShelfLife = dialog.findViewById<TextView>(R.id.iv_shelf_life)
        var  iv_product_type = dialog.findViewById<TextView>(R.id.iv_product_type)
        var  iv_company = dialog.findViewById<TextView>(R.id.iv_company)
        var priceRangemin = dialog.findViewById<TextView>(R.id.txtMin)
        var priceRangemax = dialog.findViewById<TextView>(R.id.txtMax)
        var priceMinPriceMargin = dialog.findViewById<TextView>(R.id.txtMinPriceMargin)
        var priceMaxPriceMargin = dialog.findViewById<TextView>(R.id.txtMaxPriceMargin)

        if(selectedShelf!=null){
            imgShelfLife.setText(selectedShelf?.name!!.toString())
        }else{
            selectedShelf = getShelfLife().first()
        }
        if(selectedProductType==null){
            selectedProductType = productTypeList as ArrayList<ProductType>
        }else{
            selectedProductType.clear()
            selectedProductType.addAll(productTypeList)
        }
        selectedCompanyText = ""
        selectedTypeText = ""
        selectedProductType.forEachIndexed{ index, company ->
            if(company.is_selected){
                selectedTypeText = selectedTypeText+","+company.name
            }
        }
        selectedTypeText = selectedTypeText.replaceFirst(",","")
        iv_product_type.setText(selectedTypeText)
        if(selectedCompany == null){
            selectedCompany = companyList as ArrayList<Company>
        }else{
            selectedCompany.clear()
            selectedCompany.addAll(companyList)
        }
        selectedCompany.forEachIndexed{ index, company ->
           if(company.is_selected){
               selectedCompanyText = selectedCompanyText+","+company.name
            }
        }
        selectedCompanyText = selectedCompanyText.replaceFirst(",","")
        iv_company.setText(selectedCompanyText)

        if(priceRangeMin!!.isNotEmpty() || priceRangeMin!=null){
            priceRangemin.setText(priceRangeMin!!.toString())
        }
        if(priceRangeMax!!.isNotEmpty() || priceRangeMax!=null){
            priceRangemax.setText(priceRangeMax!!.toString())
        }
        if(priceMarginMin!!.isNotEmpty() || priceMarginMin!=null){
            priceMinPriceMargin.setText(priceMarginMin!!.toString())
        }
        if(priceMarginMax!!.isNotEmpty() || priceMarginMax!=null){
            priceMaxPriceMargin.setText(priceMarginMax!!.toString())
        }

        imgOrderedProduct.setOnClickListener {
            mostOrderedProduct = !mostOrderedProduct
            if(mostOrderedProduct){
                imgOrderedProduct.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.icon_check))
            }else{
                imgOrderedProduct.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.icon_un_check))
            }
        }
        if(mostOrderedProduct){
            imgOrderedProduct.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.icon_check))
        }else{
            imgOrderedProduct.setImageDrawable(ContextCompat.getDrawable(activity,R.drawable.icon_un_check))
        }
        imgShelfLife.setOnClickListener {
            singleChoiceDailogForShelfLife(
                activity,
                "Select Shelf Life ",
                shelfLife,
                selectedShelf,
                {
                    selectedShelf = it
                    imgShelfLife.text = if(selectedShelf!!.isSelected!!) selectedShelf?.value+" "+selectedShelf?.name else ""
                })
        }

        iv_company.setOnClickListener {
            singleChoiceDailogForCompanyList(
                activity,
                "Select Company  ",
                selectedCompany
            ) {
                selectedCompany = it as ArrayList<Company>
                selectedCompanyText = ""
                selectedCompany.forEachIndexed{ index, company ->
                    if(company.is_selected){
                        selectedCompanyText = selectedCompanyText+","+company.name
                    }
                }
                selectedCompanyText = selectedCompanyText.replaceFirst(",","")
                iv_company.setText(selectedCompanyText)
            }
        }

        iv_product_type.setOnClickListener {
            singleChoiceDailogForProductTypeList(
                activity,
                "Select Product type  ",
                selectedProductType
            ){
                selectedProductType = it as ArrayList<ProductType>
                selectedTypeText = ""
                selectedProductType.forEachIndexed{ index, company ->
                    if(company.is_selected){
                        selectedTypeText = selectedTypeText+","+company.name
                    }
                }
                selectedTypeText = selectedTypeText.replaceFirst(",","")
                iv_product_type.setText(selectedTypeText)
            }
        }


        imgCross.setOnClickListener {
            dialog.cancel()
         }

        txtApplyFilter.setOnClickListener {
            dialog.cancel()
            priceRangeMin = priceRangemin.text.toString()
            priceRangeMax = priceRangemax.text.toString()
            priceMarginMin = priceMinPriceMargin.text.toString()
            priceMarginMax = priceMaxPriceMargin.text.toString()
            if(!this::selectedFilter.isInitialized){
                selectedFilter = ProductRequest(selectedShelf!!.value,selectedShelf!!.name,selectedProductType,selectedCompany,
                    priceRangeMin, priceRangeMax, priceMarginMin,priceMarginMax,mostOrderedProduct)
            }else{
                selectedFilter = ProductRequest(selectedShelf!!.value,selectedShelf!!.name,selectedProductType,selectedCompany,
                    priceRangeMin, priceRangeMax, priceMarginMin,priceMarginMax,mostOrderedProduct)
            }
            callBack.invoke(selectedFilter)
        }

        txtClearAll.setOnClickListener {
            dialog.cancel()
            selectedProductType.forEach{productType -> productType.is_selected = false }
            selectedCompany.forEach{productType -> productType.is_selected = false }
            if(!this::selectedFilter.isInitialized){
                selectedFilter = ProductRequest("","",selectedProductType,selectedCompany,
                    "", "", "","",false)
            }else{
                selectedFilter = ProductRequest("","",selectedProductType,selectedCompany,
                    "", "", "","",false)
            }
            selectedProductType.forEach{productType -> productType.is_selected = false }
            selectedCompany.forEach{productType -> productType.is_selected = false }
            selectedShelf = null
            callBack.invoke(selectedFilter)
        }

        dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.show()
    }

    @JvmName("getShelfLife1")
    fun getShelfLife():ArrayList<ShelfLife>{
        var shelfLife = ArrayList<ShelfLife>()
        shelfLife.add(ShelfLife("Days","1",false))
        shelfLife.add(ShelfLife("Months","1",false))
        shelfLife.add(ShelfLife("Years","1",false))
        return  shelfLife
    }

    fun singleChoiceDailogForLocation(
            activity: Activity,
            title: String,
            businessLineModels: ArrayList<GetLocation.Location>? = null,
            location: GetLocation.Location? = null,
            callBack: (value: GetLocation.Location) -> Unit) {
        lateinit var citySelectionAdapter: LocationSelectionAdapter
        var selectedLocation: GetLocation.Location? = location
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_recyler_view)
        val rvMultiSelect = dialog.findViewById<RecyclerView>(R.id.rvMultiSelect)
        val txtOK = dialog.findViewById<TextView>(R.id.txtOK)
        val txtTitle = dialog.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = title
        citySelectionAdapter = LocationSelectionAdapter({
            selectedLocation = businessLineModels?.get(it)
            for (i in 0..businessLineModels?.size!! - 1) {
                val businessLineModel = businessLineModels.get(i)
                if (i == it) {
                    businessLineModel.isSelected = true
                } else {
                    businessLineModel.isSelected = false
                }
                businessLineModels.set(i, businessLineModel)
            }
            citySelectionAdapter.notifyDataSetChanged()
        })
        rvMultiSelect.adapter = citySelectionAdapter
        citySelectionAdapter.setData(businessLineModels!!)
        txtOK.setOnClickListener {
            if (selectedLocation != null) {
                callBack.invoke(selectedLocation!!)
                dialog.cancel()
            }
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }


    fun checkOutDialog(
            activity: Activity,
            total: String,
            totalPrice: String,
            callBack: (String) -> Unit
    ) {
        val dialog = Dialog(activity, android.R.style.Theme_Material_Dialog_Alert)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_check_out)
        val imgCross = dialog.findViewById<ImageView>(R.id.imgCross)
        val txtCheckOut = dialog.findViewById<TextView>(R.id.txtCheckOut)
        val txtTotal = dialog.findViewById<TextView>(R.id.txtTotal)
        val txtTotalPrice = dialog.findViewById<TextView>(R.id.txtTotalPrice)
        txtTotal.setText(total)
        txtTotalPrice.setText("₹ " + totalPrice)

        imgCross.setOnClickListener {
            dialog.cancel()
        }
        txtCheckOut.setOnClickListener {
            dialog.cancel()
            callBack.invoke("")
        }

        imgCross.setOnClickListener {
            dialog.cancel()
        }
        dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

}