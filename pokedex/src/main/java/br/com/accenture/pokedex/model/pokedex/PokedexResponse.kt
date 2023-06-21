package br.com.accenture.pokedex.model.pokedex

import br.com.accenture.pokedex.model.presentation.PokedexPresentation
import com.google.gson.annotations.SerializedName

data class PokedexResponse(
    @SerializedName("pokemon_entries")
    var listPokemons: List<PokedexEntries>
) {
    fun toPokedexPresentation() : List<PokedexPresentation> = listPokemons.map {
        PokedexPresentation(
            id = it.id,
            name = it.pokedexSpecies.pokemonName
        )
    }
}