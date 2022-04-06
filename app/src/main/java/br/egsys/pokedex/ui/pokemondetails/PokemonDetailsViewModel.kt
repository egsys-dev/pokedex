package br.egsys.pokedex.ui.pokemondetails

import androidx.lifecycle.ViewModel
import br.egsys.pokedex.data.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor() : ViewModel() {

    var pokemon: Pokemon? = null
        private set

    fun setupPokemon(pokemon: Pokemon) {
        this.pokemon = pokemon
    }
}
