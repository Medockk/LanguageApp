package com.example.languageapp.feature_app.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import com.example.languageapp.feature_app.presentation.ui.theme._B6B6B6
import com.example.languageapp.feature_app.presentation.ui.theme.primaryColor

@Composable
fun CustomIndicator(
    showIndicator: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = showIndicator,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SolidColor(_B6B6B6), alpha = 0.3f)
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = modifier,
                color = primaryColor
            )
        }
    }
}