package com.mihanovak1024.coronavirus.util

fun String.zeroPrefixed(): String {
    return if (length == 1) {
        "0$this"
    } else {
        this
    }
}