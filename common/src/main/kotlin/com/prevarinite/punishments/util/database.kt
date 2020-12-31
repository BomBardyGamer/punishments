package com.prevarinite.punishments.util

import com.prevarinite.punishments.PunishmentsKoinComponent
import com.prevarinite.punishments.api.punishment.PunishmentType
import com.prevarinite.punishments.orm.*
import org.jetbrains.exposed.sql.SortOrder
import java.time.LocalDateTime
import java.util.*

// This enables me to make all the below functions accessible at top-level
private object Hacks : PunishmentsKoinComponent

suspend fun UUID.toDatabasePlayer() = Hacks.transaction {
    PlayerEntity.find { PlayersTable.uuid eq this@toDatabasePlayer }.single()
}

suspend fun String.toDatabasePlayer() = Hacks.transaction {
    HistoryEntity.find { HistoryTable.name eq this@toDatabasePlayer }
        .orderBy(HistoryTable.time to SortOrder.DESC)
        .limit(1)
        .singleOrNull()
        ?.player
}

suspend fun createPlayer(uuid: UUID) = Hacks.transaction { PlayerEntity.new { this.uuid = uuid } }

suspend fun createHistory(player: PlayerEntity, name: String, ip: String) = Hacks.transaction {
    HistoryEntity.new {
        this.player = player
        this.name = name
        this.ip = ip
    }
}

suspend fun createPunishment(player: PlayerEntity, executor: PlayerEntity, time: LocalDateTime, until: LocalDateTime?, reason: String, type: PunishmentType) = Hacks.transaction {
    PunishmentEntity.new {
        this.user = player
        this.executor = executor
        this.time = time
        this.until = until
        this.reason = reason
        this.type = type
    }
}