package com.books.app.remote

import android.util.Log
import com.books.app.domain.entities.Book
import com.books.app.domain.entities.BookResponse
import com.books.app.domain.entities.TopBannerSlide
import com.books.app.presentation.viewmodels.MainViewModel
import com.google.common.reflect.TypeToken
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.gson.Gson
import javax.inject.Inject

class FirebaseRemoteConfigDataExtractor @Inject constructor() {
    companion object {
        private var booksList: List<Book> = listOf()
        private var topBannerSlideList: List<TopBannerSlide> = listOf()
        private var youWillLikList: List<Int> = listOf()
    }

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun extractDataFromRemoteConfig(viewModel: MainViewModel) {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("CONFIG", "Config fetched successfully!")
                val jsonString = remoteConfig.getString("json_data")

                if (jsonString.isNotEmpty()) {
                    val gson = Gson()
                    val type = object : TypeToken<BookResponse>() {}.type
                    val response: BookResponse = gson.fromJson(jsonString, type)

                    booksList = response.books
                    topBannerSlideList = response.top_banner_slides
                    youWillLikList = response.you_will_like_section

                    viewModel.dataFetchedSuccessfully()
                }
            } else {
                Log.d("CONFIG", "Error while fetching config data!")
                viewModel.dataFetchedWithError()
            }
        }
    }

    fun extractTopBannerSlidesList(): List<TopBannerSlide> {
        return topBannerSlideList
    }

    fun extractBookList(): List<Book> {
        return booksList
    }

    fun extractYouWillLikeCarouselList(): List<Int> {
        return youWillLikList
    }
}