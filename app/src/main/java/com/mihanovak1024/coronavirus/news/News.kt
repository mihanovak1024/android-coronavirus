package com.mihanovak1024.coronavirus.news

import java.time.LocalDateTime

private const val SHORT_DESCRIPTION_MAX_LENGTH = 200

data class News(
        val title: String,
        val description: String,
        val shortDescription: String = if (description.length < SHORT_DESCRIPTION_MAX_LENGTH) description.substring(0, SHORT_DESCRIPTION_MAX_LENGTH) else description,
        val linkUrl: String,
        val dateReadable: String,
        val dateOfNews: LocalDateTime
)