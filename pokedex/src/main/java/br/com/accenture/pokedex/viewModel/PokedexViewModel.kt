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

    fun getPokemonListToPokedex(id: String) {
        viewModelScope.launch {
            repository.getPokedex(id).toPokedexPresentation().let {
                if(it.isEmpty()) {
                    _pokedexListEmpty.postValue(true)
                } else {
                    _pokedexList.postValue(it)
                }
            }
        }
    }
}