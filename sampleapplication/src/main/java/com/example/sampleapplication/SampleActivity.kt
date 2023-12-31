package com.example.sampleapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapplication.ui.theme.KMMChartTheme
import com.limao6.kmmchart.barchart.ui.BarChart
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
    val xValues = (1..12).map { it.toFloat() }
    val yValues = xValues.map { it * it }
    val maxYValue = yValues.max()
    val minYValue = yValues.min()
    val chartData = xValues.zip(yValues).toMap()

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        BarChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(2.dp),
            backgroundColor = Color(0xFF3F51B5),
            barsColor = Color(0xFFF5F5F5),
            barsSize = 0.7f,
            barRoundedCorner = 12.dp,
            chartData = chartData,
            maxValue = maxYValue.roundToInt(),
            minValue = minYValue.roundToInt()
        )

        val xValues2 = (0..10).map { it.toFloat() }
        val yValues2 = xValues2.map { it * (-1) }
        val maxYValue2 = yValues2.max()
        val minYValue2 = yValues2.min()
        val chartData2 = xValues2.zip(yValues2).toMap()

        BarChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(2.dp),
            backgroundColor = Color.White,
            barsColor = Color.DarkGray,
            barsSize = 0.7f,
            chartData = chartData2,
            maxValue = maxYValue2.roundToInt(),
            minValue = minYValue2.roundToInt(),
            xlabelsVisibility = true,
            ylabelsVisibility = true
        )

        val xValues3 = (-5..5).map { it.toFloat() }
        val yValues3 = xValues3.map { it }
        val maxYValue3 = yValues3.max()
        val minYValue3 = yValues3.min()
        val chartData3 = xValues3.zip(yValues3).toMap()

        BarChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(360.dp)
                .padding(2.dp),
            backgroundColor = Color.Gray,
            barsColor = Color.Black,
            barsSize = 0.7f,
            barRoundedCorner = 8.dp,
            chartData = chartData3,
            maxValue = maxYValue3.roundToInt(),
            minValue = minYValue3.roundToInt(),
            xlabelsVisibility = true,
            ylabelsVisibility = true
        )
    }
}
