package br.egsys.pokedex.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.egsys.pokedex.R
import br.egsys.pokedex.data.model.PokemonName
import br.egsys.pokedex.databinding.ItemPokemonBinding
import br.egsys.pokedex.extension.inflate

class PokemonAdapter(
    private val onItemClicked: (pokemonName: PokemonName) -> Unit
) : ListAdapter<PokemonName, PokemonAdapter.PokemonViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            parent.inflate(R.layout.item_pokemon, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PokemonViewHolder(
        view: View,
        private val onItemClicked: (pokemonName: PokemonName) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val viewBinding = ItemPokemonBinding.bind(itemView)
        private var pokemonName: PokemonName? = null

        init {
            viewBinding.container.setOnClickListener {
                pokemonName?.let {
                    onItemClicked(it)
                }
            }
        }

        fun bind(pokemonName: PokemonName) {
            this.pokemonName = pokemonName

            viewBinding.name.text = pokemonName.name
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonName>() {
            override fun areItemsTheSame(oldItem: PokemonName, newItem: PokemonName): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: PokemonName, newItem: PokemonName): Boolean =
                oldItem == newItem
        }
    }
}
