package br.egsys.pokedex.data.repository

import br.egsys.pokedex.data.model.PokemonView
import br.egsys.pokedex.data.model.PokemonsState
import kotlinx.coroutines.flow.StateFlow

interface PokemonRepository {

    val listPokemons: StateFlow<List<PokemonView>>

    suspend fun getPokemons(limit: Int, offSet: Int): PokemonsState
    suspend fun getRandomPokemon(idRandom: Int): PokemonsState
}
