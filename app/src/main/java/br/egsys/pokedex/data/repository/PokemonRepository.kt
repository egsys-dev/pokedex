package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.Pokemons
import kotlinx.coroutines.flow.StateFlow

interface PokemonRepository {

    val pokemon: LiveData<Pokemon>
    val pokemons: LiveData<Pokemons>

    val pokemonState: StateFlow<NetworkState>
    val pokemonsState: StateFlow<NetworkState>

    suspend fun getPokemonById(id: Long)
    suspend fun getPokemons()
}
