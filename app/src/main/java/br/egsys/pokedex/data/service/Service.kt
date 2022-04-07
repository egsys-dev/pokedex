package br.egsys.pokedex.data.service

import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.Pokemons
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Long): Pokemon

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Pokemon

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offSet: Int
    ): Pokemons
}
