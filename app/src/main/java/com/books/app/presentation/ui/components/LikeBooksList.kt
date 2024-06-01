package com.books.app.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.books.app.R
import com.books.app.domain.entities.Book
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LikeBookList(
    listTitle: String,
    likedList: List<Int>,
    allBookList: List<Book>,
    itemClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = listTitle,
            modifier = Modifier.padding(24.dp, 16.dp, 24.dp, 0.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
            )
        )

        LazyRow(
            contentPadding = PaddingValues(20.dp, 4.dp, 20.dp, 4.dp),
            modifier = Modifier.wrapContentHeight()
        ) {
            items(likedList) {item ->
                Column(
                    modifier = Modifier
                        .width(180.dp)
                        .clickable { itemClicked(item) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlideImage(
                        model = allBookList[item].cover_url,
                        contentDescription = "Book Image",
                        contentScale = ContentScale.Crop,
                        loading = placeholder(R.drawable.loading_image_background),
                        failure = placeholder(R.drawable.loading_image_background),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                            .padding(vertical = 4.dp, horizontal = 4.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Text(
                        text = allBookList[item].name,
                        color = Color.Black,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        textAlign = TextAlign.Start,
                        minLines = 2,
                        modifier = Modifier
                            .padding(4.dp, 0.dp, 4.dp, 4.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontSize = 22.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Light,
                            letterSpacing = 0.sp,
                            lineHeight = 15.sp,
                            fontStyle = FontStyle.Normal
                        )
                    )
                }
            }
        }
    }
}