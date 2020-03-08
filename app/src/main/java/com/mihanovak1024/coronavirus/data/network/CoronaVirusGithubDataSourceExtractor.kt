package com.mihanovak1024.coronavirus.data.network

import com.mihanovak1024.coronavirus.data.CoronaCase
import com.mihanovak1024.coronavirus.data.database.time.TimeTypeConverter
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCasePlace
import com.mihanovak1024.coronavirus.util.zeroPrefixed
import org.threeten.bp.OffsetDateTime

class CoronaVirusGithubDataSourceExtractor {

    /**
     * Extracts the Github CSV response data to a map based on OffsetDateTime and state_country keys
     * and TimeSeriesCaseData values.
     */
    fun addTimeSeriesCaseDataToMap(coronaCase: CoronaCase, response: String, casesMap: LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>>) {
        val lineSeparated = response.lines()

        val dateList = extractCsvDateListStringToMap(lineSeparated.first())
        dateList.forEach {
            if (!casesMap.contains(it)) {
                casesMap[it] = LinkedHashMap()
            }
        }

        // iterate through CSV lines except the first one,
        // since it was already processed above
        lineSeparated.drop(1).forEach {
            // splits each line by "," and preserves quoted sections
            val splitLine = it.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)".toRegex())

            val timeSeriesCasePlace = transformCsvLocationInformationToObject(splitLine)
            val placeString = timeSeriesCasePlace.stateCountry()

            // First 4 sections are about location
            val splitLineIterator = splitLine.drop(4).iterator()
            val dateIterator = dateList.iterator()
            while (splitLineIterator.hasNext()) {
                // There should be the same numbers of dates specified in first line
                // and data specified in all other lines
                val dataForDate = splitLineIterator.next()
                val date = dateIterator.next()

                val mapForDate = casesMap[date]!!
                var mapForPlace = mapForDate[placeString]

                if (mapForPlace == null) {
                    mapForPlace = TimeSeriesCaseData(place = timeSeriesCasePlace, offsetDateTime = date)
                    mapForDate[placeString] = mapForPlace
                }

                when (coronaCase) {
                    CoronaCase.INFECTED -> mapForPlace.infectedCases = dataForDate.toInt()
                    CoronaCase.RECOVERED -> mapForPlace.recoveredCases = dataForDate.toInt()
                    CoronaCase.DEATH -> mapForPlace.deathCases = dataForDate.toInt()
                }
            }
        }
    }

    /**
     * Method responsible for extracting date from string list to [OffsetDateTime] list.
     *
     * The first line of the Github CSV looks like:
     * Province/State,Country/Region,Lat,Long,1/22/20,1/23/20,1/24/20,1/25/20,...
     */
    private fun extractCsvDateListStringToMap(firstCsvLine: String): List<OffsetDateTime> {
        // bottom line splits it by "," and drops the first four sections resulting in list of dates
        return firstCsvLine.split(",").drop(4).map { dateNonFormatted ->
            // Each date of format "month/day/year" is then transformed to OffsetDateTime
            dateNonFormatted.split("/").let {
                val day = it[1].zeroPrefixed()
                val month = it[0].zeroPrefixed()
                val year = "20${it[2]}"
                // Since data is provided daily there's no information of a specific time,
                // thus midnight will do for now
                val time = "T00:00Z"
                val dateTimeFormatted = "${year}-${month}-${day}${time}"
                TimeTypeConverter.toOffsetDateTime(dateTimeFormatted)!!
            }

        }
    }


    /**
     * Method responsible for parsing the first four sections of the line into a location object [TimeSeriesCasePlace].
     *
     * Each line of the Github CSV looks like:
     * Province/State,Country/Region,Lat,Long,1/22/20,1/23/20,1/24/20,1/25/20,...
     *
     * This method extracts Province/State,Country/Region,Lat,Long into [TimeSeriesCasePlace]
     */
    private fun transformCsvLocationInformationToObject(dataListLine: List<String>): TimeSeriesCasePlace {
        val splitLineIterator = dataListLine.listIterator()
        // Format of CSV lines:
        // Province/State, Country/Region, Lat, Long, 1/22/20 data, 1/23/20 data, 1/24/20 data,...
        val state = splitLineIterator.next()
        val country = splitLineIterator.next()
        val latitude = splitLineIterator.next().toDouble()
        val longitude = splitLineIterator.next().toDouble()

        return TimeSeriesCasePlace(
                state = state,
                country = country,
                latitude = latitude,
                longitude = longitude
        )
    }

}