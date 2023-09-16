package com.example.kmmchart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kmmchart.ui.commons.isNegative
import com.example.kmmchart.ui.commons.orMinValueIfZero
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun KMMChart(
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
                val isNegative = it.isNegative()

                val filledPercentage = if (isNegative) abs(it / maxValue) else it / maxValue
                val emptyPercentage =
                    if (isNegative) abs(1 - filledPercentage) else 1 - filledPercentage

                if (isNegative) Column(Modifier.weight(1f).align(Alignment.Top)) {
                    Spacer(Modifier.weight(1f))
                    Column(Modifier.weight(1f)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = it.roundToInt().toString()
                        )
                        Row(modifier = Modifier.weight(filledPercentage)) {
                            Spacer(Modifier.weight(1f))
                            Row(
                                Modifier.weight(1f)
                                    .background(color = barsColor)
                                    .fillMaxHeight()
                            ) {}
                            Spacer(Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
                    }
                } else {
                    Column(Modifier.weight(1f).align(Alignment.Bottom).offset(x = 0.dp)) {
                        Column(Modifier.weight(1f)) {
                            Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
                            Row(modifier = Modifier.weight(filledPercentage)) {
                                Spacer(Modifier.weight(1f))
                                Row(
                                    Modifier.weight(1f)
                                        .background(color = barsColor)
                                        .fillMaxHeight()
                                ) {}
                                Spacer(Modifier.weight(1f))
                            }
                        }
                        Row(Modifier.weight(1f)) {
                            Text(
                                modifier = Modifier.align(Alignment.Top),
                                text = it.roundToInt().toString()
                            )
                        }
                    }
                }
            }
        }
    }
}

data class chartData(
    val xValues: ArrayList<Float>,
    val yValues: ArrayList<Float>
)
