package br.egsys.pokedex.data.model

sealed class NetworkState {
    object Initial : NetworkState()
    object Loaded : NetworkState()
    object Loading : NetworkState()
    data class Failed(val exception: Exception) : NetworkState()
}
