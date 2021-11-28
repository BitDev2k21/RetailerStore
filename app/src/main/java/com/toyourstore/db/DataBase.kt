package de.footprinttech.wms.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.toyourstore.model.User


@Database(
        entities = arrayOf(
                User::class
         ),
        version = 2,
        exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun dao(): DatabaseDao

    companion object {
        @Volatile
        private var instance: DataBase? = null

        operator fun invoke(context: Context): DataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, "store-database.db")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}