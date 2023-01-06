package com.agprastyo.newsapplication.repository

import com.agprastyo.newsapplication.api.RetrofitInstance
import com.agprastyo.newsapplication.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}