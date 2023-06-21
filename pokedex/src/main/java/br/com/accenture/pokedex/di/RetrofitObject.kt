package br.com.accenture.pokedex.di

import br.com.accenture.pokedex.network.interceptor.LoggingInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    inline fun <reified T> createNetworkService(): T {
        val log = LoggingInterceptor().getInterceptor()
        val client = OkHttpClient().newBuilder().addInterceptor(log)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(T::class.java)
    }
}