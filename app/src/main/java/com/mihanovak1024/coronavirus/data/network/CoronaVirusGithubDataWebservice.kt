package com.mihanovak1024.coronavirus.data.network

import retrofit2.Call
import retrofit2.http.GET

interface CoronaVirusGithubDataWebservice {

    @GET("/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv")
    fun getCoronaVirusDataConfirmedCases(): Call<String>

    @GET("/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv")
    fun getCoronaVirusDataDeathCases(): Call<String>

    @GET("/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv")
    fun getCoronaVirusDataRecoveredCases(): Call<String>

}