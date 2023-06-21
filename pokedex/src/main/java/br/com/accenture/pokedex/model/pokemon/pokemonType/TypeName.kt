package br.com.accenture.pokedex.model.pokemon.pokemonType

import com.google.gson.annotations.SerializedName

data class TypeName(
    @SerializedName("name")
    var name: String
)