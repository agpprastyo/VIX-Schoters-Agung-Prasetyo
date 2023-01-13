package com.agprastyo.newsapplication.models


class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)




