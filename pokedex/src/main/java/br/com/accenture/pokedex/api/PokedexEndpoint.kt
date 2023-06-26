package br.com.accenture.pokedex.api

import br.com.accenture.pokedex.network.responses.PokedexResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexEndpoint {
    @GET("pokedex/{id}")
    suspend fun getPokedex(@Path("id")id: String): PokedexResponse
}