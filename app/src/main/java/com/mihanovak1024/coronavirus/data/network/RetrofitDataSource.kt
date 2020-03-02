package com.mihanovak1024.coronavirus.data.network

import com.mihanovak1024.coronavirus.data.CoronaCase
import com.mihanovak1024.coronavirus.data.DataSource
import com.mihanovak1024.coronavirus.data.TimeSeriesCaseAllData
import com.mihanovak1024.coronavirus.data.TimeSeriesCaseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.*


class RetrofitDataSource : DataSource {

    private val webservice: CoronaVirusGithubDataWebservice

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

        webservice = retrofit.create(CoronaVirusGithubDataWebservice::class.java)
    }

    override suspend fun getTimeSeriesCaseAllData(): TimeSeriesCaseAllData {
        Timber.d("getStatistics()")
        return withContext(Dispatchers.IO) {
            return@withContext getTimeSeriesCaseOrderedData()
        }
    }


    private fun getTimeSeriesCaseOrderedData(): TimeSeriesCaseAllData {
        var casesPerDate = LinkedHashMap<String, LinkedHashMap<String, TimeSeriesCaseData>>()

        webservice.getCoronaVirusDataConfirmedCases().execute().body()?.let {
            addTimeSeriesCaseDataToMap(CoronaCase.INFECTED, it, casesPerDate)
        }


        webservice.getCoronaVirusDataDeathCases().execute().body()?.let {
            addTimeSeriesCaseDataToMap(CoronaCase.DEATH, it, casesPerDate)
        }

        webservice.getCoronaVirusDataRecoveredCases().execute().body()?.let {
            addTimeSeriesCaseDataToMap(CoronaCase.RECOVERED, it, casesPerDate)
        }


        return TimeSeriesCaseAllData(casesPerDate)
    }

    private fun addTimeSeriesCaseDataToMap(coronaCase: CoronaCase, response: String, casesMap: LinkedHashMap<String, LinkedHashMap<String, TimeSeriesCaseData>>): LinkedHashMap<String, LinkedHashMap<String, TimeSeriesCaseData>> {
        val lineSeparated = response.lines()

        val dateList = lineSeparated.first().split(",").drop(4)

        if (casesMap.isEmpty()) {
            dateList.forEach {
                casesMap[it] = LinkedHashMap()
            }
        }

        lineSeparated.drop(1).forEach {
            val splitLine = it.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)".toRegex())

            val splitLineIterator = splitLine.iterator()


            val state = splitLineIterator.next()
            val country = splitLineIterator.next()
            val lat = splitLineIterator.next().toDouble()
            val long = splitLineIterator.next().toDouble()

            val dateIterator = dateList.iterator()
            splitLine.drop(4).forEach {
                val date = dateIterator.next()
                val mapForDate = casesMap[date]
                mapForDate!![state + "_" + country]?.let { timeSeriesCaseData ->
                    when (coronaCase) {
                        CoronaCase.INFECTED -> timeSeriesCaseData.infectedCases = it.toInt()
                        CoronaCase.RECOVERED -> timeSeriesCaseData.recoveredCases = it.toInt()
                        CoronaCase.DEATH -> timeSeriesCaseData.deathCases = it.toInt()
                    }
                }
                        ?: mapForDate.put(state + "_" + country, TimeSeriesCaseData(state, country, lat, long, date, it.toInt(), 0, 0))
            }
        }

        return casesMap
    }

}