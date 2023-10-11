package com.example.kmmchart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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
    minValue: Int,
    backgroundColor: Color = MaterialTheme.colors.surface,
    barsColor: Color = MaterialTheme.colors.surface,
    ylabelsAmount: Int = 8,
    xlabelsAmount: Int = 8,
    roundedCorner: Dp? = null,
    barsSize: Float = 0.7f
) {

    Surface(
        modifier = modifier,
        color = backgroundColor
    ) {
        val yValuesSorted = chartData.values.sortedByDescending { it }

        val isAllPositives = yValuesSorted.isAllPositives()
        val isAllNegatives = yValuesSorted.isAllNegatives()

        val yLabelsAmountList = (1..(ylabelsAmount / 2)).toList()
        val halfOfYLabelAmount = ylabelsAmount / 2

        Row {
            SetupYTexts(
                yValuesList = yLabelsAmountList,
                ylabelsAmount = halfOfYLabelAmount,
                yValuesSorted = yValuesSorted,
                isAllPositives = isAllPositives,
                isAllNegatives = isAllNegatives
            )

            val modifierOnlyNegativeValues =
                if (isAllPositives) Modifier
                else Modifier.weight(1f)

            val modifierOnlyPositiveValues =
                if (isAllNegatives) Modifier
                else Modifier.weight(1f)

            chartData.forEach { graphMap ->
                val isYPositive = !graphMap.value.isNegative()
                val isXPositive = !graphMap.key.isNegative()

                val isQ1 = isYPositive && isXPositive
                val isQ2 = isYPositive && !isXPositive
                val isQ3 = !isYPositive && !isXPositive
                val isQ4 = !isYPositive && isXPositive

                val filledPercentage =
                    if (!isYPositive) abs(graphMap.value) / abs(minValue)
                    else abs(graphMap.value) / abs(maxValue)

                val emptyPercentage =
                    if (!isYPositive) abs(1 - filledPercentage)
                    else 1 - abs(filledPercentage)

                if (isQ4) Column(Modifier.weight(1f).align(Alignment.Top)) {

                    Spacer(modifier = modifierOnlyPositiveValues)

                    Column(Modifier.weight(1f)) {
                        SetupXTexts(yValue = graphMap)

                        SetupQ4Bars(
                            emptyPercentage = emptyPercentage,
                            filledPercentage = filledPercentage,
                            barsSize = barsSize,
                            barsColor = barsColor,
                        )
                    }
                } else if (isQ1) {

                    Column(Modifier.weight(1f).align(Alignment.Bottom)) {

                        SetupQ1Bars(
                            emptyPercentage = emptyPercentage,
                            filledPercentage = filledPercentage,
                            barsSize = barsSize,
                            barsColor = barsColor,
                            modifierOnlyNegativeValues = modifierOnlyNegativeValues,
                            yValue = graphMap,
                            roundedCorner = roundedCorner
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.SetupQ1Bars(
    emptyPercentage: Float,
    filledPercentage: Float,
    barsSize: Float,
    barsColor: Color,
    modifierOnlyNegativeValues: Modifier,
    yValue: Map.Entry<Float, Float>,
    roundedCorner: Dp?
) {
    val modifier =
        if (roundedCorner != null) Modifier.clip(
            shape = RoundedCornerShape(topStart = roundedCorner, topEnd = roundedCorner)
        )
        else Modifier

    Column(Modifier.weight(1f)) {

        Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))

        Row(modifier = Modifier.weight(filledPercentage.orMinValueIfZero())) {
            Spacer(modifier = Modifier.weight((1 - barsSize).orMinValueIfZero()))

            Row(
                modifier.weight(barsSize)
                    .background(color = barsColor)
                    .fillMaxHeight()
            ) {}

            Spacer(modifier = Modifier.weight((1 - barsSize).orMinValueIfZero()))
        }
    }

    Column(modifierOnlyNegativeValues.align(Alignment.CenterHorizontally)) {

        Text(text = yValue.key.roundToInt().toString())
    }
}

@Composable
private fun ColumnScope.SetupQ4Bars(
    filledPercentage: Float,
    barsSize: Float,
    barsColor: Color,
    emptyPercentage: Float
) {
    Row(modifier = Modifier.weight(filledPercentage)) {

        Spacer(modifier = Modifier.weight((1 - barsSize).orMinValueIfZero()))

        Row(
            Modifier
                .weight(barsSize)
                .background(color = barsColor)
                .fillMaxHeight()
        ) {}

        Spacer(modifier = Modifier.weight((1 - barsSize).orMinValueIfZero()))
    }

    Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
}

@Composable
private fun ColumnScope.SetupXTexts(yValue: Map.Entry<Float, Float>) {
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = yValue.key.roundToInt().toString()
    )
}

@Composable
private fun RowScope.SetupYTexts(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    isAllPositives: Boolean,
    isAllNegatives: Boolean,
    ylabelsAmount: Int
) {
    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
        if (!isAllNegatives) setupPositivesYLabels(
            yValuesList = yValuesList,
            yValuesSorted = yValuesSorted,
            ylabelsAmount = ylabelsAmount
        )
        Text(text = "0")
        if (!isAllPositives) setupNegativesYLabels(
            yValuesList = yValuesList,
            yValuesSorted = yValuesSorted,
            ylabelsAmount = ylabelsAmount
        )
    }
}

@Composable
private fun ColumnScope.setupNegativesYLabels(
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
private fun ColumnScope.setupPositivesYLabels(
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
