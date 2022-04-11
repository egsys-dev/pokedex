package br.egsys.pokedex.data.model

sealed class PokemonsState {
    object Initial : PokemonsState()
    data class Loaded(val pokemons: List<PokemonView>, val offSet: Int) : PokemonsState()
    object Loading : PokemonsState()
    data class Failed(val exception: Exception) : PokemonsState()
}
