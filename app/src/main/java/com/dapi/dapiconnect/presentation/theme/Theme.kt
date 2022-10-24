package com.dapi.dapiconnect.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorPalette = AppColors(
    material = darkColors(
        primary = Blue1,
        onPrimary = White1,
        background = White2,
        error = Red1
    ),
    secondaryBackground = White1,
    accountBackground = White3,
    amountCurrencyCode = Grey4,
    primaryText = Black2,
    secondaryText = Grey9,
    tertiaryText = Grey7,
    disabledText = Black1
)

private val LightColorPalette = AppColors(
    material = lightColors(
        primary = Blue1,
        onPrimary = White1,
        background = White2,
        error = Red1
    ),
    secondaryBackground = White1,
    accountBackground = White3,
    amountCurrencyCode = Grey4,
    primaryText = Black2,
    secondaryText = Grey9,
    tertiaryText = Grey7,
    disabledText = Black1
)

private val LocalColors = staticCompositionLocalOf { LightColorPalette }

@Suppress("unused")
val MaterialTheme.appColors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors.material,
        typography = MuliTypography,
        shapes = Shapes,
        content = content
    )

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            typography = MuliTypography,
            shapes = Shapes,
            content = content
        )
    }
}