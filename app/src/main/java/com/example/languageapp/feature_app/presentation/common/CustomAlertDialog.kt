package com.example.languageapp.feature_app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.languageapp.R

@Composable
fun CustomAlertDialog(
    description: String,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.Error),
    dismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = dismissClick,
        confirmButton = {},
        containerColor = MaterialTheme.colorScheme.errorContainer,
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
                    .copy(MaterialTheme.colorScheme.onError)
            )
        },
        text = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
                    .copy(MaterialTheme.colorScheme.onError)
            )
        },
        icon = {
            Box(Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)){
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    )
}