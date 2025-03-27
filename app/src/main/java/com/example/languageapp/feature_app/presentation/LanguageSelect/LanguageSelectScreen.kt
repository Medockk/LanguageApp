package com.example.languageapp.feature_app.presentation.LanguageSelect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.languageapp.R
import com.example.languageapp.feature_app.presentation.Route
import com.example.languageapp.feature_app.presentation.common.CustomButton
import com.example.languageapp.feature_app.presentation.common.CustomScaffold
import com.example.languageapp.feature_app.presentation.common.CustomScaffoldTextAlign
import com.example.languageapp.feature_app.presentation.ui.theme._F76400
import com.example.languageapp.feature_app.presentation.ui.theme._FFF6EB
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium

@Composable
fun LanguageSelectScreen(
    navController: NavController,
    viewModel: LanguageSelectViewModel = viewModel()
) {
    val state = viewModel.state.value
    val locale = Locale.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(LanguageSelectEvent.SetLocale(locale))
    }

    CustomScaffold(
        text = "Language select",
        textSize = 17.sp,
        textAlign = CustomScaffoldTextAlign.CENTER,
        showBackIcon = false,
    ) {
        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.what_is_your_mother_language),
            fontFamily = fontFredokaMedium,
            fontWeight = FontWeight(500),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 20.dp),
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.height(15.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp)
        ) {
            items(state.languageList.sortedBy { it != state.motherLanguage }){
                Card(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .heightIn(min = 65.dp)
                        .animateItem(),
                    colors = CardDefaults.cardColors(if (it == state.motherLanguage) _F76400 else _FFF6EB),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text(
                        text = it,
                        fontFamily = fontFredokaMedium,
                        fontWeight = FontWeight(500),
                        fontSize = 22.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 15.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        CustomButton(
            text = "Choose",
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
                .fillMaxWidth()
                .height(55.dp)
        ) {
            navController.navigate(Route.MainScreen.route){
                popUpTo(Route.LanguageSelectScreen.route){
                    inclusive = true
                }
            }
        }
    }
}