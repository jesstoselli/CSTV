package dev.jessto.desafiocstv.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse

class MatchesRepositoryImpl {

    private val cstvApiService = CSTVApi.cstvApiService

    private val _responseError = MutableLiveData<String>()
    val responseError: LiveData<String>
        get() = _responseError

    suspend fun getMatchesList(): List<MatchResponse> {
        val response = cstvApiService.getMatches()

        val responseBody = response.body()!!

        try {
            return if (response.code() == 200 && response.body() != null) {
                responseBody
            } else {
                _responseError.value = response.message()

                emptyList()
            }

        } catch (exception: Exception) {
            _responseError.value = exception.message

            return emptyList()
        }
    }


//    Log.i(TAG, response.body().toString())

    //    suspend fun getOpponentsList(matchId: String): List<OpponentDTO> {
//        var opponentsDTOList: List<OpponentDTO> = listOf()
//
//        try {
//            val response = cstvApiService.getOpponentsListByMatchId(matchId)
//            val opponentsListFromApi = response.body()
//
//            if (opponentsListFromApi != null) {
//                opponentsDTOList = opponentsDTOMapper.mapList(opponentsListFromApi)
//            }
//
//        } catch (exception: Exception) {
//            _responseError.postValue(exception.message)
//            return emptyList()
//        }
//
//        return opponentsDTOList
//    }
//
    companion object {
        const val TAG = "MatchesRepositoryImpl"
    }

}

//return if (response.isSuccessful) {
//    val responseBody = response.body()
//    if (responseBody != null) {
//        NetworkState.Success(responseBody)
//    } else {
//        NetworkState.Error(response)
//    }
//} else {
//    NetworkState.Error(response)
//}


//override suspend fun getMatchesList() {
//    wrapEspressoIdlingResource {
//        val call = cstvApiService.getMatches()
//
//        call.enqueue(object : Callback<MutableList<MatchResponse>> {
//            override fun onResponse(call: Call<MutableList<MatchResponse>>, response: Response<MutableList<MatchResponse>>) {
//                if (response.code() == 200) {
//                    val matchesResponse = response.body()!!
//
//                    matchesResponse.removeIf { it.opponentTeams.isEmpty() }
//
//                    val convertedList = matchDTOMapper.mapList(matchesResponse)
//
//                    _matchesList.postValue(convertedList)
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<MatchResponse>>, t: Throwable) {
//                _responseError.postValue(t.message)
//            }
//
//        })
//    }
//}
//
//override suspend fun getOpponentsList(matchId: String) {
//    wrapEspressoIdlingResource {
//        val call = cstvApiService.getMatchById(matchId)
//
//        call.enqueue(object : Callback<List<OpponentsResponse>> {
//            override fun onResponse(call: Call<List<OpponentsResponse>>, response: Response<List<OpponentsResponse>>) {
//                if (response.code() == 200) {
//                    val opponentsResponse = response.body()!!
//                    val convertedList = opponentsDTOMapper.mapList(opponentsResponse)
//
//                    _opponentsList.postValue(convertedList)
//                }
//            }
//
//            override fun onFailure(call: Call<List<OpponentsResponse>>, t: Throwable) {
//                _responseError.postValue(t.message)
//            }
//        })
//    }
//}
