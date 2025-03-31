package com.example.languageapp.feature_app.domain.model

interface UserDataModel{
    val id: Int
    val userID: String
    val firstName: String
    val lastName: String
    val avatar: String
}

interface UserDataConfig {
    val id: Int?
    val userID: String
    val language: String
    val isSystemInDarkTheme: Boolean
}