package com.example.languageapp.feature_app.presentation.NoConnection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaBold

@Composable
fun NoConnectionScreen(
    navController: NavController,
    viewModel: NoConnectionViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    LaunchedEffect(!state.isConnected) {
        if (state.isConnected){
            navController.navigate(Route.LoginScreen.route){
                popUpTo(Route.NoConnectionScreen.route){
                    inclusive = true
                }
            }
        }
    }

    CustomScaffold(
        text = "",
        showBackIcon = false
    ) {
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.no_connection_icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.4f)
        )
        Spacer(Modifier.height(30.dp))
        Text(
            text = "No \n" +
                    "internet connection ",
            fontFamily = fontFredokaBold,
            fontSize = 30.sp,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(Modifier.weight(2f))
        CustomButton(
            text = "Check again",
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)
        ) {
            viewModel.onEvent(NoConnectionEvent.CheckNetworkConnection)
        }
    }
}