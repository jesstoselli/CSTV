package dev.jessto.desafiocstv.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class MatchResponse(
    @SerializedName("begin_at") val beginAt: Date,
    @SerializedName("games") val games: List<Game>,
    @SerializedName("id") val id: Int,
    @SerializedName("league") val league: League,
    @SerializedName("league_id") val leagueId: Int,
    @SerializedName("live") val live: Live,
    @SerializedName("opponents") val opponents: List<Opponent>,
    @SerializedName("serie") val serie: Serie,
)

data class Game(
    @SerializedName("id") val id: Int,
    @SerializedName("match_id") val matchId: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("status") val status: String
)

data class League(
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("name") val name: String
)

data class Live(
    @SerializedName("opens_at") val opensAt: Date? = null
)

data class Opponent(
    @SerializedName("image_url") val opponentPic: String? = null,
    @SerializedName("name") val name: String
)

data class Serie(
    @SerializedName("name") val name: String,
)
