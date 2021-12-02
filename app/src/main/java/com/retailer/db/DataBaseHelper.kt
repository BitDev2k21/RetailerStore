package de.footprinttech.wms.db

import android.content.Context

object DataBaseHelper {
    fun getDatabaseDao(context: Context): DatabaseDao {
        return DataBase(context).dao()
    }

}