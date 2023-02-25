package dev.jessto.desafiocstv.utils.mappers

import dev.jessto.desafiocstv.data.networkmodel.MatchResponse
import dev.jessto.desafiocstv.data.networkmodel.OpponentTeams
import dev.jessto.desafiocstv.ui.model.MatchDTO
import dev.jessto.desafiocstv.ui.model.TeamDTO

class MatchDTOMapper(private val teamDTOMapper: TeamDTOMapper) : DataMapper<MatchResponse, MatchDTO>() {

    override fun toDomain(data: MatchResponse): MatchDTO = data.let {
        MatchDTO(
            id = it.id,
            leagueName = it.league.name,
            leagueImg = it.league.imageUrl,
            series = it.series.name,
            teams = it.opponentTeams.map { team -> teamDTOMapper.toDomain(team) },
            scheduledTime = it.beginAt
        )
    }
}

class TeamDTOMapper : DataMapper<OpponentTeams, TeamDTO>() {
    override fun toDomain(data: OpponentTeams): TeamDTO = data.let {
        TeamDTO(
            name = it.opponent.name,
            badgeImg = it.opponent.opponentImg
        )
    }
}
