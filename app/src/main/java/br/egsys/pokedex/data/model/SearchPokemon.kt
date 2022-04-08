package br.egsys.pokedex.data.model

import br.egsys.pokedex.data.dto.PokemonDto

sealed class SearchPokemon {
    object Loading : SearchPokemon()
    data class Loaded(val pokemonsDto: List<PokemonDto>) : SearchPokemon()
    object Empty : SearchPokemon()
    data class Failed(val e: Exception) : SearchPokemon()
}
