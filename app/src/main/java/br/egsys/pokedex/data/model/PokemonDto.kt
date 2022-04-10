package br.egsys.pokedex.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class PokemonDto(
    @PrimaryKey
    val id: String,
    val name: String,
    val types: String,
    val weight: Int,
    val height: Int,
    val image: String
) : Parcelable
