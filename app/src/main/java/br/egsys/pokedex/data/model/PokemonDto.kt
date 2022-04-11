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
) : Parcelable {

    companion object {
        fun mapPokemonDtoToPokemonView(pokemonDto: PokemonDto): PokemonView =
            PokemonView(
                id = pokemonDto.id,
                name = pokemonDto.name,
                types = pokemonDto.types,
                weight = pokemonDto.weight,
                height = pokemonDto.height,
                image = pokemonDto.image
            )
    }
}
