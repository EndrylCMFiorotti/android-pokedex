package br.com.accenture.pokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.accenture.pokedex.model.presentation.PokemonAbilityPresentation
import br.com.accenture.pokedex.model.presentation.PokemonPresentation
import br.com.accenture.pokedex.repository.AbilityRepository
import br.com.accenture.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository, private val repositoryAbility: AbilityRepository) : ViewModel() {
    private val _pokemon = MutableLiveData<PokemonPresentation>()
    val pokemon: LiveData<PokemonPresentation> = _pokemon

    private val _pokemonDescription = MutableLiveData<String>()
    val pokemonDescription: LiveData<String> = _pokemonDescription

    private val _setSecondTypeInvisible = MutableLiveData<Boolean>()
    val setSecondTypeInvisible: LiveData<Boolean> = _setSecondTypeInvisible

    private val _ability = MutableLiveData<PokemonAbilityPresentation>()
    val ability: LiveData<PokemonAbilityPresentation> = _ability

    fun getPokemon(id: Int) {
        viewModelScope.launch {
            repository.getPokemon(id).toPokemonPresentation().let { pokemon ->
                _pokemon.postValue(pokemon)
                if (pokemon.types.secondType.isEmpty()) _setSecondTypeInvisible.postValue(true)
                repository.getPokemonText(id).toPokemonTextPresentation().let { pokemonDescription ->
                    _pokemonDescription.postValue(pokemonDescription.text)
                }
            }
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