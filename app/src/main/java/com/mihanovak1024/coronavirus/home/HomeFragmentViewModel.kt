package com.mihanovak1024.coronavirus.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mihanovak1024.coronavirus.data.Repository
import com.mihanovak1024.coronavirus.data.model.DailyStatistics
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

class HomeFragmentViewModel(
        repository: Repository
) : ViewModel() {

    val numberDailyStatistics: LiveData<DailyStatistics> = repository.getStatisticsForDate(OffsetDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIDNIGHT, ZoneOffset.UTC))
}