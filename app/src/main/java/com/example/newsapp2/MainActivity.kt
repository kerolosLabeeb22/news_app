@file:Suppress("UNCHECKED_CAST")

package com.example.newsapp2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsapp2.api.model.ApiManager
import com.example.newsapp2.api.model.ArticlesItem
import com.example.newsapp2.api.model.NewsResponse
import com.example.newsapp2.api.model.SourcesItem
import com.example.newsapp2.api.model.SourcesResponse
import com.example.newsapp2.ui.theme.NewsApp2Theme
import com.example.newsapp2.ui.theme.black
import com.example.newsapp2.ui.theme.gray
import com.example.newsapp2.ui.theme.white
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

@Composable
fun SourceTabsLazyRow(
    sources: List<SourcesItem>,
    modifier: Modifier = Modifier,
    onTabSelected: (sourceId: String) -> Unit,

    ) {
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }

    val selectedModifier = Modifier.drawBehind {
        val width = size.width
        val height = size.height

        drawLine(
            color = white,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = height),
            strokeWidth = 2.dp.toPx()
        )
    }
    LaunchedEffect(sources) {
        if (sources.isNotEmpty()) {
            onTabSelected(sources[0].id ?: "")
        }
    }
    LazyRow {

        itemsIndexed(sources) { index, sourceitem ->
            Tab(
                modifier = Modifier.padding(horizontal = 8.dp, 2.dp),
                selected = index == selectedIndex.intValue,
                onClick = {
                    onTabSelected(sourceitem.id ?: "")
                    selectedIndex.intValue = index
                }
            ) {
                Text(
                    text = sourceitem.name ?: "",
                    color = white,
                    modifier = if (index == selectedIndex.intValue) selectedModifier else modifier
                )

            }
        }
    }
}

@Preview
@Composable
private fun SourcesTabLazyRowPrev() {
    SourceTabsLazyRow(
        listOf(
            SourcesItem(name = "ABC News"),
            SourcesItem(name = "Al Jazeera News"),
            SourcesItem(name = "CBC News")
        )
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsToolbar(title: String, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets(right = 8.dp, left = 8.dp, top = 26.dp),
        title = {
            Text(text = title)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = black,
            titleContentColor = white,
            navigationIconContentColor = white,
            actionIconContentColor = white
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "menu icon"
            )
        }, actions = {
            Image(
                painter = painterResource(id = R.drawable.search_ic),
                contentDescription = stringResource(
                    R.string.search_icon
                )
            )
        }
    )
}

@Preview
@Composable
private fun NewsToolbarPrev() {
    NewsToolbar(title = "General")
}

@Composable
fun NewsScreenContent(modifier: Modifier = Modifier) {
    val sourcesList = remember {
        mutableStateListOf<SourcesItem>()
    }

    val articlesList = remember {
        mutableStateListOf<ArticlesItem>()
    }
    val selectedSourceId = remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        getSources(onSuccess = { list ->
            sourcesList.addAll(list)


        }, onFailure = {
            Log.e("TAG", "onFailure:$it ")
        })
    }

    LaunchedEffect(selectedSourceId.value) {
        if (selectedSourceId.value.isNotEmpty()) {
            ApiManager.newsService.getNewsBySources(sources = selectedSourceId.value)
                .enqueue(object : Callback<NewsResponse> {
                    override fun onResponse(
                        p0: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        val list = response.body()?.articles
                        if (list?.isNotEmpty() == true) {
                            
                            articlesList.addAll(list as List<ArticlesItem>)
                        }
                    }

                    override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }

    }

    Scaffold(containerColor = black, topBar = {
        NewsToolbar(title = "business")
    }) { PaddingValues ->
        PaddingValues
        Column(
            modifier = Modifier.padding(PaddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (sourcesList.isNotEmpty())
                SourceTabsLazyRow(sources = sourcesList) { sourceId ->
                    articlesList.clear()
                    selectedSourceId.value = sourceId

                }
            NewsList(articlesList)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NewsScreenPrev() {
    NewsScreenContent()

}

@Composable
fun NewsList(articlesList: List<ArticlesItem>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(articlesList) {
            NewsCard(articlesItem = it)
        }
    }
}

@Composable
fun NewsCard(articlesItem: ArticlesItem, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(0.9F)
            .border(1.dp, white, RoundedCornerShape(12.dp))
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = white
        )

    ) {
        AsyncImage(
            model = articlesItem.urlToImage,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            contentDescription = "news article image"
        )

        Text(
            text = articlesItem.title ?: "",
            color = white,
            fontWeight = FontWeight.W700,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,


            )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = (stringResource(R.string.by) + articlesItem.author),
                color = gray,
                fontWeight = FontWeight.W500,
                fontSize = 10.sp,
                modifier = Modifier.fillMaxWidth(0.5F),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
            Spacer(modifier = Modifier.weight(1F))

            Text(
                text = articlesItem.publishedAt ?: "",
                color = gray,
                fontWeight = FontWeight.W500,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
private fun NewsCardPrev() {
    NewsCard(
        ArticlesItem(
            title = "40-year-old man falls 200 feet to his death while canyoneering at national park",
            author = "Jon Haworth"
        )
    )
}

fun getSources(
    onSuccess: (sources: List<SourcesItem>) -> Unit,
    onFailure: (message: String) -> Unit
) {
    ApiManager.newsService.getResource()
        .enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>

            ) {
                val list = response.body()?.sources
                if (list?.isNotEmpty() == true) {
                    onSuccess(list as List<SourcesItem>)
                }

                Log.e("TAG", "onResponse:${response.body()} ")
            }

            override fun onFailure(
                call: Call<SourcesResponse>,
                throwable: Throwable
            ) {
                onFailure(throwable.message ?: "")

            }

        })
}