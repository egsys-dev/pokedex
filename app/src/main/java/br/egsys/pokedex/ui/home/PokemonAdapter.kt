package br.egsys.pokedex.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.egsys.pokedex.R
import br.egsys.pokedex.data.model.PokemonView
import br.egsys.pokedex.databinding.ViewPokemonItemBinding
import br.egsys.pokedex.extension.firstLetterUpperCase
import br.egsys.pokedex.extension.inflate
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private val onItemClicked: (pokemonsView: PokemonView) -> Unit
) : ListAdapter<PokemonView, PokemonAdapter.PokemonViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            parent.inflate(R.layout.view_pokemon_item, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun updateList(pokemonsView: List<PokemonView>) {
        val currentListToUpdated = currentList.toMutableList()

        pokemonsView.forEach {
            currentListToUpdated.add(it)
        }

        submitList(pokemonsView)
    }

    inner class PokemonViewHolder(
        view: View,
        private val onItemClicked: (pokemonsView: PokemonView) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val viewBinding = ViewPokemonItemBinding.bind(itemView)
        private var pokemonsView: PokemonView? = null

        init {
            viewBinding.container.setOnClickListener {
                pokemonsView?.let {
                    onItemClicked(it)
                }
            }
        }

        fun bind(pokemonsView: PokemonView) {
            this.pokemonsView = pokemonsView

            Picasso.with(viewBinding.root.context)
                .load(pokemonsView.image)
                .into(viewBinding.image)

            viewBinding.apply {
                name.text = firstLetterUpperCase(pokemonsView.name)
                counter.text = "#${pokemonsView.id}"
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonView>() {
            override fun areItemsTheSame(oldItem: PokemonView, newItem: PokemonView): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonView, newItem: PokemonView): Boolean =
                oldItem == newItem
        }
    }
}
