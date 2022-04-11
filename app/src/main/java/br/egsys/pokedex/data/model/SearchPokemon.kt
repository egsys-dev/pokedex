package br.egsys.pokedex.data.model

sealed class SearchPokemon {
    object Initial : SearchPokemon()
    object Loading : SearchPokemon()
    data class Loaded(val pokemonsView: List<PokemonView>) : SearchPokemon()
    object Empty : SearchPokemon()
}
