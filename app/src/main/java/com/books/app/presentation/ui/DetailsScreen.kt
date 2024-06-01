package com.books.app.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.books.app.R
import com.books.app.presentation.ui.components.LikeBookList
import com.books.app.presentation.ui.components.PortraitSlider
import com.books.app.presentation.ui.theme.MainActivityBackgroundColor
import com.books.app.presentation.ui.theme.SplashTitleTextColor
import com.books.app.presentation.ui.theme.White
import com.books.app.presentation.viewmodels.MainViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    selectedBookIdClicked: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainActivityBackgroundColor)
    ) {
        val mainViewModel: MainViewModel = hiltViewModel()

        mainViewModel.getMainBookList()
        mainViewModel.getYouWillLikeList()

        val mainListState by mainViewModel.mainListResponse.observeAsState()
        val youWillLikeListState by mainViewModel.youWillLikeResponse.observeAsState()

        var readerStateValue by rememberSaveable { mutableStateOf("") }
        var likesStateValue by rememberSaveable { mutableStateOf("") }
        var quotesStateValue by rememberSaveable { mutableStateOf("") }
        var genreStateValue by rememberSaveable { mutableStateOf("") }
        var summaryStateValue by rememberSaveable { mutableStateOf("") }

        LaunchedEffect(mainListState, youWillLikeListState) {
            mainListState?.let {
                readerStateValue = it[selectedBookIdClicked].views
                likesStateValue = it[selectedBookIdClicked].likes
                quotesStateValue = it[selectedBookIdClicked].quotes
                genreStateValue = it[selectedBookIdClicked].genre
                summaryStateValue = it[selectedBookIdClicked].summary
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
        ) {
            if (mainListState != null) {
                PortraitSlider(
                    sliderList = mainListState!!,
                    selectedPage = selectedBookIdClicked,
                    itemSelected = {
                        readerStateValue = it.views
                        likesStateValue = it.likes
                        quotesStateValue = it.quotes
                        genreStateValue = it.genre
                        summaryStateValue = it.summary
                    },
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Button(
                modifier = Modifier.padding(top = 24.dp),
                onClick = { navController.popBackStack() },

                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button",
                    tint = White
                )
            }

        }

        Box(modifier = Modifier
            .fillMaxSize()
            .weight(0.9f)
            .background(MainActivityBackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(White)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 16.dp, 24.dp, 8.dp)
                        .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = readerStateValue,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = stringResource(id = R.string.readers_str),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = likesStateValue,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = stringResource(id = R.string.likes_str),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = quotesStateValue,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = stringResource(id = R.string.quotes_str),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = genreStateValue,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = stringResource(id = R.string.genre_str),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp
                            )
                        )
                    }
                }

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        text = stringResource(id = R.string.summary_str),
                        modifier = Modifier.padding(24.dp, 16.dp, 8.dp, 0.dp),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            fontSize = 20.sp,
                        )
                    )

                    Text(
                        text = summaryStateValue,
                        modifier = Modifier.padding(24.dp, 8.dp, 24.dp, 16.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            fontSize = 14.sp,
                        )
                    )
                }

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 24.dp))

                if (youWillLikeListState != null && mainListState != null) {
                    LikeBookList(
                        listTitle = stringResource(id = R.string.you_will_also_like_str),
                        likedList = youWillLikeListState!!,
                        allBookList = mainListState!!,
                        itemClicked = {  }
                    )
                }
                
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 48.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(SplashTitleTextColor),
                    shape = ShapeDefaults.ExtraLarge,
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = stringResource(id = R.string.read_now_str),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp).fillMaxWidth())
            }
        }
    }
}