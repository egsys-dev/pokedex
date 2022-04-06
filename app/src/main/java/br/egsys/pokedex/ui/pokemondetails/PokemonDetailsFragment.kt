package br.egsys.pokedex.ui.pokemondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import br.egsys.pokedex.databinding.FragmentPokemonDetailsBinding
import br.egsys.pokedex.ui.basedialog.BaseBottomSheetDialogFragment

class PokemonDetailsFragment : BaseBottomSheetDialogFragment() {

    private lateinit var viewBinding: FragmentPokemonDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentPokemonDetailsBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }

    companion object {
        const val TAG = "br.egsys.pokedex.ui.pokemondetails"

//        private fun newInstance(athleteSelected: String) = PokemonDetailsFragment().apply {
//            arguments = bundleOf(
//                ARG to athleteSelected
//            )
//        }

        //        fun show(fragmentManager: FragmentManager, athleteSelected: String) =
//            newInstance(athleteSelected).show(fragmentManager, TAG)
        private fun newInstance() = PokemonDetailsFragment()

        fun show(fragmentManager: FragmentManager) = newInstance().show(fragmentManager, TAG)
    }
}
