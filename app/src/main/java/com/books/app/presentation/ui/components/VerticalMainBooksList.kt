package com.books.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.books.app.domain.entities.Book
import com.books.app.domain.entities.TopBannerSlide
import com.books.app.presentation.ui.theme.MainActivityBackgroundColor
import java.util.TreeSet

@Composable
fun VerticalMainBooksList(
    genresSet: TreeSet<String>,
    mainListState: List<Book>?,
    sliderList: List<TopBannerSlide>?,
    onItemBannerClicked: (Int) -> Unit,
    itemClicked: (Book) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true,
        contentPadding = PaddingValues(bottom = 36.dp)
    ) {
        // Set LandscapeSlider as part as list
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(MainActivityBackgroundColor)
            ) {
                if (sliderList != null) {
                    LandscapeSlider(
                        sliderList = sliderList,
                        itemClicked = { onItemBannerClicked(it) })
                }
            }
        }

        // Horizontal list with books info and genres
        items(genresSet.toList()) {genreItem ->
            HorizontalBooksList(
                genreTitle = genreItem,
                bookList = mainListState!!,
                itemClicked = { itemClicked(it) })
        }
    }
}