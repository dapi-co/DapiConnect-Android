package com.dapi.dapiconnect.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue1,
    onPrimary = White1,
    secondary = Black2,
    onSecondary = Grey9,
    background = White2,
    error = Red1
)

private val LightColorPalette = lightColors(
    primary = Blue1,
    onPrimary = White1,
    secondary = Black2,
    onSecondary = Grey9,
    background = White2,
    error = Red1
)

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
        colors = colors,
        typography = MuliTypography,
        shapes = Shapes,
        content = content
    )
}