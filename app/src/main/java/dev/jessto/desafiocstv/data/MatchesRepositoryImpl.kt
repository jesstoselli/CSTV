package dev.jessto.desafiocstv.data

import android.service.autofill.FieldClassification
import dev.jessto.desafiocstv.ui.MatchDTO
import dev.jessto.desafiocstv.utils.wrapEspressoIdlingResource
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepositoryImpl(
    private val cstvApiService: CSTVApiService
) : MatchesRepository {

    override suspend fun getMatchesList(): List<MatchDTO> {
        wrapEspressoIdlingResource {
            val call = cstvApiService.getMatches()

            call.enqueue(object : Callback<List<MatchResponse>>{
                override fun onResponse(call: Call<List<MatchResponse>>, response: Response<List<MatchResponse>>) {
                    if (response.code() == 200) {

                    }
                }

                override fun onFailure(call: Call<List<MatchResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

            return matchesList
        }
    }

    override suspend fun getMatchDetails(id: String): MatchDTO {
        wrapEspressoIdlingResource {
            return cstvApiService.getMatchById(id)
        }
    }


}
