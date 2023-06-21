package br.com.accenture.pokedex.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.accenture.pokedex.api.PokedexEndpoint
import br.com.accenture.pokedex.model.pokedex.PokedexEntries
import br.com.accenture.pokedex.model.pokedex.PokedexResponse
import br.com.accenture.pokedex.model.pokedex.PokedexSpecies
import br.com.accenture.pokedex.model.presentation.PokedexPresentation
import br.com.accenture.pokedex.repository.PokedexRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokedexViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockPokedexEndpoint: PokedexEndpoint
    private lateinit var repository: PokedexRepository
    private lateinit var viewModel: PokedexViewModel
    private lateinit var observerPokedexList: Observer<List<PokedexPresentation>>
    private lateinit var observerPokedexEmptyList: Observer<Boolean>
    private lateinit var dispatcher: CoroutineDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockPokedexEndpoint = mockk()
        repository = PokedexRepository(mockPokedexEndpoint)
        viewModel = PokedexViewModel(repository)
        observerPokedexList = mockk(relaxed = true)
        observerPokedexEmptyList = mockk(relaxed = true)
        dispatcher = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `WHEN requests the pokemons of a region SHOULD return a list of pokemons`() = runTest {
        viewModel.pokedexList.observeForever(observerPokedexList)

        coEvery { repository.getPokedex(PokedexViewModel.REGION) } returns pokedex // Use Any()

        viewModel.getPokemonListToPokedex()

        coVerify { observerPokedexList.onChanged(pokedex.toPokedexPresentation()) }
    }

    @Test
    fun `WHEN requests the pokemons of a region SHOULD return an empty list of pokemons`() {
        viewModel.pokedexListEmpty.observeForever(observerPokedexEmptyList)

        coEvery { repository.getPokedex(PokedexViewModel.REGION) } returns emptyPokedex // Use Any()

        viewModel.getPokemonListToPokedex()

        coVerify { observerPokedexEmptyList.onChanged(true) }
    }

    companion object {
        val pokedex =  PokedexResponse(
            listPokemons = arrayListOf(
                PokedexEntries(
                    id = 1,
                    pokedexSpecies = PokedexSpecies(
                        pokemonName = "bulbasaur",
                        pokemonUrl = "https://pokeapi.co/api/v2/pokemon-species/1/"
                    )
                )
            )
        )

        val emptyPokedex = PokedexResponse(listPokemons = arrayListOf())
    }
}