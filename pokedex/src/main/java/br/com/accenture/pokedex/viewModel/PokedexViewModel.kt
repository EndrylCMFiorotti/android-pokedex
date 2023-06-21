package br.com.accenture.pokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.accenture.pokedex.model.presentation.PokedexPresentation
import br.com.accenture.pokedex.repository.PokedexRepository
import kotlinx.coroutines.launch

class PokedexViewModel(private val repository: PokedexRepository) : ViewModel() {
    private var _pokedexList = MutableLiveData<List<PokedexPresentation>>()
    val pokedexList: LiveData<List<PokedexPresentation>> = _pokedexList

    private var _pokedexListEmpty = MutableLiveData<Boolean>()
    val pokedexListEmpty: LiveData<Boolean> = _pokedexListEmpty

    fun getPokemonListToPokedex() {
        viewModelScope.launch {
            repository.getPokedex(REGION).toPokedexPresentation().let {
                if(it.isEmpty()) {
                    _pokedexListEmpty.postValue(true)
                } else {
                    _pokedexList.postValue(it)
                }
            }
        }
    }

    companion object {
        const val REGION = 1
    }
}