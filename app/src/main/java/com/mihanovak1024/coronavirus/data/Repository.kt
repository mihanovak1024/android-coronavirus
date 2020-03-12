package com.mihanovak1024.coronavirus.data

import androidx.lifecycle.LiveData
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import org.threeten.bp.OffsetDateTime

interface Repository {

    fun getTimeSeriesCaseAllDataForDate(date: OffsetDateTime): LiveData<List<TimeSeriesCaseData>>

    fun getTimeSeriesCaseDataForLastDateAndCountry(country: String): LiveData<TimeSeriesCaseData>

    fun getInfectedCountries(): LiveData<List<String>>

}