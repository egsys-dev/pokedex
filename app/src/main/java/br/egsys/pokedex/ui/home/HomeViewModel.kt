package br.egsys.pokedex.ui.home

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.model.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    var offSet = OFF_SET_INITIAL

    private val _pokemons = MutableLiveData<PokemonsState>(PokemonsState.Initial)
    private val _pokemon = MutableStateFlow<PokemonsState>(PokemonsState.Initial)
    private val _pokemonsSearched = MutableStateFlow<SearchPokemon>(SearchPokemon.Initial)

    val pokemons: LiveData<PokemonsState> = _pokemons
    val pokemon: StateFlow<PokemonsState> get() = _pokemon
    val pokemonSearched: StateFlow<SearchPokemon> get() = _pokemonsSearched

    init {
        getPokemons()
    }

    fun getPokemons() {
        viewModelScope.launch {
            _pokemons.postValue(PokemonsState.Loading)

            val response = pokemonRepository.getPokemons(limit = LIMIT, offSet = offSet)

            if (response is PokemonsState.Loaded) {
                offSet = response.offSet
            }

            _pokemons.postValue(response)
        }
    }

    fun getRandomPokemon() {
        viewModelScope.launch {
            _pokemon.value = PokemonsState.Loading

            val response = pokemonRepository.getRandomPokemon(Random.nextInt(100))

            _pokemon.value = response
        }
    }

    fun searchPlaylist(term: String) {
        _pokemonsSearched.value = SearchPokemon.Loading

        if (_pokemons.value is PokemonsState.Loaded) {
            val searchResponse = (_pokemons.value as PokemonsState.Loaded).pokemons.filter {
                it.name.contains(term, true) || it.id.contains(term)
            }
            searchResponse.let {
                if (it.isEmpty()) {
                    _pokemonsSearched.value = SearchPokemon.Empty
                } else {
                    _pokemonsSearched.value = SearchPokemon.Loaded(it)
                }
            }
        }
    }

    companion object {
        const val LIMIT = 20
        const val OFF_SET_INITIAL = 0
    }
}
