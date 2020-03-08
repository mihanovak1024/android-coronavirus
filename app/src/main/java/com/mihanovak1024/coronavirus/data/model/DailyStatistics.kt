package com.mihanovak1024.coronavirus.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "statistics")
data class DailyStatistics(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "statistics_id")
        var statisticsId: Int = 0,
        var date: OffsetDateTime? = null,
        @ColumnInfo(name = "infected_number")
        var infectedNumber: Int = 0,
        @ColumnInfo(name = "recovered_number")
        var recoveredNumber: Int = 0,
        @ColumnInfo(name = "death_number")
        var deathNumber: Int = 0
)