package br.egsys.pokedex.ui.pokemondetails

import androidx.lifecycle.ViewModel
import br.egsys.pokedex.data.dto.PokemonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor() : ViewModel() {

    var pokemonDto: PokemonDto? = null
        private set

    fun setupPokemonDto(pokemonDto: PokemonDto) {
        this.pokemonDto = pokemonDto
    }
}
