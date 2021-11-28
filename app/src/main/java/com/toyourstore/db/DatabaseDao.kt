package de.footprinttech.wms.db

import androidx.room.*
import com.toyourstore.model.User

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM table_user  WHERE id=:id")
    suspend fun getUser(id: Long): User

    @Query("SELECT * FROM table_user")
    suspend fun getAllManuallList(): List<User>

}