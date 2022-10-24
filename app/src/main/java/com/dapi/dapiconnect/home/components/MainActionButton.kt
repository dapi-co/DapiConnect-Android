package com.dapi.dapiconnect.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dapi.dapiconnect.theme.appColors

@Composable
internal fun MainActionButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.appColors.primary,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.5f),
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
        onClick = { onClick() },
        border = BorderStroke(1.dp, color),
        shape = RoundedCornerShape(20),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.appColors.onPrimary,
            backgroundColor = color,
            disabledContentColor = MaterialTheme.appColors.onPrimary
        )
    ) {
        Text(text = text)
    }
}