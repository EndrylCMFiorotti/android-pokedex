package br.com.accenture.pokedex.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.accenture.pokedex.model.presentation.PokemonAbilityPresentation
import br.com.accenture.pokedex.model.presentation.PokemonPresentation
import br.com.accenture.pokedex.network.wrapper.ResultWrapper
import br.com.accenture.pokedex.repository.AbilityRepository
import br.com.accenture.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class PokemonViewModel(
    private val repository: PokemonRepository,
    private val repositoryAbility: AbilityRepository
) : ViewModel() {
    private val _pokemon = MutableLiveData<PokemonPresentation>()
    val pokemon: LiveData<PokemonPresentation> = _pokemon

    private val _notFound = MutableLiveData<Exception>()
    val notFound: LiveData<Exception> = _notFound

    private val _pokemonDescription = MutableLiveData<String>()
    val pokemonDescription: LiveData<String> = _pokemonDescription

    private val _pokemonDescriptionEmpty = MutableLiveData<Unit>()
    val pokemonDescriptionEmpty: LiveData<Unit> = _pokemonDescriptionEmpty

    private val _setSecondTypeInvisible = MutableLiveData<Boolean>()
    val setSecondTypeInvisible: LiveData<Boolean> = _setSecondTypeInvisible

    private val _ability = MutableLiveData<PokemonAbilityPresentation>()
    val ability: LiveData<PokemonAbilityPresentation> = _ability

    fun getPokemon(id: String) {
        viewModelScope.launch {
            when (val response = repository.getPokemon(id)) {
                is ResultWrapper.Success -> response.content.toPokemonPresentation()
                    .let { pokemonRequestSuccess(it) }

                is ResultWrapper.Error -> _notFound.postValue(response.error)
            }
        }
    }

    private suspend fun pokemonRequestSuccess(pokemon: PokemonPresentation) {
        _pokemon.postValue(pokemon)
        if (pokemon.types.secondType.isEmpty()) _setSecondTypeInvisible.postValue(true)
        getDescription(pokemon.id)
    }

    private suspend fun getDescription(id: Int) {
        when (val response = repository.getPokemonText(id.toString())) {
            is ResultWrapper.Success -> {
                when {
                    response.content.descriptions.isEmpty() -> _pokemonDescriptionEmpty.postValue(
                        Unit
                    )

                    else -> response.content.toPokemonTextPresentation().let {
                        _pokemonDescription.postValue(it.text)
                    }
                }
            }

            is ResultWrapper.Error -> Log.e("ERROR", response.error.toString())
        }
    }

    fun getAbility(name: String) {
        viewModelScope.launch {
            repositoryAbility.getAbility(name).toAbilityPresentation().let { ability ->
                _ability.postValue(ability)
            }
        }
    }
}