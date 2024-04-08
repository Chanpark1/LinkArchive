package com.chanyoung.jack.application

import android.content.Context
import androidx.room.Room
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.repository.networking.WebScraperRepository
import com.chanyoung.jack.data.room.dao.LinkDao
import com.chanyoung.jack.data.room.dao.LinkGroupDao
import com.chanyoung.jack.data.room.database.JDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModules {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            JDatabase::class.java,
            "link_db"
        ).fallbackToDestructiveMigration().build()

    // Link
    @Provides
    fun provideLinkDao(db: JDatabase): LinkDao = db.getLinkDao()

    @Provides
    fun provideLinkRepo(linkDao: LinkDao): LinkRepositoryImpl = LinkRepositoryImpl(linkDao)


    // Group
    @Provides
    fun provideGroupDao(db: JDatabase): LinkGroupDao = db.getGroupDao()

    @Provides
    fun provideGroupRepo(groupDao: LinkGroupDao): GroupRepositoryImpl = GroupRepositoryImpl(groupDao)


}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideWebScrapperRepo(okHttpClient: OkHttpClient) : WebScraperRepository = WebScraperRepository(okHttpClient)

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

}
