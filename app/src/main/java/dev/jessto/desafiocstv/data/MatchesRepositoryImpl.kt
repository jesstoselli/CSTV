package dev.jessto.desafiocstv.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jessto.desafiocstv.data.model.MatchResponse
import dev.jessto.desafiocstv.ui.MatchDTO
import dev.jessto.desafiocstv.utils.MatchDTOMapper
import dev.jessto.desafiocstv.utils.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepositoryImpl(
    private val cstvApiService: CSTVApiService,
    private val matchDTOMapper: MatchDTOMapper
) : MatchesRepository {

    private val _matchesList = MutableLiveData<List<MatchDTO>>()
    val matchesList: LiveData<List<MatchDTO>>
        get() = _matchesList

    private val _responseError = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _responseError

    override suspend fun getMatchesList(): List<MatchDTO> {
        wrapEspressoIdlingResource {
            val call = cstvApiService.getMatches()

            call.enqueue(object : Callback<List<MatchResponse>> {
                override fun onResponse(call: Call<List<MatchResponse>>, response: Response<List<MatchResponse>>) {
                    if (response.code() == 200) {
                        val matchesResponse = response.body()!!
                        val convertedList = matchesResponse.map { matchResponse -> matchDTOMapper.toDomain(matchResponse) }

                        _matchesList.postValue(convertedList)
                    }
                }

                override fun onFailure(call: Call<List<MatchResponse>>, t: Throwable) {
                    _responseError.postValue(t.message)
                }

            })
        }
    }

    override suspend fun getMatchDetails(id: String): MatchDTO {
        wrapEspressoIdlingResource {
            val call = cstvApiService.getMatchById(id)

            call.enqueue(object : Callback<MatchResponse>)
        }
    }


}
