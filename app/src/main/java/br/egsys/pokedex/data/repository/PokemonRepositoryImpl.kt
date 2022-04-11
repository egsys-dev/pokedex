package br.egsys.pokedex.data.repository

import br.egsys.pokedex.data.dao.PokemonDao
import br.egsys.pokedex.data.model.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.model.Pokemon.Companion.mapToPokemonDTO
import br.egsys.pokedex.data.model.Pokemon.Companion.mapToPokemonView
import br.egsys.pokedex.data.service.Service
import br.egsys.pokedex.data.util.EmptyLibraryException
import br.egsys.pokedex.data.util.HasPokemonInDBException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: Service,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    override suspend fun getPokemons(limit: Int, offSet: Int): PokemonsState =
        withContext(Dispatchers.IO) {
            try {
                val listPokemonsBD = pokemonDao.getPokemonsDto()

                if (listPokemonsBD.isEmpty()) {
                    requisitionToApi(limit, offSet)
                } else {
                    if (listPokemonsBD.size == offSet) {
                        requisitionToApi(limit, offSet)
                    }
                }

                val listPokemonsUpdated = pokemonDao.getPokemonsDto()

                PokemonsState.Loaded(
                    listPokemonsUpdated.map {
                        PokemonDto.mapPokemonDtoToPokemonView(it)
                    },
                    listPokemonsUpdated.size
                )
            } catch (e: Exception) {
                val listPokemons = pokemonDao.getPokemonsDto()

                if (listPokemons.isEmpty() && e is UnknownHostException) {
                    PokemonsState.Failed(EmptyLibraryException())
                } else if (listPokemons.isNotEmpty() && e is UnknownHostException) {
                    PokemonsState.Failed(HasPokemonInDBException())
                } else {
                    PokemonsState.Failed(e)
                }
            }
        }

    private suspend fun requisitionToApi(
        limit: Int,
        offSet: Int
    ) {
        val response = service.getPokemons(
            limit = limit,
            offSet = offSet
        )

        response.results.map {
            val pokemon = service.getPokemonByName(it.name)

            pokemonDao.addPokemonDto(mapToPokemonDTO(pokemon))
        }
    }

    override suspend fun getRandomPokemon(idRandom: Int): PokemonsState =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getRandomPokemon(idRandom)

                PokemonsState.Loaded(listOf(mapToPokemonView(response)), 0)
            } catch (e: Exception) {
                PokemonsState.Failed(e)
            }
        }
}
