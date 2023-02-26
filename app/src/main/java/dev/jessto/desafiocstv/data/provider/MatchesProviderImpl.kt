package dev.jessto.desafiocstv.data.provider

import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.repository.MatchesRepositoryImpl
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.OpponentDTO
import dev.jessto.desafiocstv.utils.mappers.MatchDTOMapper
import dev.jessto.desafiocstv.utils.mappers.OpponentsDTOMapper
import dev.jessto.desafiocstv.utils.mappers.getYesterday
import java.util.*

class MatchesProviderImpl(
    private val matchesRepositoryImpl: MatchesRepositoryImpl,
    private val matchDTOMapper: MatchDTOMapper,
    private val opponentsDTOMapper: OpponentsDTOMapper
) : MatchesProvider {

    override suspend fun getMatchesList(): List<MatchDTO> {

        val matchesList = matchesRepositoryImpl.getMatchesList()

        if (matchesList.isEmpty()) {
            return emptyList()
        }

        val filteredList = filterList(matchesList)

        val liveMatchesList = filteredList.filter { it.live.opensAt != null }

        val listSortedByLiveAndDate = filteredList.sortedWith(
            compareBy<MatchResponse> { it.live.opensAt }
                .thenBy { it.beginAt }
        )

        val finalList = liveMatchesList + listSortedByLiveAndDate

        return finalList.map { matchResponse -> matchDTOMapper.toDomain(matchResponse) }
    }

    override suspend fun getOpponentsList(matchId: String): List<OpponentDTO> {

        val opponentsList = matchesRepositoryImpl.getOpponentsList(matchId)

        return opponentsDTOMapper.mapList(opponentsList)

    }

    private fun filterList(matchesList: List<MatchResponse>): List<MatchResponse> {
        val today = getYesterday()

        return matchesList.filterNot {
            it.opponentTeams.isEmpty() || it.opponentTeams.size < 2 || it
                .opponentTeams[0].opponent.name.isNullOrEmpty() || it
                .opponentTeams[1].opponent.name.isNullOrEmpty() || it.beginAt == null || it.beginAt < today
        }
    }

    companion object {
        const val TAG = "MatchesProviderImpl"
    }

}
