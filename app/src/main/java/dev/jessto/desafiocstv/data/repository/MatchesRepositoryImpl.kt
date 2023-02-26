package dev.jessto.desafiocstv.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.jessto.desafiocstv.data.CSTVApi
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.networkmodel.OpponentsResponse

class MatchesRepositoryImpl : MatchesRepository {

    private val cstvApiService = CSTVApi.cstvApiService

    private val _responseError = MutableLiveData<String>()
    val responseError: LiveData<String>
        get() = _responseError

    override suspend fun getMatchesList(number: Int): List<MatchResponse> {
        val response = cstvApiService.getMatches(20, number)

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

    override suspend fun getOpponentsList(matchId: String): List<OpponentsResponse> {

        val response = cstvApiService.getOpponentsListByMatchId(matchId)

        val responseBody = response.body()!!

        try {
            return if (response.code() == 200 && response.body() != null) {
                responseBody.opponents
            } else {
                _responseError.value = response.message()

                emptyList()
            }

        } catch (exception: Exception) {
            _responseError.value = exception.message

            return emptyList()
        }

    }

    //
    companion object {
        const val TAG = "MatchesRepositoryImpl"
    }
}
