package br.com.accenture.pokedex.model.pokedex

import com.google.gson.annotations.SerializedName

class PokedexEntries(
    @SerializedName("entry_number")
    var id: Int,
    @SerializedName("pokemon_species")
    var pokedexSpecies: PokedexSpecies
)