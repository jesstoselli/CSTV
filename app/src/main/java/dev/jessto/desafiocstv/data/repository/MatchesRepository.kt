package dev.jessto.desafiocstv.data.repository

import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.networkmodel.OpponentsResponse

interface MatchesRepository {

    suspend fun getMatchesList(number: Int): List<MatchResponse>

    suspend fun getOpponentsList(matchId: String): List<OpponentsResponse>
}
