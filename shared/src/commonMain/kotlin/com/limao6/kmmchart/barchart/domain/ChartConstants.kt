package com.limao6.kmmchart.barchart.domain

object ChartConstants {
    object Strings {
        const val EMPTY = ""
    }

    object Warnings {
        const val CHART_LIMIT_SIZE =
            "ChartData can only support size up to 12, if you want to increase this limit enable the 'noSizeLimit' to true. It can lead to performance issues so don't say i didn't warn you. Also is recommended to set ylabelsVisibility to false and xlabelsVisibility"
    }
}
