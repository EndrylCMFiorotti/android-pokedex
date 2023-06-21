package br.com.accenture.pokedex.api

import br.com.accenture.pokedex.model.pokedex.PokedexResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokedexEndpoint {
    @GET("pokedex/{id}")
    suspend fun getPokedex(@Path("id")id: Int): PokedexResponse
}