package com.prevarinite.punishments.api.punishment

import java.util.*

interface PunishmentManager {

    suspend fun retrieve(id: Int): Punishment?

    suspend fun retrieve(uuid: UUID): List<Punishment>

    suspend fun retrieve(name: String): List<Punishment>

    suspend fun create(punishment: Punishment): Punishment

    suspend fun update(punishment: Punishment): Punishment?

    suspend fun delete(punishment: Punishment)

    suspend fun createOrUpdate(punishment: Punishment): Punishment
}