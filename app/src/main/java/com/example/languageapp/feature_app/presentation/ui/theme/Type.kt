package com.example.languageapp.feature_app.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.languageapp.R

val fontFredokaBold = FontFamily(Font(R.font.fredoka_bold))
val fontFredokaMedium = FontFamily(Font(R.font.fredoka_medium))
val fontFredokaRegular = FontFamily(Font(R.font.fredoka_regular))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFredokaMedium,
        fontWeight = FontWeight(500),
        fontSize = 22.sp,
        textAlign = TextAlign.Center
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFredokaMedium,
        fontWeight = FontWeight(500),
        fontSize = 22.sp,
        textAlign = TextAlign.Center
    ),
    bodySmall = TextStyle(
        fontFamily = fontFredokaRegular,
        fontWeight = FontWeight(400),
        fontSize = 15.sp,
        textAlign = TextAlign.Center
    ),
    titleLarge = TextStyle(
        fontFamily = fontFredokaBold,
        fontWeight = FontWeight(600),
        fontSize = 36.sp,
        textAlign = TextAlign.Center

    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)