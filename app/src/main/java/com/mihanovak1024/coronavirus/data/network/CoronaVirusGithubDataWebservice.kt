package com.mihanovak1024.coronavirus.data.network

import retrofit2.Call
import retrofit2.http.GET

/**
 * Polls data from CSEGISandData/COVID-19 Github repo with CSV files
 * that are updated daily.
 */
interface CoronaVirusGithubDataWebservice {

    @GET("/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv")
    fun getCoronaVirusDataConfirmedCases(): Call<String>

    @GET("/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv")
    fun getCoronaVirusDataDeathCases(): Call<String>

    @GET("/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv")
    fun getCoronaVirusDataRecoveredCases(): Call<String>

}