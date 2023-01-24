package com.example.paging3demo.di

import android.content.Context
import androidx.room.Room
import com.example.paging3demo.BuildConfig
import com.example.paging3demo.data.db.TwitterDatabase
import com.example.paging3demo.data.network.TwitterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.twitter.com/2/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideTwitterDatabase(@ApplicationContext appContext: Context): TwitterDatabase {
        return Room.databaseBuilder(
            appContext,
            TwitterDatabase::class.java,
            "TwitterDatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): TwitterService = retrofit.create(TwitterService::class.java)
}