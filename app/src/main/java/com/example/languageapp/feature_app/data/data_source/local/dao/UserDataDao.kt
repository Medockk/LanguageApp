package com.example.languageapp.feature_app.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.languageapp.feature_app.data.model.UserDataModelEntity

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertData(userDataModelEntity: UserDataModelEntity)

    @Query("SELECT * FROM UserDataModelEntity WHERE userID =:userId")
    fun getUserData(userId: String) : UserDataModelEntity

    @Query("DELETE FROM UserDataModelEntity")
    fun clearData()
}