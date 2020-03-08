package com.mihanovak1024.coronavirus.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mihanovak1024.coronavirus.data.model.DailyStatistics
import org.threeten.bp.OffsetDateTime

@Dao
interface DailyStatisticsDao {

    @Insert
    fun saveMultiple(statistics: List<DailyStatistics>)

    @Query("SELECT * FROM statistics WHERE date = :dateTime")
    fun loadStatisticsForDate(dateTime: OffsetDateTime): LiveData<DailyStatistics>

    @Query("SELECT offset_date_time FROM time_series_case_data ORDER BY offset_date_time DESC")
    fun loadDates(): List<OffsetDateTime>

}