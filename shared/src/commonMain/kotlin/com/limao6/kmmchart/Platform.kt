package com.limao6.kmmchart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform