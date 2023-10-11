package com.example.sampleapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmmchart.ui.KMMChart
import com.example.sampleapplication.ui.theme.KMMChartTheme
import kotlin.math.roundToInt

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KMMChartTheme {
                KMMChart()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KMMChart() {
    val xValues = (1 .. 12).map { it.toFloat() }
    val yValues = xValues.map { it*it }
    val maxYValue = yValues.max()
    val minYValue = yValues.min()
    val chartData = xValues.zip(yValues).toMap()

    Column {
        KMMChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(2.dp),
            backgroundColor = Color.LightGray,
            barsColor = Color.Black,
            barsSize = 0.7f,
            chartData = chartData,
            maxValue = maxYValue.roundToInt(),
            minValue = minYValue.roundToInt(),
            roundedCorner = 12.dp
        )

        val xValues2 = (-5..12).map { it.toFloat() }
        val yValues2 = xValues.map { it * it }
        val maxYValue2 = yValues2.max()
        val minYValue2 = yValues2.min()
        val chartData2 = xValues2.zip(yValues2).toMap()
        KMMChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(2.dp),
            backgroundColor = Color.LightGray,
            barsColor = Color.Black,
            barsSize = 0.7f,
            chartData = chartData2,
            maxValue = maxYValue2.roundToInt(),
            minValue = minYValue2.roundToInt(),
        )
    }
}
