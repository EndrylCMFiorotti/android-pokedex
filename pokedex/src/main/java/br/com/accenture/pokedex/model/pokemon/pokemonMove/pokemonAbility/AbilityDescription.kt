package br.com.accenture.pokedex.model.pokemon.pokemonMove.pokemonAbility

import com.google.gson.annotations.SerializedName

data class AbilityDescription(
    @SerializedName("flavor_text")
    var flavorText: String
)