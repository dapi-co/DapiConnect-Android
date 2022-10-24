package com.dapi.dapiconnect.api.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dapi.dapiconnect.theme.appColors

@Composable
fun ResponseItem(text: String) {
    Text(
        text = text,
        color = MaterialTheme.appColors.primaryText,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 24.dp)
    )
}