package dev.jessto.desafiocstv.utils.mappers

import dev.jessto.desafiocstv.data.networkmodel.OpponentsResponse
import dev.jessto.desafiocstv.data.networkmodel.PlayerResponse
import dev.jessto.desafiocstv.ui.model.OpponentDTO
import dev.jessto.desafiocstv.ui.model.PlayerDTO

class OpponentsDTOMapper(private val playersDTOMapper: PlayersDTOMapper) : DataMapper<OpponentsResponse, OpponentDTO>() {
    override fun toDomain(data: OpponentsResponse): OpponentDTO = data.let {
        OpponentDTO(
            id = it.id,
            teamBadgeImg = it.teamBadgeImg,
            teamName = it.teamName,
            players = it.players.map { playerResponse -> playersDTOMapper.toDomain(playerResponse) }
        )
    }
}

class PlayersDTOMapper : DataMapper<PlayerResponse, PlayerDTO>() {
    override fun toDomain(data: PlayerResponse): PlayerDTO = data.let {
        PlayerDTO(
            name = it.firstName + " " + it.lastName,
            nickname = it.name,
            playerImg = it.playerImg
        )
    }
}
