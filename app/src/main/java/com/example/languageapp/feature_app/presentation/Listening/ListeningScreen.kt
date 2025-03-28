package com.example.languageapp.feature_app.presentation.Listening

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.common.CustomTextField
import com.example.languageapp.feature_app.presentation.ui.theme._00B5AE
import com.example.languageapp.feature_app.presentation.ui.theme._5BA890
import com.example.languageapp.feature_app.presentation.ui.theme._EF5DA8
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaRegular

@Composable
fun ListeningScreen(
    navController: NavController,
    viewModel: ListeningViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    CustomScaffold(
        text = "Listening",
        icon = ImageVector.vectorResource(R.drawable.custom_back_icon),
        backClick = {
            navController.navigate(Route.MainScreen.route) {
                popUpTo(Route.Listening.route) {
                    inclusive = true
                }
            }
        }
    ) {
        Spacer(Modifier.height(30.dp))

        Text(
            text = state.word?.word ?: "",
            fontFamily = fontFredokaMedium,
            fontWeight = FontWeight(500),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = state.word?.transcription ?: "",
            fontFamily = fontFredokaMedium,
            fontWeight = FontWeight(400),
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(Modifier.height(70.dp))
        Text(
            text = stringResource(R.string.please_press_button_and_say_this_word_our_service_will_check_your_pronunciation),
            fontFamily = fontFredokaMedium,
            fontWeight = FontWeight(500),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 25.dp)
                .align(Alignment.Start),
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(8.dp))
        if (state.isRightAnswer == null) {
            CustomButton(
                text = stringResource(R.string.check_my_speech),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp)
            ) {
                viewModel.onEvent(ListeningEvent.CheckMySpeechClick)
            }
        } else {
            Text(
                text = "Your result",
                fontFamily = fontFredokaRegular,
                fontWeight = FontWeight(400),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))

            CustomTextField(
                value = state.userAnswer,
                onValueChange = {
                    viewModel.onEvent(ListeningEvent.EnterUserAnswer(it))
                },
                hint = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                enabled = false,
                textColor = if (state.isRightAnswer) _5BA890 else _EF5DA8
            )
            Spacer(Modifier.height(10.dp))

            if (state.isRightAnswer){
                CustomButton(
                    text = "Yay! Go next",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 25.dp)
                ) {
                    viewModel.onEvent(ListeningEvent.NextClick)
                }
            }

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(160.dp)
                    .background(
                        if (state.isListening) _00B5AE else _EF5DA8,
                        RoundedCornerShape(50.dp)
                    )
                    .clickable {
                        viewModel.onEvent(ListeningEvent.ChangeListeningClick)
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.microphone_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }
    }
}