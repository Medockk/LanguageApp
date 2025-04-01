package com.example.languageapp.feature_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.languageapp.feature_app.domain.model.UserDataConfig
import com.example.languageapp.feature_app.domain.model.UserDataModel
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
@Entity
data class UserDataModelEntity(
    @PrimaryKey override val id: Int = 0,
    @ColumnInfo(defaultValue = "") override val userID: String,
    @ColumnInfo(defaultValue = "") override val firstName: String,
    @ColumnInfo(defaultValue = "") override val lastName: String,
    @ColumnInfo(defaultValue = "") override val avatar: String
) : UserDataModel

@Entity
data class UserDataConfigImpl(
    @PrimaryKey override val id: Int = 0,
    @ColumnInfo(defaultValue = "") override val userID: String = "",
    @ColumnInfo(defaultValue = "") override val language: String = Locale.getDefault().language,
    @ColumnInfo(defaultValue = "") override val isSystemInDarkTheme: Boolean,
) : UserDataConfig