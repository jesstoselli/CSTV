package dev.jessto.desafiocstv.ui.model

import java.util.*

data class MatchDTO(
    val id: Int,
    val leagueName: String,
    val leagueImg: String? = null,
    val series: String,
    val teams: List<TeamDTO>,
    val scheduledTime: Date
)

data class TeamDTO(
    val name: String,
    val badgeImg: String? = null
)

