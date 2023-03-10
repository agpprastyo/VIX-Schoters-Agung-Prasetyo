package com.agprastyo.newsapplication.repository

import androidx.room.Query
import com.agprastyo.newsapplication.api.RetrofitInstance
import com.agprastyo.newsapplication.db.ArticleDatabase
import com.agprastyo.newsapplication.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) =db.getArticleDao().deleteArticle(article)

}