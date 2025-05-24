package com.example.data.di


import android.util.Log
import com.example.data.api.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor{
            Log.e("TAG", it )
        }.apply {
            level= HttpLoggingInterceptor.Level.BODY
        }
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }
    @Singleton
    @Provides
    fun provideRetrofit(
        okhttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService{
        return retrofit.create(NewsService::class.java)
    }
}