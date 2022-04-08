package br.egsys.pokedex.data.dto

data class PokemonDto(
    val id: String,
    val name: String,
    val types: List<String>,
    val weight: Int,
    val height: Int,
    val image: String
)
