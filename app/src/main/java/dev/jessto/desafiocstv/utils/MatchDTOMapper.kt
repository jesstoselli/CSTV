package dev.jessto.desafiocstv.utils

import dev.jessto.desafiocstv.data.MatchResponse
import dev.jessto.desafiocstv.data.OpponentTeams
import dev.jessto.desafiocstv.ui.MatchDTO
import dev.jessto.desafiocstv.ui.TeamDTO

class MatchDTOMapper(private val teamDTOMapper: TeamDTOMapper) : DataMapper<MatchResponse, MatchDTO>() {

    override fun toDomain(data: MatchResponse): MatchDTO = data.let {
        MatchDTO(
            id = it.id,
            leagueName = it.league.name,
            leagueImg = it.league.imageUrl,
            series = it.serie.name,
            teams = it.opponentTeams.map { team -> teamDTOMapper.toDomain(team) },
            scheduledTime = it.beginAt
        )
    }

}

class TeamDTOMapper : DataMapper<OpponentTeams, TeamDTO>() {
    override fun toDomain(data: OpponentTeams): TeamDTO = data.let {
        TeamDTO(
            name = it.name,
            badgeImg = it.opponentPic
        )
    }
}
