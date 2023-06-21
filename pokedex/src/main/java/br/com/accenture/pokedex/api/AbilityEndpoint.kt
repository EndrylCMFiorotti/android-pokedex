package br.com.accenture.pokedex.api

import br.com.accenture.pokedex.model.pokemon.pokemonMove.pokemonAbility.PokemonAbilityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityEndpoint {
    @GET("move/{name}")
    suspend fun getAbility(@Path("name")name: String) : PokemonAbilityResponse
}