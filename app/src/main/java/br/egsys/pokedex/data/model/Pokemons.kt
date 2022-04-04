package br.egsys.pokedex.data.model

data class Pokemons(
    val count: Long,
    val next: String,
    val previous: String?,
    val results: List<PokemonName>
)

data class PokemonName(
    val name: String,
    val url: String
)
