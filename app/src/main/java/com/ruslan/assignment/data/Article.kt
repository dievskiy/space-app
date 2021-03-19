package com.ruslan.assignment.data

import java.util.*

data class Article(
    val id: String,
    val imageUrl: String,
    val publishedAt: Date,
    val summary: String,
    val title: String,
    val url: String
)