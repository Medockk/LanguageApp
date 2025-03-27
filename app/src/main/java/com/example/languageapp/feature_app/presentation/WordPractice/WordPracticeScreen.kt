package com.example.languageapp.feature_app.presentation.WordPractice

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.WordPractice.compoments.CustomWordCard
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaBold
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaRegular

@Composable
fun WordPracticeScreen(
    navController: NavController,
    viewModel: WordPracticeViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    CustomScaffold(
        text = "Word practice",
        icon = ImageVector.vectorResource(R.drawable.custom_back_icon),
        backClick = {
            navController.navigate(Route.MainScreen.route){
                popUpTo(Route.WordPractice.route){
                    inclusive = true
                }
            }
        }
    ) {
        Spacer(Modifier.height(35.dp))
        Text(
            text = state.word?.word ?: "",
            fontFamily = fontFredokaBold,
            fontWeight = FontWeight(600),
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = state.word?.transcription ?: "",
            fontFamily = fontFredokaRegular,
            fontWeight = FontWeight(400),
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(Modifier.height(35.dp))

        if (state.word != null){
            repeat(state.word.wrongAnswer.size){
                CustomWordCard(
                    word = state.word.wrongAnswer[it],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    isSelected = state.userAnswer.isNotEmpty() && state.word.wrongAnswer[it] == state.userAnswer,
                    isRightAnswer = state.word.rightAnswer == state.userAnswer && state.isUserCheckingAnswer
                ) {  word ->
                    viewModel.onEvent(WordPracticeEvent.SetAnswer(word))
                }
                Spacer(Modifier.height(10.dp))
            }
        }

        Spacer(Modifier.weight(1f))

        CustomButton(
            text = "Check",
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 25.dp)
        ) {
            viewModel.onEvent(WordPracticeEvent.CheckClick)
        }
        Spacer(Modifier.height(30.dp))
    }
}