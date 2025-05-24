package com.example.newsapp2.categories


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.data.api.model.NewsScreen2
import com.example.newsapp2.R

import com.example.newsapp2.ui.theme.blackWithOpacity50
import com.example.newsapp2.ui.theme.white

@Composable
fun CategoriesScreen(navController: NavHostController, modifier: Modifier = Modifier) {

    LazyColumn {
        item {
            CategoriesTitleText()
        }
        val list = Category.getCategoriesList()
        items(list.size) { position ->
            CategoryCard(category = list[position], isRight = position % 2 == 0, onCardClick = {
                navController.navigate(NewsScreen2(it))
            })
        }
    }
}

@Composable
fun CategoriesTitleText(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.good_morning_here_is_some_news_for_you),
        color = white,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp
    )
}

@Preview
@Composable
private fun CategoriesScreenPrev() {
    CategoriesScreen(rememberNavController())
}

@Composable
fun CategoryCard(
    category: Category,
    isRight: Boolean = true,
    onCardClick: (categoryID: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isRight) {

    }
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onCardClick(category.apiId ?: "")
        },
        colors = CardDefaults.cardColors(containerColor = white),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .height(200.dp)

    ) {
        if (isRight) {
            Row {
                Image(
                    contentDescription = stringResource(R.string.news_category),
                    painter = painterResource(id = category.imageResId ?: R.drawable.news_app_logo),
                    modifier = Modifier.fillMaxWidth(0.37F),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.weight(0.5F))
                Column(
                    modifier
                        .fillMaxHeight()
                        .padding(end = 18.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = category.titleResId ?: R.string.news_app_logo),
                        fontSize = 24.sp
                    )
                    ViewAllText(isRight = isRight)
                }

            }
        } else {
            Row {
                Column(
                    modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = stringResource(id = category.titleResId ?: R.string.news_app_logo),
                        fontSize = 24.sp
                    )
                    ViewAllText(isRight = isRight)
                }
                Spacer(modifier.weight(1F))
                Image(
                    contentDescription = stringResource(R.string.news_category),
                    painter = painterResource(id = category.imageResId ?: R.drawable.news_app_logo),
                    modifier = Modifier
                        .padding(end = 50.dp)
                        .fillMaxWidth(0.5F)
                        .fillMaxHeight()
                        .scale(2F),

                    )

            }
        }


    }
}

@Composable
fun ViewAllText(modifier: Modifier = Modifier, isRight: Boolean = true) {
    Row(
        modifier = modifier.background(blackWithOpacity50, shape = CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isRight) {
            Text(
                stringResource(R.string.view_all), modifier = modifier.padding(18.dp), color = white
            )
            Image(
                painter = painterResource(id = R.drawable.view_all_right_arrow),
                contentDescription = stringResource(
                    R.string.category_view_all
                )
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.view_all_right_arrow),
                contentDescription = stringResource(
                    R.string.category_view_all
                )
            )
            Text(
                stringResource(R.string.view_all), modifier = modifier.padding(18.dp), color = white
            )
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun RightArrowCategoryCardPrev() {
    CategoryCard(category = Category.getCategoriesList().get(0), onCardClick = {})
}

@Composable
fun LeftArrowCategoryCard(category: Category, modifier: Modifier = Modifier) {

}