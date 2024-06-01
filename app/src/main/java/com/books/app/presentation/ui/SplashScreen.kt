package com.books.app.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.books.app.R
import com.books.app.presentation.ui.theme.SplashTitleTextColor
import com.books.app.presentation.ui.theme.TestAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    val launchMainScreenDelay: Long = 2000

    LaunchedEffect(Unit) {
        delay(launchMainScreenDelay)
        navController.navigate("MainScreen")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_fon),
            contentDescription = "Splash background",
            modifier = Modifier.fillMaxSize(1f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = SplashTitleTextColor,
                    fontSize = 48.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            )

            Text(
                text = stringResource(id = R.string.welcome_str),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                    fontStyle = FontStyle.Normal
                )
            )

            Spacer(modifier = Modifier.padding(top = 4.dp, bottom = 36.dp))

            LinearProgressIndicator(
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .wrapContentSize()
                    .height(5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "SplashActivityLightPreview")
@Composable
fun SplashActivityLightPreview() {
    TestAppTheme {
        SplashScreen(rememberNavController())
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "SplashActivityDarkPreview")
@Composable
fun SplashActivityDarkPreview() {
    TestAppTheme {
        SplashScreen(rememberNavController())
    }
}
