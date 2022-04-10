package br.egsys.pokedex.data.appdatabase

import android.content.Context
import androidx.room.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.dao.PokemonDao
import br.egsys.pokedex.data.model.PokemonDto

@Database(entities = arrayOf(PokemonDto::class), version = 1, exportSchema = false)
abstract class PokemonDataBase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {

        @Volatile
        private var INSTANCE: PokemonDataBase? = null

        fun getDatabase(context: Context): PokemonDataBase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PokemonDataBase::class.java,
                "pokemon_db"
            ).build()
            INSTANCE = instance
            return instance
        }
    }
}
