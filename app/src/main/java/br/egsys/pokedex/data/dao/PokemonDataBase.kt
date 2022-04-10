package br.egsys.pokedex.data.dao

import androidx.room.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.model.PokemonDto

@Database(entities = [PokemonDto::class], version = 1, exportSchema = false)
abstract class PokemonDataBase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}
