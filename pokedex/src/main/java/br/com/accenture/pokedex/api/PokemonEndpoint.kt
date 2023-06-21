package br.com.accenture.pokedex.api

import br.com.accenture.pokedex.network.responses.PokemonResponse
import br.com.accenture.pokedex.network.responses.PokemonTextResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonEndpoint {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id")id: String): PokemonResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonText(@Path("id") id: String): PokemonTextResponse
}