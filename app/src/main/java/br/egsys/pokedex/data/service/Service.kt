package br.egsys.pokedex.data.service

import br.egsys.pokedex.data.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Long): Pokemon
}
