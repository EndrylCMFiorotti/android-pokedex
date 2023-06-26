package br.com.accenture.pokedex.repository

import br.com.accenture.pokedex.api.PokedexEndpoint

class PokedexRepository(private val endpoint: PokedexEndpoint) {
    suspend fun getPokedex(id: String) = endpoint.getPokedex(id)
}