package com.mihanovak1024.coronavirus.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.statistics.Statistics
import timber.log.Timber

class HomeFragmentViewModel(
        private val repository: Repository
) : ViewModel() {

    private val _numberStatistics = MutableLiveData<Statistics>()

    val numberStatistics: LiveData<Statistics> = _numberStatistics

    suspend fun updateNumberStatistics() {
        Timber.d("Updating statistics")
        _numberStatistics.value = repository.getTimeSeriesCaseAllData().let {
            Statistics(
                    it.casesMap.values.last().values.sumBy { timeSeriesCaseData -> timeSeriesCaseData.infectedCases },
                    it.casesMap.values.last().values.sumBy { timeSeriesCaseData -> timeSeriesCaseData.recoveredCases },
                    it.casesMap.values.last().values.sumBy { timeSeriesCaseData -> timeSeriesCaseData.deathCases }
            )
        }
    }
}