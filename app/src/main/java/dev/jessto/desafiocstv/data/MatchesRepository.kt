package dev.jessto.desafiocstv.data

interface MatchesRepository {

    suspend fun getMatchesList()

    suspend fun getOpponentsList(matchId: String)
}
