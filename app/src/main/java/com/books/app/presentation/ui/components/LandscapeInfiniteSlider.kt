package com.books.app.presentation.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.books.app.R
import com.books.app.domain.entities.TopBannerSlide
import com.books.app.presentation.ui.theme.TopBarTitleColor
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@Composable
@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
fun LandscapeSlider(
    sliderList: List<TopBannerSlide>,
    itemClicked: (Int) -> Unit
) {
    val pagerState  = rememberPagerState(
        pageCount = sliderList.size,
        initialPage = 0,
        infiniteLoop = true
    )

    LaunchedEffect(Unit){
        while (true){
            yield()
            delay(3000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(12.dp)
        ) { page ->
            Card(modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                }
                .fillMaxWidth(),
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                val sliderItem = sliderList[page]
                Box(modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                ) {
                    GlideImage(
                        model = sliderItem.cover,
                        contentDescription = "Book Cover",
                        contentScale = ContentScale.Crop,
                        loading = placeholder(R.drawable.loading_image_background),
                        failure = placeholder(R.drawable.loading_image_background),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { itemClicked(sliderItem.book_id) }
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            inactiveColor = Color.Gray,
            activeColor = TopBarTitleColor,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}