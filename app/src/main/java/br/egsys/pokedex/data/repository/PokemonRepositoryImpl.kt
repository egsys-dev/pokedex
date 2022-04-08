package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.PokemonWithCount
import br.egsys.pokedex.data.service.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: Service
) : PokemonRepository {

    private val _pokemon = MutableLiveData<Pokemon>()
    private val _pokemons = MutableStateFlow(PokemonWithCount())
    private val _pokemonState = MutableStateFlow<NetworkState>(NetworkState.Initial)
    private val _pokemonsState = MutableStateFlow<NetworkState>(NetworkState.Initial)
    private val _paginationState = MutableStateFlow<NetworkState>(NetworkState.Initial)

    override val pokemon: LiveData<Pokemon> = _pokemon
    override val pokemons: StateFlow<PokemonWithCount> = _pokemons
    override val pokemonState: StateFlow<NetworkState> = _pokemonState
    override val pokemonsState: StateFlow<NetworkState> = _pokemonsState
    override val paginationState: StateFlow<NetworkState> = _paginationState

    private var listPokemon = mutableListOf<Pokemon>()

    override suspend fun getPokemonByName(name: String) {
        withContext(Dispatchers.IO) {
            try {
                _pokemonState.value = NetworkState.Loading

                _pokemon.postValue(service.getPokemonByName(name))

                _pokemonState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _pokemonState.value = NetworkState.Failed(e)
            }
        }
    }

    override suspend fun getPokemons(limit: Int, offSet: Int) {
        withContext(Dispatchers.IO) {
            try {
                _pokemonsState.value = NetworkState.Loading
                _paginationState.value = NetworkState.Loading

                val response = service.getPokemons(
                    limit = limit,
                    offSet = offSet
                )

                response.results.forEach {
                    val pokemon = service.getPokemonByName(it.name)

                    listPokemon.add(pokemon)
                }

                _pokemons.value = PokemonWithCount(
                    count = response.count,
                    pokemons = listPokemon.toList()
                )

                _pokemonsState.value = NetworkState.Loaded
                _paginationState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _pokemonsState.value = NetworkState.Failed(e)
                _paginationState.value = NetworkState.Loaded
            }
        }
    }

    override suspend fun getRandomPokemon(idRandom: Int) {
        withContext(Dispatchers.IO) {
            try {
                _pokemonState.value = NetworkState.Loading

                _pokemon.postValue(service.getRandomPokemon(idRandom))

                _pokemonState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _pokemonState.value = NetworkState.Failed(e)
            }
        }
    }
}
