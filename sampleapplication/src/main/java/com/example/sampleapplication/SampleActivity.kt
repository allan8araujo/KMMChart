package com.example.sampleapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmmchart.ui.Chart
import com.example.kmmchart.ui.chartData
import com.example.sampleapplication.ui.theme.KMMChartTheme

class SampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KMMChartTheme {
                GreetingPreview()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Chart(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp),
        backgroundColor = Color.LightGray,
        barsColor = Color.Black,
        barsSize = 5,
        chartData = chartData(
            xValues = arrayListOf(160, 40, 50),
            yValues = arrayListOf(1, 40, 4, 4)
        ),
        maxValue = 6,
    )
}
