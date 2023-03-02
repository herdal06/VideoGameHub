package com.herdal.videogamehub.di

import com.herdal.videogamehub.data.repository.GameRepositoryImpl
import com.herdal.videogamehub.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @Binds
    abstract fun bindGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository
}