package br.com.accenture.pokedex.model.pokemon.pokemonMove

import com.google.gson.annotations.SerializedName

data class MoveName (
    @SerializedName("name")
    var moveName: String
)