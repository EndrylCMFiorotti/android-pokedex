package br.com.accenture.pokedex.repository

import br.com.accenture.pokedex.api.PokemonEndpoint
import br.com.accenture.pokedex.network.handler.requestHandler
import br.com.accenture.pokedex.network.responses.PokemonResponse
import br.com.accenture.pokedex.network.responses.PokemonTextResponse
import br.com.accenture.pokedex.network.wrapper.ResultWrapper

class PokemonRepository (private val pokemonEndpoint: PokemonEndpoint) {
    suspend fun getPokemon(id: String) : ResultWrapper<PokemonResponse> {
        return requestHandler { pokemonEndpoint.getPokemon(id) }
    }
    suspend fun getPokemonText(id: String) : ResultWrapper<PokemonTextResponse> {
        return requestHandler { pokemonEndpoint.getPokemonText(id) }
    }
}