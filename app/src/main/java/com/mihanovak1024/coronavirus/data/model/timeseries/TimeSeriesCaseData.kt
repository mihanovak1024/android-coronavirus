package com.mihanovak1024.coronavirus.data.model.timeseries

import androidx.room.*
import org.threeten.bp.OffsetDateTime

@Entity(
        tableName = "time_series_case_data"
)
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class TimeSeriesCaseData(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null,
        @Embedded
        var place: TimeSeriesCasePlace? = null,
        @ColumnInfo(name = "offset_date_time")
        var offsetDateTime: OffsetDateTime? = null,
        @ColumnInfo(name = "infected_cases")
        var infectedCases: Int = 0,
        @ColumnInfo(name = "death_cases")
        var deathCases: Int = 0,
        @ColumnInfo(name = "recovered_cases")
        var recoveredCases: Int = 0
)