package com.example.languageapp.feature_app.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryDarkColor,

    secondary = secondaryColor,
    onSecondary = onSecondaryColor,

    tertiaryContainer = _FFFFFF4D,
    onTertiary = onTertiaryDarkColor,

    errorContainer = errorContainerDark,
    onError = onError,

    background = backgroundDark,
    outline = onTertiaryDarkColor,

    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    onPrimary = onPrimaryLightColor,

    secondary = secondaryColor,
    onSecondary = onSecondaryColor,

    tertiaryContainer = _080E1E33,
    onTertiary = onTertiaryLightColor,

    errorContainer = errorContainerLight,
    onError = onError,

    background = backgroundLight,
    outline = backgroundDark,

    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun LanguageAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}