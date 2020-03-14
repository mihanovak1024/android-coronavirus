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
    val numberDailyStatistics: LiveData<List<TimeSeriesCaseData>> = Transformations.switchMap(country) {
        repository.getTimeSeriesCaseDataForLastDateAndCountry(it)
    }

    val infected = MutableLiveData<String>("0")
    val recovered = MutableLiveData<String>("0")
    val deaths = MutableLiveData<String>("0")

}