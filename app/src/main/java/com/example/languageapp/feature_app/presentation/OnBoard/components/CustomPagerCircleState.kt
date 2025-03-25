package com.example.languageapp.feature_app.presentation.OnBoard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.languageapp.feature_app.presentation.ui.theme._F76400

@Composable
fun CustomPagerCircleState(
    count: Int,
    currentValue: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(count) {
            Box(
                Modifier
                    .size(8.dp)
                    .background(
                        if (it == currentValue) _F76400 else MaterialTheme.colorScheme.tertiaryContainer,
                        CircleShape
                    )
            )
            Spacer(Modifier.width(8.dp))
        }
    }
}