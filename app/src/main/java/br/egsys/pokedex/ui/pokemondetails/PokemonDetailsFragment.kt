package br.egsys.pokedex.ui.pokemondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.egsys.pokedex.data.model.Pokemon
import br.egsys.pokedex.databinding.FragmentPokemonDetailsBinding
import br.egsys.pokedex.ui.basedialog.BaseBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailsFragment : BaseBottomSheetDialogFragment() {

    private lateinit var viewBinding: FragmentPokemonDetailsBinding
    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentPokemonDetailsBinding
            .inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        readArgs()
    }

    private fun readArgs() {
        (arguments?.getParcelable(ARG) as? Pokemon)?.let {
            viewModel.setupPokemon(it)
        }
    }

    companion object {
        const val TAG = "br.egsys.pokedex.ui.pokemondetails"
        private const val ARG = "arg"

        private fun newInstance(pokemon: Pokemon) = PokemonDetailsFragment().apply {
            arguments = bundleOf(
                ARG to pokemon
            )
        }

        fun show(fragmentManager: FragmentManager, pokemon: Pokemon) =
            newInstance(pokemon).show(fragmentManager, TAG)
    }
}
