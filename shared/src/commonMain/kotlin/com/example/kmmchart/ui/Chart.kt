package com.example.kmmchart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chart(
    modifier: Modifier,
    chartData: chartData,
    maxValue: Int,
    backgroundColor: Color = MaterialTheme.colors.surface,
    barsColor: Color = MaterialTheme.colors.surface,
    barsSize: Int = 2
) {
    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        Row(modifier = Modifier) {
            chartData.xValues.forEach {
                Column(Modifier.weight(1f)) {
                    Spacer(modifier = Modifier.weight(0.7f))
                    Column(
                        modifier = Modifier
                            .weight(0.4f)
                            .background(color = barsColor)
                            .requiredWidthIn(barsSize.dp)
                            .align(Alignment.CenterHorizontally)
                            .weight(1f)
                    ) { }
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = it.toString()
                    )
                }
            }
        }
    }
}

data class chartData(
    val xValues: ArrayList<Int>,
    val yValues: ArrayList<Int>
)
