package br.egsys.pokedex.ui.pokemondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import br.egsys.pokedex.data.dto.PokemonDto
import br.egsys.pokedex.databinding.FragmentPokemonDetailsBinding
import br.egsys.pokedex.extension.firstLetterUpperCase
import br.egsys.pokedex.ui.basedialog.BaseBottomSheetDialogFragment
import com.squareup.picasso.Picasso
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
        (arguments?.getParcelable(ARG) as? PokemonDto)?.let {
            viewModel.setupPokemonDto(it)
            setupView(it)
        }
    }

    private fun setupView(pokemonDto: PokemonDto) {
        viewBinding.apply {
            Picasso.with(context).load(pokemonDto.image).into(image)
            name.text = firstLetterUpperCase(pokemonDto.name)
            valueWeight.text = "${pokemonDto.weight}Kg"
            valueHeight.text = "${pokemonDto.height}m"
            type.text = pokemonDto.types
                .takeIf { it.isNotEmpty() }
                ?.joinToString(", ")
        }
    }

    companion object {
        const val TAG = "br.egsys.pokedex.ui.pokemondetails"
        private const val ARG = "arg"

        private fun newInstance(pokemonDto: PokemonDto) = PokemonDetailsFragment().apply {
            arguments = bundleOf(
                ARG to pokemonDto
            )
        }

        fun show(fragmentManager: FragmentManager, pokemonDto: PokemonDto) =
            newInstance(pokemonDto).show(fragmentManager, TAG)
    }
}
