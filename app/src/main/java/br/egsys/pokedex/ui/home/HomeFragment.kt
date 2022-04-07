package br.egsys.pokedex.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.data.model.SearchPokemon
import br.egsys.pokedex.databinding.FragmentHomeBinding
import br.egsys.pokedex.extension.closeKeyboard
import br.egsys.pokedex.ui.pokemondetails.PokemonDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentHomeBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupPokemonObserver()
        setupSearchBarClickButton()
        setupSearchBarTextChanged()
        setupCleanSearchBarClick()
        setupCancelButtonClick()
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.pokemons.layoutManager = layoutManager

        viewBinding.pokemons.adapter = PokemonAdapter {
            viewModel.getPokemonByName(it.name)
        }

        viewBinding.pokemons.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val offSet = viewModel.offSet

                    if (offSet == lastVisibleItem + 1) {
                        viewModel.getPokemons()
                    }
                }
            }
        )
    }

    private fun setupPokemonObserver() {
        viewModel.pokemon.observe(viewLifecycleOwner) {
            PokemonDetailsFragment.show(childFragmentManager, it)
        }

        viewModel.pokemonLoadState.observe(viewLifecycleOwner) {
        }

        viewModel.pokemons.observe(viewLifecycleOwner) {
            updateList(it.pokemons)
        }

        viewModel.pokemonsLoadState.observe(viewLifecycleOwner) {
        }

        viewModel.pokemonSearch.observe(viewLifecycleOwner) {
            when (it) {
                is SearchPokemon.Loading -> {
                }
                is SearchPokemon.Loaded -> {
                    updateList(it.pokemons)
                }
                is SearchPokemon.Empty -> {
                }
                is SearchPokemon.Failed -> {
                }
            }
        }
    }

    private fun setupSearchBarClickButton() {
        viewBinding.searchBar.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                performCurrentTextSearch()
                closeKeyboard()
            } else {
                viewBinding.cancel.isVisible = true
            }
        }
    }

    private fun setupSearchBarTextChanged() {
        viewBinding.searchBar.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank()) {
                viewModel.searchPlaylist(text.toString())
            }
        }
    }

    private fun setupCleanSearchBarClick() {
        viewBinding.apply {
            searchBar.setClearSearchButtonClickListener {
                viewModel.pokemons.value?.pokemons?.let {
                    updateList(it)
                }

                searchBar.text = null

//                playlistRecyclerView.isVisible = true
//                noResultsPlaylist.isVisible = false
            }
        }
    }

    private fun setupCancelButtonClick() {
        viewBinding.cancel.setOnClickListener {
            closeKeyboard()
            viewBinding.apply {
                cancel.isVisible = false
                searchBar.text = null
            }
        }
    }

    private fun performCurrentTextSearch() {
        viewBinding.searchBar.text?.toString()?.let {
            performSearch(it)
        }
    }

    private fun performSearch(term: String) {
        viewModel.searchPlaylist(term)
    }

    private fun closeKeyboard() {
        context?.closeKeyboard(viewBinding.root)
    }

    private fun updateList(pokemons: List<Pokemon>) {
        (viewBinding.pokemons.adapter as? PokemonAdapter)?.updateList(pokemons)
    }

    companion object {
        const val TAG = "br.egsys.pokedex.ui.home"

        fun newInstance() = HomeFragment()
    }
}
