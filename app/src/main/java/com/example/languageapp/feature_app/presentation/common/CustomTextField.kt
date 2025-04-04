package com.example.languageapp.feature_app.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.languageapp.R

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isPasswordState: Boolean = true,
    enabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    changeIsPasswordState: () -> Unit = {},
) {
    TextField(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        value = value,
        enabled = enabled,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.displayMedium.copy(
            textColor
        ),
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.displaySmall
            )
        },
        trailingIcon = {
            if (isPassword) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.eye_icon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .clickable { changeIsPasswordState() }
                )
            }
        },
        visualTransformation = if (isPassword && isPasswordState) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    )
}