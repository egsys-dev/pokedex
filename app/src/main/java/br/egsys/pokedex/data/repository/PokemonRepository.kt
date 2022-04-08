package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.PokemonDtoWithCount
import kotlinx.coroutines.flow.StateFlow

interface PokemonRepository {

    val pokemon: LiveData<PokemonDto>
    val pokemons: StateFlow<PokemonDtoWithCount>

    val pokemonState: StateFlow<NetworkState>
    val pokemonsState: StateFlow<NetworkState>
    val paginationState: StateFlow<NetworkState>

    suspend fun getPokemonByName(name: String)
    suspend fun getPokemons(limit: Int, offSet: Int)
    suspend fun getRandomPokemon(idRandom: Int)
}
