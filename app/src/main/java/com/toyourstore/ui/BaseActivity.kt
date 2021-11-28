package com.toyourstore.ui

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract fun init()


//    open fun <T> jsonToPojo(jsonObject: JsonObject?, response: Class<T>?): T {
//        val json_string: String = Gson().toJson(jsonObject)
//        val obj: T
//        obj = try {
//            Gson().fromJson(json_string, response)
//        } catch (e: Exception) {
//            Gson().fromJson(json_string, BaseResponse::class.java) as T
//        }
//        return obj
//    }


}