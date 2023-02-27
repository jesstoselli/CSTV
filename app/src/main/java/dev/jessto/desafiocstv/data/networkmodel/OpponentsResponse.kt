package dev.jessto.desafiocstv.data.networkmodel

import com.google.gson.annotations.SerializedName

data class OpponentsData(
    @SerializedName("opponents") val opponents: List<OpponentsResponse>
)

data class OpponentsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val teamBadgeImg: String,
    @SerializedName("name") val teamName: String,
    @SerializedName("players") val players: List<PlayerResponse>
)

data class PlayerResponse(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("image_url") val playerImg: String?,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("name") val name: String,
)
