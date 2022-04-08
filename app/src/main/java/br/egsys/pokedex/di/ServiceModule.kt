package br.egsys.pokedex.di

import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.repository.PokemonRepositoryImpl
import br.egsys.pokedex.data.service.Service
import br.egsys.pokedex.data.util.DomainMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): Service = retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun providesRepository(service: Service, pokemonMap: DomainMapper<PokemonDto, Pokemon>) =
        PokemonRepositoryImpl(service, pokemonMap)
}
