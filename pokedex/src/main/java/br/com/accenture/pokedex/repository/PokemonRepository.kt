package br.com.accenture.pokedex.repository

import br.com.accenture.pokedex.api.PokemonEndpoint

class PokemonRepository (private val pokemonEndpoint: PokemonEndpoint) {
    suspend fun getPokemon(id: Int) = pokemonEndpoint.getPokemon(id)
    suspend fun getPokemonText(id: Int) = pokemonEndpoint.getPokemonText(id)
}