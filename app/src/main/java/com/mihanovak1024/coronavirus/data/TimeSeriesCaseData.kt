package com.mihanovak1024.coronavirus.data

data class TimeSeriesCaseData(
        val state: String,
        val country: String,
        val lat: Double,
        val long: Double,
        val date: String,
        var infectedCases: Int,
        var deathCases: Int,
        var recoveredCases: Int
)