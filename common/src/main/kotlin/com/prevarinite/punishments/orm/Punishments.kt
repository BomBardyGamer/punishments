package com.prevarinite.punishments.orm

import com.prevarinite.punishments.api.punishment.Punishment
import com.prevarinite.punishments.api.punishment.PunishmentType
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime
import java.time.ZoneOffset

object PunishmentsTable : IntIdTable("punishments") {

    val user = reference("player_id", PlayersTable)
    val executor = reference("executor_id", PlayersTable)
    val time = datetime("time").clientDefault { LocalDateTime.now(ZoneOffset.UTC) }
    val until = datetime("until").nullable()
    val reason = varchar("reason", 255)
    val type = enumeration("type", PunishmentType::class)
    val expired = bool("expired").default(false)
}

class PunishmentEntity(id: EntityID<Int>) : IntEntity(id), Punishment {

    companion object : IntEntityClass<PunishmentEntity>(PunishmentsTable)

    override var user by PlayerEntity referencedOn PunishmentsTable.user
    override var executor by PlayerEntity referencedOn PunishmentsTable.executor
    override var time by PunishmentsTable.time
    override var until by PunishmentsTable.until
    override var reason by PunishmentsTable.reason
    override var type by PunishmentsTable.type
    override var expired by PunishmentsTable.expired
}
