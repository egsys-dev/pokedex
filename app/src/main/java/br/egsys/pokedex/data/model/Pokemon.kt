package br.egsys.pokedex.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val id: Long,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val weight: Long,
    val height: Long
) : Parcelable

@Parcelize
data class Sprites(
    val other: Other
) : Parcelable

@Parcelize
data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld
) : Parcelable

@Parcelize
data class DreamWorld(
    @SerializedName("front_default")
    val frontDefault: String
) : Parcelable

@Parcelize
data class PokemonType(
    val type: Type
) : Parcelable

@Parcelize
data class Type(
    val slot: Long,
    val name: String
) : Parcelable
