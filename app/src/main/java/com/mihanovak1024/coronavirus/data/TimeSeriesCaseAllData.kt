package com.mihanovak1024.coronavirus.data

data class TimeSeriesCaseAllData(
        val casesMap: Map<String, Map<String, TimeSeriesCaseData>>
)