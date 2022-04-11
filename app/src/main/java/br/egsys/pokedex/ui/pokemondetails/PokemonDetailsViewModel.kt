package br.egsys.pokedex.ui.pokemondetails

import androidx.lifecycle.ViewModel
import br.egsys.pokedex.data.model.PokemonView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor() : ViewModel() {

    var pokemonView: PokemonView? = null
        private set

    fun setupPokemonDto(pokemonView: PokemonView) {
        this.pokemonView = pokemonView
    }
}
