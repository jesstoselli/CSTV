package dev.jessto.desafiocstv.data

import dev.jessto.desafiocstv.data.ServiceHelpers.createOkHttpClient
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.networkmodel.OpponentsData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CSTVApiService {

    @GET("matches")
    suspend fun getMatches(
        @Query("sort") sort: String,
        @Query("range[begin_at]") range: String,
        @Query("page[size]") size: Int,
        @Query("filter[videogame]") videogame: String,
    ):
            Response<MutableList<MatchResponse>>

    @GET("matches/{id}/opponents")
    suspend fun getOpponentsListByMatchId(@Path("id") id: String): Response<OpponentsData>

}

object CSTVApi {

    private const val BASE_URL = "https://api.pandascore.co/"

    private val okHttpClient = createOkHttpClient()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val cstvApiService: CSTVApiService by lazy {
        retrofit.create(CSTVApiService::class.java)
    }
}
