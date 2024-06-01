package com.books.app.data

import com.books.app.domain.MainRepository
import com.books.app.domain.entities.Book
import com.books.app.domain.entities.TopBannerSlide
import com.books.app.remote.FirebaseRemoteConfigDataExtractor
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val firebaseRemoteConfigDataExtractor: FirebaseRemoteConfigDataExtractor,
): MainRepository {
    override suspend fun getTopBannerSlidesListFromRemoteConfig(): List<TopBannerSlide> {
        return firebaseRemoteConfigDataExtractor.extractTopBannerSlidesList()
    }

    override suspend fun getMainBookListFromRemoteConfig(): List<Book> {
        return firebaseRemoteConfigDataExtractor.extractBookList()
    }

    override suspend fun getYouWillLikeListFromRemoteConfig(): List<Int> {
        return firebaseRemoteConfigDataExtractor.extractYouWillLikeCarouselList()
    }
}