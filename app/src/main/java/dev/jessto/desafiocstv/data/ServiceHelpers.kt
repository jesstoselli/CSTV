package dev.jessto.desafiocstv.data

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceHelpers {

    private const val TOKEN = "m8xdnepKMbCKN-_jdpXc9Pnv9q1Fw4beICJJi6FTjbvBgMLSC2Q"

    class OAuthInterceptor(private val tokenType: String, private val acceessToken: String) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

            return chain.proceed(request)
        }
    }

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor("Bearer", TOKEN))
            .build()
    }

    inline fun <reified T> createService(
        client: OkHttpClient,
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(T::class.java)
    }

}
