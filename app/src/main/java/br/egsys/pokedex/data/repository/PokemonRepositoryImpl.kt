package br.egsys.pokedex.data.repository

import br.egsys.pokedex.data.model.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.model.Pokemon.Companion.mapToPokemonView
import br.egsys.pokedex.data.service.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: Service,
) : PokemonRepository {

    private val listPokemonView = mutableListOf<PokemonView>()
    private val _listPokemons = MutableStateFlow<List<PokemonView>>(emptyList())

    override val listPokemons: StateFlow<List<PokemonView>> get() = _listPokemons

    override suspend fun getPokemons(limit: Int, offSet: Int): PokemonsState =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getPokemons(
                    limit = limit,
                    offSet = offSet
                )

                response.results.map {
                    val pokemon = service.getPokemonByName(it.name)

                    listPokemonView.add(mapToPokemonView(pokemon))
                }

                _listPokemons.value = listPokemonView

                PokemonsState.Loaded(listPokemonView.toList())
            } catch (e: Exception) {
                PokemonsState.Failed(e)
            }
        }

    override suspend fun getRandomPokemon(idRandom: Int): PokemonsState =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getRandomPokemon(idRandom)

                PokemonsState.Loaded(listOf(mapToPokemonView(response)))
            } catch (e: Exception) {
                PokemonsState.Failed(e)
            }
        }
}
