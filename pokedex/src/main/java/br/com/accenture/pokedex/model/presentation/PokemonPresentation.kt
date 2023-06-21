package br.com.accenture.pokedex.model.presentation

data class PokemonPresentation(
    val id: Int,
    val name: String,
    val height: String,
    val weight: String,
    val image: String,
    val types: PokemonTypesPresentation,
    val moves: List<String>
)