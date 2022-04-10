package br.egsys.pokedex.di

import android.app.Application
import androidx.room.Room
import br.egsys.pokedex.data.dao.PokemonDao
import br.egsys.pokedex.data.dao.PokemonDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): PokemonDataBase {
        return Room
            .databaseBuilder(application, PokemonDataBase::class.java, "pokemon_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(pokemonDataBase: PokemonDataBase): PokemonDao {
        return pokemonDataBase.pokemonDao()
    }
}
