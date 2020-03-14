package com.mihanovak1024.coronavirus.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import org.threeten.bp.OffsetDateTime

@Dao
interface TimeSeriesCaseDataDao {

    @Insert(onConflict = IGNORE)
    suspend fun saveMultiple(timeSeriesCaseDataList: List<TimeSeriesCaseData>)

    @Query("SELECT * FROM time_series_case_data")
    fun loadAll(): LiveData<TimeSeriesCaseData>

    @Query("SELECT DISTINCT offset_date_time FROM time_series_case_data ORDER BY offset_date_time DESC")
    fun loadDates(): List<OffsetDateTime>

    @Query("SELECT * FROM time_series_case_data WHERE offset_date_time = :dateTime")
    fun loadForDate(dateTime: OffsetDateTime): LiveData<List<TimeSeriesCaseData>>

    @Query("SELECT * FROM time_series_case_data WHERE country = :country AND state = :state ORDER BY offset_date_time DESC LIMIT 1")
    fun loadForCountryAndStateForLastDate(country: String, state: String): LiveData<TimeSeriesCaseData>

    @Query("SELECT * FROM time_series_case_data WHERE country = :country AND offset_date_time = (SELECT offset_date_time FROM time_series_case_data ORDER BY offset_date_time DESC LIMIT 1)")
    fun loadForCountryForLastDate(country: String): LiveData<List<TimeSeriesCaseData>>

    @Query("SELECT * FROM time_series_case_data WHERE country = :country AND state = :state")
    fun loadForPlace(country: String, state: String): LiveData<List<TimeSeriesCaseData>>
}