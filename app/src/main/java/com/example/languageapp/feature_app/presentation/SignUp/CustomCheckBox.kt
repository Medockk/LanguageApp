package com.example.languageapp.feature_app.presentation.SignUp

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.languageapp.feature_app.presentation.ui.theme._5B7BFEFF

@Composable
fun CustomCheckBox(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = value,
        onCheckedChange = onValueChange,
        modifier = modifier,
        colors = CheckboxDefaults.colors(
            checkmarkColor = Color.White,
            checkedColor = _5B7BFEFF,
        )
    )
}