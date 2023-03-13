package com.herdal.videogamehub.di

import android.content.Context
import androidx.room.Room
import com.herdal.videogamehub.data.local.AppDatabase
import com.herdal.videogamehub.utils.constants.DatabaseConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DatabaseConstants.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideGameDao(db: AppDatabase) = db.gameDao()

    @Singleton
    @Provides
    fun provideGameRemoteKeyDao(db: AppDatabase) = db.gameRemoteKeyDao()

    @Singleton
    @Provides
    fun provideGenreDao(db: AppDatabase) = db.genreDao()

    @Singleton
    @Provides
    fun provideFavoriteGameDao(db: AppDatabase) = db.favoriteGameDao()

    @Singleton
    @Provides
    fun provideStoreDao(db: AppDatabase) = db.storeDao()

    @Singleton
    @Provides
    fun provideTagDao(db: AppDatabase) = db.tagDao()

    @Singleton
    @Provides
    fun provideScreenshotDao(db: AppDatabase) = db.screenShotDao()

    @Singleton
    @Provides
    fun provideTrailerDao(db: AppDatabase) = db.trailerDao()
}