package com.example.intellecta.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowsFillMaxWidth(
    verticalAlignment: Alignment.Vertical,
    horizontalArrangement: Arrangement.HorizontalOrVertical,
    topStart: Int,
    topEnd: Int,
    bottomStart: Int,
    bottomEnd: Int,
    height: Int,
    start: Int,
    top: Int,
    end: Int,
    bottom: Int,
    content: @Composable () -> Unit

){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .clip(RoundedCornerShape(topStart = topStart.dp, topEnd = topEnd.dp, bottomStart = bottomStart.dp, bottomEnd = bottomEnd.dp))

            .padding(start = start.dp, top = top.dp, end = end.dp, bottom = bottom.dp)




    ) {
        content
    }


}

@Composable
fun RowsComposable(
    verticalAlignment: Alignment,
    horizontalArrangement : Arrangement,
    topStart : Int,
    topEnd : Int,
    bottomStart : Int,
    bottomEnd : Int,
    height : Int,
    width : Int,
    color: Color?,
    start : Int,
    top : Int,
    end : Int,
    bottom : Int,
    content: @Composable () -> Unit

){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .clip(RoundedCornerShape(topStart = topStart.dp, topEnd = topEnd.dp, bottomStart = bottomStart.dp, bottomEnd = bottomEnd.dp))
            .background(color = color!!)
            .padding(start = start.dp, top = top.dp, end = end.dp, bottom = bottom.dp)




    ) {
        content
    }


}