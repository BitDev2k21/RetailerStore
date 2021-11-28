package com.toyourstore.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.toyourstore.utils.Constant

class SessionData(context: Context) {

    var context: Context;
    var prefs: SharedPreferences;
    val MY_PREFS_NAME = "YourStore"
    val editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()

    init {
        this.context = context
        prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    companion object {
        var sessionData: SessionData? = null
        fun getInstance(context: Context): SessionData {
            if (sessionData == null) {
                sessionData = SessionData(context)
            }
            return sessionData as SessionData
        }
    }

    fun saveToken(status: String) {
        editor.putString(Constant.SAVE_TOKEN, status)
        editor.commit()
    }

    fun getToken(): String? {
        return prefs.getString(Constant.SAVE_TOKEN, "")
    }

    fun saveUserId(status: String) {
        editor.putString(Constant.USER_ID, status)
        editor.commit()
    }

    fun getSelectedShopId(): String? {
        return prefs.getString(Constant.SELECTED_SHOP_ID, "")
    }

    fun saveSeletedShopId(id: String) {
        editor.putString(Constant.SELECTED_SHOP_ID, id)
        editor.commit()
    }

    fun getSeletedPos(): Int {
        return prefs.getInt(Constant.SELECTED_SHOP_POS, 0)
    }

    fun saveSeletedPos(pos: Int) {
        editor.putInt(Constant.SELECTED_SHOP_POS, pos)
        editor.commit()
    }


    fun getUserId(): String? {
        return prefs.getString(Constant.USER_ID, "")
    }

    fun saveIsRegister(status: Boolean) {
        editor.putBoolean(Constant.REGISTRE_DISTRIBUTE, status)
        editor.commit()
    }

    fun isRegisterDis(): Boolean {
        return prefs.getBoolean(Constant.REGISTRE_DISTRIBUTE, false)
    }


    fun clearData() {
        editor.clear()
        editor.commit()
        editor.apply()
        editor.putBoolean(Constant.REGISTRE_DISTRIBUTE, true)
        editor.commit()
    }


}


