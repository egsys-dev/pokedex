package br.egsys.pokedex.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.egsys.pokedex.R
import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.databinding.ViewPokemonItemBinding
import br.egsys.pokedex.extension.firstLetterUpperCase
import br.egsys.pokedex.extension.inflate
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private val onItemClicked: (pokemonsDto: PokemonDto) -> Unit
) : ListAdapter<PokemonDto, PokemonAdapter.PokemonViewHolder>(diffCallback) {

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

    fun updateList(pokemonsDto: List<PokemonDto>) {
        val currentListToUpdated = currentList.toMutableList()

        pokemonsDto.forEach {
            currentListToUpdated.add(it)
        }

        submitList(pokemonsDto)
    }

    inner class PokemonViewHolder(
        view: View,
        private val onItemClicked: (pokemonsDto: PokemonDto) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val viewBinding = ViewPokemonItemBinding.bind(itemView)
        private var pokemonDto: PokemonDto? = null

        init {
            viewBinding.container.setOnClickListener {
                pokemonDto?.let {
                    onItemClicked(it)
                }
            }
        }

        fun bind(pokemonDto: PokemonDto) {
            this.pokemonDto = pokemonDto

            Picasso.with(viewBinding.root.context)
                .load(pokemonDto.image)
                .into(viewBinding.image)

            viewBinding.apply {
                name.text = firstLetterUpperCase(pokemonDto.name)
                counter.text = "#${bindingAdapterPosition + 1}"
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PokemonDto>() {
            override fun areItemsTheSame(oldItem: PokemonDto, newItem: PokemonDto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PokemonDto, newItem: PokemonDto): Boolean =
                oldItem == newItem
        }
    }
}
