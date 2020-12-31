package com.prevarinite.punishments.api.user

import com.prevarinite.punishments.api.punishment.Punishment
import java.util.*

interface PunishUser {

    val uuid: UUID

    val punishments: Iterable<Punishment>

    val history: Iterable<UserHistory>
}