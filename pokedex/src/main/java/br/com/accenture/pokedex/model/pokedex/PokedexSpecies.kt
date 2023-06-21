package br.com.accenture.pokedex.model.pokedex

import com.google.gson.annotations.SerializedName

data class PokedexSpecies(
    @SerializedName("name")
    var pokemonName: String,
    @SerializedName("url")
    var pokemonUrl: String
)