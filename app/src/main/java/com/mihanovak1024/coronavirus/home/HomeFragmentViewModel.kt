package com.mihanovak1024.coronavirus.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData

class HomeFragmentViewModel(
        repository: Repository
) : ViewModel() {

    val country = MutableLiveData<String>("worldwide")
    val numberDailyStatistics: LiveData<TimeSeriesCaseData> = Transformations.switchMap(country) {
        repository.getTimeSeriesCaseDataForLastDateAndCountry(it)
    }

}