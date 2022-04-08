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
import br.egsys.pokedex.R
import br.egsys.pokedex.data.model.NetworkState
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
        setupGetPokemonRandom()
        setupTryAgain()
        setupSearchBarClickButton()
        setupSearchBarTextChanged()
        setupCleanSearchBarClick()
        setupCancelButtonClick()
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.pokemons.layoutManager = layoutManager

        viewBinding.pokemons.adapter = PokemonAdapter {
            PokemonDetailsFragment.show(childFragmentManager, it)
        }

        viewBinding.pokemons.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val offSet = viewModel.offSet

                    if (offSet == lastVisibleItem + 1) {
                        if (viewModel.resquestPagination.value == NetworkState.Loaded) {
                            viewModel.getPokemons()
                        }
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
            when (it) {
                is NetworkState.Initial -> {
                }
                is NetworkState.Loading -> {
                    viewBinding.apply {
                        loading.isVisible = true
                        pokemons.isVisible = true
                        emptyPokemon.isVisible = false
                        noConnectionContainer.isVisible = false
                        meetPokemonContainer.isVisible = false
                    }
                }
                is NetworkState.Loaded -> {
                    viewBinding.apply {
                        pokemons.isVisible = true
                        meetPokemonContainer.isVisible = true
                        emptyPokemon.isVisible = false
                        noConnectionContainer.isVisible = false
                        loading.isVisible = false
                    }
                }
                is NetworkState.Failed -> {
                    viewBinding.apply {
                        noConnectionContainer.isVisible = true
                        pokemons.isVisible = false
                        loading.isVisible = false
                        meetPokemonContainer.isVisible = false
                    }
                }
            }
        }

        viewModel.pokemonSearch.observe(viewLifecycleOwner) {
            when (it) {
                is SearchPokemon.Loading -> {
                    viewBinding.pokemons.isVisible = false
                }
                is SearchPokemon.Loaded -> {
                    updateList(it.pokemons)

                    viewBinding.pokemons.isVisible = true
                }
                is SearchPokemon.Empty -> {
                    viewBinding.apply {
                        emptyPokemon.isVisible = true
                        pokemons.isVisible = false
                    }
                }
                else -> {
                }
            }
        }
    }

    private fun setupGetPokemonRandom() {
        viewBinding.randomPokemon.setOnClickListener {
            viewModel.getRandomPokemon()
        }
    }

    private fun setupTryAgain() {
        viewBinding.tryAgain.setOnClickListener {
            viewModel.getPokemons()
        }
    }

    private fun setupSearchBarClickButton() {
        viewBinding.searchBar.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                performCurrentTextSearch()
                closeKeyboard()
                setupNameResultSearch(R.string.all_pokemons)
            } else {
                viewBinding.cancel.isVisible = true
                setupNameResultSearch(R.string.result_search)
            }
        }
    }

    private fun setupSearchBarTextChanged() {
        viewBinding.searchBar.doOnTextChanged { text, start, before, count ->
            viewModel.searchPlaylist(text.toString())

            if (text.isNullOrBlank()) {
                viewModel.pokemons.value?.let {
                    updateList(it.pokemons)

                    setupNameResultSearch(R.string.all_pokemons)
                }
            }
        }
    }

    private fun setupNameResultSearch(text: Int) {
        viewBinding.resultSearch.text = getString(text)
    }

    private fun setupCleanSearchBarClick() {
        viewBinding.searchBar.setClearSearchButtonClickListener {
            viewModel.pokemons.value?.pokemons?.let {
                updateList(it)
            }
        }
    }

    private fun setupCancelButtonClick() {
        viewBinding.cancel.setOnClickListener {
            closeKeyboard()
            setupNameResultSearch(R.string.all_pokemons)

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
