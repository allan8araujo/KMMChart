package com.example.sampleapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
    val xValues = (-1 downTo -16).map { it.toFloat() }
    val yValues = xValues.map { it }
    val maxYValue = yValues.max()
    val chartData = xValues.zip(yValues).toMap()

    KMMChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(2.dp),
        backgroundColor = Color.LightGray,
        barsColor = Color.Black,
        barsSize = 5,
        chartData = chartData,
        maxValue = maxYValue.roundToInt(),
    )
}
