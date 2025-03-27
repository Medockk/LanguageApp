package com.example.languageapp.feature_app.presentation.WordPractice.compoments

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.languageapp.feature_app.presentation.ui.theme._5B7BFEFF
import com.example.languageapp.feature_app.presentation.ui.theme._5BA890
import com.example.languageapp.feature_app.presentation.ui.theme._E5E5E5
import com.example.languageapp.feature_app.presentation.ui.theme.fontFredokaMedium
import com.example.languageapp.feature_app.presentation.ui.theme.onPrimaryDarkColor
import com.example.languageapp.feature_app.presentation.ui.theme.onPrimaryLightColor

@Composable
fun CustomWordCard(
    word: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    isRightAnswer: Boolean? = null,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            if (isRightAnswer != null && isRightAnswer) {
                _5BA890
            } else if (isSelected) {
                _5B7BFEFF
            } else {
                _E5E5E5
            }
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onClick(word)
        }
    ) {
        Text(
            text = word,
            fontFamily = fontFredokaMedium,
            fontWeight = FontWeight(500),
            fontSize = 20.sp,
            color = if (isSelected) onPrimaryDarkColor else onPrimaryLightColor,
            modifier = modifier
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}