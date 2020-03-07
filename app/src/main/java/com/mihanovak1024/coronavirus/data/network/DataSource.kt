package com.mihanovak1024.coronavirus.data.network

import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import org.threeten.bp.OffsetDateTime
import java.util.*

/**
 * API for fetching data from non-local sources
 */
interface DataSource {

    suspend fun getTimeSeriesCaseAllData(): LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>>

}