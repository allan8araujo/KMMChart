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
import com.example.kmmchart.ui.chartData
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
    val xValues = arrayListOf(160f, 40f, 50f, 16f, 180f, 1000f, -100f, -400f, -600f, -1000f, -2200f)
    val yValues = arrayListOf(1f, 40f, 4f, 4f)
    val maxValue = xValues.max()

    KMMChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(2.dp),
        backgroundColor = Color.LightGray,
        barsColor = Color.Black,
        barsSize = 5,
        chartData = chartData(
            xValues = xValues,
            yValues = yValues
        ),
        maxValue = maxValue.roundToInt(),
    )
}
