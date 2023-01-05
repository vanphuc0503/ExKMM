package com.vanphuc.exkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform