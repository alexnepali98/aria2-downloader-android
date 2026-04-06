package com.aria2.downloader.di

import android.content.Context
import com.aria2.downloader.data.local.AppDatabase
import com.aria2.downloader.data.repository.DownloadRepository
import com.aria2.downloader.domain.engine.DownloadEngine
import com.aria2.downloader.util.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return HttpClientFactory.createHttpClient(context, enableLogging = false)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideDownloadRepository(
        database: AppDatabase
    ): DownloadRepository {
        return DownloadRepository(database)
    }

    @Singleton
    @Provides
    fun provideDownloadEngine(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): DownloadEngine {
        return DownloadEngine(context, okHttpClient, maxConnections = 4)
    }
}
