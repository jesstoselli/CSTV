package dev.jessto.desafiocstv.data.provider

import android.os.Build
import androidx.annotation.RequiresApi
import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.repository.MatchesRepositoryImpl
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.OpponentDTO
import dev.jessto.desafiocstv.utils.mappers.MatchDTOMapper
import dev.jessto.desafiocstv.utils.mappers.OpponentsDTOMapper
import dev.jessto.desafiocstv.utils.mappers.getYesterday
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

class MatchesProviderImpl(
    private val matchesRepositoryImpl: MatchesRepositoryImpl,
    private val matchDTOMapper: MatchDTOMapper,
    private val opponentsDTOMapper: OpponentsDTOMapper
) : MatchesProvider {

    //override suspend fun getMatchesList(number: Int): List<MatchDTO> {
    override suspend fun getMatchesList(): List<MatchDTO> {
        //val matchesList = matchesRepositoryImpl.getMatchesList(number)
        val matchesList = matchesRepositoryImpl.getMatchesList()

        if (matchesList.isEmpty()) {
            return emptyList()
        }

        val filteredList = filterList(matchesList)

        return filteredList.map { matchResponse -> matchDTOMapper.toDomain(matchResponse) }
    }

    override suspend fun getOpponentsList(matchId: String): List<OpponentDTO> {

        val opponentsList = matchesRepositoryImpl.getOpponentsList(matchId)

        return opponentsDTOMapper.mapList(opponentsList)

    }

    private fun filterList(matchesList: List<MatchResponse>): List<MatchResponse> {

        return matchesList.filterNot { match ->
            (match.opponentTeams.isEmpty() || match.opponentTeams.size < 2)
                    ||
            (match.opponentTeams.any { it.opponent.name.isNullOrEmpty()})
                    ||
            (match.status == "finished" || match.status == "cancelled" )
                    //it.beginAt!!.toInstant().atZone(ZoneId.systemDefault()).toEpochSecond() >today
        }
    }

    companion object {
        const val TAG = "MatchesProviderImpl"
    }

}
