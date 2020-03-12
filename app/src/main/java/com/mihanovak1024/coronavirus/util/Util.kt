package com.mihanovak1024.coronavirus.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

val LOCAL_COUNTRY_STRING_SP = "LOCAL_COUNTRY_STRING_SP"
val LOCAL_COUNTRY_CLICKED_SP = "LOCAL_COUNTRY_CLICKED_SP"

fun String.zeroPrefixed(): String {
    return if (length == 1) {
        "0$this"
    } else {
        this
    }
}

fun getDefaultSharedPreference(context: Context): SharedPreferences = context.getSharedPreferences("corona-sp", MODE_PRIVATE)

