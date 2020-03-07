package com.mihanovak1024.coronavirus.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mihanovak1024.coronavirus.data.database.time.TimeTypeConverter
import com.mihanovak1024.coronavirus.data.model.DailyStatistics
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCasePlace

@Database(entities = [DailyStatistics::class, TimeSeriesCasePlace::class, TimeSeriesCaseData::class], version = 1, exportSchema = false)
@TypeConverters(TimeTypeConverter::class)
abstract class CoronaVirusDatabase : RoomDatabase() {

    abstract fun timeSeriesCaseDataDao(): TimeSeriesCaseDataDao

    abstract fun timeSeriesCasePlaceDao(): TimeSeriesCasePlaceDao

    abstract fun statisticsDao(): DailyStatisticsDao

}