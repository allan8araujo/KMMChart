package com.example.kmmchart.ui.commons

/**
 * A utility function that returns the minimum positive value if the calling Float is zero.
 * If the Float is already non-zero, it returns the Float itself.
 *
 * @return The Float value, or [Float.MIN_VALUE] if the calling Float is zero.
 */
internal fun Float.orMinValueIfZero() = if (this == 0f) Float.MIN_VALUE else this

/**
 * Checks whether the calling Float is negative.
 *
 * @return `true` if the Float is negative, `false` otherwise.
 */
internal fun Float.isNegative() = this < 0f

/**
 * Checks if all elements in the calling List of Floats are positive.
 *
 * @return `true` if all elements in the List are greater or equals to zero, `false` otherwise.
 */
internal fun List<Float>.isAllPositives() = this.all { it >= 0.0 }

/**
 * Checks if all elements in the calling List of Floats are negatives.
 *
 * @return `true` if all elements in the List are greater or equals to zero, `false` otherwise.
 */
internal fun List<Float>.isAllNegatives() = this.all { it <= 0.0 }
