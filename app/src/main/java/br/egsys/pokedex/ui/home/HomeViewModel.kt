package br.egsys.pokedex.ui.home

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.model.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonSearch: MutableLiveData<SearchPokemon> = MutableLiveData()

    val pokemonSearch: LiveData<SearchPokemon> = _pokemonSearch
    var offSet = 0

    val pokemon: LiveData<Pokemon>
        get() = pokemonRepository.pokemon
    val pokemonLoadState: LiveData<NetworkState>
        get() = pokemonRepository.pokemonState.asLiveData()
    val pokemons: LiveData<PokemonWithCount>
        get() = pokemonRepository.pokemons.asLiveData()
    val pokemonsLoadState: LiveData<NetworkState>
        get() = pokemonRepository.pokemonsState.asLiveData()
    val resquestPagination: StateFlow<NetworkState>
        get() = pokemonRepository.paginationState

    init {
        getPokemons()
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonByName(name)
        }
    }

    fun getPokemons() {
        viewModelScope.launch {
            pokemonRepository.getPokemons(limit = LIMIT, offSet = offSet)
            offSet += 20
        }
    }

    fun getRandomPokemon() {
        viewModelScope.launch {
            val maxValue = pokemons.value?.count
            pokemonRepository.getRandomPokemon(Random.nextInt(maxValue ?: 100))
        }
    }

    fun searchPlaylist(term: String) {
        _pokemonSearch.postValue(SearchPokemon.Loading)

        val searchResponse = pokemonRepository.pokemons.value.pokemons.filter {
            it.name.contains(term, true)
        }

        searchResponse.let {
            if (it.isEmpty()) {
                _pokemonSearch.postValue(SearchPokemon.Empty)
            } else {
                _pokemonSearch.postValue(
                    SearchPokemon.Loaded(it)
                )
            }
        }
    }

    companion object {
        const val LIMIT = 20
    }
}
