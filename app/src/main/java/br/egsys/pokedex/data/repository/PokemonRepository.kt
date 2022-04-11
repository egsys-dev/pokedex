package br.egsys.pokedex.data.repository

import br.egsys.pokedex.data.model.PokemonsState

interface PokemonRepository {

    suspend fun getPokemons(limit: Int, offSet: Int): PokemonsState
    suspend fun getRandomPokemon(idRandom: Int): PokemonsState
}
