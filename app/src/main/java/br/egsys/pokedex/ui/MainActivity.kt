package br.egsys.pokedex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import br.egsys.pokedex.databinding.ActivityMainBinding
import br.egsys.pokedex.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        goToHome()
    }

    private fun goToHome() {
        supportFragmentManager.commit(true) {
            replace(viewBinding.container.id, HomeFragment.newInstance(), HomeFragment.TAG)
        }
    }

    fun navigateToFragment(
        fragment: Fragment,
        tag: String,
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.container.id, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}
