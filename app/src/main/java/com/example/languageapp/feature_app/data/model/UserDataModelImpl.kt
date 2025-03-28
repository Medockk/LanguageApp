package com.example.languageapp.feature_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.languageapp.feature_app.domain.model.UserDataModel
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class UserDataModelEntity(
    @PrimaryKey(true)  override val id: Int,
    @ColumnInfo(defaultValue = "") override val userID: String,
    @ColumnInfo(defaultValue = "") override val firstName: String,
    @ColumnInfo(defaultValue = "") override val lastName: String,
    @ColumnInfo(defaultValue = "") override val avatar: String
) : UserDataModel