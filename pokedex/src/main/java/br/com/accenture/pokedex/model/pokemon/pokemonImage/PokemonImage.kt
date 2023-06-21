package br.com.accenture.pokedex.model.pokemon.pokemonImage

import com.google.gson.annotations.SerializedName

data class PokemonImage(
    @SerializedName("other")
    var imgList: Image
)