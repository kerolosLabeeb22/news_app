package com.example.newsapp2

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

import com.example.newsapp2.ui.theme.black


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashScreenContent()
            LaunchedEffect(Unit) {
                android.os.Handler(Looper.getMainLooper())
                    .postDelayed({
                        val intent= Intent(this@SplashActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },2500)
            }
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(color = black)
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.news_app_logo),
            contentDescription = stringResource(R.string.news_app_logo),
            modifier = modifier.fillMaxHeight(0.25f),
            contentScale = ContentScale.Crop

        )
        Image(
            painter = painterResource(id = R.drawable.news_app_signature),
            contentDescription = stringResource(R.string.news_app_signature),
            modifier =Modifier.fillMaxWidth(0.4F)
                .align(alignment = Alignment.BottomCenter)
            , contentScale = ContentScale.Crop

        )

    }
}

@Preview(showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreenContent()
}

//LaunchedEffect(Unit) {
//    android.os.Handler(Looper.getMainLooper())
//        .postDelayed({
//            val intent =Intent(this@SplashActivity,MainActivity::class.java)
//            startActivity(intent)
//        },2_500)
//}