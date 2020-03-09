package com.mihanovak1024.coronavirus.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCasePlace

@Dao
interface TimeSeriesCasePlaceDao {

    @Insert(onConflict = IGNORE)
    suspend fun saveMultiple(timeSeriesCasePlaces: List<TimeSeriesCasePlace>)

    @Query("SELECT * FROM time_series_case_place")
    fun loadAll(): LiveData<List<TimeSeriesCasePlace>>

    @Query("SELECT DISTINCT country FROM time_series_case_place ORDER BY country ASC")
    fun loadCountries(): LiveData<List<String>>

    @Query("SELECT * FROM time_series_case_place WHERE country = :country")
    fun loadByCountry(country: String): LiveData<TimeSeriesCasePlace>

    @Query("SELECT * FROM time_series_case_place WHERE country = :country AND state = :state")
    fun loadByCountryAndState(country: String, state: String): LiveData<TimeSeriesCasePlace>

}