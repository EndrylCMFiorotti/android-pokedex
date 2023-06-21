package br.com.accenture.pokedex.model.pokemon.pokemonMove

import com.google.gson.annotations.SerializedName

data class PokemonMove(
    @SerializedName("move")
    var move: MoveName
)