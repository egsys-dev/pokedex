package br.egsys.pokedex.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.egsys.pokedex.R
import br.egsys.pokedex.extension.setupFullScreenSystemUiFlags
import br.egsys.pokedex.ui.MainActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setupFullScreenSystemUiFlags()

        goToMainScreen()
    }

    private fun goToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(baseContext, MainActivity::class.java).apply {
                    putExtras(intent)
                    data = intent.data
                }
            )
        }, OPEN_DELAY)
    }

    companion object {
        private const val OPEN_DELAY = 2500L
    }
}
