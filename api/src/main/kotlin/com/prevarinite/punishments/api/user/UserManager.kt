package com.prevarinite.punishments.api.user

import java.util.*

interface UserManager {

    val users: Collection<PunishUser>

    suspend fun retrieve(uuid: UUID): PunishUser

    suspend fun retrieve(name: String): PunishUser?
}