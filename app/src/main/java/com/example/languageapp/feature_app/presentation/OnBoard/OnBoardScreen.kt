package com.example.languageapp.feature_app.presentation.OnBoard

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.feature_app.presentation.OnBoard.components.CustomPagerCircleState
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomAlertDialog
import com.example.languageapp.feature_app.presentation.common.CustomButton

@Composable
fun OnBoardScreen(
    navController: NavController,
    viewModel: OnBoardViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val pagerState = rememberPagerState(state.currentPage) { state.list.size }

    if (state.exception.isNotEmpty()) {
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(OnBoardEvent.ResetException)
        }
    }

    LaunchedEffect(!state.isComplete) {
        if (state.isComplete) {
            navController.navigate(Route.LoginScreen.route)
        }
    }

    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = 25.dp,
                end = 25.dp,
                bottom = (LocalConfiguration.current.screenHeightDp / 20).dp
            ),
        state = pagerState,
        userScrollEnabled = false,
    ) {
        Crossfade(
            targetState = state.currentPage,
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.weight(2f))
                Image(
                    painter = painterResource(state.list[page].image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.65f),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.weight(2f))

                CustomPagerCircleState(
                    state.list.size,
                    currentValue = state.currentPage
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = state.list[page].title,
                    style = MaterialTheme.typography.bodyMedium
                        .copy(MaterialTheme.colorScheme.onPrimary)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.list[page].description,
                    style = MaterialTheme.typography.bodySmall
                        .copy(MaterialTheme.colorScheme.onTertiary)
                )
                Spacer(Modifier.weight(1f))
                CustomButton(
                    text = stringResource(state.list[page].buttonText),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    viewModel.onEvent(OnBoardEvent.NextPage(state.currentPage+1))
                }
                Spacer(Modifier.height(8.dp))
                TextButton(
                    onClick = {
                        viewModel.onEvent(OnBoardEvent.SkipOnBoardClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Text(
                        text = "Skip onboarding",
                        style = MaterialTheme.typography.bodySmall
                            .copy(
                                MaterialTheme.colorScheme.outline
                            )
                    )
                }
            }
        }
    }
}