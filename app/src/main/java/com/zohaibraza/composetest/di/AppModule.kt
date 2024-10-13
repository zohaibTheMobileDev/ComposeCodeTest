package com.zohaibraza.composetest.di

import android.content.Context
import androidx.room.Room
import com.zohaibraza.composetest.BuildConfig
import com.zohaibraza.composetest.data.remote.repository.TracksRemoteRepositoryImplementation
import com.zohaibraza.composetest.domain.database.AppDatabase
import com.zohaibraza.composetest.domain.remoterepository.TracksRemoteRepository
import com.zohaibraza.composetest.data.constants.ApiUrls
import com.zohaibraza.composetest.data.constants.RoomConstants
import com.zohaibraza.composetest.data.local.TrackDao
import com.zohaibraza.composetest.data.remote.TracksApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideTracksApi(): TracksApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiUrls.API_BASE_PATH)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TracksApi::class.java)
    }


    @Provides
    @Singleton
    fun provideTracksRemoteRepository(api: TracksApi): TracksRemoteRepository {
        return TracksRemoteRepositoryImplementation(api)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            RoomConstants.DATA_BASE_NAME
        ).fallbackToDestructiveMigration()  // Handle migration or provide custom migration strategy
            .build()
    }

    @Provides
    fun provideTrackDao(database: AppDatabase): TrackDao {
        return database.trackDao()
    }
}