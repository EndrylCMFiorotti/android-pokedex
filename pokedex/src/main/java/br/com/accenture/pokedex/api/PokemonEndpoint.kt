package br.com.accenture.pokedex.api

import br.com.accenture.pokedex.model.pokemon.PokemonResponse
import br.com.accenture.pokedex.model.pokemon.pokemonFlavorText.PokemonTextResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonEndpoint {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id")id: Int): PokemonResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonText(@Path("id") id: Int): PokemonTextResponse
}