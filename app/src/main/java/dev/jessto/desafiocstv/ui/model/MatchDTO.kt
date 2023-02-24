package dev.jessto.desafiocstv.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class MatchDTO(
    val id: Int,
    val leagueName: String,
    val leagueImg: String? = null,
    val series: String,
    val teams: List<TeamDTO>,
    val scheduledTime: Date
) : Parcelable

@Parcelize
data class TeamDTO(
    val name: String,
    val badgeImg: String? = null
): Parcelable

