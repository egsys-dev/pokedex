package br.egsys.pokedex.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.egsys.pokedex.data.model.NetworkState
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    val pokemon: LiveData<Pokemon>
        get() = pokemonRepository.pokemon
    val pokemonLoadState: LiveData<NetworkState>
        get() = pokemonRepository.pokemonState.asLiveData()

    init {
        getPokemonById(1)
    }

    fun getPokemonById(id: Long) {
        viewModelScope.launch {
            pokemonRepository.getPokemonById(id)
        }
    }

    fun searchPlaylist(term: String) {
//        _playlistSearch.postValue(SearchPlaylistState.Loading)
//
//        val searchResponse = playlists.value?.filter {
//            it.name.contains(term, true)
//        }
//
//        searchResponse?.let {
//            if (it.isEmpty()) {
//                _playlistSearch.postValue(SearchPlaylistState.Failed)
//            } else {
//                _playlistSearch.postValue(
//                    SearchPlaylistState.Loaded(it)
//                )
//            }
//        }
    }
}
