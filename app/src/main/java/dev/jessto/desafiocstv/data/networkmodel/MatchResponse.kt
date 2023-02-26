package dev.jessto.desafiocstv.data.networkmodel

import com.google.gson.annotations.SerializedName
import java.util.*

data class MatchResponse(
    @SerializedName("begin_at") val beginAt: Date?,
    @SerializedName("games") val games: List<Game>,
    @SerializedName("id") val id: Int,
    @SerializedName("league") val league: League,
    @SerializedName("league_id") val leagueId: Int,
    @SerializedName("opponents") val opponentTeams: List<OpponentTeams>,
    @SerializedName("serie") val series: Series,
    @SerializedName("status") val status: String
)

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("match_id") val matchId: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("status") val status: String
)

data class League(
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("name") val name: String
)

data class OpponentTeams(
    @SerializedName("opponent") val opponent: Opponent,
)

data class Opponent(
    @SerializedName("image_url") val opponentImg: String?,
    @SerializedName("name") val name: String?
)

data class Series(
    @SerializedName("name") val name: String,
)
