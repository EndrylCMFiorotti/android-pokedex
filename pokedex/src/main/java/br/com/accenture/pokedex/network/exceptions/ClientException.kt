package br.com.accenture.pokedex.network.exceptions

data class ClientException(
    override val message: String?,
    val code: Int
) : Exception(message)