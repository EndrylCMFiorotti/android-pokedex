package br.com.accenture.pokedex.network.responses

import br.com.accenture.pokedex.model.pokemon.pokemonFlavorText.PokemonText
import br.com.accenture.pokedex.model.presentation.PokemonTextPresentation
import com.google.gson.annotations.SerializedName

data class PokemonTextResponse(
    @SerializedName("flavor_text_entries")
    var descriptions: List<PokemonText>
) {
    fun toPokemonTextPresentation(): PokemonTextPresentation = PokemonTextPresentation(
        text = descriptions.first().text
    )
}