package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
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
    private val _pokemonState = MutableStateFlow<NetworkState>(NetworkState.Idle)

    override val pokemon: LiveData<Pokemon> = _pokemon
    override val pokemonState: StateFlow<NetworkState> = _pokemonState

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
}
