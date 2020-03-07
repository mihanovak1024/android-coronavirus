package com.mihanovak1024.coronavirus.data

import androidx.lifecycle.LiveData
import com.mihanovak1024.coronavirus.data.database.DailyStatisticsDao
import com.mihanovak1024.coronavirus.data.database.TimeSeriesCaseDataDao
import com.mihanovak1024.coronavirus.data.database.TimeSeriesCasePlaceDao
import com.mihanovak1024.coronavirus.data.model.DailyStatistics
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
        private val timeSeriesCasePlaceDao: TimeSeriesCasePlaceDao,
        private val dailyStatisticsDao: DailyStatisticsDao
) : Repository {

    override fun getTimeSeriesCaseAllDataForDate(date: OffsetDateTime): LiveData<List<TimeSeriesCaseData>> {
        Timber.d("getTimeSeriesCaseAllDataForDate - date = %s", date)
        refreshTimeSeriesDataAsync()

        return timeSeriesCaseDataDao.loadForDate(date)
    }

    override fun getStatisticsForDate(date: OffsetDateTime): LiveData<DailyStatistics> {
        Timber.d("getStatisticsForDate - date = %s", date)
        refreshTimeSeriesDataAsync()

        return dailyStatisticsDao.loadStatisticsForDate(date)
    }

    private fun refreshTimeSeriesDataAsync() {
        GlobalScope.launch(Dispatchers.IO) {
            Timber.d("refreshTimeSeriesDataAsync")

            val dateYesterday = OffsetDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, UTC).minusDays(1)
            Timber.d("dateYesterday = %s", dateYesterday)

            val databaseDates = dailyStatisticsDao.loadDates()
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
     * Updates the database of [TimeSeriesCaseData], [TimeSeriesCasePlace] and [DailyStatistics].
     *
     * Ignores values already present in database.
     */
    private suspend fun updateDatabaseData(databaseDates: List<OffsetDateTime>, timeSeriesData: LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>>) {
        val timeSeriesDataList: MutableList<TimeSeriesCaseData> = mutableListOf()
        val timeSeriesPlaceList: MutableList<TimeSeriesCasePlace> = mutableListOf()
        val dailyStatisticsList: MutableList<DailyStatistics> = mutableListOf()

        timeSeriesData.forEach {
            // Checks which dates are already in the local database
            if (!databaseDates.contains(it.key)) {
                val statistics = DailyStatistics(date = it.key)
                it.value.forEach { placeMap ->
                    placeMap.value.apply {
                        statistics.deathNumber += deathCases
                        statistics.infectedNumber += infectedCases
                        statistics.recoveredNumber += recoveredCases

                        timeSeriesPlaceList.add(place!!)
                        timeSeriesDataList.add(this)
                    }
                }
                dailyStatisticsList.add(statistics)
            }
        }

        timeSeriesCaseDataDao.saveMultiple(timeSeriesDataList)
        timeSeriesCasePlaceDao.saveMultiple(timeSeriesPlaceList)
        dailyStatisticsDao.saveMultiple(dailyStatisticsList)
    }

}