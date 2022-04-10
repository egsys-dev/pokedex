package br.egsys.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.egsys.pokedex.data.model.PokemonDto

@Dao
interface PokemonDao {

    @Query("SELECT * FROM PokemonDto")
    fun getPokemonsDto(): List<PokemonDto>

    @Query("SELECT * FROM PokemonDto WHERE id = :id")
    fun getPokemonById(id: String): PokemonDto?

    @Insert
    fun addPokemonDto(pokemonDto: PokemonDto)

    @Delete
    fun deleteAllPokemon(pokemonDto: PokemonDto)
}
