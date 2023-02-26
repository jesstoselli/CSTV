package dev.jessto.desafiocstv.data.provider

import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.OpponentDTO

interface MatchesProvider {

    suspend fun getMatchesList(number: Int): List<MatchDTO>

    suspend fun getOpponentsList(matchId: String): List<OpponentDTO>
}
