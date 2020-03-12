package com.mihanovak1024.coronavirus.data

import androidx.lifecycle.LiveData
import com.mihanovak1024.coronavirus.data.database.TimeSeriesCaseDataDao
import com.mihanovak1024.coronavirus.data.database.TimeSeriesCasePlaceDao
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCasePlace
import com.mihanovak1024.coronavirus.data.network.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset.UTC
import timber.log.Timber

class DataRepository(
        private val dataSource: DataSource,
        private val timeSeriesCaseDataDao: TimeSeriesCaseDataDao,
        private val timeSeriesCasePlaceDao: TimeSeriesCasePlaceDao
) : Repository {

    override fun getTimeSeriesCaseAllDataForDate(date: OffsetDateTime): LiveData<List<TimeSeriesCaseData>> {
        Timber.d("getTimeSeriesCaseAllDataForDate - date = %s", date)
        refreshTimeSeriesDataAsync()

        return timeSeriesCaseDataDao.loadForDate(date)
    }

    override fun getTimeSeriesCaseDataForLastDateAndCountry(country: String): LiveData<TimeSeriesCaseData> {
        Timber.d("getTimeSeriesCaseDataForLastDateAndPlace")
        refreshTimeSeriesDataAsync()

        return timeSeriesCaseDataDao.loadForCountryForLastDate(country)
    }

    override fun getInfectedCountries(): LiveData<List<String>> {
        Timber.d("getInfectedLocations")
        refreshTimeSeriesDataAsync()

        return timeSeriesCasePlaceDao.loadCountries()
    }

    private fun refreshTimeSeriesDataAsync() {
        GlobalScope.launch(Dispatchers.IO) {
            Timber.d("refreshTimeSeriesDataAsync")

            val dateYesterday = OffsetDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, UTC).minusDays(1)
            Timber.d("dateYesterday = %s", dateYesterday)

            val databaseDates = timeSeriesCaseDataDao.loadDates()
            if (databaseDates.isEmpty() || !databaseDates.contains(dateYesterday)) {
                Timber.d("Yesterday data not in database")

                // Fetch data from DataSource
                val timeSeriesData = dataSource.getTimeSeriesCaseAllData()
                updateDatabaseData(databaseDates, timeSeriesData)

                Timber.d("Multiple data saved")
            }
        }
    }

    /**
     * Updates the database of [TimeSeriesCaseData] and [TimeSeriesCasePlace].
     *
     * Ignores values already present in database.
     */
    private suspend fun updateDatabaseData(databaseDates: List<OffsetDateTime>, timeSeriesData: LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>>) {
        val timeSeriesDataList: MutableList<TimeSeriesCaseData> = mutableListOf()
        val timeSeriesPlaceMap: MutableMap<String, TimeSeriesCasePlace> = mutableMapOf()
        var timeSeriesCaseWorldwide = timeSeriesCasePlaceDao.loadByCountry("worldwide").value
        if (timeSeriesCaseWorldwide == null) {
            Timber.d("No worldwide place in database, creating...")
            timeSeriesCaseWorldwide = TimeSeriesCasePlace(0, "", "worldwide", 0.0, 0.0)
            timeSeriesPlaceMap["-worldwide"] = timeSeriesCaseWorldwide
        }

        timeSeriesData.forEach {
            // Checks which dates are already in the local database
            if (!databaseDates.contains(it.key)) {
                val dailyStatistics = TimeSeriesCaseData(offsetDateTime = it.key, place = timeSeriesCaseWorldwide)
                it.value.forEach { placeMap ->
                    placeMap.value.apply {
                        dailyStatistics.deathCases += deathCases
                        dailyStatistics.infectedCases += infectedCases
                        dailyStatistics.recoveredCases += recoveredCases

                        timeSeriesDataList.add(this)
                        timeSeriesPlaceMap["${place!!.state}-${place!!.country}"] = place!!
                    }
                }
                timeSeriesDataList.add(dailyStatistics)
            }
        }



        timeSeriesCaseDataDao.saveMultiple(timeSeriesDataList)
        timeSeriesCasePlaceDao.saveMultiple(timeSeriesPlaceMap.values.toList())
    }

}