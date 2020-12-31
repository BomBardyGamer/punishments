package com.prevarinite.punishments.punishment

import com.prevarinite.punishments.PunishmentsKoinComponent
import com.prevarinite.punishments.api.punishment.Punishment
import com.prevarinite.punishments.api.punishment.PunishmentManager
import com.prevarinite.punishments.api.punishment.PunishmentType
import com.prevarinite.punishments.api.user.PunishUser
import com.prevarinite.punishments.orm.*
import java.time.LocalDateTime
import java.util.*

class PunishmentManagerImpl : PunishmentManager, PunishmentsKoinComponent {

    override val punishments: Collection<Punishment>
        get() = PunishmentEntity.all().toList()

    override suspend fun retrieve(id: Int) = transaction {
        PunishmentEntity.find { PunishmentsTable.id eq id }.singleOrNull()
    }

    override suspend fun retrieve(uuid: UUID) = transaction {
        PunishmentEntity.find { PlayersTable.uuid eq uuid }.toList()
    }

    override suspend fun retrieve(name: String) = transaction {
        PunishmentEntity.find { HistoryTable.name eq name }.toList()
    }

    override suspend fun create(
        user: PunishUser,
        executor: PunishUser,
        reason: String,
        type: PunishmentType,
        time: LocalDateTime,
        until: LocalDateTime?
    ): Punishment = PunishmentEntity.new {
        this.user = user as PlayerEntity
        this.executor = executor as PlayerEntity
        this.reason = reason
        this.type = type
        this.time = time
        this.until = until
    }

    override suspend fun update(punishment: Punishment) = transaction {
        PunishmentEntity.find { PunishmentsTable.id eq (punishment as PunishmentEntity).id }
            .singleOrNull()
            ?.applyValues(punishment)
    }

    override suspend fun delete(punishment: Punishment): Unit = transaction {
        PunishmentEntity.find { PunishmentsTable.id eq (punishment as PunishmentEntity).id }
            .singleOrNull()
            ?.delete()
    }

    private fun PunishmentEntity?.applyValues(punishment: Punishment): PunishmentEntity? {
        this?.user = punishment.user as PlayerEntity
        this?.executor = punishment.executor as PlayerEntity
        this?.time = punishment.time
        this?.until = punishment.until
        this?.reason = punishment.reason
        this?.type = punishment.type
        this?.expired = punishment.expired
        return this
    }
}