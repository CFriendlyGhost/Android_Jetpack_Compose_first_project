package com.example.appdrawer.settings

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// DAO
@Dao
interface DataDao {
    @Query("SELECT * FROM data_table ORDER BY ID ASC")
    fun getAllData(): List<DataEntity>

    @Query("SELECT * FROM data_table WHERE id = :itemId")
    fun getDataById(itemId: Long): DataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: DataEntity)

    @Query("DELETE FROM data_table")
    fun deleteAllData()

    @Delete
    fun delete(data: DataEntity): Int
}