package br.com.accenture.pokedex.viewModel

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

    private val _setSecondTypeInvisible = MutableLiveData<Boolean>()
    val setSecondTypeInvisible: LiveData<Boolean> = _setSecondTypeInvisible

    private val _ability = MutableLiveData<PokemonAbilityPresentation>()
    val ability: LiveData<PokemonAbilityPresentation> = _ability

    fun getPokemon(id: String) {
        viewModelScope.launch {
            when (val response = repository.getPokemon(id)) {
                is ResultWrapper.Success -> response.content.toPokemonPresentation().let { success(it) }
                is ResultWrapper.Error -> _notFound.postValue(response.error)
            }
        }
    }

    private suspend fun success(pokemon: PokemonPresentation) {
        _pokemon.postValue(pokemon)
        if (pokemon.types.secondType.isEmpty()) _setSecondTypeInvisible.postValue(true)
        repository.getPokemonText(pokemon.id.toString()).toPokemonTextPresentation()
            .let { pokemonDescription ->
                _pokemonDescription.postValue(pokemonDescription.text)
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