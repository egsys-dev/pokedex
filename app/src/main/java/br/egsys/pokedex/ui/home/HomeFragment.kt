package br.egsys.pokedex.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.egsys.pokedex.R
import br.egsys.pokedex.databinding.FragmentHomeBinding
import br.egsys.pokedex.extension.closeKeyboard
import br.egsys.pokedex.extension.updateMargin
import br.egsys.pokedex.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var viewBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val mainActivity: MainActivity?
        get() = activity as? MainActivity

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

        setupSearchBarClickButton()
        setupSearchBarTextChanged()
        setupCleanSearchBarClick()
        setupCancelButtonClick()
    }

    private fun setupSearchBarClickButton() {
        viewBinding.searchBar.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                performCurrentTextSearch()
                closeKeyboard()
            } else {
                viewBinding.apply {
                    searchBar.updateMargin(right = 0)
                    cancel.isVisible = true
                }
            }
        }
    }

    private fun setupSearchBarTextChanged() {
        viewBinding.searchBar.doOnTextChanged { text, start, before, count ->
            viewModel.searchPlaylist(text.toString())
            Log.d("SEARCH", text.toString())

            if (text.isNullOrBlank()) {
//                viewModel.playlists.value?.let {
//                    submitPlaylists(it)
//                }

                Log.d("SEARCH", "text null")
            }
        }
    }

    private fun setupCleanSearchBarClick() {
        viewBinding.apply {
            searchBar.setClearSearchButtonClickListener {
//                viewModel.playlists.value?.let {
//                    submitPlaylists(it)
//                }

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
                searchBar.apply {
                    text = null
                    updateMargin(right = resources.getDimension(R.dimen.spacing_x_large).toInt())
                }
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

    companion object {
        const val TAG = "br.egsys.pokedex.ui.home"

        fun newInstance() = HomeFragment()
    }
}
