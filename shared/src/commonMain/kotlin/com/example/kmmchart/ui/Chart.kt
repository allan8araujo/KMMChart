package com.example.kmmchart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
    ylabelsAmount: Int = 8,
    xlabelsAmount: Int = 8,
    barsSize: Int = 2
) {
    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        val yValuesSorted = chartData.values.sortedByDescending { it }

        val isAllPositives = yValuesSorted.isAllPositives()
        val isAllNegatives = yValuesSorted.isAllNegatives()

        val ylabelsAmountList = (1..(ylabelsAmount / 2)).toList()
        val kekwylabelsAmount = ylabelsAmount / 2

        Row {
            setupYTexts(
                yValuesList = ylabelsAmountList,
                ylabelsAmount = kekwylabelsAmount,
                yValuesSorted = yValuesSorted,
                isAllPositives = isAllPositives,
                isAllNegatives = isAllNegatives
            )

            val modifierNegativeNumbers =
                if (isAllPositives) Modifier
                else Modifier.weight(1f)

            val modifierPositiveNumbers =
                if (isAllNegatives) Modifier
                else Modifier.weight(1f)

            chartData.forEach { yValue ->
                val isNegative = yValue.value.isNegative()

                val filledPercentage =
                    if (isNegative) abs(yValue.value) / abs(maxValue)
                    else yValue.value / maxValue
                val emptyPercentage =
                    if (isNegative) 1 + abs(filledPercentage)
                    else 1 + filledPercentage

                if (isNegative) Column(Modifier.weight(1f).align(Alignment.Top)) {
                    SpacerStandard()
                    Column(Modifier.weight(1f)) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = yValue.key.roundToInt().toString()
                        )
                        Row(modifier = Modifier.weight(filledPercentage)) {
                            SpacerStandard()
                            Row(
                                Modifier.weight(1f).background(color = barsColor)
                                    .fillMaxHeight()
                            ) {}
                            SpacerStandard()
                        }
                        Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
                    }
                } else {
                    Column(Modifier.weight(1f).align(Alignment.Bottom)) {
                        Column(Modifier.weight(1f)) {
                            Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
                            Row(modifier = Modifier.weight(filledPercentage.orMinValueIfZero())) {
                                SpacerStandard()
                                Row(
                                    Modifier.weight(1f)
                                        .background(color = barsColor)
                                        .fillMaxHeight()
                                ) {}
                                SpacerStandard()
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

@Composable
private fun RowScope.setupYTexts(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    isAllPositives: Boolean,
    isAllNegatives: Boolean,
    ylabelsAmount: Int
) {
    Column(modifier = Modifier.Companion.align(Alignment.CenterVertically)) {

        if (!isAllNegatives) setupPositivesY(yValuesList, yValuesSorted, ylabelsAmount)
        Text(text = "0")
        if (!isAllPositives) setupNegativesY(yValuesList, yValuesSorted, ylabelsAmount)
    }
}

@Composable
private fun ColumnScope.setupNegativesY(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    ylabelsAmount: Int
) {
    val negativeYLabelsList = yValuesList.sortedDescending()
    negativeYLabelsList.forEach {
        SpacerStandard()
        val yText =
            (yValuesSorted.last() / ylabelsAmount) * (ylabelsAmount - it + 1)

        Text(text = yText.roundToInt().toString())
    }
}

@Composable
private fun ColumnScope.setupPositivesY(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    ylabelsAmount: Int
) {
    yValuesList.forEach {
        val yText =
            (yValuesSorted.first() / ylabelsAmount) * (ylabelsAmount - it + 1)

        Text(text = yText.roundToInt().toString())
        SpacerStandard()
    }
}

@Composable
fun ColumnScope.SpacerStandard() = Spacer(Modifier.weight(1f))

@Composable
fun RowScope.SpacerStandard() = Spacer(Modifier.weight(1f))
