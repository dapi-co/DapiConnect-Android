package com.dapi.dapiconnect.api.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dapi.dapiconnect.theme.Grey1
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

@Composable
fun ResponseListItem(topLeft: String?, topRight: String?, bottomLeft: String?, bottomRight: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .background(
                color = Grey1,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, end = 16.dp, bottom = 10.dp)
        ) {
            Text(
                text = topLeft.toString(),
                color = MaterialTheme.appColors.secondaryText,
                fontSize = 14.sp,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = topRight.toString(),
                color = MaterialTheme.appColors.primaryText,
                fontSize = 14.sp,
                style = MaterialTheme.typography.subtitle1,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 16.dp, bottom = 10.dp)
        ) {
            Text(
                text = bottomLeft.toString(),
                color = MaterialTheme.appColors.secondaryText,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = bottomRight.toString(),
                color = MaterialTheme.appColors.secondaryText,
                fontSize = 12.sp,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}