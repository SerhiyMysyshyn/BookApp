package com.books.app.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.books.app.domain.entities.Book
import com.books.app.presentation.ui.components.CustomTopBar
import com.books.app.presentation.ui.components.VerticalMainBooksList
import com.books.app.presentation.ui.theme.MainActivityBackgroundColor
import com.books.app.presentation.viewmodels.MainViewModel
import java.util.TreeSet

@Composable
fun MainScreen(navController: NavController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val dataFetchedSuccessfully by mainViewModel.dataFetchedSuccessfully.observeAsState(false)

    val topBannersSlideState by mainViewModel.topBannerResponse.observeAsState()
    val mainListState by mainViewModel.mainListResponse.observeAsState()

    // States for showing main content in MainScreen
    var showTopContent by rememberSaveable { mutableStateOf(false) }
    var showMainContent by rememberSaveable { mutableStateOf(false) }

    // Starting fetching data from Firebase remote Config
    mainViewModel.startFirebaseDataExtractor()

    // If config data fetched successfully -> show content
    // Otherwise -> show circular loader
    LaunchedEffect(dataFetchedSuccessfully) {
        dataFetchedSuccessfully?.let {
            if (dataFetchedSuccessfully) {
                mainViewModel.getTopBannerSlides()
                mainViewModel.getMainBookList()
            }
        }
    }

    // After successfully fetching get TopBanner data and Books data
    LaunchedEffect(topBannersSlideState) {
        topBannersSlideState?.let {
            if (topBannersSlideState != null) {
                showTopContent = true
            } else {
                showTopContent = false
            }
        }
    }

    LaunchedEffect(mainListState) {
        mainListState?.let {
            if (mainListState != null) {
                showMainContent = true
            } else {
                showMainContent = false
            }
        }
    }

    // Get a list of genres from config and write to a set to exclude all the same values
    // and sort them at the same time
    val sortedGenresSet = TreeSet<String>()
    if (mainListState != null) {
        for (element: Book in mainListState!!) {
            sortedGenresSet.add(element.genre)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopBar() },
        backgroundColor = MainActivityBackgroundColor,
    ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding()))
        {
            if (showMainContent && showTopContent) {
                VerticalMainBooksList(
                    sortedGenresSet,
                    mainListState,
                    topBannersSlideState,
                    // Handle banner slide click
                    onItemBannerClicked = { bookId ->
                        if (mainListState != null) {
                            navController.navigate("DetailsScreen/${bookId}")
                        }
                    },
                    // Handle books list item clicked
                    itemClicked = { book ->
                        if (mainListState != null) {
                            navController.navigate("DetailsScreen/${book.id}")
                        }
                    })
            } else {
                // Show progress indicator while app tries to fetch config data
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}