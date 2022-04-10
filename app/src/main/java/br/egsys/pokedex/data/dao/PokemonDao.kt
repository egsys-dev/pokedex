package br.egsys.pokedex.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.egsys.pokedex.data.model.PokemonDto

@Dao
interface PokemonDao {

    @Query("SELECT * FROM PokemonDto")
    fun getPokemonsDto(): List<PokemonDto>
}
