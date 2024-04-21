package com.chanyoung.jack.application

import android.content.Context
import androidx.room.Room
import com.chanyoung.jack.data.model.Link
import com.chanyoung.jack.data.model.LinkGroup
import com.chanyoung.jack.data.repository.GroupRepositoryImpl
import com.chanyoung.jack.data.repository.LinkRepositoryImpl
import com.chanyoung.jack.data.web.WebScraper
import com.chanyoung.jack.data.room.dao.LinkDao
import com.chanyoung.jack.data.room.dao.LinkGroupDao
import com.chanyoung.jack.data.room.database.JDatabase
import com.chanyoung.jack.ui.viewmodel.paging.basic.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("test_db")
    fun provideInMemoryDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            JDatabase::class.java
        ).allowMainThreadQueries().build()
}


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
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideLinkDao(db: JDatabase): LinkDao = db.getLinkDao()

    @Provides
    fun provideLinkRepo(linkDao: LinkDao, groupDao: LinkGroupDao): LinkRepositoryImpl =
        LinkRepositoryImpl(linkDao, groupDao)


    @Provides
    fun provideGroupDao(db: JDatabase): LinkGroupDao = db.getGroupDao()

    @Provides
    fun provideGroupRepo(groupDao: LinkGroupDao): GroupRepositoryImpl =
        GroupRepositoryImpl(groupDao)
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideWebScrapperRepo(okHttpClient: OkHttpClient): WebScraper =
        WebScraper(okHttpClient)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
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

@Module
@InstallIn(SingletonComponent::class)
class OtherModules {
    @Provides
    @Singleton
    fun provideListLinkPagingSourceParamsProvider(
        linkRepo: LinkRepositoryImpl
    ): PagingSourceParamsProvider<Link> {
        return ListLinkPagingSourceParamsProvider(linkRepo)
    }

    @Singleton
    @Provides
    fun provideListGroupPagingSourceParamsProvider(
        groupRepo: GroupRepositoryImpl
    ): PagingSourceParamsProvider<LinkGroup> {
        return ListGroupPagingSourceParamsProvider(groupRepo)
    }


    @Provides
    @Singleton
    @LinkInGroupProvider
    fun provideListLinkInGroupIdPagingSourceParamsProvider(
        linkRepo: LinkRepositoryImpl
    ) : PagingSourceParamsProvider<Link> {
        return ListLinkInGroupPagingSourceParamsProvider(linkRepo)
    }

    @Provides
    @Singleton
    @SearchProvider
    fun provideListLinkSearchPagingSourceParamsProvider(
        linkRepo: LinkRepositoryImpl
    ) : PagingSourceParamsProvider<Link> {
        return ListLinkSearchPagingSourceParamsProvider(linkRepo)
    }

}