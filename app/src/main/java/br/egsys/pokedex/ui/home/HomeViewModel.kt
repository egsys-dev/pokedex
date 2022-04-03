package br.egsys.pokedex.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

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