package br.egsys.pokedex.di

import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.data.mapper.PokemonMap
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.repository.PokemonRepository
import br.egsys.pokedex.data.repository.PokemonRepositoryImpl
import br.egsys.pokedex.data.util.DomainMapper
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

    @Singleton
    @Binds
    abstract fun bindPokemonMap(
        domainMapper: PokemonMap
    ): DomainMapper<PokemonDto, Pokemon>
}
