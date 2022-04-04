package br.egsys.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Long,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val weight: Long,
    val height: Long
)

data class Sprites(
    val other: Other
)

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld
)

data class DreamWorld(
    @SerializedName("front_default")
    val frontDefault: String
)

data class PokemonType(
    val type: Type
)

data class Type(
    val slot: Long,
    val name: String
)
