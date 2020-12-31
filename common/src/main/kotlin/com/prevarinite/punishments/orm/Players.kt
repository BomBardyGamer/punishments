package com.prevarinite.punishments.orm

import com.prevarinite.punishments.api.user.PunishUser
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object PlayersTable : IntIdTable("players") {

    val uuid = uuid("uuid")
}

class PlayerEntity(id: EntityID<Int>) : IntEntity(id), PunishUser {

    companion object : IntEntityClass<PlayerEntity>(PlayersTable)

    override var uuid by PlayersTable.uuid

    override val punishments by PunishmentEntity referrersOn PunishmentsTable.user
    override val history by HistoryEntity referrersOn HistoryTable.player
}