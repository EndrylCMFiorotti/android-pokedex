package br.com.accenture.pokedex.model.pokemon.pokemonMove.pokemonAbility

import com.google.gson.annotations.SerializedName

data class AbilityType(
    @SerializedName("name")
    var nameType: String
)