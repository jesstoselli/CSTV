package dev.jessto.desafiocstv.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.networkmodel.OpponentsResponse
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.OpponentsDTO
import dev.jessto.desafiocstv.utils.mappers.MatchDTOMapper
import dev.jessto.desafiocstv.utils.mappers.OpponentsDTOMapper
import dev.jessto.desafiocstv.utils.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchesRepositoryImpl(
    private val cstvApiService: CSTVApiService,
    private val matchDTOMapper: MatchDTOMapper,
    private val opponentsDTOMapper: OpponentsDTOMapper
) : MatchesRepository {

    private val _matchesList = MutableLiveData<List<MatchDTO>>()
    val matchesList: LiveData<List<MatchDTO>>
        get() = _matchesList

    private val _opponentsList = MutableLiveData<List<OpponentsDTO>>()
    val opponentsList: LiveData<List<OpponentsDTO>>
        get() = _opponentsList

    private val _responseError = MutableLiveData<String>()
    val errorResponse: LiveData<String>
        get() = _responseError

    override suspend fun getMatchesList() {
        wrapEspressoIdlingResource {
            val call = cstvApiService.getMatches()

            call.enqueue(object : Callback<List<MatchResponse>> {
                override fun onResponse(call: Call<List<MatchResponse>>, response: Response<List<MatchResponse>>) {
                    if (response.code() == 200) {
                        val matchesResponse = response.body()!!
                        val convertedList = matchDTOMapper.mapList(matchesResponse)

                        _matchesList.postValue(convertedList)
                    }
                }

                override fun onFailure(call: Call<List<MatchResponse>>, t: Throwable) {
                    _responseError.postValue(t.message)
                }

            })
        }
    }

    override suspend fun getOpponentsList(matchId: String) {
        wrapEspressoIdlingResource {
            val call = cstvApiService.getMatchById(matchId)

            call.enqueue(object : Callback<List<OpponentsResponse>> {
                override fun onResponse(call: Call<List<OpponentsResponse>>, response: Response<List<OpponentsResponse>>) {
                    if (response.code() == 200) {
                        val opponentsResponse = response.body()!!
                        val convertedList = opponentsDTOMapper.mapList(opponentsResponse)

                        _opponentsList.postValue(convertedList)
                    }
                }

                override fun onFailure(call: Call<List<OpponentsResponse>>, t: Throwable) {
                    _responseError.postValue(t.message)
                }
            })
        }
    }

}
