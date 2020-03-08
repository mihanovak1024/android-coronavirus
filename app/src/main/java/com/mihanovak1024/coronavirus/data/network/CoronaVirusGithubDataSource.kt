package com.mihanovak1024.coronavirus.data.network

import com.mihanovak1024.coronavirus.data.CoronaCase
import com.mihanovak1024.coronavirus.data.database.time.TimeTypeConverter
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCasePlace
import com.mihanovak1024.coronavirus.util.zeroPrefixed
import org.threeten.bp.OffsetDateTime
import timber.log.Timber


class CoronaVirusGithubDataSource(
        private val webservice: CoronaVirusGithubDataWebservice,
        private val dataSourceExtractor: CoronaVirusGithubDataSourceExtractor
) : DataSource {

    /**
     * Creates three different calls to Github repo for INFECTED, DEATH and RECOVERED csv data files
     * and extracts the data to a map based on OffsetDateTime and state_country keys and TimeSeriesCaseData.
     */
    override suspend fun getTimeSeriesCaseAllData(): LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>> {
        Timber.d("getTimeSeriesCaseAllData")
        val casesPerDate = LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>>()

        webservice.getCoronaVirusDataConfirmedCases().execute().body()?.let {
            dataSourceExtractor.addTimeSeriesCaseDataToMap(CoronaCase.INFECTED, it, casesPerDate)
        }

        webservice.getCoronaVirusDataDeathCases().execute().body()?.let {
            dataSourceExtractor.addTimeSeriesCaseDataToMap(CoronaCase.DEATH, it, casesPerDate)
        }

        webservice.getCoronaVirusDataRecoveredCases().execute().body()?.let {
            dataSourceExtractor.addTimeSeriesCaseDataToMap(CoronaCase.RECOVERED, it, casesPerDate)
        }

        return casesPerDate
    }

}