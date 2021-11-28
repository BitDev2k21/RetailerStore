package com.toyourstore.utils

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {


    fun getCurrentDate():String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var retDate = ""
        try {
            retDate = formatter.format(Date())
        }catch (e: Exception){
            e.printStackTrace()
        }
        return retDate
    }


    fun convertToDate(crateDate: String):String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var retDate = ""
        try {
            var date: Date? = null
            date = formatter.parse(crateDate)
            retDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return retDate
    }

    fun getEndDateOFPayment(createDate: String, creditPeriod: Int):String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var retDate = ""
        var date: Date? = null
        date = formatter.parse(createDate)
        try {
            val c: Calendar = Calendar.getInstance()
            c.setTime(date)
            if (creditPeriod != null) {
                c.add(Calendar.DATE, creditPeriod)
            }
            date = c.getTime()
            retDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return retDate
    }

}