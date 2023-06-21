package br.com.accenture.pokedex.modules

import br.com.accenture.pokedex.di.RetrofitObject
import br.com.accenture.pokedex.repository.AbilityRepository
import br.com.accenture.pokedex.repository.PokemonRepository
import br.com.accenture.pokedex.viewModel.PokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonModule = module {
    factory { PokemonRepository(RetrofitObject.createNetworkService()) }
    factory { AbilityRepository(RetrofitObject.createNetworkService()) }
    viewModel {
        PokemonViewModel(get(), get())
    }
}