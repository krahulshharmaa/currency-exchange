package com.currency.flip.ui.view.util.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.currency.flip.ui.theme.Blue1
import com.currency.flip.ui.theme.Blue2

@Composable
fun GradientTopAppBar() {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), modifier = Modifier
        .fillMaxWidth()
        .height(350.dp)
        .clip(
            RoundedCornerShape(
                topEnd = 0.dp,
                topStart = 0.dp,
                bottomEnd = 40.dp,
                bottomStart = 40.dp
            )
        )) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Blue1,
                        Blue2
                    )
                )
            )) {

            Spacer(modifier = Modifier.height(28.dp))

        }
    }

}