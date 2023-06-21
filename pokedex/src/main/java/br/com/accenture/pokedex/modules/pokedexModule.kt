package br.com.accenture.pokedex.modules

import br.com.accenture.pokedex.di.RetrofitObject
import br.com.accenture.pokedex.repository.PokedexRepository
import br.com.accenture.pokedex.viewModel.PokedexViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokedexModule = module {
    factory {
        PokedexRepository(RetrofitObject.createNetworkService())
    }
    viewModel {
        PokedexViewModel(get())
    }
}