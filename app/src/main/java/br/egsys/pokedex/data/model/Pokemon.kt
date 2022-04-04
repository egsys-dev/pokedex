package br.egsys.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val id: Long,
    val name: String,
//    val sprites: Sprites,
//    val types: Types,
//    val weight: Int,
//    val height: Int
)

data class Sprites(
    val other: Other
)

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: FrontDefault
)

data class FrontDefault(
    @SerializedName("front_default")
    val frontDefault: String
)

data class Types(
    val type: Type
)

data class Type(
    val name: String
)
