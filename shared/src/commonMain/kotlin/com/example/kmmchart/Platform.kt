package com.example.kmmchart

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform