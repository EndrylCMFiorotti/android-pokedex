package br.com.accenture.pokedex.api

import br.com.accenture.pokedex.network.responses.PokemonAbilityResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AbilityEndpoint {
    @GET("move/{name}")
    suspend fun getAbility(@Path("name")name: String) : PokemonAbilityResponse
}