package com.example.kmmchart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.kmmchart.ui.commons.isAllNegatives
import com.example.kmmchart.ui.commons.isAllPositives
import com.example.kmmchart.ui.commons.isNegative
import com.example.kmmchart.ui.commons.orMinValueIfZero
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun KMMChart(
    modifier: Modifier,
    chartData: Map<Float, Float>,
    maxValue: Int,
    backgroundColor: Color = MaterialTheme.colors.surface,
    barsColor: Color = MaterialTheme.colors.surface,
    ylabelsAmount: Int = 6,
    barsSize: Int = 2
) {
    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        val yValuesSorted = chartData.values.sortedByDescending { it }
        val isAllPositives = yValuesSorted.isAllPositives()
        val isAllNegatives = yValuesSorted.isAllNegatives()
        val halfOfYlabelsAmount = ylabelsAmount / 2

        Row {
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                for (num in 1..halfOfYlabelsAmount) {
                    Text(text = (yValuesSorted.first() / num).roundToInt().toString())
                    Spacer(Modifier.weight(1f))
                }

                Text(
                    text = 0.toString()
                )

                for (num in 1..halfOfYlabelsAmount) {
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = (yValuesSorted.last() / (halfOfYlabelsAmount - num + 1))
                            .roundToInt()
                            .toString(),
                    )
                }
            }

            val modifierNegativeNumbers =
                if (isAllPositives) Modifier
                else Modifier.weight(1f)

            chartData.forEach { yValue ->
                val isNegative = yValue.value.isNegative()

                val filledPercentage =
                    if (isNegative) abs(yValue.value / maxValue) else yValue.value / maxValue
                val emptyPercentage =
                    if (isNegative) abs(1 - filledPercentage) else 1 - filledPercentage

                if (isNegative) Column(Modifier.weight(1f).align(Alignment.Top)) {
                    Spacer(Modifier.weight(1f))
                    Column(Modifier.weight(1f)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = yValue.key.roundToInt().toString()
                        )
                        Row(modifier = Modifier.weight(filledPercentage)) {
                            Spacer(Modifier.weight(1f))
                            Row(
                                Modifier.weight(1f).background(color = barsColor)
                                    .fillMaxHeight()
                            ) {}
                            Spacer(Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
                    }
                } else {
                    Column(Modifier.weight(1f).align(Alignment.Bottom)) {
                        Column(Modifier.weight(1f)) {
                            Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
                            Row(modifier = Modifier.weight(filledPercentage.orMinValueIfZero())) {
                                Spacer(Modifier.weight(1f))
                                Row(
                                    Modifier.weight(1f).background(color = barsColor)
                                        .fillMaxHeight()
                                ) {}
                                Spacer(Modifier.weight(1f))
                            }
                        }
                        Column(modifierNegativeNumbers.align(Alignment.CenterHorizontally)) {
                            Text(text = yValue.key.roundToInt().toString())
                        }
                    }
                }
            }
        }
    }
}
