package br.com.accenture.pokedex.model.pokemon.pokemonImage

import com.google.gson.annotations.SerializedName

data class ImageUrl(
    @SerializedName("front_default")
    var urlImg: String
)