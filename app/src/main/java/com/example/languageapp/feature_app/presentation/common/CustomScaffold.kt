@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.languageapp.feature_app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomScaffold(
    text: String,
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textAlign: CustomScaffoldTextAlign = CustomScaffoldTextAlign.START,
    textSize: TextUnit = 22.sp,
    icon: ImageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
    showBackIcon: Boolean = true,
    backClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0,0,0,0),
        containerColor = MaterialTheme.colorScheme.background,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(it),
                content = content,
                horizontalAlignment = Alignment.CenterHorizontally
            )
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                ),
                title = {
                    Row(
                        modifier = modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (showBackIcon){
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clickable { backClick() }
                            )
                            Spacer(Modifier.width(20.dp))
                        }

                        if (textAlign == CustomScaffoldTextAlign.CENTER){
                            Spacer(Modifier.weight(1.5f))
                        }
                        Text(
                            text = text,
                            style = MaterialTheme.typography.titleMedium
                                .copy(
                                    fontSize = textSize
                                )
                        )
                        if (textAlign == CustomScaffoldTextAlign.CENTER){
                            Spacer(Modifier.weight(2f))
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun CustomScaffoldWithLargeTopAppBar(
    topAppBarContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth(),
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0,0,0,0),
        containerColor = MaterialTheme.colorScheme.background,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(it),
                content = content,
                horizontalAlignment = Alignment.CenterHorizontally
            )
        },
        topBar = {
            Column(
                modifier = modifier
                    .background(backgroundColor),
                content = topAppBarContent
            )
        }
    )
}

enum class CustomScaffoldTextAlign{
    START,
    CENTER
}