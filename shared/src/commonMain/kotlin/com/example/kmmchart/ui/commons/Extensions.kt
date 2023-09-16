package com.example.kmmchart.ui.commons

import kotlin.Float.Companion.MIN_VALUE

fun Float.orMinValueIfZero() = if (this == 0f) MIN_VALUE else this

fun Float.isNegative() = this < 0f
