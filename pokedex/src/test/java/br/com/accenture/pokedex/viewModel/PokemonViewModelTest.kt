package br.com.accenture.pokedex.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.accenture.pokedex.api.PokemonEndpoint
import br.com.accenture.pokedex.network.responses.PokemonResponse
import br.com.accenture.pokedex.model.pokemon.pokemonImage.Image
import br.com.accenture.pokedex.model.pokemon.pokemonImage.ImageUrl
import br.com.accenture.pokedex.model.pokemon.pokemonImage.PokemonImage
import br.com.accenture.pokedex.model.pokemon.pokemonMove.MoveName
import br.com.accenture.pokedex.model.pokemon.pokemonMove.PokemonMove
import br.com.accenture.pokedex.model.pokemon.pokemonType.PokemonType
import br.com.accenture.pokedex.model.pokemon.pokemonType.TypeName
import br.com.accenture.pokedex.model.presentation.PokemonPresentation
import br.com.accenture.pokedex.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

class PokemonViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var endpoint: PokemonEndpoint
    private lateinit var repository: PokemonRepository
    private lateinit var viewModel: PokemonViewModel
    private lateinit var observer: Observer<PokemonPresentation>
    private lateinit var observerVisibility: Observer<Boolean>
    private lateinit var dispatcher: CoroutineDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        endpoint = mockk()
        repository = PokemonRepository(endpoint)
        viewModel = PokemonViewModel(repository)
        observer = mockk(relaxed = true)
        observerVisibility = mockk(relaxed = true)
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
    fun `WHEN a pokemon is chosen SHOULD return the information of that pokemon`() = runTest {
        viewModel.pokemon.observeForever(observer)

        coEvery { repository.getPokemon(1) } returns pokemonMock

        viewModel.getPokemon(1)

        coVerify { observer.onChanged(pokemonMock.toPokemonPresentation()) }
    }

    @Test
    fun `WHEN the pokemon has only one type SHOULD set field to invisible`() {
        viewModel.setSecondTypeInvisible.observeForever(observerVisibility)

        coEvery { repository.getPokemon(1) } returns pokemonMock

        viewModel.getPokemon(1)

        coVerify { observerVisibility.onChanged(true) }
    }

    companion object {
        private val pokemonMock = PokemonResponse(
            id = 1,
            name = "bulbasaur",
            height = "7",
            weight = "69",
            typeList = arrayListOf(
                PokemonType(
                    slot = 1,
                    type = TypeName(
                        name = "grass"
                    )
                )
            ),
            image = PokemonImage(
                imgList = Image(
                    type = ImageUrl(
                        urlImg = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
                    )
                )
            ),
            movesList = arrayListOf(
                PokemonMove(
                    move = MoveName(
                        moveName = "razor-wind"
                    )
                )
            )
        )
    }
}