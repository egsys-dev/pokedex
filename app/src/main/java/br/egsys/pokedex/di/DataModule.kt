package br.egsys.pokedex.di

import br.egsys.pokedex.data.repository.PokemonRepository
import br.egsys.pokedex.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindPokemonRepository(
        pokemonRepository: PokemonRepositoryImpl
    ): PokemonRepository
}
