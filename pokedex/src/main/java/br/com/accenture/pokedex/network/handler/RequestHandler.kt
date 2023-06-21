package br.com.accenture.pokedex.network.handler

import br.com.accenture.pokedex.network.exceptions.ClientException
import br.com.accenture.pokedex.network.wrapper.ResultWrapper
import retrofit2.HttpException

suspend fun <T> requestHandler(function: suspend () -> T): ResultWrapper<T> {
    return try {
        ResultWrapper.Success(function())
    } catch (e: HttpException) {
        ResultWrapper.Error(
            ClientException(code = e.code(), message = e.message)
        )
    }
}
