@file:Suppress("UNCHECKED_CAST")

package com.example.newsapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp2.categories.CategoriesScreen
import com.example.newsapp2.news.NewsToolbar
import com.example.newsapp2.ui.theme.NewsApp2Theme
import com.example.newsapp2.ui.theme.black
import androidx.compose.ui.Modifier
import com.example.data.api.model.CategoriesScreen
import com.example.data.api.model.NewsScreen2
import com.example.newsapp2.news.NewsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            NewsApp2Theme {
                NewsScreenContent()
            }
        }
    }
}

@Preview
@Composable
private fun NewsToolbarPrev() {
    NewsToolbar(title = "General")
}

@Composable
fun NewsScreenContent(modifier: Modifier = Modifier) {


    Scaffold(containerColor = black, topBar = {
        NewsToolbar(title = "business")
    }) { PaddingValues ->
        PaddingValues

        val navController = rememberNavController()
        NavHost(
            navController = navController, startDestination = CategoriesScreen,
            modifier = modifier.padding(PaddingValues)
        ) {
            composable<CategoriesScreen> {
                CategoriesScreen(navController)
            }
            composable<NewsScreen2> { navBackStackEntry ->
                val newsScreen = navBackStackEntry.toRoute<NewsScreen2>()
                NewsScreen(newsScreen.categoryApiId)
            }


        }
    }
}
