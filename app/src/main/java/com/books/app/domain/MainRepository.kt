package com.books.app.domain

import com.books.app.domain.entities.Book
import com.books.app.domain.entities.TopBannerSlide

interface MainRepository {
    suspend fun getTopBannerSlidesListFromRemoteConfig(): List<TopBannerSlide>

    suspend fun getMainBookListFromRemoteConfig(): List<Book>

    suspend fun getYouWillLikeListFromRemoteConfig(): List<Int>
}