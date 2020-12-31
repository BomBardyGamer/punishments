package com.prevarinite.punishments.api.punishment

import com.prevarinite.punishments.api.user.PunishUser
import java.time.LocalDateTime
import java.util.*

interface PunishmentManager {

    val punishments: Collection<Punishment>

    suspend fun retrieve(id: Int): Punishment?

    suspend fun retrieve(uuid: UUID): List<Punishment>

    suspend fun retrieve(name: String): List<Punishment>

    suspend fun create(
        user: PunishUser,
        executor: PunishUser,
        reason: String,
        type: PunishmentType,
        time: LocalDateTime = LocalDateTime.now(),
        until: LocalDateTime? = null
    ): Punishment

    suspend fun update(punishment: Punishment): Punishment?

    suspend fun delete(punishment: Punishment)
}