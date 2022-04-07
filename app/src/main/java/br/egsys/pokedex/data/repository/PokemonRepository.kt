package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.PokemonWithCount
import kotlinx.coroutines.flow.StateFlow

interface PokemonRepository {

    val pokemon: LiveData<Pokemon>
    val pokemons: StateFlow<PokemonWithCount>

    val pokemonState: StateFlow<NetworkState>
    val pokemonsState: StateFlow<NetworkState>

    suspend fun getPokemonById(id: Long)
    suspend fun getPokemonByName(name: String)
    suspend fun getPokemons(limit: Int, offSet: Int)
}
