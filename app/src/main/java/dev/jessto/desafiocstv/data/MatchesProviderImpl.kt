package dev.jessto.desafiocstv.data

import android.util.Log
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.utils.mappers.MatchDTOMapper
import dev.jessto.desafiocstv.utils.mappers.OpponentsDTOMapper

class MatchesProviderImpl(
    private val matchesRepositoryImpl: MatchesRepositoryImpl,
    private val matchDTOMapper: MatchDTOMapper,
    private val opponentsDTOMapper: OpponentsDTOMapper
) {

    suspend fun getMatchesList(): List<MatchDTO> {

        val matchesList = matchesRepositoryImpl.getMatchesList()

        if (matchesList.isEmpty()) {
            return emptyList()
        }

        val filteredList = filterList(matchesList)

        Log.i("MatchesProviderImpl", filteredList.toString())
        Log.i("MatchesProviderImpl", filteredList.size.toString())

        return filteredList.map { matchResponse -> matchDTOMapper.toDomain(matchResponse) }
    }

    private fun filterList(matchesList: List<MatchResponse>) : List<MatchResponse> {
        return matchesList.filterNot {
            it.opponentTeams.isEmpty() || it.opponentTeams.size < 2 || it
                .opponentTeams[0].opponent.name.isNullOrEmpty() || it
                .opponentTeams[1].opponent.name.isNullOrEmpty() || it.beginAt == null
        }

    }

}
