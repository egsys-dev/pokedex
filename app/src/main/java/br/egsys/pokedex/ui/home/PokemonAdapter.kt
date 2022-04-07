package br.egsys.pokedex.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.egsys.pokedex.R
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.databinding.ItemPokemonBinding
import br.egsys.pokedex.extension.firstLetterUpperCase
import br.egsys.pokedex.extension.inflate
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private val onItemClicked: (pokemons: Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            parent.inflate(R.layout.item_pokemon, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun updateList(pokemons: List<Pokemon>) {
        val currentListToUpdated = currentList.toMutableList()

        pokemons.forEach {
            currentListToUpdated.add(it)
        }

        submitList(pokemons)
    }

    inner class PokemonViewHolder(
        view: View,
        private val onItemClicked: (pokemons: Pokemon) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val viewBinding = ItemPokemonBinding.bind(itemView)
        private var pokemon: Pokemon? = null

        init {
            itemView.setOnClickListener {
                pokemon?.let {
                    onItemClicked(it)
                }
            }
        }

        fun bind(pokemon: Pokemon) {
            this.pokemon = pokemon

            Picasso.with(viewBinding.root.context)
                .load(pokemon.sprites.frontDefault)
                .into(viewBinding.image)

            viewBinding.name.text = firstLetterUpperCase(pokemon.name)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }
}
