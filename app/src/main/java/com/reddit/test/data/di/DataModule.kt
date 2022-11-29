package com.reddit.test.data.di

import android.content.Context
import com.reddit.test.data.Constants.BASE_URL
import com.reddit.test.data.boundary.DomainMapper
import com.reddit.test.data.repositories.RedditPostRepositoryImpl
import com.reddit.test.data.source.remote.RetrofitService
import com.google.gson.Gson
import com.reddit.test.data.source.local.AppDatabase
import com.reddit.test.data.source.local.PostsDao
import com.reddit.test.domain.repositories.DomainRedditPostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val mCache = Cache(context.cacheDir, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .cache(mCache) // make your app offline-friendly without a database!
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .build()
                chain.proceed(request)
            }
        return client.build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun providesDomainPostsRepository(
        retrofitService: RetrofitService,
        domainMapper: DomainMapper
    ): DomainRedditPostRepository = RedditPostRepositoryImpl(
        retrofitService = retrofitService,
        domainMapper = domainMapper
    )

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context):
            AppDatabase = AppDatabase.getAppDatabase(context.applicationContext)!!


    @Provides
    @Singleton
    fun providesPostsDao(
        appDatabase: AppDatabase
    ): PostsDao = appDatabase.postDao()

}