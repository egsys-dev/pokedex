package br.egsys.pokedex.ui.home

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.model.* // ktlint-disable no-wildcard-imports
import br.egsys.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonSearch: MutableLiveData<SearchPokemon> = MutableLiveData()

    val pokemonSearch: LiveData<SearchPokemon> = _pokemonSearch
    val pokemon: LiveData<Pokemon>
        get() = pokemonRepository.pokemon
    val pokemonLoadState: LiveData<NetworkState>
        get() = pokemonRepository.pokemonState.asLiveData()
    val pokemons: LiveData<Pokemons>
        get() = pokemonRepository.pokemons
    val pokemonsLoadState: LiveData<NetworkState>
        get() = pokemonRepository.pokemonsState.asLiveData()

    init {
//        getPokemonById(1)
        getPokemons()
    }

    fun getPokemonById(id: Long) {
        viewModelScope.launch {
            pokemonRepository.getPokemonById(id)
        }
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonByName(name)
        }
    }

    fun getPokemons() {
        viewModelScope.launch {
            pokemonRepository.getPokemons()
        }
    }

    fun searchPlaylist(term: String) {
        _pokemonSearch.postValue(SearchPokemon.Loading)

        val searchResponse = pokemons.value?.results?.filter {
            it.name.contains(term, true)
        }

        searchResponse?.let {
            if (it.isEmpty()) {
                _pokemonSearch.postValue(SearchPokemon.Empty)
            } else {
                _pokemonSearch.postValue(
                    SearchPokemon.Loaded(it)
                )
            }
        }
    }
}
