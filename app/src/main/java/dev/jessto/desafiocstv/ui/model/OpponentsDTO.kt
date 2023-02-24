package dev.jessto.desafiocstv.ui.model

data class OpponentsDTO(
    val id: Int,
    val teamBadgeImg: String? = null,
    val teamName: String,
    val players: List<PlayerDTO>
)

data class PlayerDTO(
    val name: String,
    val nickname: String,
    val playerImg: String? = null
)
