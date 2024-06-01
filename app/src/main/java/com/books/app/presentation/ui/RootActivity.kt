package com.books.app.presentation.ui

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.books.app.presentation.ui.theme.TestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RootActivityScreen()
                }
            }
        }
    }
}

@Composable
fun RootActivityScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable("SplashScreen") {
            SplashScreen(navController)
        }

        composable("MainScreen") {
            MainScreen(navController)
        }

        composable("DetailsScreen/{selectedBookId}") { backStackEntry ->
            val selectedBookIdString = backStackEntry.arguments?.getString("selectedBookId")
            val selectedBookId = selectedBookIdString?.toIntOrNull()
            if (selectedBookId != null) {
                DetailsScreen(navController, selectedBookId)
            } else {
                Toast.makeText(context, "Ooops, something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}







@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "MainActivityLightPreview")
@Composable
fun MainActivityLightPreview() {
    TestAppTheme {
        RootActivityScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "MainActivityDarkPreview")
@Composable
fun MainActivityDarkPreview() {
    TestAppTheme {
        RootActivityScreen()
    }
}