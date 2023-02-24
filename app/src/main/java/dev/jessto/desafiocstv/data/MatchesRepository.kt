package dev.jessto.desafiocstv.data

import dev.jessto.desafiocstv.ui.MatchDTO

interface MatchesRepository {

    suspend fun getMatchesList(): List<MatchDTO>

    suspend fun getMatchDetails(id: String): MatchDTO
}
