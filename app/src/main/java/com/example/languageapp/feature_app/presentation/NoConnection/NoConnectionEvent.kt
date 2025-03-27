package com.example.languageapp.feature_app.presentation.NoConnection

sealed class NoConnectionEvent {

    data object CheckNetworkConnection : NoConnectionEvent()
}