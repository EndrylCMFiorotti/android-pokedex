package br.com.accenture.android_pokedex

import android.app.Application
import br.com.accenture.pokedex.modules.pokedexModule
import br.com.accenture.pokedex.modules.pokemonModule
import org.koin.core.context.startKoin

class PokedexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpKoin()
    }

    private fun setUpKoin() {
        startKoin {
            modules(pokedexModule, pokemonModule)
        }
    }
}