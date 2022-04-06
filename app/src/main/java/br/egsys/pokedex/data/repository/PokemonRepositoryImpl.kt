package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.Pokemons
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
    private val _pokemons = MutableLiveData<Pokemons>()
    private val _pokemonState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    private val _pokemonsState = MutableStateFlow<NetworkState>(NetworkState.Idle)

    override val pokemon: LiveData<Pokemon> = _pokemon
    override val pokemons: LiveData<Pokemons> = _pokemons
    override val pokemonState: StateFlow<NetworkState> = _pokemonState
    override val pokemonsState: StateFlow<NetworkState> = _pokemonsState

    override suspend fun getPokemonById(id: Long) {
        withContext(Dispatchers.IO) {
            try {
                _pokemonState.value = NetworkState.Loading

                _pokemon.postValue(service.getPokemonById(id))

                _pokemonState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _pokemonState.value = NetworkState.Failed(e)
            }
        }
    }

    override suspend fun getPokemonByName(name: String) {
        withContext(Dispatchers.IO) {
            try {
                _pokemonState.value = NetworkState.Loading

                _pokemon.postValue(service.getPokemonByName(name))

                _pokemonState.value = NetworkState.Loaded
            } catch (e: Exception) {
                NetworkState.Failed(e)
            }
        }
    }

    override suspend fun getPokemons() {
        withContext(Dispatchers.IO) {
            try {
                _pokemonsState.value = NetworkState.Loading

                _pokemons.postValue(service.getPokemons(1))

                _pokemonsState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _pokemonsState.value = NetworkState.Failed(e)
            }
        }
    }
}
