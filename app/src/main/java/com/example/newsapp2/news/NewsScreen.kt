package com.example.newsapp2.news


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.newsapp2.NewsScreenContent
import com.example.newsapp2.R
import com.example.newsapp2.api.model.ApiManager
import com.example.newsapp2.api.model.ArticlesItem
import com.example.newsapp2.api.model.NewsResponse
import com.example.newsapp2.api.model.SourcesItem
import com.example.newsapp2.api.model.SourcesResponse
import com.example.newsapp2.ui.theme.black
import com.example.newsapp2.ui.theme.gray
import com.example.newsapp2.ui.theme.white
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsScreen(
    categoryAPIKEY: String,
    viewModel: NewsViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    val sourcesList = viewModel.sourcesList
    val articlesList = viewModel.articlesList

    LaunchedEffect(Unit) {
        viewModel.getSources(categoryAPIKEY)
    }

    LaunchedEffect(viewModel.selectedSourceId.value) {
        viewModel.getNewsByResource()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (sourcesList.isNotEmpty())
            SourceTabsLazyRow(sources = sourcesList) { sourceId ->
                articlesList.clear()
                viewModel.selectedSourceId.value = sourceId

            }
        NewsList(articlesList)

        if (viewModel.isLoading.value) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = white)
            }
        }

        if (viewModel.errorState.value.isNotEmpty()) {
            ErrorDialog(viewmodel = viewModel)
        }

    }

}

@Composable
fun ErrorDialog(modifier: Modifier = Modifier, viewmodel: NewsViewModel) {
    AlertDialog(
        onDismissRequest = { viewmodel.errorState.value = "" },
        confirmButton = {
            TextButton(onClick = { viewmodel.errorState.value = "" }) {
                Text(text = stringResource(R.string.ok))
            }
        }, title = {
            Text(text = viewmodel.errorState.value, color = black, fontSize = 20.sp)
        }
        , containerColor = white
    )
}

@Preview
@Composable
private fun ErrorDialogPrev() {
    ErrorDialog(viewmodel = viewModel())
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

