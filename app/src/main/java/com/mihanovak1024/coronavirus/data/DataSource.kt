package com.mihanovak1024.coronavirus.data

interface DataSource {

    suspend fun getTimeSeriesCaseAllData(): TimeSeriesCaseAllData

}