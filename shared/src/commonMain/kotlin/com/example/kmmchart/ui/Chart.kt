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
import com.example.kmmchart.domain.ChartConstants
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
    xlabelsVisibility: Boolean = false,
    ylabelsVisibility: Boolean = false,
    barRoundedCorner: Dp? = null,
    barsSize: Float = 0.7f,
    noSizeLimit: Boolean = false,
) {

    if (!noSizeLimit) require(chartData.size <= 12) {
        ChartConstants.Warnings.CHART_LIMIT_SIZE
    }

    Surface(
        modifier = modifier, color = backgroundColor
    ) {
        val yValuesSorted = chartData.values.sortedByDescending { it }

        val isAllPositives = yValuesSorted.isAllPositives()
        val isAllNegatives = yValuesSorted.isAllNegatives()

        val yLabelsAmountList = (1..(ylabelsAmount / 2)).toList()
        val halfOfYLabelAmount = ylabelsAmount / 2

        Row {
            SetupYTexts(
                ylabelsVisibility = ylabelsVisibility,
                yValuesList = yLabelsAmountList,
                ylabelsAmount = halfOfYLabelAmount,
                yValuesSorted = yValuesSorted,
                isAllPositives = isAllPositives,
                isAllNegatives = isAllNegatives
            )

            val modifierForOnlyPositiveValues = if (isAllPositives) Modifier
            else Modifier.weight(1f)

            val modifierForOnlyNegativeValues = if (isAllNegatives) Modifier
            else Modifier.weight(1f)

            chartData.forEach { graphMap ->

                val isYPositive =
                    if (graphMap.value != 0.0f) !graphMap.value.isNegative() else isAllPositives
                val isXPositive = !graphMap.key.isNegative()

                val isQ1 = isYPositive && isXPositive
                val isQ4 = !isYPositive && isXPositive

                val filledPercentage = if (!isYPositive) abs(graphMap.value) / abs(minValue)
                else abs(graphMap.value) / abs(maxValue)

                val emptyPercentage = if (!isYPositive) abs(1 - filledPercentage.orMinValueIfZero())
                else 1 - abs(filledPercentage.orMinValueIfZero())

                val roundedModifier = if (barRoundedCorner != null) Modifier.clip(
                    shape = RoundedCornerShape(
                        topStart = barRoundedCorner, topEnd = barRoundedCorner
                    )
                ) else Modifier

                if (isQ4) Column(Modifier.weight(1f).align(Alignment.Top)) {

                    Column(Modifier.weight(1f)) {

                        SetupQ4Bars(
                            onlyNegativeValuesModifier = modifierForOnlyNegativeValues,
                            roundedModifier = roundedModifier,
                            chartData = graphMap,
                            emptyPercentage = emptyPercentage,
                            filledPercentage = filledPercentage,
                            barsSize = barsSize,
                            barsColor = barsColor,
                            xlabelsVisibility = xlabelsVisibility
                        )
                    }
                } else if (isQ1) {

                    Column(Modifier.weight(1f).align(Alignment.Bottom)) {

                        SetupQ1Bars(
                            emptyPercentage = emptyPercentage,
                            filledPercentage = filledPercentage,
                            barsSize = barsSize,
                            barsColor = barsColor,
                            modifierOnlyNegativeValues = modifierForOnlyPositiveValues,
                            chartData = graphMap,
                            roundedModifier = roundedModifier,
                            xlabelsVisibility = xlabelsVisibility
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
    chartData: Map.Entry<Float, Float>,
    xlabelsVisibility: Boolean,
    roundedModifier: Modifier
) {

    ColumnEmpty(emptyPercentage = emptyPercentage)

    ColumnBar(
        roundedModifier = roundedModifier,
        filledPercentage = filledPercentage,
        barsSize = barsSize,
        barsColor = barsColor
    )

    XLabels(
        chartData = chartData,
        labelVisibility = xlabelsVisibility,
        onlyThoseValuesModifier = modifierOnlyNegativeValues
    )
}

@Composable
private fun ColumnScope.SetupQ4Bars(
    onlyNegativeValuesModifier: Modifier,
    roundedModifier: Modifier,
    filledPercentage: Float,
    barsSize: Float,
    barsColor: Color,
    emptyPercentage: Float,
    chartData: Map.Entry<Float, Float>,
    xlabelsVisibility: Boolean,
) {

    XLabels(
        chartData = chartData,
        labelVisibility = xlabelsVisibility,
        onlyThoseValuesModifier = onlyNegativeValuesModifier
    )

    ColumnBar(
        roundedModifier = roundedModifier,
        filledPercentage = filledPercentage,
        barsSize = barsSize,
        barsColor = barsColor
    )

    ColumnEmpty(emptyPercentage = emptyPercentage)
}

@Composable
private fun ColumnScope.ColumnEmpty(emptyPercentage: Float) {

    Spacer(modifier = Modifier.weight(emptyPercentage.orMinValueIfZero()))
}

@Composable
private fun ColumnScope.ColumnBar(
    roundedModifier: Modifier,
    filledPercentage: Float,
    barsSize: Float,
    barsColor: Color
) {

    Row(modifier = Modifier.weight(filledPercentage.orMinValueIfZero())) {

        Spacer(modifier = Modifier.weight((1 - barsSize).orMinValueIfZero()))

        Row(
            roundedModifier.weight(barsSize).background(color = barsColor).fillMaxHeight()
        ) {}

        Spacer(modifier = Modifier.weight((1 - barsSize).orMinValueIfZero()))
    }
}

@Composable
private fun ColumnScope.XLabels(
    chartData: Map.Entry<Float, Float>,
    labelVisibility: Boolean,
    onlyThoseValuesModifier: Modifier,
) {
    Spacer(modifier = onlyThoseValuesModifier)

    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = if (labelVisibility) chartData.key.roundToInt()
            .toString() else ChartConstants.Strings.EMPTY
    )
}

@Composable
private fun RowScope.SetupYTexts(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    isAllPositives: Boolean,
    isAllNegatives: Boolean,
    ylabelsAmount: Int,
    ylabelsVisibility: Boolean
) {

    Column(modifier = Modifier.align(Alignment.CenterVertically)) {

        if (!isAllNegatives) setupPositivesYLabels(
            yValuesList = yValuesList,
            yValuesSorted = yValuesSorted,
            ylabelsAmount = ylabelsAmount,
            ylabelsVisibility = ylabelsVisibility
        )

        if (!isAllPositives) setupNegativesYLabels(
            yValuesList = yValuesList,
            yValuesSorted = yValuesSorted,
            ylabelsAmount = ylabelsAmount,
            ylabelsVisibility = ylabelsVisibility
        )
    }
}

@Composable
private fun ColumnScope.setupNegativesYLabels(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    ylabelsAmount: Int,
    ylabelsVisibility: Boolean
) {

    val negativeYLabelsList = yValuesList.sortedDescending()

    negativeYLabelsList.forEach {

        SpacerStandard()

        val yText = (yValuesSorted.last() / ylabelsAmount) * (ylabelsAmount - it + 1)

        Text(
            text = if (ylabelsVisibility) yText.roundToInt()
                .toString() else ChartConstants.Strings.EMPTY
        )
    }
}

@Composable
private fun ColumnScope.setupPositivesYLabels(
    yValuesList: List<Int>,
    yValuesSorted: List<Float>,
    ylabelsAmount: Int,
    ylabelsVisibility: Boolean
) {

    yValuesList.forEach {

        val yText = (yValuesSorted.first() / ylabelsAmount) * (ylabelsAmount - it + 1)

        Text(
            text = if (ylabelsVisibility) yText.roundToInt()
                .toString() else ChartConstants.Strings.EMPTY
        )

        SpacerStandard()
    }
}

@Composable
internal fun ColumnScope.SpacerStandard() = Spacer(Modifier.weight(1f))
