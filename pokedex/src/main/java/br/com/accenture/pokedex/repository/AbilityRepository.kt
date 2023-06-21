package br.com.accenture.pokedex.repository

import br.com.accenture.pokedex.api.AbilityEndpoint

class AbilityRepository(private val endpoint: AbilityEndpoint) {
    suspend fun getAbility(name: String) = endpoint.getAbility(name)
}