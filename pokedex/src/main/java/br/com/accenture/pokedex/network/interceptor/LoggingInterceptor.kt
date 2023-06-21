package br.com.accenture.pokedex.network.interceptor

import br.com.accenture.pokedex.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor {
    fun getInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }
    }
}