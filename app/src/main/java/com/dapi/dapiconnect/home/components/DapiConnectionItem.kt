package com.dapi.dapiconnect.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.dapi.connect.data.models.DapiConnection
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.dapi.dapiconnect.theme.appColors

@Composable
fun DapiConnectionItem(
    connection: DapiConnection,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .background(
                color = Color(android.graphics.Color.parseColor(connection.color["primaryColor"])),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onClick()
            }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.appColors.secondaryBackground
            ),
            modifier = Modifier
                .width(72.dp)
                .height(72.dp)
                .padding(all = 12.dp),
            elevation = 0.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    data = connection.miniLogoPng,
                    builder = {
                        transformations(RoundedCornersTransformation())
                    }),
                contentDescription = "Bank Logo",
                modifier = Modifier
                    .widthIn(64.dp, 128.dp)
                    .height(64.dp)
            )
        }

        Text(
            text = connection.name,
            color = MaterialTheme.appColors.onPrimary,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        )

    }
}