package com.example.languageapp.feature_app.presentation.Animals

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.common.CustomTextField
import com.example.languageapp.feature_app.presentation.ui.theme._5BA890
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaBold
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium
import com.example.languageapp.feature_app.presentation.ui.theme.onError

@Composable
fun AnimalsScreen(
    navController: NavController,
    viewModel: AnimalsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    CustomScaffold(
        text = "Guess the animal",
        icon = ImageVector.vectorResource(R.drawable.custom_back_icon),
        backClick = {
            navController.navigate(Route.MainScreen.route) {
                popUpTo(Route.Animals.route) {
                    inclusive = true
                }
            }
        },
        backgroundColor = if (state.isRightAnswer == null) {
            MaterialTheme.colorScheme.primary
        } else if (state.isRightAnswer) {
            _5BA890
        } else {
            onError
        }
    ) {
        Spacer(Modifier.height(15.dp))

        if (state.isRightAnswer == null) {
            AnimatedVisibility(
                visible = state.animal != null,
                enter = fadeIn(tween(500)),
                exit = fadeOut(tween(500))
            ) {
                AsyncImage(
                    model = state.animal!!.animalImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(15.dp))
            Text(
                text = "Write who is on image",
                fontFamily = fontFredokaMedium,
                fontWeight = FontWeight(400),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(10.dp))
            CustomTextField(
                value = state.userAnswer,
                onValueChange = { viewModel.onEvent(AnimalsEvent.EnterAnswer(it)) },
                hint = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
            )
        } else {
            Image(
                painter = painterResource(if (state.isRightAnswer) {
                    R.drawable.right_answer_icon
                } else {
                    R.drawable.wrong_answer_icon
                }),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = if (state.isRightAnswer) {
                    "Holy Molly! That is Right!"
                }else{
                    "Eh? Wrong answer :(\n" +
                            "That is: ${state.animal?.animalName}"
                },
                fontFamily = fontFredokaBold,
                fontWeight = FontWeight(500),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(
            Modifier.height(
                if (state.isRightAnswer != null && state.isRightAnswer) {
                    40.dp
                } else {
                    20.dp
                }
            )
        )
        CustomButton(
            text = if (state.isRightAnswer != null) {
                "Next"
            } else {
                "Check"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)
        ) {
            viewModel.onEvent(AnimalsEvent.CheckClick)
        }
        Spacer(Modifier.height(10.dp))
        if (state.isRightAnswer != null && !state.isRightAnswer){
            CustomButton(
                text = "Try again",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(horizontal = 25.dp)
            ) {
                viewModel.onEvent(AnimalsEvent.TryAgainClick)
            }
        }
    }
}