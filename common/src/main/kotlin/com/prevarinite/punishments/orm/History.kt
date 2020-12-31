package com.prevarinite.punishments.orm

import com.prevarinite.punishments.api.user.UserHistory
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset

object HistoryTable : IntIdTable("history") {

    val player = reference("player_id", PlayersTable)
    val name = varchar("name", 16)
    val ip = varchar("ip", 39)
    val time = datetime("time").clientDefault { LocalDateTime.now(ZoneOffset.UTC) }
}

class HistoryEntity(id: EntityID<Int>) : IntEntity(id), UserHistory {

    companion object : IntEntityClass<HistoryEntity>(HistoryTable)

    var player by PlayerEntity referencedOn HistoryTable.player
    override var name by HistoryTable.name
    override var ip by HistoryTable.ip
    override var time by HistoryTable.time
}