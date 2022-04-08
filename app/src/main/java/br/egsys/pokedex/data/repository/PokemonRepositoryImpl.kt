package br.egsys.pokedex.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.PokemonDtoWithCount
import br.egsys.pokedex.data.service.Service
import br.egsys.pokedex.data.util.DomainMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val service: Service,
    private val pokemonMap: DomainMapper<PokemonDto, Pokemon>
) : PokemonRepository {

    private val _pokemon = MutableLiveData<PokemonDto>()
    private val _pokemons = MutableStateFlow(PokemonDtoWithCount())
    private val _pokemonState = MutableStateFlow<NetworkState>(NetworkState.Initial)
    private val _pokemonsState = MutableStateFlow<NetworkState>(NetworkState.Initial)
    private val _paginationState = MutableStateFlow<NetworkState>(NetworkState.Initial)

    override val pokemon: LiveData<PokemonDto> = _pokemon
    override val pokemons: StateFlow<PokemonDtoWithCount> = _pokemons
    override val pokemonState: StateFlow<NetworkState> = _pokemonState
    override val pokemonsState: StateFlow<NetworkState> = _pokemonsState
    override val paginationState: StateFlow<NetworkState> = _paginationState

    private var listPokemon = mutableListOf<Pokemon>()

    override suspend fun getPokemonByName(name: String) {
        withContext(Dispatchers.IO) {
            try {
                _pokemonState.value = NetworkState.Loading

                val response = service.getPokemonByName(name)

                _pokemon.postValue(pokemonMap.mapFromDomainModel(response))

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

                _pokemons.value = PokemonDtoWithCount(
                    count = response.count,
                    pokemonsDto = pokemonMap.toEntityList(listPokemon)
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

                val response = service.getRandomPokemon(idRandom)

                _pokemon.postValue(pokemonMap.mapFromDomainModel(response))

                _pokemonState.value = NetworkState.Loaded
            } catch (e: Exception) {
                _pokemonState.value = NetworkState.Failed(e)
            }
        }
    }
}
