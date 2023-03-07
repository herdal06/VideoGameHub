package com.herdal.videogamehub.di

import com.herdal.videogamehub.data.repository.FavoriteGameRepositoryImpl
import com.herdal.videogamehub.data.repository.GameRepositoryImpl
import com.herdal.videogamehub.data.repository.GenreRepositoryImpl
import com.herdal.videogamehub.data.repository.StoreRepositoryImpl
import com.herdal.videogamehub.domain.repository.FavoriteGameRepository
import com.herdal.videogamehub.domain.repository.GameRepository
import com.herdal.videogamehub.domain.repository.GenreRepository
import com.herdal.videogamehub.domain.repository.StoreRepository
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
}