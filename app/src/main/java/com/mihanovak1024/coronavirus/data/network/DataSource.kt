package com.mihanovak1024.coronavirus.data.network

import com.mihanovak1024.coronavirus.data.TimeSeriesCaseAllData

interface DataSource {

    suspend fun getTimeSeriesCaseAllData(): TimeSeriesCaseAllData

}