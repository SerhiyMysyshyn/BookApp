package com.books.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.data.MainRepositoryImpl
import com.books.app.domain.entities.Book
import com.books.app.domain.entities.TopBannerSlide
import com.books.app.remote.FirebaseRemoteConfigDataExtractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepositoryImpl,
    private val firebaseRemoteConfigDataExtractor: FirebaseRemoteConfigDataExtractor
) : ViewModel() {
    val dataFetchedSuccessfully: MutableLiveData<Boolean> = MutableLiveData()

    val topBannerResponse: MutableLiveData<List<TopBannerSlide>> = MutableLiveData()
    val mainListResponse: MutableLiveData<List<Book>> = MutableLiveData()
    val youWillLikeResponse: MutableLiveData<List<Int>> = MutableLiveData()

    fun startFirebaseDataExtractor() {
        viewModelScope.launch {
            firebaseRemoteConfigDataExtractor.extractDataFromRemoteConfig(this@MainViewModel)
        }
    }

    fun dataFetchedSuccessfully() {
        dataFetchedSuccessfully.value = true
    }

    fun dataFetchedWithError() {
        dataFetchedSuccessfully.value = false
    }

    fun getTopBannerSlides() {
        viewModelScope.launch {
            topBannerResponse.value = mainRepository.getTopBannerSlidesListFromRemoteConfig()
        }
    }

    fun getMainBookList() {
        viewModelScope.launch {
            mainListResponse.value = mainRepository.getMainBookListFromRemoteConfig()
        }
    }

    fun getYouWillLikeList() {
        viewModelScope.launch {
            youWillLikeResponse.value = mainRepository.getYouWillLikeListFromRemoteConfig()
        }
    }
}