package br.egsys.pokedex.data.model

sealed class SearchPokemon {
    object Loading : SearchPokemon()
    data class Loaded(val pokemons: List<PokemonName>) : SearchPokemon()
    object Empty : SearchPokemon()
    data class Failed(val e: Exception) : SearchPokemon()
}
