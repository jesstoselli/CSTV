package dev.jessto.desafiocstv.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CSTVApiService {

    @GET("matches")
    suspend fun getMatches(): Call<List<MatchResponse>>

    @GET("matches/{id}")
    suspend fun getMatchById(@Path("id") id: String): Call<MatchResponse>

}

//private const val BASE_URL = "https://api.pandascore.co/"
//private const val TOKEN = "m8xdnepKMbCKN-_jdpXc9Pnv9q1Fw4beICJJi6FTjbvBgMLSC2Q"
//
//class OAuthInterceptor(private val tokenType: String, private val acceessToken: String) : Interceptor {
//
//    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
//        var request = chain.request()
//        request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()
//
//        return chain.proceed(request)
//    }
//}
//
//val client = OkHttpClient.Builder()
//    .addInterceptor(OAuthInterceptor("Bearer", TOKEN))
//    .build()

//object CSTVApi {
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(client)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val cstvApiService: CSTVApiService by lazy {
//        retrofit.create(CSTVApiService::class.java)
//    }
//}
