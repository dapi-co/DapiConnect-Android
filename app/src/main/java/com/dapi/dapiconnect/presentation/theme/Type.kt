package com.dapi.dapiconnect.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import co.dapi.connect.R

val MuliFont = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.mulish_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.mulish_semi_bold,
            weight = FontWeight.SemiBold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.mulish_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        )
    )
)

val MuliTypography = Typography(
    h1 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
    ),
    h2 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    h3 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
    h4 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    h5 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    h6 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = MuliFont,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = MuliFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)