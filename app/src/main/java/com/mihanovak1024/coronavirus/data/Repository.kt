package com.mihanovak1024.coronavirus.data

interface Repository {

    suspend fun getTimeSeriesCaseAllData(): TimeSeriesCaseAllData

}