package br.com.accenture.pokedex.model.pokemon.pokemonMove.pokemonAbility

import com.google.gson.annotations.SerializedName

data class AbilityDamage(
    @SerializedName("name")
    var nameDamage: String
)