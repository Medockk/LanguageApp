package com.example.languageapp.feature_app.data.data_source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.languageapp.feature_app.data.data_source.local.dao.UserConfigDao
import com.example.languageapp.feature_app.data.data_source.local.dao.UserDataDao
import com.example.languageapp.feature_app.data.model.UserDataConfigImpl
import com.example.languageapp.feature_app.data.model.UserDataModelEntity

@Database(entities = [UserDataModelEntity::class], version = 1)
abstract class UserDataDatabase : RoomDatabase() {

    abstract val userDao: UserDataDao

    companion object{
        fun createDatabase(context: Context) : UserDataDatabase{
            return Room.databaseBuilder(context, UserDataDatabase::class.java, "userdata.db").build()
        }
    }
}
@Database(entities = [UserDataConfigImpl::class], version = 1)
abstract class UserDataConfigDatabase : RoomDatabase() {

    abstract val userConfig: UserConfigDao

    companion object{
        fun createDatabase(context: Context) : UserDataConfigDatabase{
            return Room.databaseBuilder(context, UserDataConfigDatabase::class.java, "config.db").build()
        }
    }
}