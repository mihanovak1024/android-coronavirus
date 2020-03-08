package com.mihanovak1024.coronavirus.data.network

import com.mihanovak1024.coronavirus.data.CoronaCase
import com.mihanovak1024.coronavirus.data.model.timeseries.TimeSeriesCaseData
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import org.threeten.bp.OffsetDateTime

@RunWith(MockitoJUnitRunner::class)
class CoronaVirusGithubDataSourceExtractorTest {

    @Test
    fun addTimeSeriesCaseDataToMap_validInfectedCSVFile_successfulResponse() {
        // preconditions
        val csvFileContent = getLocalFileContent("testGithubDataSourceDataInfected.csv")
        val caseData = LinkedHashMap<OffsetDateTime, LinkedHashMap<String, TimeSeriesCaseData>>()

        // test
        val dataSourceExtractor = CoronaVirusGithubDataSourceExtractor()
        dataSourceExtractor.addTimeSeriesCaseDataToMap(CoronaCase.INFECTED, csvFileContent, caseData)

        // assert
        var place = "-Spain"
        var date = OffsetDateTime.parse("2020-01-30T00:00Z")

        assertThat(caseData.size, equalTo(4))
        caseData.forEach {
            assertThat(it.value.size, equalTo(4))
        }
        assertThat(caseData[date]!![place]!!.infectedCases, equalTo(165))
        assertThat(caseData[date]!![place]!!.place!!.longitude, equalTo((-4).toDouble()))

        place = "\" Montreal, QC\"-Canada"
        date = OffsetDateTime.parse("2020-02-01T00:00Z")
        assertThat(caseData[date]!![place]!!.infectedCases, equalTo(11))
        assertThat(caseData[date]!![place]!!.place!!.latitude, equalTo(45.5017))
    }

    private fun getLocalFileContent(fileName: String): String {
        val resourceInputStream = ClassLoader.getSystemResourceAsStream(fileName)
        return resourceInputStream.bufferedReader().use { it.readText() }
    }

}