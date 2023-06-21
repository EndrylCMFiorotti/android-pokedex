package br.com.accenture.pokedex.model.pokemon.pokemonFlavorText

import com.google.gson.annotations.SerializedName

data class PokemonText(
    @SerializedName("flavor_text")
    var text: String
)