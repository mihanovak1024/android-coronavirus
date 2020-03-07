package com.mihanovak1024.coronavirus.data.model.timeseries

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
        tableName = "time_series_case_place"
)
data class TimeSeriesCasePlace(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "place_id")
        var placeId: Int = 0,
        var state: String,
        var country: String,
        @ColumnInfo(name = "lat")
        var latitude: Double,
        @ColumnInfo(name = "lon")
        var longitude: Double
) {
    fun stateCountry(): String = "$state-$country"
}