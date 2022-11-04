package com.dapi.dapiconnect.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

data class AppColors(
    val material: Colors,
    val secondaryBackground: Color,
    val accountBackground: Color,
    val amountCurrencyCode: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val tertiaryText: Color,
    val disabledText: Color,
) {
    val primary: Color get() = material.primary
    val primaryVariant: Color get() = material.primaryVariant
    val secondary: Color get() = material.secondary
    val secondaryVariant: Color get() = material.secondaryVariant
    val background: Color get() = material.background
    val surface: Color get() = material.surface
    val error: Color get() = material.error
    val onPrimary: Color get() = material.onPrimary
    val onSecondary: Color get() = material.onSecondary
    val onBackground: Color get() = material.onBackground
    val onSurface: Color get() = material.onSurface
    val onError: Color get() = material.onError
    val isLight: Boolean get() = material.isLight
}