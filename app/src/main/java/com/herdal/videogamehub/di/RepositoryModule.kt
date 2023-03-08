package com.herdal.videogamehub.di

import com.herdal.videogamehub.data.repository.*
import com.herdal.videogamehub.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @Binds
    abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun bindGenreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository

    @Binds
    abstract fun bindFavoriteGameRepository(favoriteGameRepositoryImpl: FavoriteGameRepositoryImpl): FavoriteGameRepository

    @Binds
    abstract fun bindStoreRepository(storeRepositoryImpl: StoreRepositoryImpl): StoreRepository

    @Binds
    abstract fun bindTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository

    @Binds
    abstract fun bindScreenshotRepository(screenshotRepositoryImpl: ScreenshotRepositoryImpl): ScreenshotRepository
}